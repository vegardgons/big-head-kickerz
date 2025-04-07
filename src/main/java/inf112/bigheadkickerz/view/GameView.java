package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.Texture;
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
public class GameView extends AScreen {
  private final SpriteBatch spriteBatch;
  private final Texture inGameBackground;
  private final GameModel gameModel;
  private final ScoreBoard scoreBoard;
  private final ControlsOverlay controlsOverlay;

  /**
   * Constructor initializes rendering components.
   */
  public GameView(GameModel gameModel) {
    this.gameModel = gameModel;
    spriteBatch = new SpriteBatch();

    // Initialize field
    inGameBackground = new Texture("OldTrafford.png");
    scoreBoard = new ScoreBoard();
    controlsOverlay = new ControlsOverlay();
  }

  @Override
  public void render(float delta) {
    FitViewport viewport = gameModel.getViewport();
    viewport.apply();
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    draw(spriteBatch, viewport);

  }

  @Override
  public void resize(int width, int height) {
    gameModel.getViewport().update(width, height, true);
  }

  @Override
  public void dispose() {
    spriteBatch.dispose();
    scoreBoard.dispose();
    controlsOverlay.dispose();
    inGameBackground.dispose();
  }

  /**
   * Draw all game objects.
   *
   * @param batch    SpriteBatch for rendering
   * @param viewport Viewport for rendering
   */
  public void draw(SpriteBatch batch, Viewport viewport) {
    batch.begin();
    batch.draw(inGameBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    Player player1 = gameModel.getPlayer1();
    player1.draw(batch);

    Player player2 = gameModel.getPlayer2();
    player2.draw(batch);

    Ball ball = gameModel.getBall();
    ball.draw(batch);

    Goal leftGoal = gameModel.getLeftGoal();
    leftGoal.draw(batch);

    Goal rightGoal = gameModel.getRightGoal();
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