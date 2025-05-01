package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * GameController is responsible for handling user input and controlling the
 * game model.
 */
public class GameController extends InputAdapter {

  private final ControllableGameModel model;

  private boolean p1Left;
  private boolean p1Right;
  private boolean p2Left;
  private boolean p2Right;

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
      return true;
    }
    switch (keycode) {
      case Input.Keys.W -> model.jump(true);
      case Input.Keys.A -> {
        p1Left = true;
        updateDirection(true);
      }
      case Input.Keys.D -> {
        p1Right = true;
        updateDirection(true);
      }
      case Input.Keys.SPACE -> model.kick(true);
      case Input.Keys.UP -> model.jump(false);
      case Input.Keys.LEFT -> {
        p2Left = true;
        updateDirection(false);
      }
      case Input.Keys.RIGHT -> {
        p2Right = true;
        updateDirection(false);
      }
      case Input.Keys.P -> model.kick(false);

      default -> {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Input.Keys.A -> {
        p1Left = false;
        updateDirection(true);
      }
      case Input.Keys.D -> {
        p1Right = false;
        updateDirection(true);
      }
      case Input.Keys.LEFT -> {
        p2Left = false;
        updateDirection(false);
      }
      case Input.Keys.RIGHT -> {
        p2Right = false;
        updateDirection(false);
      }
      default -> {
        return false;
      }
    }
    return true;
  }

  private void updateDirection(boolean isPlayer1) {
    int dir = 0;

    if (isPlayer1) {
      if (p1Left && !p1Right) {
        dir = -1;
      }
      if (p1Right && !p1Left) {
        dir = 1;
      }
    } else {
      if (p2Left && !p2Right) {
        dir = -1;
      }
      if (p2Right && !p2Left) {
        dir = 1;
      }
    }
    model.setPlayerDirection(isPlayer1, dir);
  }
}