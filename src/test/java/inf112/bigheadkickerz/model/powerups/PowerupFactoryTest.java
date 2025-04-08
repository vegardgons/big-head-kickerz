package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.SecureRandom;
import org.junit.jupiter.api.Test;

class PowerupFactoryTest {

  SecureRandom random = new SecureRandom();

  @Test
  void testGetRandomPowerupReturnsNonNullPowerup() {
    int rand = random.nextInt(6);
    Powerup powerup = PowerupFactory.getRandomPowerup(rand);
    assertNotNull(powerup);
  }

  @Test
  void testFasterPowerup() {
    int rand = 0;
    Powerup powerup = PowerupFactory.getRandomPowerup(rand);
    assertNotNull(powerup);
    assert (powerup instanceof SpeedPowerup);
  }

  @Test
  void testSlowerPowerup() {
    int rand = 1;
    Powerup powerup = PowerupFactory.getRandomPowerup(rand);
    assertNotNull(powerup);
    assert (powerup instanceof SpeedPowerup);
  }

  @Test
  void testSmallerJumpPowerup() {
    int rand = 2;
    Powerup powerup = PowerupFactory.getRandomPowerup(rand);
    assertNotNull(powerup);
    assert (powerup instanceof JumpPowerup);
  }

  @Test
  void testHigherJumpPowerup() {
    int rand = 3;
    Powerup powerup = PowerupFactory.getRandomPowerup(rand);
    assertNotNull(powerup);
    assert (powerup instanceof JumpPowerup);
  }

  @Test
  void testBiggerPowerup() {
    int rand = 4;
    Powerup powerup = PowerupFactory.getRandomPowerup(rand);
    assertNotNull(powerup);
    assert (powerup instanceof SizePowerup);
  }

  @Test
  void testSmallerPowerup() {
    int rand = 5;
    Powerup powerup = PowerupFactory.getRandomPowerup(rand);
    assertNotNull(powerup);
    assert (powerup instanceof SizePowerup);
  }

  @Test
  void testDefaultException() {
    int rand = 6;
    try {
      PowerupFactory.getRandomPowerup(rand);
    } catch (IllegalStateException e) {
      assertNotNull(e);
    }
  }

}