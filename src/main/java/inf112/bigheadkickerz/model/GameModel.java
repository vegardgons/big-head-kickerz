package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import inf112.bigheadkickerz.controller.ControllableGameModel;
import inf112.bigheadkickerz.model.powerups.Powerup;
import inf112.bigheadkickerz.model.powerups.PowerupFactory;
import inf112.bigheadkickerz.model.powerups.PowerupManager;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;
import inf112.bigheadkickerz.view.ViewableGameModel;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * GameModel holds all game state and implements ControllableGameModel
 * to allow the controller to interact with it.
 */
public class GameModel implements ControllableGameModel, ViewableGameModel {
  // Game dimensions
  private static final float WIDTH = 15;
  private static final float HEIGHT = 8;

  // Powerup variables
  private PowerupPickup currentPowerup;
  private float powerupSpawnTimer = 0f;
  private float nextSpawnDelay = 2f;

  // Game objects
  private final BigHeadKickerzGame game;
  private Player player1;
  private Player player2;
  private Ball ball;
  private Goal leftGoal;
  private Goal rightGoal;
  private ArrayList<Collideable> collideables;
  private Collision collisionHandler;

  private final SecureRandom random;

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
  private boolean gameOver;

  // Viewport for game boundaries
  private final FitViewport viewport;

  private GameState gameState;
  private float gameTime; // Total time in timed mode (seconds)
  private static final float DEFAULT_GAME_TIME = 60f; // e.g., 60 seconds
  private int goalThreshold; // For first-to-seven mode, threshold = 7

  /**
   * Constructor initializes game objects and viewport.
   */
  public GameModel(BigHeadKickerzGame game, GameState gameState) {
    this.game = game;
    this.gameState = gameState;
    this.viewport = new FitViewport(WIDTH, HEIGHT);
    this.random = new SecureRandom();

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

  /**
   * Update game state.
   */
  public void update(float delta) {
    if (showControls) {
      return;
    }

    assessCurrentGameState(delta);
    handleGoal(delta);
    handleGameOver(delta);
    updateGameObjects(delta);

    updatePowerupSpawning(delta);
  }

  private void assessCurrentGameState(float delta) {
    if (gameState == GameState.TIMED) {
      if (!isGoal) {
        gameTime -= delta;
      }
      if (gameTime <= 0) {
        setGameOver(true);
      }
    } else if (gameState == GameState.FIRST_TO_SEVEN
        && (player1Score >= goalThreshold || player2Score >= goalThreshold)) {
      setGameOver(true);
    }

  }

  private void handleGoal(float delta) {
    if (!isGoal && checkForGoal()) {
      setIsGoal(true);
    }
    if (isGoal) {
      goalTimer += delta;
      if (goalTimer >= GOAL_DELAY) {
        setIsGoal(false);
        goalTimer = 0;
        resetPositions();
      }
    }
  }

  private void handleGameOver(float delta) {
    if (gameOver) {
      gameState = GameState.GAME_OVER;
      gameTime = 0;
      endTimer += delta;
      if (endTimer >= END_DELAY) {
        game.endScreen();
        endTimer = 0;
      }
    }
  }

  private void updateGameObjects(float delta) {
    player2.update(viewport, delta);
    player1.update(viewport, delta);
    ball.update(viewport, delta);
    collisionHandler.checkCollision();
  }

  /**
   * Checks if controls should be shown.
   *
   * @return true if controls should be shown, false otherwise
   */
  public boolean isShowControls() {
    return showControls;
  }

  /**
   * Dismisses the controls overlay.
   */
  public void dismissControls() {
    showControls = false;
  }

  private boolean checkForGoal() {
    float rightGoalX = rightGoal.getPosition().x;
    float leftGoalX = leftGoal.getPosition().x;
    if (ball.getPosition().x > rightGoalX
        && ball.getPosition().y + ball.getHeight() < rightGoal.getHeight()) {
      player1Score++;
      Assets.playGoalSound();
      return true;
    } else if (ball.getPosition().x + ball.getWidth() < leftGoalX + leftGoal.getWidth()
        && ball.getPosition().y + ball.getHeight() < leftGoal.getHeight()) {
      player2Score++;
      Assets.playGoalSound();
      return true;
    }
    return false;
  }

  private void resetPositions() {
    ball.reset();
    player2.reset();
    player1.reset();
  }

  private void setIsGoal(boolean isGoal) {
    this.isGoal = isGoal;
  }

  private void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
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

  private void initGameObjects() {
    // Initialize ball at center
    float ballX = viewport.getWorldWidth() / 2;
    float ballY = viewport.getWorldHeight() / 2 + 1.5f;
    Texture ballTexture = new Texture("BallImage.png");
    ball = new Ball(ballTexture, ballX, ballY);

    // Initialize players at opposite ends
    float player2X = viewport.getWorldWidth() / 8 * 6.5f;
    Texture player2Texture = new Texture("player_2.png");
    player2 = new Player(player2Texture, player2X, 0, false);

    float playerWidth = player2.getWidth();
    float player1X = viewport.getWorldWidth() / 8 * (8 - 6.5f) - playerWidth;
    Texture player1Texture = new Texture("Player_1.png");
    player1 = new Player(player1Texture, player1X, 0, true);

    Texture leftGoalTexture = new Texture("GoalLeft.png");
    leftGoal = new Goal(leftGoalTexture, 0, 0);

    Texture rightGoalTexture = new Texture("GoalRight.png");
    float rightGoalX = viewport.getWorldWidth() - leftGoal.getWidth();
    rightGoal = new Goal(rightGoalTexture, rightGoalX, 0);
  }

  private void initCollideables() {
    collideables = new ArrayList<>();
    collideables.add(ball);
    collideables.add(player2);
    collideables.add(player1);
    collideables.add(leftGoal);
    collideables.add(rightGoal);

    // Initialize collision handler
    collisionHandler = new Collision(collideables);
  }

  private void initScoreTracking() {
    player1Score = 0;
    player2Score = 0;
    goalTimer = 0;
    setIsGoal(false);
  }

  private void initEndScreenTimer() {
    endTimer = 0;
    setGameOver(false);
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
  // There must be at least 5 seconds before spawning a new powerup after the last
  // one disappears,
  // and a new powerup should spawn within 10 seconds.

  private float getRandomSpawnDelay() {
    // Delay between 5 and 10 seconds
    return 5f + random.nextFloat() * 5f;
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
    PowerupManager.update(delta);
  }

  private void spawnPowerup() {
    Powerup randomPowerup = PowerupFactory.getRandomPowerup();
    float spawnX = random.nextFloat() * viewport.getWorldWidth() - 1f;
    float spawnY = random.nextFloat() * viewport.getWorldHeight() / 4;
    Texture powerupTexture = new Texture("Powerup.png");
    currentPowerup = new PowerupPickup(randomPowerup,
        new Vector2(spawnX, spawnY), powerupTexture, 1f);
    collideables.add(currentPowerup);
  }

  public PowerupPickup getCurrentPowerup() {
    return currentPowerup;
  }

}
