package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the NoJumpPowerup class.
 * This class tests the functionality of the NoJumpPowerup power-up.
 */
class NoJumpPowerupTest {

  private Player player;
  private NoJumpPowerup powerup;
  private static final float JUMP_MULTIPLIER = 0;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    powerup = new NoJumpPowerup(DURATION, JUMP_MULTIPLIER);
  }

  @Test
  void testApplyPowerupIncreasesPlayerSize() {
    when(player.getJumpHeight()).thenReturn(5f);

    powerup.apply(player);

    verify(player).setJumpHeight(0);
  }

  @Test
  void testExpirePowerupResetsPlayerSize() {
    when(player.getJumpHeight()).thenReturn(5f);

    powerup.expire(player);

    verify(player).setJumpHeight(5);
  }

  @Test
  void testConstructorSetsCorrectSizeMultiplier() {
    NoJumpPowerup powerup = new NoJumpPowerup(DURATION, JUMP_MULTIPLIER);
    assertNotNull(powerup);
  }
}