package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.Player;  // Import the Player class

/** Class for controlling the player */
public class PlayerController {

    private float speed = 4f;
    private float velocityY = 0;
    private Sprite sprite;
    private boolean isJumping = false;
    private float gravity = -9.81f;
    private boolean player1;
    private Player player; // Added reference to the Player model

    /**
     * Updated constructor that accepts a Player instance.
     * (Minimal change: we add only what’s necessary for kicking.)
     */
    public PlayerController(Sprite sprite, boolean player1, Player player) {
        this.sprite = sprite;
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

        if (player1) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                sprite.translateX(speed * delta);
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                sprite.translateX(-speed * delta);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isJumping) {
                velocityY = 4.2f;
            }
            // Added kick input for Player 1 (K key)
            if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
                player.kick();
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                sprite.translateX(speed * delta);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                sprite.translateX(-speed * delta);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !isJumping) {
                velocityY = 4.2f;
                isJumping = true;
            }
            // Added kick input for Player 2 (Q key)
            if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                player.kick();
            }
        }

        // Movement: apply gravity and update position
        velocityY += gravity * delta;
        sprite.translateY(velocityY * delta);

        // Constrain movement to the screen bounds
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));

        // Stop the player on the ground
        if (sprite.getY() <= 0) {
            sprite.setY(0);
            velocityY = 0;
            isJumping = false;
        } else {
            isJumping = true;
        }
    }

    /**
     * Method for checking if the player is jumping.
     *
     * @return true if jumping, false otherwise.
     */
    public boolean getIsJumping() {
        return isJumping;
    }
}
