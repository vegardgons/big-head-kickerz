package inf112.skeleton.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Field {
    private Texture backgroundTexture;

    public Field(String texturePath) {
        backgroundTexture = new Texture(texturePath);
    }

    public void draw(SpriteBatch batch, Viewport viewport) {
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    }
}