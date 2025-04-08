package inf112.bigheadkickerz.model.powerups;

/**
 * A factory to randomly generate a powerup.
 */
public final class PowerupFactory {
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
  public static Powerup getRandomPowerup(int rand) {

    return switch (rand) {
      case 0 -> new SpeedPowerup(DURATION, 1.5f, true);
      case 1 -> new SpeedPowerup(DURATION, 0.5f, false);
      case 2 -> new JumpPowerup(DURATION, 0.5f, false);
      case 3 -> new JumpPowerup(DURATION, 1.5f, true);
      case 4 -> new SizePowerup(DURATION, 1.5f, true);
      case 5 -> new SizePowerup(DURATION, 0.75f, false);
      default -> throw new IllegalStateException("Unexpected value: " + rand);
    };
  }
}
