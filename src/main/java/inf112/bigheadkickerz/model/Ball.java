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
    private float bounceFactor = 0.65f;
    private final static float BALL_SIZE = 0.6f;
    private float initialX;
    private float initialY;

    /** Constructor for Ball */
    public Ball(String texturePath, float startX, float startY) {
        ballTexture = new Texture(texturePath);
        sprite = new Sprite(ballTexture);
        sprite.setSize(BALL_SIZE, BALL_SIZE);
        this.initialX = startX;
        this.initialY = startY;
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
            velocityX *= 0.95f;
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

    /**
     * Sets the ball's position to the specified coordinates
     * 
     * @param x the x-coordinate to set
     * @param y the y-coordinate to set
     */
    public void setPosition(float x, float y) {
        // Adjust x to account for ball's center alignment
        x -= sprite.getWidth() / 2;
        sprite.setPosition(x, y);
    }

    /**
     * Sets the ball's velocity to the specified values
     * 
     * @param x the x-velocity to set
     * @param y the y-velocity to set
     */
    public void setVelocity(float x, float y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    /**
     * Gets the ball's X position (center of the ball)
     * 
     * @return the x-coordinate of the ball's center
     */
    public float getX() {
        return sprite.getX() + sprite.getWidth() / 2;
    }

    /**
     * Resets the ball to its initial position and sets velocity to zero
     */
    public void reset() {
        // Reset position to initial values
        setPosition(initialX, initialY);

        // Reset velocity to zero
        setVelocity(0, 0);
    }

    /**
     * Gets the ball's velocity in the x direction
     *
     * @return the x-velocity of the ball
     */
    public float getVelocityX() {
        return velocityX;
    }

    /**
     * Gets the ball's velocity in the y direction
     *
     * @return the y-velocity of the ball
     */
    public float getVelocityY() {
        return velocityY;
    }
}
