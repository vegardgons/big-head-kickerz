package inf112.bigheadkickerz.model.powerups;

/**
 * An abstract implementation of Powerup that stores a duration.
 */
public abstract class AbstractPowerup implements Powerup {
  final float duration;

  /**
   * Constructor for AbstractPowerup.
   *
   * @param duration the duration of the powerup
   */
  AbstractPowerup(float duration) {
    this.duration = duration;
  }

  @Override
  public float getDuration() {
    return duration;
  }
}
