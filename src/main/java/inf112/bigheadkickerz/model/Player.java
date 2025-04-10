package inf112.bigheadkickerz.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.controller.PlayerController;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;
import java.util.Comparator;

/** Class for the player object. */
public class Player implements GameObject, Collideable, IPlayerPowerup {

  private static final float WEIGHT = 300;
  private float width = 1f;
  private float height = 1.2f;
  private float gravity = -9.81f;
  private float movementSpeed = 4f;
  private float jumpHeight = 5f;
  private float kickPower = 4f;

  private final PlayerController playerController;
  private Vector2 velocity;
  private final Vector2 startPos;
  private Vector2 pos;
  private final boolean isPlayer1;

  // fields for kick animation
  private boolean isKicking = false;
  private float kickStateTime = 0f;
  private Animation<TextureRegion> kickAnimation;
  private final TextureRegion idleFrame;

  /** Constructor for Player. */
  public Player(Texture texture, float startX, float startY, boolean isPlayer1) {
    this.idleFrame = new TextureRegion(texture);
    this.startPos = new Vector2(startX, startY);
    this.pos = new Vector2(startX, startY);
    this.velocity = new Vector2(0, 0);
    this.playerController = new PlayerController(isPlayer1, this);
    this.isPlayer1 = isPlayer1;
  }

  // TODO: Bør ikke la denne være public, kanskje finne et sted/interface hvor vi
  // kan legge den inn, evt. om den bør være i denne klassen.
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
    Vector2 newVel = playerController.movePlayer();
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
    if (pos.x + width > viewport.getWorldWidth()) {
      pos.x = viewport.getWorldWidth() - width;
      velocity.x = 0;
    }
    if (pos.y < 0) {
      pos.y = 0;
      velocity.y = 0;
    }
    if (pos.y + height > viewport.getWorldHeight()) {
      pos.y = viewport.getWorldHeight() - height;
      velocity.y = 0;
    }
  }

  @Override
  public void draw(SpriteBatch batch) {
    TextureRegion currentFrame;
    if (isKicking) {
      currentFrame = kickAnimation.getKeyFrame(kickStateTime, false);
    } else {
      currentFrame = idleFrame;
    }
    batch.draw(currentFrame, pos.x, pos.y, width, height);
  }

  // TODO: Bør ikke la denne være public, kanskje finne et sted/interface hvor vi
  // kan legge den inn
  void reset() {
    setPosition(new Vector2(startPos));
    setVelocity(new Vector2(0, 0));
  }

  private void initKickAnimation() {
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("kick_animation.atlas"));
    Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("kick");

    frames.sort(Comparator.comparingInt(a -> a.index));

    if (!isPlayer1) {
      for (TextureAtlas.AtlasRegion region : frames) {
        region.flip(true, false);
      }
    }
    kickAnimation = new Animation<>(0.015f, frames);
    kickAnimation.setPlayMode(Animation.PlayMode.NORMAL);
  }

  @Override
  public void collision(Collideable other) {
    if (other instanceof Ball || other instanceof Goal) {
      return;
    }

    Vector2 otherPos = other.getPosition();

    float overlapX = Math.min(pos.x + width - otherPos.x, otherPos.x + other.getWidth() - pos.x);
    float overlapY = Math.min(pos.y + height - otherPos.y, otherPos.y + height - pos.y);

    if (overlapX < overlapY) {
      if (pos.x < otherPos.x) {
        setPosition(new Vector2(pos.x - overlapX / 2, pos.y)); // Move left
        other.setPosition(new Vector2(otherPos.x + overlapX / 2, otherPos.y)); // Move right
      } else {
        setPosition(new Vector2(pos.x + overlapX / 2, pos.y)); // Move right
        other.setPosition(new Vector2(otherPos.x - overlapX / 2, otherPos.y)); // Move left
      }
    } else {
      if (pos.y < otherPos.y) {
        other.setPosition(new Vector2(otherPos.x, otherPos.y + overlapY / 2)); // Move up
      } else {
        setPosition(new Vector2(pos.x, pos.y + overlapY / 2)); // Move up
      }
    }

    Vector2 tempVel = velocity.cpy();
    velocity.set(other.getVelocity());
    other.setVelocity(tempVel);
  }

  @Override
  public boolean collides(Collideable other) {
    if (other instanceof Goal goal) {
      return goal.collides(this);
    }
    if (other instanceof PowerupPickup) {
      return false;
    }
    return rectangleCollides(other);
  }

  // TODO: Bør ikke la denne være public, kanskje finne et sted/interface hvor vi
  // kan legge den inn, evt. om vi i det hele tatt trenger den
  boolean isKicking() {
    return isKicking;
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
    return width;
  }

  @Override
  public float getHeight() {
    return height;
  }

  @Override
  public Vector2 getVelocity() {
    return velocity.cpy();
  }

  @Override
  public void setHeight(float height) {
    this.height = height;
  }

  @Override
  public void setWidth(float width) {
    this.width = width;
  }

  @Override
  public float getMovementSpeed() {
    return movementSpeed;
  }

  @Override
  public void setMovementSpeed(float movementSpeed) {
    this.movementSpeed = movementSpeed;
  }

  @Override
  public float getJumpHeight() {
    return jumpHeight;
  }

  @Override
  public void setJumpHeight(float jumpHeight) {
    this.jumpHeight = jumpHeight;
  }

  @Override
  public float getKickPower() {
    return kickPower;
  }

  @Override
  public void setKickPower(float kickPower) {
    this.kickPower = kickPower;
  }

}
