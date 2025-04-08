package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.bigheadkickerz.model.Assets;

/**
 * The GameController class is responsible for handling the game logic
 * and updating the game model based on user input and game state.
 */
public class GameController {

  private final ControllableGameModel model;

  /**
   * Constructor for the GameController class.
   *
   * @param model the game model to be controlled
   */
  public GameController(ControllableGameModel model) {
    this.model = model;
  }

  /**
   * Updates the game state based on user input and game state.
   *
   * @param delta time since last update
   */
  public void update(float delta) {
    if (model.isShowingControls()) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        model.dismissControls();
        Assets.playStartWhistle();
      }
    } else {
      model.update(delta);
    }
  }
}
