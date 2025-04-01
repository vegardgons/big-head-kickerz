package inf112.bigheadkickerz.model.powerups;

import java.util.Random;

/**
 * A factory to randomly generate a powerup.
 */
public class PowerupFactory {
  private static final Random random = new Random();

  /**
   * Generates a random powerup.
   *
   * @return a random powerup
   */
  public static Powerup getRandomPowerup() {
    int rand = random.nextInt(7); // generates a value from 0 to 6

    switch (rand) {
      case 0:
        return new SuperSpeedPowerup(5f, 1.5f); // e.g. 5 sec duration, 1.5× speed
      case 1:
        return new SuperSlowPowerup(5f, 0.5f); // 5 sec, 50% speed
      case 2:
        return new LowGravityPowerup(5f, 0.5f); // 5 sec, 50% gravity
      case 3:
        return new SuperKickPowerup(5f, 2f); // 5 sec, 2× kick power
      case 4:
        return new BiggerPowerup(5f, 1.5f); // 5 sec, 1.5× size
      case 5:
        return new SmallerPowerup(5f, 0.75f); // 5 sec, 75% size
      case 6:
        return new SuperJumpPowerup(5f, 2f); // 5 sec, 2× jump power
      default:
        return new SuperSpeedPowerup(5f, 1.5f);
    }
  }
}
