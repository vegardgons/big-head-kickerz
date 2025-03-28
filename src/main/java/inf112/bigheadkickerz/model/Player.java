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
public class Player implements GameObject, Collideable, IPowerup {

    private static final float WEIGHT = 300;
    private float WIDTH = 1f;
    private float HEIGHT = 1.2f;
    private float gravity = -9.81f;
    private float movementSpeed = 4f;
    private float jumpHeight = 6f;
    private float kickPower = 4f;


    private Texture texture;
    private PlayerController playerController;
    private Vector2 velocity;
    private Vector2 startPos;
    private Vector2 pos;
    private boolean player1;

    // fields for kick animation
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

        velocity.y += gravity * delta;
        Vector2 newVel = playerController.movePlayer(viewport, delta);
        setVelocity(new Vector2(newVel));
        pos.add(velocity.x * delta, velocity.y * delta);

        boundaries(viewport);

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

    /** Reset the player to their initial position */
    public void reset() {
        setPosition(new Vector2(startPos));
        setVelocity(new Vector2(0, 0));
    }

    private void initKickAnimation() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("kick_animation.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("kick");

        frames.sort((a, b) -> Integer.compare(a.index, b.index));

        if (!player1) {
            for (TextureAtlas.AtlasRegion region : frames) {
                region.flip(true, false);
            }
        }
        kickAnimation = new Animation<>(0.015f, frames);
        kickAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    @Override
    public void collision(Collideable other) {
        if (other instanceof Ball) {
            return; // Skip collision handling if it's with a Ball (if desired)
        }

        Vector2 otherPos = other.getPosition();

        // Compute penetration depth
        float xOverlap = Math.min(pos.x + WIDTH - otherPos.x, otherPos.x + other.getWidth() - pos.x);
        float yOverlap = Math.min(pos.y + HEIGHT - otherPos.y, otherPos.y + HEIGHT - pos.y);

        // Resolve based on the smaller penetration axis
        if (xOverlap < yOverlap) {
            if (pos.x < otherPos.x) {
                setPosition(new Vector2(pos.x - xOverlap / 2, pos.y)); // Move left
                other.setPosition(new Vector2(otherPos.x + xOverlap / 2, otherPos.y)); // Move right
            } else {
                setPosition(new Vector2(pos.x + xOverlap / 2, pos.y)); // Move right
                other.setPosition(new Vector2(otherPos.x - xOverlap / 2, otherPos.y)); // Move left
            }
        } else {
            if (pos.y < otherPos.y) {
                other.setPosition(new Vector2(otherPos.x, otherPos.y + yOverlap / 2)); // Move up
            } else {
                setPosition(new Vector2(pos.x, pos.y + yOverlap / 2)); // Move up
            }
        }

        // Adjust velocities (simple elastic collision)
        Vector2 tempVel = velocity.cpy();
        velocity.set(other.getVelocity());
        other.setVelocity(tempVel);
    }

    @Override
    public boolean collides(Collideable other) {
        Vector2 otherPos = other.getPosition();
        float otherWidth = other.getWidth();
        float otherHeight = other.getHeight();

        // Rectangular collision detection
        boolean xOverlap = pos.x < otherPos.x + otherWidth && pos.x + WIDTH > otherPos.x;

        boolean yOverlap = pos.y < otherPos.y + otherHeight && pos.y + HEIGHT > otherPos.y;

        if (other instanceof Goal) {
            return xOverlap;
        }
        return xOverlap && yOverlap;
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
    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public boolean isKicking() {
        return isKicking;
    }

    @Override
    public float getGravity() {
        return gravity;
    }
    
    @Override
    public float setGravity(float gravity) {
        return this.gravity = gravity;
    }   
    
    @Override
    public float setHeight(float height) {
        return this.HEIGHT = height;
    }

    @Override
    public float setWidth(float width) {
        return this.WIDTH = width;
    }

    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public float setMovementSpeed(float movementSpeed) {
        return this.movementSpeed = movementSpeed;
    }

    @Override
    public float getJumpHeight() {
        return jumpHeight;
    }

    @Override
    public float setJumpHeight(float jumpHeight) {
        return this.jumpHeight = jumpHeight;
    }

    @Override
    public float getKickPower() {
        return kickPower;
    }

    @Override
    public float setKickPower(float kickPower) {
        return this.kickPower = kickPower;
    }

}
