package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.Foot;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.model.Goal;
import inf112.bigheadkickerz.model.Player;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;

/**
 * GameView is responsible for rendering the game objects on the screen.
 */
public class GameView {

  private final ViewableGameModel model;
  private final SpriteBatch spriteBatch;
  private final Texture inGameBackground;
  private final ScoreBoard scoreBoard;
  private final ControlsOverlay controlsOverlay;

  /**
   * Constructor for GameView.
   *
   * @param model the game model to be rendered
   */
  GameView(ViewableGameModel model) {
    this.model = model;
    this.spriteBatch = new SpriteBatch();
    this.inGameBackground = new Texture("OldTrafford.png");
    this.scoreBoard = new ScoreBoard();
    this.controlsOverlay = new ControlsOverlay();
  }

  /**
   * Draws the game objects on the screen.
   */
  void draw() {
    FitViewport viewport = model.getViewport();
    viewport.apply();
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    drawObjects(viewport);
  }

  private void drawObjects(FitViewport viewport) {
    spriteBatch.begin();
    spriteBatch.draw(inGameBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    PowerupPickup powerup = model.getCurrentPowerup();
    if (powerup != null) {
      powerup.draw(spriteBatch);
    }
    Foot foot1 = model.getFootPlayer1();
    Foot foot2 = model.getFootPlayer2();
    foot1.draw(spriteBatch);
    foot2.draw(spriteBatch);
    Player p1 = model.getPlayer1();
    Player p2 = model.getPlayer2();
    Ball ball = model.getBall();
    p1.draw(spriteBatch);
    p2.draw(spriteBatch);
    ball.draw(spriteBatch);
    Goal leftGoal = model.getLeftGoal();
    Goal rightGoal = model.getRightGoal();
    leftGoal.draw(spriteBatch);
    rightGoal.draw(spriteBatch);

    spriteBatch.end();

    scoreBoard.drawPlayer1Score(model.getPlayer1Score());
    scoreBoard.drawPlayer2Score(model.getPlayer2Score());
    if (model.getGameState() == GameState.TIMED) {
      scoreBoard.drawTimer(model.getRemainingTime());
    }

    if (model.isShowingControls()) {
      controlsOverlay.draw();
    }
    if (model.isGoalTextActive()) {
      scoreBoard.drawGoalOrGameOverText(model.getGoalText());
    }
    if (model.isGameOverTextActive()) {
      scoreBoard.drawGoalOrGameOverText(model.getGameOverText());
    }

  }

  /**
   * Resizes the viewport when the window is resized.
   *
   * @param width  the new width of the window
   * @param height the new height of the window
   */
  void resize(int width, int height) {
    model.getViewport().update(width, height, true);
  }

  /**
   * Disposes of the resources used by the GameView.
   */
  void dispose() {
    spriteBatch.dispose();
    scoreBoard.dispose();
    controlsOverlay.dispose();
    inGameBackground.dispose();
  }
}
