package inf112.bigheadkickerz.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.controller.PlayerController;
import com.badlogic.gdx.utils.Array;


/** Class for the player object */
public class Player implements GameObject {
    private Sprite sprite;
    private PlayerController playerController;
    private Vector2 velocity;
    private Vector2 previousPosition;
    private Vector2 initialPosition;
    private boolean canMove = true;

     // New fields for kick animation
     private boolean isKicking = false;
     private float kickStateTime = 0f;
     private Animation<TextureRegion> kickAnimation;
     private TextureRegion idleFrame;
 

    /** Constructor for Player */
    public Player(String texturePath, float startX, float startY, boolean isFlipped, boolean player1) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        sprite.setPosition(startX, startY);
        idleFrame = new TextureRegion(texture);


        previousPosition = new Vector2(startX, startY);
        initialPosition = new Vector2(startX, startY);
        velocity = new Vector2(0, 0);

        if (isFlipped) {
            sprite.flip(true, false);
        }

        playerController = new PlayerController(sprite, player1, this);

        // --- MANUAL FRAME ORDERING HERE ---
        // 1) Load your atlas
        // Load the kick atlas
        // Load the atlas from the assets folder (adjust the path if needed)
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("kick.atlas"));

        // Find all regions with the base name "kick" (ensure it matches the atlas file exactly)
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("kick");

        // Log the number of frames found to help debug
        Gdx.app.log("Player", "Number of kick frames found: " + frames.size);

        // Sort the frames by their index field
        frames.sort((a, b) -> Integer.compare(a.index, b.index));

        // Create the kick animation from the sorted frames
        kickAnimation = new Animation<>(0.01f, frames);
        kickAnimation.setPlayMode(Animation.PlayMode.NORMAL);


    }

    /**
     * Called when we want the player to start kicking.
     */
    public void kick() {
        if (!isKicking) {
            isKicking = true;
            kickStateTime = 0f;
        }
    }
        
    

    @Override
    public void update(Viewport viewport, float delta) {
        Vector2 currentPos = new Vector2(sprite.getX(), sprite.getY());

        if (canMove) {
            playerController.movePlayer(viewport, delta);
        }

        Vector2 newPos = new Vector2(sprite.getX(), sprite.getY());
        velocity.set(newPos.x - currentPos.x, newPos.y - currentPos.y);

        previousPosition.set(newPos);

        // Update kick animation if active
        if (isKicking) {
            kickStateTime += delta;
            if (kickAnimation.isAnimationFinished(kickStateTime)) {
                isKicking = false;
                kickStateTime = 0f;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;
        if (isKicking) {
            currentFrame = kickAnimation.getKeyFrame(kickStateTime, false);
        } else {
            // Default (idle) appearance
            currentFrame = idleFrame;
        }
        batch.draw(currentFrame, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
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
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Reset the player to their initial position
     */
    public void reset() {
        sprite.setPosition(initialPosition.x, initialPosition.y);
        previousPosition.set(initialPosition);
        velocity.set(0, 0);
    }
}
