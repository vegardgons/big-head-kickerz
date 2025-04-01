package inf112.bigheadkickerz.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Interface for game objects that can collide with each other.
 * This interface defines methods for getting and setting position, velocity,
 * and handling collisions.
 */
public interface Collideable {

  /**
   * Get the weight of the object.
   *
   * @return weight of the object
   */
  float getWeight();

  /**
   * Get the width of the object.
   *
   * @return width of the object
   */
  float getWidth();

  /**
   * Get the height of the object.
   *
   * @return height of the object
   */
  float getHeight();

  /**
   * Get the velocity of the object.
   *
   * @return velocity of the object
   */
  Vector2 getVelocity();

  /**
   * Get the position of the object.
   *
   * @return position of the object
   */
  Vector2 getPosition();

  /**
   * Set the position of the game object.
   *
   * @param pos the new position of the game object
   */
  void setPosition(Vector2 pos);

  /**
   * Set the speed of the game object in the x direction.
   *
   * @param velocity the new speed of the game object
   */
  void setVelocity(Vector2 velocity);

  /**
   * Handle collision with another Collideable object.
   *
   * @param other the other Collideable object
   */
  void collision(Collideable other);

  /**
   * Check if this object collides with another Collideable object.
   *
   * @param other the other Collideable object
   * @return true if the objects collide, false otherwise
   */
  boolean collides(Collideable other);

}