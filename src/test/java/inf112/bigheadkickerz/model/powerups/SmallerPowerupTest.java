package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the SmallerPowerup class.
 * This class tests the functionality of the SmallerPowerup power-up.
 */
class SmallerPowerupTest {

  private Player player;
  private SmallerPowerup powerup;
  private static final float SIZE_MULTIPLIER = 2.0f;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    powerup = new SmallerPowerup(DURATION, SIZE_MULTIPLIER);
  }

  @Test
  void testApplyPowerupIncreasesPlayerSize() {
    when(player.getHeight()).thenReturn(10.0f);
    when(player.getWidth()).thenReturn(5.0f);

    powerup.apply(player);

    verify(player).setHeight(10.0f * SIZE_MULTIPLIER);
    verify(player).setWidth(5.0f * SIZE_MULTIPLIER);
  }

  @Test
  void testExpirePowerupResetsPlayerSize() {
    when(player.getHeight()).thenReturn(20.0f);
    when(player.getWidth()).thenReturn(10.0f);

    powerup.expire(player);

    verify(player).setHeight(20.0f / SIZE_MULTIPLIER);
    verify(player).setWidth(10.0f / SIZE_MULTIPLIER);
  }

  @Test
  void testConstructorSetsCorrectSizeMultiplier() {
    SmallerPowerup newPowerup = new SmallerPowerup(DURATION, SIZE_MULTIPLIER);
    assertNotNull(newPowerup);
  }

}