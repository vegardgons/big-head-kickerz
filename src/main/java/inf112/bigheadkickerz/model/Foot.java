package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;

/**
 * Class for the foot object.
 * The foot is a part of the player and is used for collision detection.
 */
public class Foot implements GameObject, Collideable {

  private static final float WEIGHT = 300;
  private float width = 0.8f;
  private float height = 0.3f;
  private float kickPower = 3f;
  private final Vector2 startPos;
  private Vector2 pos;
  private Vector2 velocity;
  private Texture texture;
  private Player player;
  private boolean kicking = false;
  private float kickTimer = 0f;
  private float kickDuration = 0.25f;
  private float maxKickAngle = (float) Math.toRadians(5);
  private float kickRadius = 0.75f;

  /** Constructor for Foot. */
  public Foot(Texture texture, Player player) {
    this.velocity = new Vector2(0, 0);
    this.player = player;
    float startX = player.getPosition().x + (player.getWidth() / 2) - (width / 2);
    this.startPos = new Vector2(startX, 0);
    this.pos = new Vector2(startX, 0);
    this.texture = texture;
  }

  @Override
  public void update(Viewport viewport, float delta) {
    if (kicking) {
      kickTimer += delta;
      float t = kickTimer / kickDuration;

      if (t >= 1f) {
        kicking = false;
        kickTimer = 0f;
      } else {
        float angle = calculateAngle(t);
        Vector2 newPos = calculatePos(angle);
        pos.set(newPos.x, newPos.y);
      }
    } else {
      float x = player.getPosition().x + (player.getWidth() / 2) - (width / 2);
      float y = player.getPosition().y - height;
      pos.set(x, y);
    }

    velocity.set(player.getVelocity());
  }

  @Override
  public void draw(SpriteBatch batch) {
    if (kicking) {
      float t = kickTimer / kickDuration;
      float angle = calculateAngle(t);
      Vector2 newPos = calculatePos(angle);
      float rotation;
      if (player.isPlayer1()) {
        rotation = 60f;
      } else {
        rotation = -60f;
      }
      drawRotation(batch, newPos.x, newPos.y, rotation);
    } else {
      batch.draw(texture, pos.x, pos.y, width, height);
    }
  }

  @Override
  public void collision(Collideable other) {
    if (other instanceof Ball ball) {
      ball.collision(this);
    }
  }

  @Override
  public boolean collides(Collideable other) {
    if (other instanceof PowerupPickup) {
      return false;
    } else if (other instanceof Goal goal) {
      return goal.collides(this);
    } else if (other instanceof Player newPlayer) {
      if (newPlayer.isPlayer1() == this.player.isPlayer1()) {
        return false;
      } else {
        return newPlayer.collides(this);
      }
    }
    return rectangleCollides(other);
  }

  @Override
  public float getWeight() {
    return WEIGHT;
  }

  @Override
  public float getWidth() {
    return width;
  }

  @Override
  public float getHeight() {
    return height;
  }

  @Override
  public Vector2 getPosition() {
    return pos;
  }

  @Override
  public Vector2 getVelocity() {
    return velocity;
  }

  @Override
  public void setPosition(Vector2 pos) {
    // This method is not used for the foot object as it is always
    // positioned relative to the player.
  }

  @Override
  public void setVelocity(Vector2 velocity) {
    // This method is not used for the foot object as it is always
    // positioned relative to the player.
  }

  /** Resets the foot to its starting position. */
  public void reset() {
    this.pos = startPos;
    this.velocity = new Vector2(0, 0);
  }

  /** Sets the foot to the kicking position. */
  public void kick() {
    if (!kicking) {
      kicking = true;
      kickTimer = 0f;
    }
  }

  private Vector2 calculatePos(float angle) {
    Vector2 playerCenter = new Vector2(
        player.getPosition().x + player.getWidth() / 2,
        player.getPosition().y + player.getHeight() / 2);

    float x;
    if (player.isPlayer1()) {
      x = playerCenter.x + (float) Math.cos(angle) * kickRadius - width / 2;
    } else {
      x = playerCenter.x - (float) Math.cos(angle) * kickRadius - width / 2;
    }
    float y = playerCenter.y - height / 2 + (float) Math.sin(angle) * kickRadius;
    return new Vector2(x, y);
  }

  private void drawRotation(SpriteBatch batch, float x, float y, float rotation) {
    batch.draw(
        texture,
        x, y,
        width / 2, height / 2,
        width, height,
        1f, 1f,
        rotation,
        0, 0,
        texture.getWidth(), texture.getHeight(),
        false, false);
  }

  private float calculateAngle(float t) {
    float smoothT;
    if (t < 0.5f) {
      smoothT = 4 * t * t * t;
    } else {
      smoothT = (float) (1 - Math.pow(-2 * t + 2, 3) / 2);
    }
    return smoothT * maxKickAngle;
  }

  /**
   * Returns the player that the foot belongs to.
   *
   * @return the player that the foot belongs to
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the kick power of the foot.
   *
   * @return the kick power of the foot
   */
  public float getKickPower() {
    return kickPower;
  }

  /**
   * Returns if the foot is kicking.
   *
   * @return true if the foot is kicking, false otherwise
   */
  public boolean isKicking() {
    return kicking;
  }
}
