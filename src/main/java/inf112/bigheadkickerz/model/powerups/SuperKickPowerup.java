package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;

/**
 * Increases the player's kick power.
 */
public class SuperKickPowerup extends AbstractPowerup {
    private float kickBoostMultiplier;

    public SuperKickPowerup(float duration, float kickBoostMultiplier) {
        super(duration);
        this.kickBoostMultiplier = kickBoostMultiplier;
    }

    @Override
    public void apply(Player player) {
        player.setKickPower(player.getKickPower() * kickBoostMultiplier);
    }

    @Override
    public void expire(Player player) {
        player.setKickPower(player.getKickPower() / kickBoostMultiplier);
    }
}
