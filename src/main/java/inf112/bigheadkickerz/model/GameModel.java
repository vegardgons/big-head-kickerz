package inf112.bigheadkickerz.model;

import java.util.ArrayList;

import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import inf112.bigheadkickerz.controller.ControllableGameModel;
import inf112.bigheadkickerz.controller.GameController;
import inf112.bigheadkickerz.view.Assets;
import inf112.bigheadkickerz.view.ScoreBoard;

/**
 * GameModel holds all game state and implements ControllableGameModel
 * to allow the controller to interact with it.
 */
public class GameModel implements ControllableGameModel {
    // Game dimensions
    private static final float WIDTH = 15;
    private static final float HEIGHT = 8;

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
    private static final float GOAL_DELAY = 3f;
    private boolean isGoal;

    // End screen
    private float endTimer;
    private static final float END_DELAY = 3f;
    private boolean isEnd;

    // Viewport for game boundaries
    private FitViewport viewport;

    /**
     * Constructor initializes game objects and viewport
     */
    public GameModel(BigHeadKickerzGame game, GameController controller) {
        this.game = game;
        viewport = new FitViewport(WIDTH, HEIGHT);

        initGameObjects();
        initCollideables();
        initScoreTracking();
        initEndScreenTimer();
    }

    /** Update game state */
    public void update(float delta) {
        if (isGoal) {
            Assets.playGoalSound();
            goalTimer += delta;
            if (goalTimer >= GOAL_DELAY) {
                isGoal = false;
                goalTimer = 0;
                resetPositions();
            }
        } else {
            checkForGoal();
        }

        if (isEnd) {
            endTimer += delta;
            if (endTimer >= END_DELAY) {
                game.endScreen();
                endTimer = 0;
            }
        } else {
            checkIfFinishedGame();
        }

        player2.update(viewport, delta);
        player1.update(viewport, delta);
        ball.update(viewport, delta);
        collisionHandler.checkCollision();
        checkIfFinishedGame();
    }

    private void checkForGoal() {
        float rightGoalX = rightGoal.getPosition().x;
        float leftGoalX = leftGoal.getPosition().x;
        if (ball.getPosition().x > rightGoalX && ball.getPosition().y < rightGoal.getHeight()) {
            player1Score++;
            isGoal = true;
            System.out.println("\nP1 scored!\n");
            System.out.println("P1: " + player1Score + "\nP2: " + player2Score + "\n");
        } else if (ball.getPosition().x + ball.getWidth() < leftGoalX + leftGoal.getWidth()
                && ball.getPosition().y < leftGoal.getHeight()) {
            player2Score++;
            isGoal = true;
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

    @Override
    public void checkIfFinishedGame() {
        if (player1Score >= 2 || player2Score >= 2) {
            isEnd = true;
            System.out.println("Game over!");
            System.out.println("Final score:\nP1: " + player1Score + "\nP2: " + player2Score + "\n");
        }
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

}