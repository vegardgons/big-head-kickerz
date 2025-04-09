package inf112.bigheadkickerz.model;

import java.util.List;

/**
 * CollisionHandler is responsible for checking and handling collisions
 * between Collideable objects in the game.
 */
public class CollisionHandler implements ICollisionHandler {

  @Override
  public void checkCollisions(List<Collideable> objects) {
    for (int i = 0; i < objects.size(); i++) {
      for (int j = i + 1; j < objects.size(); j++) {
        Collideable a = objects.get(i);
        Collideable b = objects.get(j);

        if (a.collides(b)) {
          a.collision(b);
          b.collision(a);
        }
      }
    }
  }

}
