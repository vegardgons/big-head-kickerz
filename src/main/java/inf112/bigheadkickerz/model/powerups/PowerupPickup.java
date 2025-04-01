package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.Collideable;
import inf112.bigheadkickerz.model.GameObject;
import inf112.bigheadkickerz.model.Player;

/**
 * Class representing a powerup pickup in the game.
 * It is a collectible item that players can pick up to gain powerups.
 */
public class PowerupPickup implements GameObject, Collideable {
  private Powerup powerup;
  private Vector2 pos;
  private Texture texture;
  private float size; // size of the pickup
  private boolean collected = false; // flag to mark if it has been picked up

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
    // Optional: Add a simple animation or floating effect
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
    if (!collected && other instanceof Player) {
      Player player = (Player) other;
      powerup.apply(player);
      PowerupManager.getInstance().addPowerup(player, powerup);
      collected = true;
    }
  }

  @Override
  public boolean collides(Collideable other) {
    Vector2 otherPos = other.getPosition();
    float otherWidth = other.getWidth();
    float otherHeight = other.getHeight();

    boolean overlapX = pos.x < otherPos.x + otherWidth && pos.x + size > otherPos.x;
    boolean overlapY = pos.y < otherPos.y + otherHeight && pos.y + size > otherPos.y;
    return overlapX && overlapY;
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
    return size;
  }

  public boolean isCollected() {
    return collected;
  }
}
