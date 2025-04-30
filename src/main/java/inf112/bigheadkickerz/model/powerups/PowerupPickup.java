package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.Collideable;
import inf112.bigheadkickerz.model.GameObject;

/**
 * Class representing a powerup pickup in the game.
 * It is a collectible item that players can pick up to gain powerups.
 */
public class PowerupPickup implements GameObject, Collideable {
  private final Powerup powerup;
  private Vector2 pos;
  private final Texture texture;
  private final float size;
  private boolean collected = false;

  /**
   * Constructor for PowerupPickup.
   *
   * @param powerup The powerup associated with this pickup.
   * @param pos     The position of the pickup in the game world.
   * @param texture The texture to be used for rendering the pickup.
   * @param size    The size of the pickup.
   */
  public PowerupPickup(Powerup powerup, Vector2 pos, Texture texture, float size) {
    this.powerup = powerup;
    this.pos = pos;
    this.texture = texture;
    this.size = size;
  }

  @Override
  public void update(Viewport viewport, float delta) {
    // Do nothing
  }

  @Override
  public void draw(SpriteBatch batch) {
    if (!collected) {
      batch.draw(texture, pos.x, pos.y, size, size);
    }
  }

  @Override
  public float getWeight() {
    return 1;
  }

  @Override
  public Vector2 getVelocity() {
    return new Vector2(0, 0);
  }

  @Override
  public void collision(Collideable other) {
    if (!collected && other instanceof Ball ball) {
      powerup.apply(ball.getPlayerLastTouch());
      PowerupManager.addPowerup(ball.getPlayerLastTouch(), powerup);
      collected = true;
    }
  }

  @Override
  public boolean collides(Collideable other) {
    if (other instanceof Ball) {
      Vector2 otherPos = other.getPosition();
      float otherWidth = other.getWidth();
      float otherHeight = other.getHeight();

      boolean overlapX = pos.x < otherPos.x + otherWidth && pos.x + getWidth() > otherPos.x;
      boolean overlapY = pos.y < otherPos.y + otherHeight && pos.y + getHeight() > otherPos.y;
      return overlapX && overlapY;
    }
    return false;
  }

  @Override
  public void setVelocity(Vector2 velocity) {
    // No movement
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
    return size;
  }

  @Override
  public float getHeight() {
    return getWidth();
  }

  @Override
  public boolean setDirection(int direction) {
    // No direction for powerup pickup
    return false;
  }

  public boolean isCollected() {
    return collected;
  }

}
