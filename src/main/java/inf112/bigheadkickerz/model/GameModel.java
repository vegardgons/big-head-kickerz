package inf112.bigheadkickerz.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import inf112.bigheadkickerz.controller.ControllableGameModel;
import inf112.bigheadkickerz.controller.GameController;
import inf112.bigheadkickerz.model.powerups.Powerup;
import inf112.bigheadkickerz.model.powerups.PowerupFactory;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;
import inf112.bigheadkickerz.view.Assets;
import inf112.bigheadkickerz.view.ScoreBoard;
import inf112.bigheadkickerz.model.powerups.PowerupManager;

/**
 * GameModel holds all game state and implements ControllableGameModel
 * to allow the controller to interact with it.
 */
public class GameModel implements ControllableGameModel {
    // Game dimensions
    private static final float WIDTH = 15;
    private static final float HEIGHT = 8;


    // Powerup variables
    private PowerupPickup currentPowerup;
    private float powerupSpawnTimer = 0f;
    private float nextSpawnDelay = 2f;

    // Game objects
    private BigHeadKickerzGame game;
    private Player player1;
    private Player player2;
    private Ball ball;
    private Goal leftGoal;
    private Goal rightGoal;
    private ScoreBoard scoreBoard;
    private ArrayList<Collideable> collideables;
    private Collision collisionHandler;

    // Score tracking
    private int player1Score;
    private int player2Score;
    private float goalTimer;
    private static final float GOAL_DELAY = 2f;
    private boolean isGoal;

    private boolean showControls = true; // Always start by showing controls

    // End screen
    private float endTimer;
    private static final float END_DELAY = 2f;
    private boolean isEnd;

    // Viewport for game boundaries
    private FitViewport viewport;

    private GameState gameState;
    private float gameTime; // Total time in timed mode (seconds)
    private static final float DEFAULT_GAME_TIME = 60f; // e.g., 60 seconds
    private int goalThreshold; // For first-to-seven mode, threshold = 7

    /**
     * Constructor initializes game objects and viewport
     */
    public GameModel(BigHeadKickerzGame game, GameController controller, GameState gameState) {
        this.game = game;
        this.gameState = gameState;
        this.viewport = new FitViewport(WIDTH, HEIGHT);

        initGameObjects();
        initCollideables();
        initScoreTracking();
        initEndScreenTimer();

        if (gameState == GameState.TIMED) {
            gameTime = DEFAULT_GAME_TIME;
        } else if (gameState == GameState.FIRST_TO_SEVEN) {
            goalThreshold = 7;
        }
    }

    /** Update game state */
    public void update(float delta) {
        if (showControls) {
            return;
        }

        if (isGoal) {
        }
        if (gameState == GameState.TIMED) {
            gameTime -= delta;
            if (gameTime <= 0) {
                isEnd = true;
                System.out.println("Game over!" + "\nP1: " + player1Score + "\nP2: " + player2Score + "\n");
            }
        } else if (gameState == GameState.FIRST_TO_SEVEN) {
            if (player1Score >= goalThreshold || player2Score >= goalThreshold) {
                isEnd = true;
                System.out.println("Game over!" + "\nP1: " + player1Score + "\nP2: " + player2Score + "\n");
            }
        }

        // Normal goal and end-screen update
        if (!isEnd) {
            if (!isGoal) {
                checkForGoal();
            } else {
                goalTimer += delta;
                if (goalTimer >= GOAL_DELAY) {
                    isGoal = false;
                    goalTimer = 0;
                    resetPositions();
                }
            }
        } else {
            gameTime = 0;
            endTimer += delta;
            if (endTimer >= END_DELAY) {
                game.endScreen();
                endTimer = 0;
            }
        }

        // Update game objects and collisions
        player2.update(viewport, delta);
        player1.update(viewport, delta);
        ball.update(viewport, delta);
        collisionHandler.checkCollision();

        // Update powerup spawning (only one active at a time)
        updatePowerupSpawning(delta);
        
        // Update active powerups (their durations, etc.)
        PowerupManager.getInstance().update(delta);
    }

    public boolean isShowControls() {
        return showControls;
    }

    public void dismissControls() {
        showControls = false;
    }

    private void checkForGoal() {
        float rightGoalX = rightGoal.getPosition().x;
        float leftGoalX = leftGoal.getPosition().x;
        if (ball.getPosition().x > rightGoalX && ball.getPosition().y < rightGoal.getHeight()) {
            player1Score++;
            isGoal = true;
            Assets.playGoalSound();
            System.out.println("\nP1 scored!\n");
            System.out.println("P1: " + player1Score + "\nP2: " + player2Score + "\n");
        } else if (ball.getPosition().x + ball.getWidth() < leftGoalX + leftGoal.getWidth()
                && ball.getPosition().y < leftGoal.getHeight()) {
            player2Score++;
            isGoal = true;
            Assets.playGoalSound();
            System.out.println("P2 scored!\n");
            System.out.println("P1: " + player1Score + "\nP2: " + player2Score + "\n");
        }
    }

    private void resetPositions() {
        ball.reset();
        player2.reset();
        player1.reset();
    }

    // Getters for game objects and viewport
    public Player getPlayer2() {
        return this.player2;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Ball getBall() {
        return this.ball;
    }

    public Goal getRightGoal() {
        return this.rightGoal;
    }

    public Goal getLeftGoal() {
        return this.leftGoal;
    }

    public FitViewport getViewport() {
        return this.viewport;
    }

    @Override
    public int getPlayer1Score() {
        return this.player1Score;
    }

    @Override
    public int getPlayer2Score() {
        return this.player2Score;
    }

    public ScoreBoard getScoreBoard() {
        return this.scoreBoard;
    }

    private void initGameObjects() {
        // Initialize ball at center
        float ballX = viewport.getWorldWidth() / 2;
        float ballY = viewport.getWorldHeight() / 2 + 1.5f;
        ball = new Ball("BallImage.png", ballX, ballY);

        // Initialize players at opposite ends
        float player2X = viewport.getWorldWidth() / 8 * 6.5f;
        player2 = new Player("player_2.png", player2X, 0, false);

        float playerWidth = player2.getWidth();
        float player1X = viewport.getWorldWidth() / 8 * (8 - 6.5f) - playerWidth;
        player1 = new Player("player_1.png", player1X, 0, true);

        leftGoal = new Goal("GoalLeft.png", 0, 0, false);

        float rightGoalX = viewport.getWorldWidth() - leftGoal.getWidth();
        rightGoal = new Goal("GoalRight.png", rightGoalX, 0, true);
    }

    private void initCollideables() {
        collideables = new ArrayList<>();
        collideables.add(ball);
        collideables.add(player2);
        collideables.add(player1);
        // Optionally add goals if needed:
        // collideables.add(leftGoal);
        // collideables.add(rightGoal);

        // Initialize collision handler
        collisionHandler = new Collision(collideables);
    }

    private void initScoreTracking() {
        scoreBoard = new ScoreBoard();
        player1Score = 0;
        player2Score = 0;
        goalTimer = 0;
        isGoal = false;
    }

    private void initEndScreenTimer() {
        endTimer = 0;
        isEnd = false;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    public float getRemainingTime() {
        return gameTime;
    }
    
    // ----------- Powerup Spawning Logic ---------------
    // Only one powerup on screen at a time.
    // There must be at least 5 seconds before spawning a new powerup after the last one disappears,
    // and a new powerup should spawn within 10 seconds.
    
    
    private float getRandomSpawnDelay() {
        // Delay between 5 and 10 seconds
        return 2f + (float)Math.random() * 5f;
    }
    
    private void updatePowerupSpawning(float delta) {
        if (currentPowerup == null) {
            powerupSpawnTimer += delta;
            if (powerupSpawnTimer >= nextSpawnDelay) {
                spawnPowerup();
                powerupSpawnTimer = 0f;
                nextSpawnDelay = getRandomSpawnDelay();
            }
        } else {
            // If the powerup is active, check if it's been collected.
            if (currentPowerup.isCollected()) {
                collideables.remove(currentPowerup);
                currentPowerup = null;
                powerupSpawnTimer = 0f;
                nextSpawnDelay = getRandomSpawnDelay();
            }
        }
    }
    
    private void spawnPowerup() {
        Powerup randomPowerup = PowerupFactory.getRandomPowerup();
        float spawnX = (float)Math.random() * viewport.getWorldWidth()-1f;
        float spawnY = (float)Math.random() * viewport.getWorldHeight()/4;
        currentPowerup = new PowerupPickup(randomPowerup, new Vector2(spawnX, spawnY), "Powerup.png", 1f);
        collideables.add(currentPowerup);
        System.out.println("Spawned powerup at " + currentPowerup.getPosition());

    }

    public PowerupPickup getCurrentPowerup() {
        return currentPowerup;
    }
    
}
