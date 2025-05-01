package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link PowerupCatalogue} class.
 */
public class PowerupCatalogueTest {

  @Test
  void createRandomPowerup_returnsNonNull() {
    Powerup p = PowerupCatalogue.createRandomPowerup();
    assertNotNull(p);
  }

  @Test
  void createRandomPowerup_returnsDistinctInstances() {
    Powerup p1 = PowerupCatalogue.createRandomPowerup();
    Powerup p2 = PowerupCatalogue.createRandomPowerup();

    assertNotSame(p1, p2);
  }

  @Test
  void testGetFactories() {
    var factories = PowerupCatalogue.getFactories();
    assertNotNull(factories);
    assertFalse(factories.isEmpty());
  }

}
