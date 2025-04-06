package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import inf112.bigheadkickerz.model.Assets;
import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.view.GameView;

/**
 * Controller for the game screen.
 * This class handles the game logic and rendering.
 */
public class GameController implements Screen {
  private final GameModel gameModel;
  private final GameView gameView;

  public GameController(BigHeadKickerzGame game, GameState gameState) {
    this.gameModel = new GameModel(game, gameState);
    this.gameView = new GameView(gameModel);
  }

  @Override
  public void show() {
    // No implementation needed
    // No implementation needed
  }

  @Override
  public void render(float delta) {
    if (gameModel.isShowControls()) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        gameModel.dismissControls();
        Assets.playStartWhistle();
      }
      gameView.render(delta);
      gameView.drawControlsOverlay();
    } else {
      gameModel.update(delta);
      gameView.render(delta);
    }
  }

  @Override
  public void resize(int width, int height) {
    gameView.resize(width, height);
  }

  @Override
  public void pause() {
    // No implementation needed
  }

  @Override
  public void resume() {
    // No implementation needed
  }

  @Override
  public void hide() {
    // No implementation needed
  }

  @Override
  public void dispose() {
    gameView.dispose();
  }

}
