package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;

/**
 * Increases the player's movement speed.
 */
public class SuperSpeedPowerup extends AbstractPowerup {
  private final float speedMultiplier;

  /**
   * Constructor for SuperSpeedPowerup.
   *
   * @param duration        the duration of the powerup
   * @param speedMultiplier the multiplier for the player's movement speed
   */
  public SuperSpeedPowerup(float duration, float speedMultiplier) {
    super(duration);
    this.speedMultiplier = speedMultiplier;
  }

  @Override
  public void apply(Player player) {
    player.setMovementSpeed(player.getMovementSpeed() * speedMultiplier);
  }

  @Override
  public void expire(Player player) {
    player.setMovementSpeed(player.getMovementSpeed() / speedMultiplier);
  }
}
