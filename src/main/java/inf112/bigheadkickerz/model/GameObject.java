package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Interface for game objects */
public interface GameObject {

    /**
     * Method for updating the game object
     *
     * @param viewport
     * @param delta
     */
    void update(Viewport viewport, float delta);

    /**
     * Method for drawing the game object
     *
     * @param batch SpriteBatch
     */
    void draw(SpriteBatch batch);

    /**
     * Get the speed of the game object
     *
     * @return the velocity
     */
    Vector2 getVelocity();
}
