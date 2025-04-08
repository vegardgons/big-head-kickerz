package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.bigheadkickerz.model.Assets;

public class GameController {

  private final ControllableGameModel model;

  public GameController(ControllableGameModel model) {
    this.model = model;
  }

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
