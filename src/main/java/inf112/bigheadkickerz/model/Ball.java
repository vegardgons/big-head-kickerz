package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Class for the ball object. */
public class Ball implements GameObject, Collideable {

  private static final float BALL_SIZE = 0.6f;
  private static final float WEIGHT = 0.5f;
  private static final float BOUNCE_FACTOR = 0.7f;
  private float gravity = -9.81f;

  private Vector2 startPos;
  private Vector2 pos;
  private Vector2 velocity;
  private Texture texture;

  /** Constructor for Ball. */
  public Ball(Texture texture, float startX, float startY) {
    this.texture = texture;
    float centerX = startX - BALL_SIZE / 2;
    startPos = new Vector2(centerX, startY);
    pos = new Vector2(centerX, startY);
    velocity = new Vector2(0, 0);
  }

  @Override
  public void update(Viewport viewport, float delta) {
    velocity.y += gravity * delta;
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
   * Resets the ball to its initial position and sets velocity to zero.
   */
  public void reset() {
    // Reset position to initial values
    setPosition(new Vector2(startPos));

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
    if (other instanceof Goal) {
      return;
    }

    Vector2 otherPos = other.getPosition();
    Vector2 normal = pos.cpy().sub(otherPos).nor(); // Normalized collision direction

    // Basic elastic collision
    float relativeVelocity = velocity.dot(normal) - other.getVelocity().dot(normal);
    if (relativeVelocity > 0) {
      return; // Ignore separating objects
    }

    float impulse = (-relativeVelocity) / (1 / WEIGHT + 1 / other.getWeight());
    Vector2 impulseVector = normal.scl(impulse);

    boolean isPlayer = other instanceof Player;

    if (isPlayer) {
      Vector2 playerVelocity = other.getVelocity();

      // Boost the ball more when hit by a player
      Vector2 kickBoost = playerVelocity.cpy();
      if (playerVelocity.len() > 0.3f) { // Lower threshold to avoid excessive boosts
        kickBoost.y += 2f; // Lowered from 6f
      }
      // Extra boost if the player is kicking
      Player player = (Player) other;
      if (player.isKicking() && playerVelocity.len() > 0.1f) {
        Vector2 facing = playerVelocity.cpy().nor();
        float dot = facing.dot(normal);
        if (dot > 0.7f) {
          Vector2 extraBoost = facing.scl(player.getKickPower());
          kickBoost.add(extraBoost);
        }
      }
      velocity.set(impulseVector.scl(1 / WEIGHT).add(kickBoost));
    } else {
      velocity.add(impulseVector.scl(1 / WEIGHT));
    }

    // Apply opposite force to the other object
    other.setVelocity(other.getVelocity().sub(impulseVector.scl(1 / other.getWeight())));
  }

  @Override
  public boolean collides(Collideable other) {
    if (other instanceof Goal) {
      Goal goal = (Goal) other;
      return goal.collides(this);
    }

    Vector2 otherPos = other.getPosition();
    float otherWidth = other.getWidth();
    float otherHeight = other.getHeight();

    boolean xOverlap = pos.x + BALL_SIZE > otherPos.x && otherPos.x + otherWidth > pos.x;
    boolean yOverlap = pos.y + BALL_SIZE > otherPos.y && otherPos.y + otherHeight > pos.y;

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

  @Override
  public float getHeight() {
    return BALL_SIZE;
  }

  public float getGravity() {
    return gravity;
  }

  public float setGravity(float gravity) {
    return this.gravity = gravity;
  }
}