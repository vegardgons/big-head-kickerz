package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;

/**
 * Reduces the gravity affecting the player.
 */
public class LowGravityPowerup extends AbstractPowerup {
    private float gravityMultiplier;

    public LowGravityPowerup(float duration, float gravityMultiplier) {
        super(duration);
        this.gravityMultiplier = gravityMultiplier;
    }

    @Override
    public void apply(Player player) {
        player.setGravity(player.getGravity() * gravityMultiplier);        
    }

    @Override
    public void expire(Player player) {
        player.setGravity(player.getGravity() / gravityMultiplier);
    }
}
