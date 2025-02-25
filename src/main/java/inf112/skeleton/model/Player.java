package inf112.skeleton.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player {
    private Sprite sprite;
    private static final float x = 6;
    private static final float y = 0;
    private float velocityY = 0;
    private float gravity = -9.81f;
    private boolean isJumping = false;

    public Player(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        sprite.setPosition(x, y);
    }

    public void update(Viewport viewport, float delta) {
        float speed = 4f;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprite.translateX(-speed * delta);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !isJumping) {
            velocityY = 4f;
            isJumping = true;
        }

        // Bevegelse og kollisjon
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

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}