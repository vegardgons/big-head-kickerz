package inf112.bigheadkickerz.model.powerups;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * A registry‑based factory that can create random {@link Powerup} instances.
 */
public final class PowerupCatalogue {

  private static final float DEFAULT_DURATION = 8f;
  private static final SecureRandom RNG = new SecureRandom();
  private static final List<PowerupFactory> FACTORIES = Arrays.asList(
      () -> new SpeedPowerup(
          DEFAULT_DURATION, 1.5f, true),
      () -> new SpeedPowerup(
          DEFAULT_DURATION, 0.5f, false),
      () -> new JumpPowerup(
          DEFAULT_DURATION, 1.5f, true),
      () -> new JumpPowerup(
          DEFAULT_DURATION, 0.5f, false),
      () -> new SizePowerup(
          DEFAULT_DURATION, 1.5f, true),
      () -> new SizePowerup(
          DEFAULT_DURATION, 0.5f, false));

  /**
   * Returns a list of all factories.
   */
  public static List<PowerupFactory> getFactories() {
    return FACTORIES;
  }

  /**
   * Creates a new random {@link Powerup} instance.
   *
   * @return a randomly selected power‑up
   * @throws IllegalStateException if no power‑ups are registered
   */
  public static Powerup createRandomPowerup() {
    if (FACTORIES.isEmpty()) {
      throw new IllegalStateException("No power‑ups registered");
    }
    PowerupFactory f = FACTORIES.get(RNG.nextInt(FACTORIES.size()));
    return f.create();
  }
}
