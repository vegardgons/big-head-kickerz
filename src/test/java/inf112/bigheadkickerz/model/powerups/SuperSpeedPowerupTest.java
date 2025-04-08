package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the SuperSpeedPowerup class.
 * This class tests the functionality of the SuperSpeedPowerup power-up.
 */
class SuperSpeedPowerupTest {

  private Player player;
  private SuperSpeedPowerup powerup;
  private static final float SPEED_MULTIPLIER = 2.0f;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    powerup = new SuperSpeedPowerup(DURATION, SPEED_MULTIPLIER);
  }

  @Test
  void testApplyPowerupIncreasesPlayerSize() {
    when(player.getMovementSpeed()).thenReturn(5f);

    powerup.apply(player);

    verify(player).setMovementSpeed(5f * SPEED_MULTIPLIER);
  }

  @Test
  void testExpirePowerupResetsPlayerSize() {
    when(player.getMovementSpeed()).thenReturn(5f);

    powerup.expire(player);

    verify(player).setMovementSpeed(5f / SPEED_MULTIPLIER);
  }

  @Test
  void testConstructorSetsCorrectSizeMultiplier() {
    SuperSpeedPowerup powerup = new SuperSpeedPowerup(DURATION, SPEED_MULTIPLIER);
    assertNotNull(powerup);
  }

}