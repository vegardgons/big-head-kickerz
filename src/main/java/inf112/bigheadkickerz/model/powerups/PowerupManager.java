package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Class to manage powerups. */
public class PowerupManager {
  // Use a singleton for easy access
  private static PowerupManager instance;

  /**
   * Get the singleton instance of PowerupManager.
   *
   * @return the singleton instance of PowerupManager
   */
  public static PowerupManager getInstance() {
    if (instance == null) {
      instance = new PowerupManager();
    }
    return instance;
  }

  private static class ActivePowerup {
    final Player player;
    final Powerup powerup;
    float timeLeft;

    public ActivePowerup(Player player, Powerup powerup) {
      this.player = player;
      this.powerup = powerup;
      this.timeLeft = powerup.getDuration();
    }
  }

  private final List<ActivePowerup> activePowerups = new ArrayList<>();

  public void addPowerup(Player player, Powerup powerup) {
    activePowerups.add(new ActivePowerup(player, powerup));
  }

  /**
   * Update the powerup manager. This should be called every frame.
   *
   * @param delta the time since the last frame in seconds
   */
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
