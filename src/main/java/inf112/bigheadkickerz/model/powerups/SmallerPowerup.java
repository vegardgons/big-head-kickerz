package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;

/**
 * Decreases the player's size.
 */
public class SmallerPowerup extends AbstractPowerup {
  private final float sizeMultiplier;

  public SmallerPowerup(float duration, float sizeMultiplier) {
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
}
