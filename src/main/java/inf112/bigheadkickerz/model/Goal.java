package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Class for the goal object.
 */
public class Goal implements GameObject, Collideable {

  private final Texture texture;
  private Vector2 pos;
  private static final float WIDTH = 1.6f;
  private static final float HEIGHT = 3f;
  private static final float WEIGHT = 1000;

  /**
   * Constructor for the goal object.
   *
   * @param texture the texture of the goal
   * @param x       the x position of the goal
   * @param y       the y position of the goal
   */
  public Goal(Texture texture, float x, float y) {
    this.texture = texture;
    this.pos = new Vector2(x, y);

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
    float crossbarY = pos.y + (HEIGHT - 0.2f);
    Vector2 vel = other.getVelocity();
    if (other.getPosition().y + other.getHeight() > crossbarY
        && vel.y > 0) {
      float newY = crossbarY - other.getHeight();
      other.setPosition(new Vector2(other.getPosition().x, newY));
      vel.y = -vel.y * 0.7f;
      other.setVelocity(vel);
    } else if (other.getPosition().y < crossbarY && vel.y < 0) {
      float newY = crossbarY + 0.2f;
      other.setPosition(new Vector2(other.getPosition().x, newY));
      vel.y = -vel.y * 0.7f;
      other.setVelocity(vel);
    }
    vel = other.getVelocity();
    if (other.getPosition().x < getWidth()) {
      vel.x += 0.5f;
    } else {
      vel.x -= 0.5f;
    }
    other.setVelocity(vel);
  }

  @Override
  public boolean collides(Collideable other) {
    float otherX = other.getPosition().x;
    float otherRightX = otherX + other.getWidth();
    float otherY = other.getPosition().y;
    float otherHeight = other.getHeight();

    float crossbarY = HEIGHT - 0.1f;
    float crossbarEndX = pos.x + WIDTH;

    boolean withinGoalWidth = otherRightX > pos.x && otherX < crossbarEndX;

    boolean hitsCrossbarFromBelow = otherY < crossbarY && otherY + otherHeight > crossbarY
        && other.getVelocity().y > 0;
    boolean hitsCrossbarFromAbove = otherY + otherHeight > crossbarY && otherY < crossbarY + 0.2f
        && other.getVelocity().y < 0;

    return withinGoalWidth && (hitsCrossbarFromBelow || hitsCrossbarFromAbove);
  }

  @Override
  public void update(Viewport viewport, float delta) {
    // Unused method
  }

  @Override
  public void setVelocity(Vector2 velocity) {
    // Unused method
  }

  @Override
  public void setPosition(Vector2 pos) {
    this.pos = pos;
  }

  @Override
  public Vector2 getPosition() {
    return pos;
  }

}
