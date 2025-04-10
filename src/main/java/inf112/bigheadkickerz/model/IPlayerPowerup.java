package inf112.bigheadkickerz.model;

/**
 * Interface for player powerup properties.
 */
public interface IPlayerPowerup {

  /**
   * Get current movement speed.
   *
   * @return current movement speed
   */
  float getMovementSpeed();

  /**
   * Set movement speed.
   *
   * @param movementSpeed new movement speed
   */
  void setMovementSpeed(float movementSpeed);

  /**
   * Get kick power.
   *
   * @return kick power
   */
  float getKickPower();

  /**
   * Set kick power.
   *
   * @param kickPower new kick power
   */
  void setKickPower(float kickPower);

  /**
   * Get width.
   *
   * @return width
   */
  float getWidth();

  /**
   * Set width.
   *
   * @param width new width
   */
  void setWidth(float width);

  /**
   * Get height.
   *
   * @return height
   */
  float getHeight();

  /**
   * Set height.
   *
   * @param height new height
   */
  void setHeight(float height);

  /**
   * Get jump height.
   *
   * @return jump height
   */
  float getJumpHeight();

  /**
   * Set jump height.
   *
   * @param jumpHeight new jump height
   */
  void setJumpHeight(float jumpHeight);
}
