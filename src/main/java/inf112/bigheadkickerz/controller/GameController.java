package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * GameController is responsible for handling user input and controlling the
 * game model.
 */
public class GameController extends InputAdapter {

  private ControllableGameModel model;

  /**
   * Constructor for GameController.
   *
   * @param model the game model to be controlled
   */
  public GameController(ControllableGameModel model) {
    this.model = model;
  }

  @Override
  public boolean keyDown(int keycode) {
    if (model.isShowingControls()) {
      if (keycode == Input.Keys.SPACE) {
        model.dismissControls();
      }
    } else {
      switch (keycode) {
        case Input.Keys.W:
          model.jump(true);
          break;
        case Input.Keys.UP:
          model.jump(false);
          break;
        case Input.Keys.A:
          model.setPlayerDirection(true, -1);
          break;
        case Input.Keys.LEFT:
          model.setPlayerDirection(false, -1);
          break;
        case Input.Keys.D:
          model.setPlayerDirection(true, 1);
          break;
        case Input.Keys.RIGHT:
          model.setPlayerDirection(false, 1);
          break;
        case Input.Keys.SPACE:
          model.kick(true);
          break;
        case Input.Keys.P:
          model.kick(false);
          break;
        default:
          return false;
      }
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {

    switch (keycode) {
      case Input.Keys.A, Input.Keys.D:
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)) {
          break;
        } else {
          model.setPlayerDirection(true, 0);
        }
        break;
      case Input.Keys.LEFT, Input.Keys.RIGHT:
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
          break;
        } else {
          model.setPlayerDirection(false, 0);
        }
        break;
      default:
        return false;
    }
    return true;
  }
}