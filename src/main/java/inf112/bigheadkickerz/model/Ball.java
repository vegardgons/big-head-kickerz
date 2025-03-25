package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Class for the ball object */
public class Ball implements GameObject, Collideable {

    private static final float BALL_SIZE = 0.6f;
    private static final float WEIGHT = 0.5f;
    private static final float GRAVITY = -9.81f;
    private static final float BOUNCE_FACTOR = 0.7f;

    private Vector2 startPos;
    private Vector2 pos;
    private Vector2 velocity;
    private Texture texture;

    /** Constructor for Ball */
    public Ball(String texturePath, float startX, float startY) {
        texture = new Texture(texturePath);
        float centerX = startX - BALL_SIZE / 2;
        startPos = new Vector2(centerX, startY);
        pos = new Vector2(centerX, startY);
        velocity = new Vector2(0, 0);
    }

    @Override
    public void update(Viewport viewport, float delta) {
        velocity.y += GRAVITY * delta;
        velocity.x *= 0.98f;
        pos.add(velocity.x * delta, velocity.y * delta);

        boundaries(viewport, BOUNCE_FACTOR);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, BALL_SIZE, BALL_SIZE);
    }

    private void boundaries(Viewport viewport, float bounce) {
        if (pos.x < 0) {
            pos.x = 0;
            velocity.x = -velocity.x * bounce;
        }
        if (pos.x + BALL_SIZE > viewport.getWorldWidth()) {
            pos.x = viewport.getWorldWidth() - BALL_SIZE;
            velocity.x = -velocity.x * bounce;
        }
        if (pos.y < 0) {
            pos.y = 0;
            velocity.y = -velocity.y * bounce;
        }
        if (pos.y + BALL_SIZE > viewport.getWorldHeight()) {
            pos.y = viewport.getWorldHeight() - BALL_SIZE;
            velocity.y = -velocity.y * bounce;
        }
    }

    /**
     * Resets the ball to its initial position and sets velocity to zero
     */
    public void reset() {
        // Reset position to initial values
        setPosition(startPos);
        // Reset velocity to zero
        setVelocity(new Vector2(0, 0));
    }

    @Override
    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void collision(Collideable other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.collision(this);
        }
    }

    @Override
    public boolean collides(Collideable other) {
        Vector2 otherPos = other.getPosition();

        // Simple rectangular collision detection
        boolean xOverlap = pos.x < otherPos.x + other.getWidth() &&
                pos.x + BALL_SIZE > otherPos.x;

        boolean yOverlap = pos.y < otherPos.y + other.getWidth() &&
                pos.y + BALL_SIZE > otherPos.y;

        return xOverlap && yOverlap;
    }

    @Override
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.pos = pos;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    @Override
    public float getWidth() {
        return BALL_SIZE;
    }
}