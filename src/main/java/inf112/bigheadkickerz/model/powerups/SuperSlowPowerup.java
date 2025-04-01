package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;

/**
 * Decreases the player's movement speed.
 */
public class SuperSlowPowerup extends AbstractPowerup {
  private final float slowMultiplier;

  public SuperSlowPowerup(float duration, float slowMultiplier) {
    super(duration);
    this.slowMultiplier = slowMultiplier;
  }

  @Override
  public void apply(Player player) {
    player.setMovementSpeed(player.getMovementSpeed() * slowMultiplier);
  }

  @Override
  public void expire(Player player) {
    player.setMovementSpeed(player.getMovementSpeed() / slowMultiplier);
  }
}
