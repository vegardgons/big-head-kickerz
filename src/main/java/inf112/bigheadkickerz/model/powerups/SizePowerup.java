package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.IPlayerPowerup;

/**
 * Increases the player's size.
 */
public class SizePowerup extends AbstractPowerup {
  private final float sizeMultiplier;
  private final boolean bigger;

  /**
   * Creates a new SizePowerup.
   *
   * @param duration       the duration of the powerup
   * @param sizeMultiplier the size multiplier
   * @param bigger         true if the player should grow, false if the player
   *                       should shrink
   */
  public SizePowerup(float duration, float sizeMultiplier, boolean bigger) {
    super(duration);
    this.sizeMultiplier = sizeMultiplier;
    this.bigger = bigger;
  }

  @Override
  public void apply(IPlayerPowerup player) {
    player.setHeight(player.getHeight() * sizeMultiplier);
    player.setWidth(player.getWidth() * sizeMultiplier);
  }

  @Override
  public void expire(IPlayerPowerup player) {
    player.setHeight(player.getHeight() / sizeMultiplier);
    player.setWidth(player.getWidth() / sizeMultiplier);
  }

  @Override
  public Texture getTexture() {
    if (bigger) {
      return new Texture("powerups/grow.png");
    } else {
      return new Texture("powerups/shrink.png");
    }
  }
}
