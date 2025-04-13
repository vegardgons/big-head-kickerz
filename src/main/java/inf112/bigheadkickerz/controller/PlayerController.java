package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import inf112.bigheadkickerz.model.Assets;
import inf112.bigheadkickerz.model.Player;

/** Class for controlling the player. */
public class PlayerController {

  private boolean isJumping;
  private final boolean isPlayer1;
  private final Player player;

  /**
   * Constructor for PlayerController.
   */
  public PlayerController(boolean isPlayer1, Player player) {
    this.isPlayer1 = isPlayer1;
    this.player = player;
  }

  /**
   * Moves the player based on input.
   *
   * @return the new velocity of the player
   */
  public Vector2 movePlayer() {
    if (player.getPosition().y == 0) {
      isJumping = false;
    }

    float currentVx = getHorizontalVelocity();
    float currentVy = handleJump(player.getVelocity().y);
    handleKick();

    return new Vector2(currentVx, currentVy);
  }

  private float getHorizontalVelocity() {
    float movementSpeed = player.getMovementSpeed();
    if (isPlayer1) {
      if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        return movementSpeed;
      } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        return -movementSpeed;
      }
    } else {
      if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        return movementSpeed;
      } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        return -movementSpeed;
      }
    }
    return 0;
  }

  private float handleJump(float currentVy) {
    float jumpHeight = player.getJumpHeight();
    if (isPlayer1) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !isJumping) {
        isJumping = true;
        Assets.playJumpingSound();
        return jumpHeight;
      }
    } else {
      if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isJumping) {
        isJumping = true;
        Assets.playJumpingSound();
        return jumpHeight;
      }
    }
    return currentVy;
  }

  private void handleKick() {
    if (isPlayer1) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        player.kick();
      }
    } else {
      if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
        player.kick();
      }
    }
  }

}
