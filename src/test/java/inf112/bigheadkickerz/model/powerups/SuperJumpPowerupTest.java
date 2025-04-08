package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the SuperJumpPowerup class.
 * This class tests the functionality of the SuperJumpPowerup power-up.
 */
class SuperJumpPowerupTest {

  private Player player;
  private SuperJumpPowerup powerup;
  private static final float JUMP_MULTIPLIER = 2.0f;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    powerup = new SuperJumpPowerup(DURATION, JUMP_MULTIPLIER);
  }

  @Test
  void testApplyPowerupIncreasesPlayerSize() {
    when(player.getJumpHeight()).thenReturn(5f);

    powerup.apply(player);

    verify(player).setJumpHeight(5f * JUMP_MULTIPLIER);
  }

  @Test
  void testExpirePowerupResetsPlayerSize() {
    when(player.getJumpHeight()).thenReturn(5f);

    powerup.expire(player);

    verify(player).setJumpHeight(5f / JUMP_MULTIPLIER);
  }

  @Test
  void testConstructorSetsCorrectSizeMultiplier() {
    SuperJumpPowerup powerup = new SuperJumpPowerup(DURATION, JUMP_MULTIPLIER);
    assertNotNull(powerup);
  }

}