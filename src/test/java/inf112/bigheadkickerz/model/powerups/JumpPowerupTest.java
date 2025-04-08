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
class JumpPowerupTest {

  private Player player;
  private JumpPowerup bigJumpPowerup;
  private JumpPowerup smallJumpPowerup;
  private static final float JUMP_MULTIPLIER = 0;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    smallJumpPowerup = new JumpPowerup(DURATION, JUMP_MULTIPLIER, false);
    bigJumpPowerup = new JumpPowerup(DURATION, JUMP_MULTIPLIER, true);
  }

  @Test
  void testApplyJumpBoost() {
    when(player.getJumpHeight()).thenReturn(5f);

    bigJumpPowerup.apply(player);

    verify(player).setJumpHeight(0);
  }

  @Test
  void testApplySmallJumpBoost() {
    when(player.getJumpHeight()).thenReturn(5f);

    smallJumpPowerup.apply(player);

    verify(player).setJumpHeight(0);
  }

  @Test
  void testConstructorNotNullBig() {
    JumpPowerup newPowerup = new JumpPowerup(DURATION, JUMP_MULTIPLIER, true);
    assertNotNull(newPowerup);
  }

  @Test
  void testConstructorNotNullSmall() {
    JumpPowerup newPowerup = new JumpPowerup(DURATION, JUMP_MULTIPLIER, false);
    assertNotNull(newPowerup);
  }
}