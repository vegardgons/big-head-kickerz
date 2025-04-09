package inf112.bigheadkickerz.model;

import java.util.List;

/**
 * Interface for handling collisions among Collideable objects.
 */
public interface ICollisionHandler {
  /**
   * Checks for collisions among all given Collideable objects,
   * and resolves them if necessary.
   */
  void checkCollisions(List<Collideable> objects);
}
