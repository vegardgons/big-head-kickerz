package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;

/**
 * Increases the player's JumpHeight.
 */
public class SuperJumpPowerup extends AbstractPowerup {
  private final float jumpBoostMultiplier;

  public SuperJumpPowerup(float duration, float jumpBoostMultiplier) {
    super(duration);
    this.jumpBoostMultiplier = jumpBoostMultiplier;
  }

  @Override
  public void apply(Player player) {
    // Assumes Player has getKickPower() and setKickPower() methods.
    player.setJumpHeight(player.getJumpHeight() * jumpBoostMultiplier);
  }

  @Override
  public void expire(Player player) {
    player.setJumpHeight(player.getJumpHeight() / jumpBoostMultiplier);
  }
}
