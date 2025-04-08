package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.Player;

/**
 * Decreases the player's movement speed.
 */
public class SpeedPowerup extends AbstractPowerup {
  private final float slowMultiplier;
  private final boolean speedBoost;

  /**
   * Constructor for SuperSlowPowerup.
   *
   * @param duration       the duration of the powerup
   * @param slowMultiplier the multiplier for the player's movement speed
   */
  public SpeedPowerup(float duration, float slowMultiplier, boolean speedBoost) {
    super(duration);
    this.slowMultiplier = slowMultiplier;
    this.speedBoost = speedBoost;
  }

  @Override
  public void apply(Player player) {
    player.setMovementSpeed(player.getMovementSpeed() * slowMultiplier);
  }

  @Override
  public void expire(Player player) {
    player.setMovementSpeed(player.getMovementSpeed() / slowMultiplier);
  }

  @Override
  public Texture getTexture() {
    if (speedBoost) {
      return new Texture("powerups/increase_speed.png");
    } else {
      return new Texture("powerups/decrease_speed.png");
    }
  }
}
