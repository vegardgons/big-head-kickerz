package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.Goal;
import inf112.bigheadkickerz.model.Player;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;

public class GameView {

  private final ViewableGameModel model;
  private final SpriteBatch spriteBatch;
  private final Texture inGameBackground;
  private final ScoreBoard scoreBoard;
  private final ControlsOverlay controlsOverlay;

  public GameView(ViewableGameModel model) {
    this.model = model;
    this.spriteBatch = new SpriteBatch();
    this.inGameBackground = new Texture("OldTrafford.png");
    this.scoreBoard = new ScoreBoard();
    this.controlsOverlay = new ControlsOverlay();
  }

  public void draw() {
    FitViewport viewport = model.getViewport();
    viewport.apply();
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    drawObjects(viewport);

  }

  private void drawObjects(FitViewport viewport) {
    spriteBatch.begin();
    spriteBatch.draw(inGameBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    Player p1 = model.getPlayer1();
    Player p2 = model.getPlayer2();
    Ball ball = model.getBall();
    Goal leftGoal = model.getLeftGoal();
    Goal rightGoal = model.getRightGoal();
    PowerupPickup powerup = model.getCurrentPowerup();
    p1.draw(spriteBatch);
    p2.draw(spriteBatch);
    ball.draw(spriteBatch);
    leftGoal.draw(spriteBatch);
    rightGoal.draw(spriteBatch);
    if (powerup != null) {
      powerup.draw(spriteBatch);
    }
    spriteBatch.end();

    scoreBoard.drawPlayer1Score(model.getPlayer1Score());
    scoreBoard.drawPlayer2Score(model.getPlayer2Score());
    if (model.getGameState() == GameState.TIMED) {
      scoreBoard.drawTimer(model.getRemainingTime());
    }
    if (model.isShowingControls()) {
      controlsOverlay.draw();
    }

  }

  public void resize(int width, int height) {
    model.getViewport().update(width, height, true);
  }

  public void dispose() {
    spriteBatch.dispose();
    scoreBoard.dispose();
    controlsOverlay.dispose();
    inGameBackground.dispose();
  }
}
