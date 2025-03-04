package inf112.bigheadkickerz.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.controller.PlayerController;

/** Class for the player object */
public class Player implements GameObject {
    private Sprite sprite;
    private PlayerController playerController;
    private Vector2 velocity;
    private Vector2 previousPosition;

    /** Constructor for Player */
    public Player(String texturePath, float startX, float startY, boolean isFlipped, boolean player1) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        sprite.setPosition(startX, startY);

        previousPosition = new Vector2(startX,startY);
        velocity = new Vector2(0,0);

        if (isFlipped) {
            sprite.flip(true, false);
        }

        playerController = new PlayerController(sprite, player1);
    }

    @Override
    public void update(Viewport viewport, float delta) {
        Vector2 currentPos = new Vector2(sprite.getX(), sprite.getY());
        
        playerController.movePlayer(viewport, delta);

        Vector2 newPos = new Vector2(sprite.getX(), sprite.getY());
        velocity.set(newPos.x - currentPos.x, newPos.y - currentPos.y);

        previousPosition.set(newPos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Move player by specified amount in x and y directions
     * 
     * @param x amount to move in x direction
     * @param y amount to move in y direction
     */
    public void moveBy(float x, float y) {
        sprite.translate(x, y);
    }

    /**
     * 
     * @return player velocity as a Vector2
     */
    public Vector2 getVelocity () {
        return velocity;
    }
}