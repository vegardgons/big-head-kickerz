package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerController {

    private float speed = 4f;
    private float velocityY = 0;
    private Sprite sprite;
    private boolean isJumping = false;
    private float gravity = -9.81f;

    public PlayerController(Sprite sprite) {
        this.sprite = sprite;
    }

    public void movePlayer(Viewport viewport, float delta) {
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprite.translateX(-speed * delta);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isJumping) {
            velocityY = 4f;
            isJumping = true;
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