package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import inf112.bigheadkickerz.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
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
    public void apply(Player player) {
      /* no‑op */
    }

    @Override
    public void expire(Player player) {
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
  void registerAddsSupplierAndFactoryCanReturnIt() {
    Supplier<Powerup> dummySupplier = DummyPowerup::new;
    int initialSize = PowerupFactory.getPowerupSuppliers().size();

    PowerupFactory.register(dummySupplier);
    assertEquals(initialSize + 1, PowerupFactory.getPowerupSuppliers().size());

    boolean produced = false;
    // Try multiple times to compensate for randomness
    for (int i = 0; i < 25 && !produced; i++) {
      produced = PowerupFactory.getRandomPowerup() instanceof DummyPowerup;
    }
    assertTrue(produced, "Factory did not produce DummyPowerup after registration");

    PowerupFactory.unregister(dummySupplier);
    assertEquals(initialSize, PowerupFactory.getPowerupSuppliers().size());
  }

  @Test
  void factoryThrowsWhenNoSuppliersRegistered() {
    // Temporarily remove every supplier
    List<Supplier<Powerup>> backups = new ArrayList<>(PowerupFactory.getPowerupSuppliers());
    backups.forEach(PowerupFactory::unregister);

    assertThrows(IllegalStateException.class, PowerupFactory::getRandomPowerup);

    // Restore original suppliers so other tests are unaffected
    backups.forEach(PowerupFactory::register);
  }

  @Test
  void registerNullSupplierThrows() {
    assertThrows(IllegalArgumentException.class, () -> PowerupFactory.register(null));
  }

}
