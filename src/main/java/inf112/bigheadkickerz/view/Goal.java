package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Goal {

    private Texture texture;
    private Sprite sprite;

    public Goal(String texturePath, float x, float y, boolean flip) {
        texture = new Texture(Gdx.files.internal(texturePath));
        sprite = new Sprite(texture);

        sprite.setSize(2, 2.4f);
        sprite.setPosition(x, y);

        if (flip) {
            sprite.flip(true, false);
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    /**
     * Get the sprite of the game object for collision detection
     *
     * @return the sprite
     */
    public Sprite getSprite() {
        return sprite;
    }
}
