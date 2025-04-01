package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.model.Goal;
import inf112.bigheadkickerz.model.Player;

/**
 * GameViewImpl is responsible for rendering all game objects.
 */
public class GameViewImpl extends AScreen {
  private final SpriteBatch spriteBatch;
  private final Field field;
  private final GameModel gameModel;
  private final ScoreBoard scoreBoard;
  private final ControlsOverlay controlsOverlay;

  /**
   * Constructor initializes rendering components.
   */
  public GameViewImpl(GameModel gameModel) {
    this.gameModel = gameModel;
    spriteBatch = new SpriteBatch();

    // Initialize field
    field = new Field("OldTrafford.png");
    scoreBoard = gameModel.getScoreBoard();
    controlsOverlay = new ControlsOverlay();
  }

  /**
   * Render all game objects.
   */
  public void render() {
    FitViewport viewport = gameModel.getViewport();
    viewport.apply();
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    draw(spriteBatch, viewport);

  }

  /**
   * Handle screen resize.
   */
  public void resize(int width, int height) {
    gameModel.getViewport().update(width, height, true);
  }

  /**
   * Dispose resources.
   */
  public void dispose() {
    spriteBatch.dispose();
    scoreBoard.dispose();
    field.dispose();
  }

  /**
   * Draw all game objects.
   *
   * @param batch    SpriteBatch for rendering
   * @param viewport Viewport for rendering
   */
  public void draw(SpriteBatch batch, Viewport viewport) {
    Player player1 = gameModel.getPlayer2();
    Player player2 = gameModel.getPlayer1();
    Ball ball = gameModel.getBall();
    Goal rightGoal = gameModel.getRightGoal();
    Goal leftGoal = gameModel.getLeftGoal();

    batch.begin();
    field.draw(batch, viewport);
    player1.draw(batch);
    player2.draw(batch);
    ball.draw(batch);
    leftGoal.draw(batch);
    rightGoal.draw(batch);
    if (gameModel.getCurrentPowerup() != null) {
      gameModel.getCurrentPowerup().draw(batch);
    }

    batch.end();
    if (gameModel.getGameState() == GameState.TIMED) {
      scoreBoard.drawTimer(gameModel.getRemainingTime());
    }
    scoreBoard.drawPlayer1Score(gameModel.getPlayer1Score());
    scoreBoard.drawPlayer2Score(gameModel.getPlayer2Score());
  }

  public void drawControlsOverlay() {
    controlsOverlay.draw();
  }

}