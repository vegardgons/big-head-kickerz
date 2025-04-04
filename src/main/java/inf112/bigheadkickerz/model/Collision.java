package inf112.bigheadkickerz.model;

import java.util.List;

/**
 * Handles collision detection between objects in the game.
 * This class checks for collisions between all pairs of Collideable objects
 * and calls the appropriate collision methods.
 */
public class Collision {

  private final List<Collideable> collideables;

  public Collision(List<Collideable> collideables) {
    this.collideables = collideables;
  }

  /**
   * Check for collisions between all pairs of Collideable objects.
   * If a collision is detected, call the collision methods on both objects.
   */
  public void checkCollision() {
    for (int i = 0; i < collideables.size(); i++) {
      for (int j = i + 1; j < collideables.size(); j++) {
        Collideable a = collideables.get(i);
        Collideable b = collideables.get(j);
        if (a.collides(b)) {
          a.collision(b);
          b.collision(a);
        }
      }
    }
  }

}
