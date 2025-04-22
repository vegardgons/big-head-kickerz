package inf112.bigheadkickerz.model.powerups;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A registry‑based factory that can create random {@link Powerup} instances.
 * <p>
 * New power‑up types can be added at runtime by calling {@link #register(Supplier)},
 */
public final class PowerupFactory {

  private static final float DEFAULT_DURATION = 8f;
  private static final SecureRandom RNG = new SecureRandom();

  /**
   * List containing {@link Supplier}s that each build a new {@link Powerup} instance.
   */
  private static final List<Supplier<Powerup>> POWERUPSUPPLIERS = new ArrayList<>();

  // Register the built‑in power‑ups once when the class is loaded
  static {
    POWERUPSUPPLIERS.add(() -> new SpeedPowerup(DEFAULT_DURATION, 1.5f, true));
    POWERUPSUPPLIERS.add(() -> new SpeedPowerup(DEFAULT_DURATION, 0.5f, false));
    POWERUPSUPPLIERS.add(() -> new JumpPowerup(DEFAULT_DURATION, 0.5f, false));
    POWERUPSUPPLIERS.add(() -> new JumpPowerup(DEFAULT_DURATION, 1.5f, true));
    POWERUPSUPPLIERS.add(() -> new SizePowerup(DEFAULT_DURATION, 1.5f, true));
    POWERUPSUPPLIERS.add(() -> new SizePowerup(DEFAULT_DURATION, 0.75f, false));
  }

  private PowerupFactory() {
  }

  /**
   * Registers a new type of power‑up.
   *
   * @param powerupSupplier supplier that creates a fresh {@link Powerup} each time it is invoked
   */
  public static void register(Supplier<Powerup> powerupSupplier) {
    if (powerupSupplier == null) {
      throw new IllegalArgumentException("Supplier cannot be null");
    }
    POWERUPSUPPLIERS.add(powerupSupplier);
  }

  /**
   * Removes a previously registered power‑up supplier.
   *
   * @param powerupSupplier the supplier to remove
   */
  public static void unregister(Supplier<Powerup> powerupSupplier) {
    POWERUPSUPPLIERS.remove(powerupSupplier);
  }

  /**
   * Returns an all currently registered PowerupSuppliers.
   */
  public static List<Supplier<Powerup>> getPowerupSuppliers() {
    return POWERUPSUPPLIERS;
  }

  /**
   * Creates a new random {@link Powerup} instance.
   *
   * @return a randomly selected power‑up
   * @throws IllegalStateException if no power‑ups are registered
   */
  public static Powerup getRandomPowerup() {
    if (POWERUPSUPPLIERS.isEmpty()) {
      throw new IllegalStateException("No power‑ups registered");
    }
    Supplier<Powerup> supplier = POWERUPSUPPLIERS.get(RNG.nextInt(POWERUPSUPPLIERS.size()));
    return supplier.get();
  }
}
