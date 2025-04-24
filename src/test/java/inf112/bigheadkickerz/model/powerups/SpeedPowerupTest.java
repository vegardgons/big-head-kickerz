package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the SpeedPowerup class.
 */
class SpeedPowerupTest {

  private Player player;
  private SpeedPowerup fasterPowerup;
  private SpeedPowerup slowerPowerup;
  private static final float SPEED_MULTIPLIER = 2.0f;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    fasterPowerup = new SpeedPowerup(DURATION, SPEED_MULTIPLIER, true);
    slowerPowerup = new SpeedPowerup(DURATION, SPEED_MULTIPLIER, false);
  }

  @Test
  void testApplyFasterSpeed() {
    when(player.getMovementSpeed()).thenReturn(5.0f);

    fasterPowerup.apply(player);

    verify(player).setMovementSpeed(5.0f * SPEED_MULTIPLIER);
  }

  @Test
  void testApplySlowerSpeed() {
    when(player.getMovementSpeed()).thenReturn(5.0f);

    slowerPowerup.apply(player);

    verify(player).setMovementSpeed(5.0f * SPEED_MULTIPLIER);
  }

  @Test
  void testExpirePlayerSpeedFast() {
    when(player.getMovementSpeed()).thenReturn(10.0f);

    fasterPowerup.expire(player);

    verify(player).setMovementSpeed(10.0f / SPEED_MULTIPLIER);
  }

  @Test
  void testExpirePlayerSpeedSlow() {
    when(player.getMovementSpeed()).thenReturn(10.0f);

    slowerPowerup.expire(player);

    verify(player).setMovementSpeed(10.0f / SPEED_MULTIPLIER);
  }

  @Test
  void testConstructorNotNullFast() {
    SpeedPowerup powerup = new SpeedPowerup(DURATION, SPEED_MULTIPLIER, true);
    assertNotNull(powerup);
  }

  @Test
  void testConstructorNotNullSlow() {
    SpeedPowerup powerup = new SpeedPowerup(DURATION, SPEED_MULTIPLIER, false);
    assertNotNull(powerup);
  }

  @Test
  void testGetDuration() {
    assertEquals(DURATION, fasterPowerup.getDuration());
    assertEquals(DURATION, slowerPowerup.getDuration());
  }
}
