package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.Player;

/**
 * Increases the player's JumpHeight.
 */
public class JumpPowerup extends AbstractPowerup {
  private final float jumpBoostMultiplier;
  private final boolean jumpBoost;

  /**
   * Constructor for JumpPowerup.
   *
   * @param duration            the duration of the powerup
   * @param jumpBoostMultiplier the multiplier for the jump height
   * @param jumpBoost           true if the powerup increases jump height, false
   *                            if it decreases
   */
  public JumpPowerup(float duration, float jumpBoostMultiplier, boolean jumpBoost) {
    super(duration);
    this.jumpBoostMultiplier = jumpBoostMultiplier;
    this.jumpBoost = jumpBoost;
  }

  @Override
  public void apply(Player player) {
    player.setJumpHeight(player.getJumpHeight() * jumpBoostMultiplier);
  }

  @Override
  public void expire(Player player) {
    player.setJumpHeight(player.getJumpHeight() / jumpBoostMultiplier);
  }

  @Override
  public Texture getTexture() {
    if (jumpBoost) {
      return new Texture("powerups/increase_jump.png");
    } else {
      return new Texture("powerups/decrease_jump.png");
    }
  }
}
