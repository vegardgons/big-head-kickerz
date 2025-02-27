package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface GameView {

    /**
     * Method for drawing objects in the game
     *
     * @param batch    SpriteBatch
     * @param viewport Viewport
     */
    void draw(SpriteBatch batch, Viewport viewport);
}