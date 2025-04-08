package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the JumpPowerup class.
 */
class JumpPowerupTest {

  private Player player;
  private JumpPowerup bigJumpPowerup;
  private JumpPowerup smallJumpPowerup;
  private static final float BIG_JUMP_MULTIPLIER = 1.5f;
  private static final float SMALL_JUMP_MULTIPLIER = 0.5f;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    smallJumpPowerup = new JumpPowerup(DURATION, SMALL_JUMP_MULTIPLIER, false);
    bigJumpPowerup = new JumpPowerup(DURATION, BIG_JUMP_MULTIPLIER, true);
  }

  @Test
  void testApplyBigJumpBoost() {
    when(player.getJumpHeight()).thenReturn(5f);

    bigJumpPowerup.apply(player);

    verify(player).setJumpHeight(5f * BIG_JUMP_MULTIPLIER);
  }

  @Test
  void testApplySmallJumpBoost() {
    when(player.getJumpHeight()).thenReturn(5f);

    smallJumpPowerup.apply(player);

    verify(player).setJumpHeight(5f * SMALL_JUMP_MULTIPLIER);
  }

  @Test
  void testExpireBigJumpBoost() {
    when(player.getJumpHeight()).thenReturn(5f);

    bigJumpPowerup.expire(player);

    verify(player).setJumpHeight(5f / BIG_JUMP_MULTIPLIER);
  }

  @Test
  void testExpireSmallJumpBoost() {
    when(player.getJumpHeight()).thenReturn(5f);

    smallJumpPowerup.expire(player);

    verify(player).setJumpHeight(5f / SMALL_JUMP_MULTIPLIER);
  }

  @Test
  void testConstructorNotNullBig() {
    JumpPowerup newPowerup = new JumpPowerup(DURATION, BIG_JUMP_MULTIPLIER, true);
    assertNotNull(newPowerup);
  }

  @Test
  void testConstructorNotNullSmall() {
    JumpPowerup newPowerup = new JumpPowerup(DURATION, SMALL_JUMP_MULTIPLIER, false);
    assertNotNull(newPowerup);
  }
}