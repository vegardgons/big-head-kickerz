package inf112.bigheadkickerz.model.powerups;

import inf112.bigheadkickerz.model.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Class to manage powerups. */
public class PowerupManager {

  /**
   * Private constructor to prevent instantiation.
   */
  private PowerupManager() {
    // Prevent instantiation
  }

  private static final List<ActivePowerup> activePowerups = new ArrayList<>();

  /**
   * Adds a powerup to the player.
   *
   * @param player  the player to add the powerup to
   * @param powerup the powerup to add
   */
  public static void addPowerup(Player player, Powerup powerup) {
    PowerupManager.activePowerups.add(new ActivePowerup(player, powerup));
  }

  /**
   * Update the powerup manager. This should be called every frame.
   *
   * @param delta the time since the last frame in seconds
   */
  public static void update(float delta) {
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
}
