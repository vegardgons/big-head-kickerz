package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.Player;

/**
 * An interface for a powerup effect.
 */
public interface Powerup {
  /**
   * Apply this powerup effect to the player.
   */
  void apply(Player player);

  /**
   * Remove (expire) this powerup effect from the player.
   */
  void expire(Player player);

  /**
   * Returns the duration of this powerup effect in seconds.
   */
  float getDuration();

  /**
   * Returns the texture of this powerup.
   */
  Texture getTexture();
}
