package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.Player; // Import the Player class

/** Class for controlling the player */
public class PlayerController {

    private static final float MOVE_SPEED = 4f;
    private static final float JUMP_SPEED = 4f;

    private boolean isJumping;
    private boolean player1;
    private Player player;

    /**
     * Updated constructor that accepts a Player instance.
     * (Minimal change: we add only what’s necessary for kicking.)
     */
    public PlayerController(boolean player1, Player player) {
        this.player1 = player1;
        this.player = player; // Assign the player so we can call kick()
    }

    /**
     * Method for moving the player and detecting kick inputs.
     *
     * @param viewport The viewport for screen boundaries.
     * @param delta    Time since the last frame.
     */
    public void movePlayer(Viewport viewport, float delta) {
        float currentVx = player.getVelocity().x;
        float currentVy = player.getVelocity().y;
        if (player.getVelocity().y == 0) {
            isJumping = false;
        }

        if (player1) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                currentVx = MOVE_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                currentVx = -MOVE_SPEED;
            } else {
                currentVx = 0;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                if (!isJumping) {
                    currentVy = JUMP_SPEED;
                    isJumping = true;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                player.kick();
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                currentVx = MOVE_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                currentVx = -MOVE_SPEED;
            } else {
                currentVx = 0;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                if (!isJumping) {
                    currentVy = JUMP_SPEED;
                    isJumping = true;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                player.kick();
            }
        }
        player.setVelocity(new Vector2(currentVx, currentVy));

    }

}
