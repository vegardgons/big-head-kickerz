package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.Player;

/**
 * Decreases the player's movement speed.
 */
public class SuperSlowPowerup extends AbstractPowerup {
  private final float slowMultiplier;

  /**
   * Constructor for SuperSlowPowerup.
   *
   * @param duration       the duration of the powerup
   * @param slowMultiplier the multiplier for the player's movement speed
   */
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

  @Override
  public Texture getTexture() {
    return new Texture("powerups/decrease_speed.png");
  }
}
