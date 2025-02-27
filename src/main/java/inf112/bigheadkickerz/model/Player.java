package inf112.bigheadkickerz.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.controller.PlayerController;

/** Class for the player object */
public class Player implements GameObject {
    private Sprite sprite;
    private PlayerController playerController;

    /** Constructor for Player */
    public Player(String texturePath, float startX, float startY, boolean flip, boolean player1) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        sprite.setPosition(startX, startY);

        if (flip) {
            sprite.flip(true, false);
        }

        playerController = new PlayerController(sprite, player1);
    }

    
    @Override
    public void update(Viewport viewport, float delta) {
        playerController.movePlayer(viewport, delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }


    public float getWidth() {
        return sprite.getWidth();
    }

}