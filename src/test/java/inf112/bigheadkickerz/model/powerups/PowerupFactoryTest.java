package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.IPlayerPowerup;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PowerupFactory} after refactor to a registry‑based
 * design.
 */
class PowerupFactoryTest {

  /**
   * Simple stand‑in power‑up used for deterministic testing.
   */
  private static class DummyPowerup implements Powerup {
    @Override
    public void apply(IPlayerPowerup player) {
      /* no‑op */
    }

    @Override
    public void expire(IPlayerPowerup player) {
      /* no‑op */
    }

    @Override
    public float getDuration() {
      return 0f;
    }

    @Override
    public Texture getTexture() {
      return null;
    }
  }

  @Test
  void getRandomPowerupReturnsNonNullInstance() {
    assertNotNull(PowerupFactory.getRandomPowerup());
  }

  @Test
  void registerAddsPowerupAndFactoryCanReturnIt() {
    Powerup dummyPowerup = new DummyPowerup();
    int initialSize = PowerupFactory.getPowerup().size();

    PowerupFactory.register(dummyPowerup);
    assertEquals(initialSize + 1, PowerupFactory.getPowerup().size());

    boolean produced = false;
    // Try multiple times to compensate for randomness
    for (int i = 0; i < 25 && !produced; i++) {
      produced = PowerupFactory.getRandomPowerup() instanceof DummyPowerup;
    }
    assertTrue(produced, "Factory did not produce DummyPowerup after registration");

    PowerupFactory.unregister(dummyPowerup);
    assertEquals(initialSize, PowerupFactory.getPowerup().size());
  }

  @Test
  void factoryThrowsWhenNoPowerupsRegistered() {
    // Temporarily remove every powerup
    List<Powerup> backups = new ArrayList<>(PowerupFactory.getPowerup());
    backups.forEach(PowerupFactory::unregister);

    assertThrows(IllegalStateException.class, PowerupFactory::getRandomPowerup);

    // Restore original powerup so other tests are unaffected
    backups.forEach(PowerupFactory::register);
  }

  @Test
  void registerNullPowerupsThrows() {
    assertThrows(IllegalArgumentException.class, () -> PowerupFactory.register(null));
  }

}
