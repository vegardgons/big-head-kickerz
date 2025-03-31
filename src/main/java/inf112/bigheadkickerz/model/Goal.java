package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Goal implements GameObject, Collideable {

    private Texture texture;
    private Vector2 pos;
    private static final float WIDTH = 1.6f;
    private static final float HEIGHT = 3f;
    private static final float WEIGHT = 1000;

    public Goal(String texturePath, float x, float y, boolean rightGoal) {
        texture = new Texture(texturePath);
        pos = new Vector2(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, WIDTH, HEIGHT);
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public Vector2 getVelocity() {
        return new Vector2(0, 0);
    }

    @Override
    public void collision(Collideable other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            Vector2 playerPos = player.getPosition();
            Vector2 playerVelocity = player.getVelocity();

            if (playerPos.y + player.getHeight() < HEIGHT) {
                player.setVelocity(new Vector2(playerVelocity.x, 0));
            } else {
                player.setPosition(new Vector2(playerPos.x, HEIGHT - 0.15f));
                player.setVelocity(new Vector2(playerVelocity.x, 0));
            }
        } else if (other instanceof Ball) {
            Ball ball = (Ball) other;
            Vector2 ballPos = ball.getPosition();
            Vector2 ballVelocity = ball.getVelocity();

            if (ballPos.y + ball.getHeight() < HEIGHT) {
                ball.setVelocity(new Vector2(ballVelocity.x, 0));
            } else {
                ball.setPosition(new Vector2(ballPos.x, HEIGHT - 0.1f));
                ball.setVelocity(new Vector2(ballVelocity.x, -ballVelocity.y * 0.8f));
            }
        }
    }

    @Override
    public boolean collides(Collideable other) {
        float otherX = other.getPosition().x;
        float otherRightX = otherX + other.getWidth();
        float otherY = other.getPosition().y;
        float otherHeight = other.getHeight();

        float crossbarY = HEIGHT - 0.1f;
        float crossbarXEnd = pos.x + WIDTH;

        boolean withinGoalWidth = otherRightX > pos.x && otherX < crossbarXEnd;

        boolean hitsCrossbarFromBelow = otherY < crossbarY && otherY + otherHeight > crossbarY
                && other.getVelocity().y > 0;
        boolean hitsCrossbarFromAbove = otherY + otherHeight > crossbarY && otherY < crossbarY + 0.2f
                && other.getVelocity().y < 0;

        return withinGoalWidth && (hitsCrossbarFromBelow || hitsCrossbarFromAbove);
    }

    @Override
    public void update(Viewport viewport, float delta) {

    }

    @Override
    public void setVelocity(Vector2 velocity) {

    }

    @Override
    public void setPosition(Vector2 pos) {

    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

}
