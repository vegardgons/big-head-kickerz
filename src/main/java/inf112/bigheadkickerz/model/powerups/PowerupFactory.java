package inf112.bigheadkickerz.model.powerups;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * A registry‑based factory that can create random {@link Powerup} instances.
 */
public final class PowerupFactory {

  private static final float DEFAULT_DURATION = 8f;
  private static final SecureRandom RNG = new SecureRandom();
  private static final List<Powerup> POWERUPLIST = new ArrayList<>();

  // Register the built‑in power‑ups once when the class is loaded
  static {
    register(new SpeedPowerup(DEFAULT_DURATION, 1.5f, true));
    register(new SpeedPowerup(DEFAULT_DURATION, 0.5f, false));
    register(new JumpPowerup(DEFAULT_DURATION, 0.5f, false));
    register(new JumpPowerup(DEFAULT_DURATION, 1.5f, true));
    register(new SizePowerup(DEFAULT_DURATION, 1.5f, true));
    register(new SizePowerup(DEFAULT_DURATION, 0.75f, false));
  }

  private PowerupFactory() {
  }

  /**
   * Registers a new type of power‑up.
   *
   * @param powerup that creates a fresh {@link Powerup} each
   *                time it is invoked
   */
  public static void register(Powerup powerup) {
    if (powerup == null) {
      throw new IllegalArgumentException("Powerup cannot be null");
    }
    POWERUPLIST.add(powerup);
  }

  /**
   * Removes a previously registered power‑up.
   *
   * @param powerup the powerup to remove
   */
  public static void unregister(Powerup powerup) {
    POWERUPLIST.remove(powerup);
  }

  /**
   * Returns an all currently registered Powerup.
   */
  public static List<Powerup> getPowerup() {
    return POWERUPLIST;
  }

  /**
   * Creates a new random {@link Powerup} instance.
   *
   * @return a randomly selected power‑up
   * @throws IllegalStateException if no power‑ups are registered
   */
  public static Powerup getRandomPowerup() {
    if (POWERUPLIST.isEmpty()) {
      throw new IllegalStateException("No power‑ups registered");
    }
    return POWERUPLIST.get(RNG.nextInt(POWERUPLIST.size()));
  }
}
