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

  private final Vector2 startPos;
  private Vector2 pos;
  private Vector2 velocity;
  private final Texture texture;
  private Player lastPlayerTouch;

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
    float gravity = -9.81f;
    velocity.y += gravity * delta;
    velocity.x *= 0.98f;
    pos.add(velocity.x * delta, velocity.y * delta);

    boundaries(viewport);
  }

  @Override
  public void draw(SpriteBatch batch) {
    batch.draw(texture, pos.x, pos.y, BALL_SIZE, BALL_SIZE);
  }

  private void boundaries(Viewport viewport) {
    if (pos.x < 0) {
      pos.x = 0;
      velocity.x = -velocity.x * Ball.BOUNCE_FACTOR;
    }
    if (pos.x + BALL_SIZE > viewport.getWorldWidth()) {
      pos.x = viewport.getWorldWidth() - BALL_SIZE;
      velocity.x = -velocity.x * Ball.BOUNCE_FACTOR;
    }
    if (pos.y < 0) {
      pos.y = 0;
      velocity.y = -velocity.y * Ball.BOUNCE_FACTOR;
    }
    if (pos.y + BALL_SIZE > viewport.getWorldHeight()) {
      pos.y = viewport.getWorldHeight() - BALL_SIZE;
      velocity.y = -velocity.y * Ball.BOUNCE_FACTOR;
    }
  }

  /**
   * Resets the ball to its initial position and sets velocity to zero.
   */
  public void reset() {
    setPosition(new Vector2(startPos));
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
    Vector2 normal = pos.cpy().sub(otherPos).nor();

    float relativeVelocity = velocity.dot(normal) - other.getVelocity().dot(normal);
    if (relativeVelocity > 0) {
      return;
    }

    float impulse = (-relativeVelocity) / (1 / WEIGHT + 1 / other.getWeight());
    Vector2 impulseVector = normal.scl(impulse);

    boolean isPlayer = other instanceof Player;
    boolean isFoot = other instanceof Foot;

    if (isPlayer) {
      collideWithPlayer(other, impulseVector);
    } else if (isFoot) {
      collideWithFoot(other, impulseVector);
    } else {
      velocity.add(impulseVector.scl(1 / WEIGHT));
    }

    // Apply opposite force to the other object
    other.setVelocity(other.getVelocity().sub(impulseVector.scl(1 / other.getWeight())));
  }

  @Override
  public boolean collides(Collideable other) {
    if (other instanceof Goal goal) {
      return goal.collides(this);
    }
    return rectangleCollides(other);
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
    return getWidth();
  }

  /**
   * Returns the last player that touched the ball.
   *
   * @return Player that last touched the ball
   */
  public Player getPlayerLastTouch() {
    return lastPlayerTouch;
  }

  private void collideWithPlayer(Collideable other, Vector2 impulseVector) {
    this.lastPlayerTouch = (Player) other;
    Vector2 playerVelocity = other.getVelocity();

    Vector2 boost = playerVelocity.cpy();
    if (playerVelocity.len() > 0.3f) {
      boost.y += 2f;
    }
    velocity.set(impulseVector.scl(1 / WEIGHT).add(boost));
  }

  private void collideWithFoot(Collideable other, Vector2 impulseVector) {
    Foot foot = (Foot) other;

    if (foot.isKicking()) {
      float kickBoostFactor = foot.getKickPower();
      float impulseY = 10f;
      velocity.set(impulseVector.scl(1 / WEIGHT).add(new Vector2(kickBoostFactor, impulseY)));
    }

    this.lastPlayerTouch = foot.getPlayer();
  }

}