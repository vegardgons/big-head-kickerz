package inf112.bigheadkickerz.model.powerups;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.Player;

/**
 * Increases the player's JumpHeight.
 */
public class NoJumpPowerup extends AbstractPowerup {
  private final float jumpBoostMultiplier;
  private static final float JUMP_HEIGHT = 5f;

  /**
   * Constructor for SuperJumpPowerup.
   *
   * @param duration            the duration of the powerup
   * @param jumpBoostMultiplier the multiplier for the player's jump height
   */
  public NoJumpPowerup(float duration, float jumpBoostMultiplier) {
    super(duration);
    this.jumpBoostMultiplier = jumpBoostMultiplier;
  }

  @Override
  public void apply(Player player) {
    player.setJumpHeight(player.getJumpHeight() * jumpBoostMultiplier);
  }

  @Override
  public void expire(Player player) {
    player.setJumpHeight(JUMP_HEIGHT);
  }

  @Override
  public Texture getTexture() {
    return new Texture("powerups/decrease_jump.png");
  }
}
