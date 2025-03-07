package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Class for controlling the player */
public class PlayerController {

    private float speed = 4f;
    private float velocityY = 0;
    private Sprite sprite;
    private boolean isJumping = false;
    private float gravity = -9.81f;
    private boolean player1;

    /** Constructor for PlayerController */
    public PlayerController(Sprite sprite, boolean player1) {
        this.sprite = sprite;
        this.player1 = player1;
    }

    /**
     * Method for moving the player
     *
     * @param viewport
     * @param delta
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
                isJumping = true;
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
        }

        // Bevegelse
        velocityY += gravity * delta;
        sprite.translateY(velocityY * delta);

        // Begrens bevegelse til skjerm
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));

        // Stopper spilleren på bakken
        if (sprite.getY() <= 0) {
            sprite.setY(0);
            velocityY = 0;
            isJumping = false;
        }

    }

}