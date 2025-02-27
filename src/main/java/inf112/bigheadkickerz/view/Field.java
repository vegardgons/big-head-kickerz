package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Class for the field object */
public class Field {
    private Texture backgroundTexture;

    /** Constructor for Field */
    public Field(String texturePath) {
        backgroundTexture = new Texture(texturePath);
    }

    /**
     * Method for drawing the field
     *
     * @param batch    SpriteBatch
     * @param viewport Viewport
     */
    public void draw(SpriteBatch batch, Viewport viewport) {
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    }
}