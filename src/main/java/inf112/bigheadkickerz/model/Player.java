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
    private static final float WEIGHT = 300;

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

        velocity.y += GRAVITY * delta;
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
        kickAnimation = new Animation<>(0.01f, frames);
        kickAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    @Override
    public void collision(Collideable other) {
        Vector2 x1 = this.getPosition();
        Vector2 x2 = other.getPosition();

        Vector2 v1 = this.getVelocity();
        Vector2 v2 = other.getVelocity();

        float m1 = this.getWeight();
        float m2 = other.getWeight();

        Vector2 collisionNormal = new Vector2(x1).sub(x2);
        if (collisionNormal.len2() == 0)
            return;

        collisionNormal.nor();
        float distanceSquared = collisionNormal.len2();
        float dotProduct = v1.cpy().sub(v2).dot(collisionNormal);
        float impulse = 2 * m2 / (m1 + m2);
        float scale = impulse * dotProduct / distanceSquared;

        Vector2 collisionNormal2 = new Vector2(x2).sub(x1);
        if (collisionNormal2.len2() == 0)
            return;

        collisionNormal2.nor();
        float distanceSquared2 = collisionNormal2.len2();
        float dotProduct2 = v2.cpy().sub(v1).dot(collisionNormal2);
        float impulse2 = 2 * m1 / (m1 + m2);
        float scale2 = impulse2 * dotProduct2 / distanceSquared2;

        this.setVelocity(v1.cpy().sub(collisionNormal.scl(scale)));
        other.setVelocity(v2.cpy().sub(collisionNormal2.scl(scale2)));
    }

    @Override
    public boolean collides(Collideable other) {
        Vector2 otherPos = other.getPosition();

        // Rectangular collision detection
        boolean xOverlap = pos.x < otherPos.x + other.getWidth() &&
                pos.x + WIDTH > otherPos.x;

        boolean yOverlap = pos.y < otherPos.y + other.getWidth() &&
                pos.y + HEIGHT > otherPos.y;

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
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity.cpy();
    }

}
