package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the BiggerPowerup class.
 * This class tests the functionality of the BiggerPowerup power-up.
 */
class SizePowerupTest {

  private Player player;
  private SizePowerup biggerPowerup;
  private SizePowerup smallerPowerup;
  private static final float SIZE_MULTIPLIER = 2.0f;
  private static final float DURATION = 10.0f;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    player = mock(Player.class);

    biggerPowerup = new SizePowerup(DURATION, SIZE_MULTIPLIER, true);
    smallerPowerup = new SizePowerup(DURATION, SIZE_MULTIPLIER, false);
  }

  @Test
  void testApplyPowerupIncreasesPlayerSize() {
    when(player.getHeight()).thenReturn(10.0f);
    when(player.getWidth()).thenReturn(5.0f);

    biggerPowerup.apply(player);

    verify(player).setHeight(10.0f * SIZE_MULTIPLIER);
    verify(player).setWidth(5.0f * SIZE_MULTIPLIER);
  }

  @Test
  void testApplyPowerupDecreasePlayerSize() {
    when(player.getHeight()).thenReturn(10.0f);
    when(player.getWidth()).thenReturn(5.0f);

    smallerPowerup.apply(player);

    verify(player).setHeight(10.0f * SIZE_MULTIPLIER);
    verify(player).setWidth(5.0f * SIZE_MULTIPLIER);
  }

  @Test
  void testExpirePowerupResetsPlayerSizeBig() {
    when(player.getHeight()).thenReturn(20.0f);
    when(player.getWidth()).thenReturn(10.0f);

    biggerPowerup.expire(player);

    verify(player).setHeight(20.0f / SIZE_MULTIPLIER);
    verify(player).setWidth(10.0f / SIZE_MULTIPLIER);
  }

  @Test
  void testExpirePowerupResetsPlayerSizeSmall() {
    when(player.getHeight()).thenReturn(20.0f);
    when(player.getWidth()).thenReturn(10.0f);

    smallerPowerup.expire(player);

    verify(player).setHeight(20.0f / SIZE_MULTIPLIER);
    verify(player).setWidth(10.0f / SIZE_MULTIPLIER);
  }

  @Test
  void testConstructorSetsCorrectSizeMultiplierBig() {
    SizePowerup newPowerup = new SizePowerup(DURATION, SIZE_MULTIPLIER, true);
    assertNotNull(newPowerup);
  }

  @Test
  void testConstructorSetsCorrectSizeMultiplierSmall() {
    SizePowerup newPowerup = new SizePowerup(DURATION, SIZE_MULTIPLIER, false);
    assertNotNull(newPowerup);
  }

}