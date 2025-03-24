package inf112.bigheadkickerz.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.controller.PlayerController;
import com.badlogic.gdx.utils.Array;

/** Class for the player object */
public class Player implements GameObject, Collideable {

    private static final float GRAVITY = -9.81f;
    private static final float WIDTH = 1f;
    private static final float HEIGHT = 1.2f;
    private static final float WEIGHT = 1f;

    private Texture texture;
    private PlayerController playerController;
    private Vector2 velocity;
    private Vector2 startPos;
    private Vector2 pos;
    private boolean player1;


    //fields for kick animation
    private boolean isKicking = false;
    private float kickStateTime = 0f;
    private Animation<TextureRegion> kickAnimation;
    private TextureRegion idleFrame;

    /** Constructor for Player */
    public Player(String texturePath, float startX, float startY, boolean player1) {
        texture = new Texture(texturePath);
        idleFrame = new TextureRegion(texture);
        startPos = new Vector2(startX, startY);
        pos = new Vector2(startX, startY);
        velocity = new Vector2(0, 0);
        playerController = new PlayerController(player1, this);
        this.player1 = player1;

    }

    /**
     * Called when we want the player to start kicking.
     */
    public void kick() {
        if (!isKicking) {
            isKicking = true;
            kickStateTime = 0f;
            initKickAnimation();
        }
    }

    @Override
    public void update(Viewport viewport, float delta) {

        playerController.movePlayer(viewport, delta);
        velocity.y += GRAVITY * delta;
        // move();
        pos.add(velocity.x * delta, velocity.y * delta);

        boundaries(viewport);
        // System.out.println("Player velocity: " + getVelocity());
        // System.out.println("Player position: " + getPosition());

        if (isKicking) {
            kickStateTime += delta;
            if (kickAnimation.isAnimationFinished(kickStateTime)) {
                isKicking = false;
            }
        }
        

    }

    private void boundaries(Viewport viewport) {
        if (pos.x < 0) {
            pos.x = 0;
            velocity.x = 0;
        }
        if (pos.x + WIDTH > viewport.getWorldWidth()) {
            pos.x = viewport.getWorldWidth() - WIDTH;
            velocity.x = 0;
        }
        if (pos.y < 0) {
            pos.y = 0;
            velocity.y = 0;
        }
        if (pos.y + HEIGHT > viewport.getWorldHeight()) {
            pos.y = viewport.getWorldHeight() - HEIGHT;
            velocity.y = 0;
        }
    }

    @Override
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.pos = pos;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
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
        batch.draw(currentFrame, pos.x, pos.y, WIDTH, HEIGHT);
    }

    public void move() {
        this.pos = new Vector2(pos.x + velocity.x, pos.y + velocity.y);
    }

    /** Reset the player to their initial position */
    public void reset() {
        setPosition(startPos);
        setVelocity(new Vector2(0, 0));
    }

    private void initKickAnimation() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("kick_animation.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("kick");

        frames.sort((a, b) -> Integer.compare(a.index, b.index));

        if (player1) {
            for (TextureAtlas.AtlasRegion region : frames) {
                if (!region.isFlipX()) {
                    region.flip(true, false);
                }
            }
        }
        kickAnimation = new Animation<>(0.01f, frames);
        kickAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    @Override
    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void collision(Collideable other) {
    }

    @Override
    public boolean collides(Collideable other) {
        return true;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    
}
