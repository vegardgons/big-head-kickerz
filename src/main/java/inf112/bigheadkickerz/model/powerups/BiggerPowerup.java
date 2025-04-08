package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.Player;

/**
 * Increases the player's size.
 */
public class BiggerPowerup extends AbstractPowerup {
  private final float sizeMultiplier;

  /**
   * Constructor for BiggerPowerup.
   *
   * @param duration       the duration of the powerup
   * @param sizeMultiplier the multiplier for the player's size
   */
  public BiggerPowerup(float duration, float sizeMultiplier) {
    super(duration);
    this.sizeMultiplier = sizeMultiplier;
  }

  @Override
  public void apply(Player player) {
    player.setHeight(player.getHeight() * sizeMultiplier);
    player.setWidth(player.getWidth() * sizeMultiplier);
  }

  @Override
  public void expire(Player player) {
    player.setHeight(player.getHeight() / sizeMultiplier);
    player.setWidth(player.getWidth() / sizeMultiplier);
  }

  @Override
  public Texture getTexture() {
    return new Texture("powerups/grow.png");
  }
}
