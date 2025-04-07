package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.bigheadkickerz.model.Assets;

public class GameController {

  private final ControllableGameModel model;

  public GameController(ControllableGameModel model) {
    this.model = model;
  }

  /**
   * Kalles hver frame av GameScreen->render()
   * for å oppdatere spillet (input + logikk).
   */
  public void update(float delta) {
    if (model.isShowingControls()) {
      // Vent på at spiller trykker space for å starte
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        model.dismissControls();
        Assets.playStartWhistle();
      }
    } else {
      model.update(delta);
    }
  }
}
