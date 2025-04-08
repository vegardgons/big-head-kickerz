package inf112.bigheadkickerz.model.powerups;

import java.security.SecureRandom;

/**
 * A factory to randomly generate a powerup.
 */
public final class PowerupFactory {
  private static final SecureRandom random = new SecureRandom();
  private static final float DURATION = 8f;

  /**
   * Private constructor to prevent instantiation.
   */
  private PowerupFactory() {
    // Prevent instantiation
  }

  /**
   * Generates a random powerup.
   *
   * @return a random powerup
   */
  public static Powerup getRandomPowerup() {
    int rand = random.nextInt(6);

    return switch (rand) {
      case 0 -> new SuperSpeedPowerup(DURATION, 1.5f);
      case 1 -> new SuperSlowPowerup(DURATION, 0.5f);
      case 2 -> new NoJumpPowerup(DURATION, 0);
      case 3 -> new BiggerPowerup(DURATION, 1.5f);
      case 4 -> new SmallerPowerup(DURATION, 0.75f);
      case 5 -> new SuperJumpPowerup(DURATION, 1.5f);
      default -> throw new IllegalStateException("Unexpected value: " + rand);
    };
  }
}
