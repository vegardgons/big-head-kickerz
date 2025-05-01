package inf112.bigheadkickerz.model.powerups;

/**
 * A factory interface for creating {@link Powerup} instances.
 */
public interface PowerupFactory {

  /**
   * Creates a new {@link Powerup} instance.
   *
   * @return a new power‑up
   */
  Powerup create();
}
