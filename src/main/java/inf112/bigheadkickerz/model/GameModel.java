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
public class GameModel implements ViewableGameModel, ControllableGameModel {
  // Game dimensions
  private static final float WIDTH = 15;
  private static final float HEIGHT = 8;

  // Powerup variables
  private PowerupPickup currentPowerup;
  private float powerupSpawnTimer = 0f;
  private float nextSpawnDelay = 7f;

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
  private static final float GAME_OVER_DELAY = 5f;
  private boolean isGoal;
  private String goalText;
  private boolean showControls = true;

  private String gameOverText;
  private boolean gameOver = false;

  private final FitViewport viewport;

  private GameState gameState;
  private float gameTime;
  private static final float DEFAULT_GAME_TIME = 60f;
  private int goalThreshold;

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

    if (gameState == GameState.TIMED) {
      gameTime = DEFAULT_GAME_TIME;
    } else if (gameState == GameState.FIRST_TO_SEVEN) {
      goalThreshold = 7;
    }
  }

  @Override
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
        gameOver = true;
      }
    } else if (gameState == GameState.FIRST_TO_SEVEN
        && (player1Score >= goalThreshold || player2Score >= goalThreshold)) {
      gameOver = true;
    }

  }

  private void handleGoal(float delta) {
    if (gameOver) {
      return;
    }
    if (!isGoal && checkForGoal()) {
      isGoal = true;
    }
    if (isGoal) {
      goalTimer += delta;
      if (goalTimer >= GOAL_DELAY) {
        isGoal = false;
        goalTimer = 0;
        goalText = null;
        resetPositions();
      }
    }
  }

  @Override
  public String getGameOverText() {
    return gameOverText;
  }

  private void handleGameOver(float delta) {
    if (gameOver) {
      gameState = GameState.GAME_OVER;
      gameTime = 0;
      gameOverText = determineGameOverText();
      goalTimer += delta;
      if (goalTimer >= GAME_OVER_DELAY) {
        game.endScreen();
      }
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
    player2.update(viewport, delta);
    player1.update(viewport, delta);
    ball.update(viewport, delta);
    collisionHandler.checkCollision();
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
    float rightGoalX = rightGoal.getPosition().x;
    float leftGoalX = leftGoal.getPosition().x;
    if (ball.getPosition().x > rightGoalX
        && ball.getPosition().y + ball.getHeight() < rightGoal.getHeight()) {
      player1Score++;
      Assets.playGoalSound();
      if (gameState == GameState.FIRST_TO_SEVEN && player1Score >= goalThreshold) {
        gameOver = true;
        return true;
      } else {
        goalText = "Player 1 scored!";
        isGoal = true;
        return true;
      }
    } else if (ball.getPosition().x + ball.getWidth() < leftGoalX + leftGoal.getWidth()
        && ball.getPosition().y + ball.getHeight() < leftGoal.getHeight()) {
      player2Score++;
      Assets.playGoalSound();
      if (gameState == GameState.FIRST_TO_SEVEN && player2Score >= goalThreshold) {
        gameOver = true;
        return true;
      } else {
        goalText = "Player 2 scored!";
        isGoal = true;
        return true;
      }
    }
    return false;
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
    isGoal = false;
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
      powerupSpawnTimer += delta;
      if (powerupSpawnTimer >= nextSpawnDelay) {
        spawnPowerup();
        powerupSpawnTimer = 0f;
        nextSpawnDelay = getRandomSpawnDelay();
      }
    } else {
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
    SecureRandom random = new SecureRandom();
    int rand = random.nextInt(6);
    Powerup randomPowerup = PowerupFactory.getRandomPowerup(rand);
    float spawnX = leftGoal.getWidth()
        + (random.nextFloat() * viewport.getWorldWidth() - leftGoal.getWidth() * 2);
    float spawnY = random.nextFloat() * viewport.getWorldHeight() / 1.5f;
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
