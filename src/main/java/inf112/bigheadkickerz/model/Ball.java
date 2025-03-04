package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Class for the ball object */
public class Ball implements GameObject {
    private Texture ballTexture;
    private Sprite sprite;
    private float velocityY = 0;
    private float velocityX = 0;
    private float gravity = -9.81f;
    private float bounceFactor = 0.7f;
    private final static float BALL_SIZE = 0.6f;

    /** Constructor for Ball */
    public Ball(String texturePath, float startX, float startY) {
        ballTexture = new Texture(texturePath);
        sprite = new Sprite(ballTexture);
        sprite.setSize(BALL_SIZE, BALL_SIZE);
        startX -= sprite.getWidth() / 2;
        sprite.setPosition(startX, startY);
    }

    @Override
    public void update(Viewport viewport, float delta) {
        // Bevegelse og kollisjon
        velocityY += gravity * delta;

        // Apply friction to horizontal velocity
        velocityX *= 0.98f; // Gradually slow down horizontal movement

        // Move the ball based on both velocity components
        sprite.translateX(velocityX * delta);
        sprite.translateY(velocityY * delta);

        // Begrens bevegelse til skjerm
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        boolean atLeftWall = sprite.getX() <= 0;
        boolean atRightWall = sprite.getX() >= worldWidth - sprite.getWidth();
        boolean atGround = sprite.getY() <= 0;
        boolean atRoof = sprite.getY() >= worldHeight - sprite.getHeight();

        // Handle wall collisions
        if (atLeftWall || atRightWall) {
            velocityX = -velocityX * bounceFactor;
            sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
            
            // Reduce horizontal velocity in corners to prevent pushing through ground
            if (atGround) {
                velocityX *= 0.5f;
            }
        }

        // Handle ground collision
        if (atGround) {
            sprite.setY(0); // Ensure ball stays above ground
            velocityY = Math.abs(velocityY) * bounceFactor;
            
            // Stop the ball if velocity is very small
            if (Math.abs(velocityY) < 0.1f) {
                velocityY = 0;
            }
            
            // Apply extra friction when on ground
            velocityX *= 0.9f;
        }

        if (atRoof) {
            sprite.setY(worldHeight - sprite.getHeight());
            velocityY = -Math.abs(velocityY) * bounceFactor;
        }
        
        // Final position clamping to ensure boundaries are respected
        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Apply an impulse to the ball in x and y directions
     * 
     * @param x force in x direction
     * @param y force in y direction
     */
    public void applyImpulse(float x, float y) {
        // Apply impulse to velocity rather than directly translating
        // This prevents teleporting by affecting the ball's movement speed instead
        velocityX += x * 10;
        velocityY += y * 10;
    }
}