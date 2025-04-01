package inf112.bigheadkickerz.model.powerups;

/**
 * An abstract implementation of Powerup that stores a duration.
 */
public abstract class AbstractPowerup implements Powerup {
  protected final float duration;

  public AbstractPowerup(float duration) {
    this.duration = duration;
  }

  @Override
  public float getDuration() {
    return duration;
  }
}
