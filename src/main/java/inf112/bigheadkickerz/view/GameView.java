package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Interface for game view components.
 */
public interface GameView {

  /**
   * Draw game objects.
   *
   * @param batch    SpriteBatch for rendering
   * @param viewport Viewport for rendering
   */
  void draw(SpriteBatch batch, Viewport viewport);


  /**
   * Dispose resources.
   */
  default void dispose() {
    // Default implementation does nothing
  }
}