package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import inf112.bigheadkickerz.model.Player;

/** Class for controlling the player. */
public class PlayerController {

  private boolean isJumping;
  private final boolean isPlayer1;
  private final Player player;

  /**
   * Updated constructor that accepts a Player instance.
   * (Minimal change: we add only what’s necessary for kicking.)
   */
  public PlayerController(boolean isPlayer1, Player player) {
    this.isPlayer1 = isPlayer1;
    this.player = player;
  }

  /**
   * Method for moving the player and detecting kick inputs.
   *
   */
  public Vector2 movePlayer() {
    float currentVx;
    float currentVy = player.getVelocity().y;
    float movementSpeed = player.getMovementSpeed();
    float jumpHeight = player.getJumpHeight();

    if (player.getPosition().y == 0) {
      isJumping = false;
    }

    if (isPlayer1) {
      if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        currentVx = movementSpeed;
      } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        currentVx = -movementSpeed;
      } else {
        currentVx = 0;
      }
      if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
        if (!isJumping) {
          currentVy = jumpHeight;
          isJumping = true;
        }
      }
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        player.kick();
      }
    } else {
      if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        currentVx = movementSpeed;
      } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        currentVx = -movementSpeed;
      } else {
        currentVx = 0;
      }
      if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
        if (!isJumping) {
          currentVy = jumpHeight;
          isJumping = true;
        }
      }
      if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
        player.kick();
      }

    }
    return new Vector2(currentVx, currentVy);

  }

}
