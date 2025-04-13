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
import java.util.Arrays;
import java.util.List;

/**
 * GameModel holds all game state and implements ControllableGameModel
 * to allow the controller to interact with it.
 */
public class GameModel implements ViewableGameModel, ControllableGameModel {
  // Game dimensions
  private static final float WIDTH = 15;
  private static final float HEIGHT = 8;

  private FitViewport viewport;

  // Powerup variables
  private PowerupPickup currentPowerup;
  private float powerupSpawnTimer = 0f;
  private float nextSpawnDelay = 7f;

  // Game objects
  private BigHeadKickerzGame game;
  private Player player1;
  private Player player2;
  private Ball ball;
  private Goal leftGoal;
  private Goal rightGoal;
  private ArrayList<Collideable> collideables;
  private ICollisionHandler collisionHandler;

  // Score tracking
  private int player1Score;
  private int player2Score;
  private float goalTimer;
  private static final int GOAL_THRESHOLD = 7;
  private static final float GOAL_DELAY = 2f;
  private static final float GAME_OVER_DELAY = 5f;
  private boolean isGoal;
  private boolean gameOver = false;
  private GameState gameState;

  private boolean showControls = true;
  private String gameOverText;
  private String goalText;
  private float gameTime;
  private static final float DEFAULT_GAME_TIME = 60f;

  private SecureRandom random;

  /**
   * Constructor initializes game objects and viewport.
   */
  public GameModel(BigHeadKickerzGame game, GameState gameState) {
    initBasicComponents(game, gameState);
    initGameSystems();
  }

  private void initBasicComponents(BigHeadKickerzGame game, GameState gameState) {
    this.game = game;
    this.gameState = gameState;
    this.gameTime = DEFAULT_GAME_TIME;
    this.viewport = new FitViewport(WIDTH, HEIGHT);
    this.random = new SecureRandom();
  }

  private void initGameSystems() {
    initGameObjects();
    initCollideables();
    initScoreTracking();
  }

  @Override
  public void update(float delta) {
    if (showControls) {
      return;
    }
    assessCurrentGameState(delta);
    handleGoal(delta);
    handleGameOver(delta);
    updatePowerupSpawning(delta);
    updateGameObjects(delta);

  }

  private void assessCurrentGameState(float delta) {
    if (gameState == GameState.TIMED) {
      handleTimedMode(delta);
    } else {
      handleFirstToSevenMode();
    }
  }

  private void handleTimedMode(float delta) {
    if (isGoal) {
      return;
    }
    gameTime -= delta;
    if (gameTime <= 0) {
      endGame();
    }
  }

  private void handleFirstToSevenMode() {
    if (hasPlayerReachedGoalThreshold()) {
      endGame();
    }
  }

  private boolean hasPlayerReachedGoalThreshold() {
    return player1Score >= GOAL_THRESHOLD || player2Score >= GOAL_THRESHOLD;
  }

  private void endGame() {
    gameOver = true;
  }

  private void handleGoal(float delta) {
    if (gameOver) {
      return;
    }
    checkForNewGoal();
    if (isGoal) {
      handleGoalDelay(delta);
    }
  }

  private void checkForNewGoal() {
    if (!isGoal && checkForGoal()) {
      isGoal = true;
    }
  }

  private void handleGoalDelay(float delta) {
    goalTimer += delta;
    if (goalTimer >= GOAL_DELAY) {
      resetAfterGoal();
    }
  }

  private void resetAfterGoal() {
    isGoal = false;
    goalTimer = 0;
    goalText = null;
    resetPositions();
  }

  private void initCollideables() {
    collideables = new ArrayList<>();
    collisionHandler = new CollisionHandler();
    addCollideables(Arrays.asList(ball, player2, player1, leftGoal, rightGoal));
  }

  private void addCollideables(List<Collideable> objects) {
    for (Collideable object : objects) {
      if (object != null) {
        collideables.add(object);
      }
    }
  }

  private void initScoreTracking() {
    player1Score = 0;
    player2Score = 0;
    goalTimer = 0;
    isGoal = false;
  }

  @Override
  public String getGameOverText() {
    return gameOverText;
  }

  private void handleGameOver(float delta) {
    if (!gameOver) {
      return;
    }
    initializeGameOverState();
    updateGameOverTimer(delta);
  }

  private void initializeGameOverState() {
    if (gameState != GameState.GAME_OVER) {
      Assets.playGameOverSound();
      gameState = GameState.GAME_OVER;
      gameTime = 0;
      gameOverText = determineGameOverText();
    }
  }

  private void updateGameOverTimer(float delta) {
    goalTimer += delta;
    if (goalTimer >= GAME_OVER_DELAY) {
      game.endScreen();
    }
  }

  private String determineGameOverText() {
    if (player1Score > player2Score) {
      return "Player 1 wins!";
    } else if (player2Score > player1Score) {
      return "Player 2 wins!";
    } else {
      return "It's a draw!";
    }
  }

  private void updateGameObjects(float delta) {
    player1.update(viewport, delta);
    player2.update(viewport, delta);
    ball.update(viewport, delta);
    collisionHandler.checkCollisions(collideables);
  }

  @Override
  public boolean isShowingControls() {
    return showControls;
  }

  @Override
  public void dismissControls() {
    showControls = false;
  }

  private boolean checkForGoal() {
    if (isRightGoalScored()) {
      handleGoalScored(1);
      return true;
    }

    if (isLeftGoalScored()) {
      handleGoalScored(2);
      return true;
    }

    return false;
  }

  private boolean isRightGoalScored() {
    return ball.getPosition().x > rightGoal.getPosition().x
        && ball.getPosition().y + ball.getHeight() < rightGoal.getHeight();
  }

  private boolean isLeftGoalScored() {
    return ball.getPosition().x + ball.getWidth() < leftGoal.getPosition().x + leftGoal.getWidth()
        && ball.getPosition().y + ball.getHeight() < leftGoal.getHeight();
  }

  private void handleGoalScored(int scoringPlayer) {
    if (scoringPlayer == 1) {
      player1Score++;
    } else {
      player2Score++;
    }
    Assets.playGoalSound();
    if (gameState == GameState.FIRST_TO_SEVEN && getPlayerScore(scoringPlayer) >= GOAL_THRESHOLD) {
      gameOver = true;
    } else {
      goalText = "Player " + scoringPlayer + " scored!";
      isGoal = true;
    }
  }

  private int getPlayerScore(int player) {
    if (player == 1) {
      return player1Score;
    }
    return player2Score;
  }

  private void resetPositions() {
    ball.reset();
    player2.reset();
    player1.reset();
  }

  @Override
  public Player getPlayer2() {
    return this.player2;
  }

  @Override
  public Player getPlayer1() {
    return this.player1;
  }

  @Override
  public Ball getBall() {
    return this.ball;
  }

  @Override
  public Goal getRightGoal() {
    return this.rightGoal;
  }

  @Override
  public Goal getLeftGoal() {
    return this.leftGoal;
  }

  @Override
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
    Texture ballTexture = Assets.getBallTexture();
    ball = new Ball(ballTexture, ballX, ballY);

    // Initialize players at opposite ends
    float player2X = viewport.getWorldWidth() / 8 * 6.5f;
    Texture player2Texture = Assets.getPlayer2Texture();
    player2 = new Player(player2Texture, player2X, 0, false);

    float playerWidth = player2.getWidth();
    float player1X = viewport.getWorldWidth() / 8 * (8 - 6.5f) - playerWidth;
    Texture player1Texture = Assets.getPlayer1Texture();
    player1 = new Player(player1Texture, player1X, 0, true);

    Texture leftGoalTexture = Assets.getLeftGoalTexture();
    leftGoal = new Goal(leftGoalTexture, 0, 0);

    Texture rightGoalTexture = Assets.getRightGoalTexture();
    float rightGoalX = viewport.getWorldWidth() - leftGoal.getWidth();
    rightGoal = new Goal(rightGoalTexture, rightGoalX, 0);
  }

  @Override
  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public float getRemainingTime() {
    return gameTime;
  }

  private float getRandomSpawnDelay() {
    return 8 + random.nextFloat() * 5;
  }

  private void updatePowerupSpawning(float delta) {
    if (currentPowerup == null) {
      handlePowerupSpawning(delta);
    } else {
      handleExistingPowerup();
    }
    PowerupManager.update(delta);
  }

  private void handlePowerupSpawning(float delta) {
    powerupSpawnTimer += delta;
    if (powerupSpawnTimer >= nextSpawnDelay) {
      spawnPowerup();
      resetPowerupSpawnTimer();
    }
  }

  private void handleExistingPowerup() {
    if (currentPowerup.isCollected()) {
      removePowerup();
      resetPowerupSpawnTimer();
    }
  }

  private void resetPowerupSpawnTimer() {
    powerupSpawnTimer = 0f;
    nextSpawnDelay = getRandomSpawnDelay();
  }

  private void removePowerup() {
    collideables.remove(currentPowerup);
    currentPowerup = null;
  }

  private void spawnPowerup() {
    int rand = random.nextInt(6);
    Powerup randomPowerup = PowerupFactory.getRandomPowerup(rand);
    float widthToRightGoal = viewport.getWorldWidth() - leftGoal.getWidth() * 2;
    float spawnX = leftGoal.getWidth()
        + (random.nextFloat() * widthToRightGoal);
    float spawnY = random.nextFloat() * (viewport.getWorldHeight() / 1.5f);
    Texture powerupTexture = randomPowerup.getTexture();
    currentPowerup = new PowerupPickup(randomPowerup,
        new Vector2(spawnX, spawnY), powerupTexture, 1f);
    collideables.add(currentPowerup);
  }

  @Override
  public PowerupPickup getCurrentPowerup() {
    return currentPowerup;
  }

  @Override
  public String getGoalText() {
    return goalText;
  }

  @Override
  public boolean isGoalTextActive() {
    return goalText != null;
  }

  @Override
  public boolean isGameOverTextActive() {
    return gameOverText != null;
  }

}
