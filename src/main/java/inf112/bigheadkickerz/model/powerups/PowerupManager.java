package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PowerupManager {
    // Use a singleton for easy access (or manage it from your GameModel)
    private static PowerupManager instance;
    
    public static PowerupManager getInstance() {
        if (instance == null) {
            instance = new PowerupManager();
        }
        return instance;
    }
    
    private static class ActivePowerup {
        Player player;
        Powerup powerup;
        float timeLeft;
        public ActivePowerup(Player player, Powerup powerup) {
            this.player = player;
            this.powerup = powerup;
            this.timeLeft = powerup.getDuration();
        }
    }
    
    private List<ActivePowerup> activePowerups = new ArrayList<>();
    
    public void addPowerup(Player player, Powerup powerup) {
        activePowerups.add(new ActivePowerup(player, powerup));
    }
    
    public void update(float delta) {
        Iterator<ActivePowerup> it = activePowerups.iterator();
        while (it.hasNext()) {
            ActivePowerup ap = it.next();
            ap.timeLeft -= delta;
            if (ap.timeLeft <= 0) {
                ap.powerup.expire(ap.player);
                it.remove();
            }
        }
    }
}
