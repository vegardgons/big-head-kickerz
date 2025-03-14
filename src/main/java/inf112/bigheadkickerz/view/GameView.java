package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Interface for game view components
 */
public interface GameView {

    /**
     * Draw game objects
     * 
     * @param batch    SpriteBatch for rendering
     * @param viewport Viewport for rendering
     */
    void draw(SpriteBatch batch, Viewport viewport);


    /**
     * Main render method for rendering the entire game
     * Default implementation does nothing
     */
    default void render() {
        // Default implementation does nothing
    }

    /**
     * Handle screen resizing
     * 
     * @param width  new width
     * @param height new height
     */
    default void resize(int width, int height) {
        // Default implementation does nothing
    }

    /**
     * Dispose resources
     * Default implementation does nothing
     */
    default void dispose() {
        // Default implementation does nothing
    }
}