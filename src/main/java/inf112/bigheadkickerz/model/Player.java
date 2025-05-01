package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;
import inf112.bigheadkickerz.view.Assets;

/** Class for the player object. */
public class Player implements GameObject, Collideable, IPlayerPowerup {

  private static final float WEIGHT = 300;
  private static final float FOOT_HEIGHT = 0.3f;
  private float width = 1f;
  private float height = 1f;
  private float movementSpeed = 4f;
  private float jumpHeight = 5f;
  private final boolean isPlayer1;

  private Vector2 velocity;
  private final Vector2 startPos;
  private Vector2 pos;
  private final Texture texture;

  /** Constructor for Player. */
  public Player(Texture texture, float startX, float startY, boolean isPlayer1) {
    this.texture = texture;
    this.startPos = new Vector2(startX, startY + FOOT_HEIGHT);
    this.pos = new Vector2(startX, startY + FOOT_HEIGHT);
    this.velocity = new Vector2(0, 0);
    this.isPlayer1 = isPlayer1;
  }

  @Override
  public void update(Viewport viewport, float delta) {

    float gravity = -9.81f;
    velocity.y += gravity * delta;
    Vector2 newVel = new Vector2(velocity.x, velocity.y);
    setVelocity(new Vector2(newVel));
    pos.add(velocity.x * delta, velocity.y * delta);

    boundaries(viewport);
  }

  private void boundaries(Viewport viewport) {
    if (pos.x < 0) {
      pos.x = 0;
      velocity.x = 0;
    }
    if (pos.x + width > viewport.getWorldWidth()) {
      pos.x = viewport.getWorldWidth() - width;
      velocity.x = 0;
    }
    if (pos.y < FOOT_HEIGHT) {
      pos.y = FOOT_HEIGHT;
      velocity.y = 0;
    }
    if (pos.y + height > viewport.getWorldHeight()) {
      pos.y = viewport.getWorldHeight() - height;
      velocity.y = 0;
    }
  }

  @Override
  public void draw(SpriteBatch batch) {
    batch.draw(texture, pos.x, pos.y, width, height);
  }

  void reset() {
    setPosition(new Vector2(startPos));
    setVelocity(new Vector2(0, 0));
  }

  @Override
  public void collision(Collideable other) {
    if (other instanceof Ball || other instanceof Goal || other instanceof Foot) {
      return;
    }

    Vector2 otherPos = other.getPosition();

    float overlapX = Math.min(pos.x + width - otherPos.x, otherPos.x + other.getWidth() - pos.x);
    float overlapY = Math.min(pos.y + height - otherPos.y, otherPos.y + height - pos.y);

    if (overlapX < overlapY) {
      if (pos.x < otherPos.x) {
        setPosition(new Vector2(pos.x - overlapX / 2, pos.y));
        other.setPosition(new Vector2(otherPos.x + overlapX / 2, otherPos.y));
      } else {
        setPosition(new Vector2(pos.x + overlapX / 2, pos.y));
        other.setPosition(new Vector2(otherPos.x - overlapX / 2, otherPos.y));
      }
    } else {
      if (pos.y < otherPos.y) {
        other.setPosition(new Vector2(otherPos.x, otherPos.y + overlapY / 2));
      } else {
        setPosition(new Vector2(pos.x, pos.y + overlapY / 2));
      }
    }

    Vector2 tempVel = velocity.cpy();
    velocity.set(other.getVelocity());
    other.setVelocity(tempVel);
  }

  @Override
  public boolean collides(Collideable other) {
    if (other instanceof Goal goal) {
      return goal.collides(this);
    }
    if (other instanceof PowerupPickup) {
      return false;
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
  public Vector2 getVelocity() {
    return velocity.cpy();
  }

  @Override
  public void setHeight(float height) {
    this.height = height;
  }

  @Override
  public void setWidth(float width) {
    this.width = width;
  }

  @Override
  public float getMovementSpeed() {
    return movementSpeed;
  }

  @Override
  public void setMovementSpeed(float movementSpeed) {
    this.movementSpeed = movementSpeed;
  }

  @Override
  public float getJumpHeight() {
    return jumpHeight;
  }

  @Override
  public void setJumpHeight(float jumpHeight) {
    this.jumpHeight = jumpHeight;
  }

  /**
   * Sets the jump height of the player.
   *
   * @return true if jump height was changed, false otherwise
   */
  public boolean jump() {
    if (velocity.y == 0) {
      velocity.y = jumpHeight;
      Assets.playJumpingSound();
      return true;
    }
    return false;
  }

  /**
   * Returns if the player is player 1 or not.
   *
   * @return true if the player is player 1, false otherwise
   */
  public boolean isPlayer1() {
    return isPlayer1;
  }

  @Override
  public boolean setDirection(int direction) {
    if (direction == 1) {
      setVelocity(new Vector2(movementSpeed, velocity.y));
    } else if (direction == -1) {
      setVelocity(new Vector2(-movementSpeed, velocity.y));
    } else {
      setVelocity(new Vector2(0, velocity.y));
    }
    return true;
  }

}
