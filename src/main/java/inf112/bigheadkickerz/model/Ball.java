package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Ball {
    private Texture ballTexture;
    private Sprite sprite;
    private float velocityY = 0;
    private float gravity = -9.81f;
    private float bounceFactor = 0.7f;
    private final static float BALL_SIZE = 0.6f;

    public Ball(String texturePath, float startX, float startY) {
        ballTexture = new Texture(texturePath);
        sprite = new Sprite(ballTexture);
        sprite.setSize(BALL_SIZE, BALL_SIZE);
        startX -= sprite.getWidth() / 2;
        sprite.setPosition(startX, startY);
    }

    public void update(Viewport viewport, float delta) {
        // Bevegelse og kollisjon
        velocityY += gravity * delta;
        sprite.translateY(velocityY * delta);

        // Begrens bevegelse til skjerm
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));

        // Spretter ballen
        if (sprite.getY() <= 0) {
            sprite.setY(0);
            velocityY = Math.abs(velocityY) * bounceFactor;
            if (velocityY < 0.1f) {
                velocityY = 0;
            }
        }
    }

    public void draw(SpriteBatch batch, Viewport viewport) {
        sprite.draw(batch);
    }

}