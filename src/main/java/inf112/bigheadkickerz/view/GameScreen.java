package inf112.bigheadkickerz.view;

import com.badlogic.gdx.ScreenAdapter;
import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import inf112.bigheadkickerz.controller.GameController;
import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.model.GameState;

/**
 * The GameScreen class is responsible for managing the game's screen,
 * including rendering and updating the game state.
 */
public class GameScreen extends ScreenAdapter {

  private final GameController controller;
  private final GameView view;

  /**
   * Constructor for GameScreen.
   *
   * @param game      the main game instance
   * @param gameState the current game state
   */
  public GameScreen(BigHeadKickerzGame game, GameState gameState) {
    GameModel model = new GameModel(game, gameState);
    this.controller = new GameController(model);
    this.view = new GameView(model);
  }

  @Override
  public void render(float delta) {
    controller.update(delta);
    view.draw();
  }

  @Override
  public void pause() {
    /* ... */
  }

  @Override
  public void resume() {
    /* ... */
  }

  @Override
  public void hide() {
    /* ... */
  }

  @Override
  public void resize(int width, int height) {
    view.resize(width, height);
  }

  @Override
  public void dispose() {
    view.dispose();
  }
}
