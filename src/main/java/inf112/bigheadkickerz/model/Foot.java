package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.powerups.PowerupPickup;

/**
 * Class for the foot object.
 * The foot is a part of the player and is used for collision detection.
 */
@SuppressWarnings("PointlessArithmeticExpression")
public class Foot implements GameObject, Collideable {

  private static final float WEIGHT = 300f; // collision mass
  private static final float BASE_WIDTH = 0.80f;
  private static final float BASE_HEIGHT = 0.30f;
  private static final float RADIUS = 0.65f; // hip→ankle distance
  private static final float MAX_SWING_DEG = 90f; // forward swing limit
  private static final float HALF_SWING_TIME = 0.25f; // seconds per phase
  private static final float KICK_POWER = 3f; // impulse boost

  private final Player player;
  private final Texture texture;

  private float width = BASE_WIDTH;
  private float height = BASE_HEIGHT;

  private final Vector2 pos = new Vector2();
  private final Vector2 velocity = new Vector2();

  private enum State {
    IDLE, FORWARD, RETURN
  }

  private State state = State.IDLE;
  private float timer = 0f;
  private float theta = 0f; // radians, 0 = downwards

  /** Constructor for Foot. */
  public Foot(Texture texture, Player owner) {
    this.texture = texture;
    this.player = owner;
    computeRestingPose();
  }

  @Override
  public void update(Viewport viewport, float delta) {
    velocity.set(player.getVelocity());

    switch (state) {
      case IDLE -> computeRestingPose();
      case FORWARD, RETURN -> advanceSwing(delta);
      default -> throw new IllegalStateException("Unexpected value: " + state);
    }
  }

  private void advanceSwing(float dt) {
    timer += dt;
    float progress = Math.min(timer / HALF_SWING_TIME, 1f);
    float eased = easeInOutSine(progress);

    if (state == State.FORWARD) {
      theta = lerp(0f, degToRad(MAX_SWING_DEG), eased);
      if (progress >= 1f) {
        changeState(State.RETURN);
      }
    } else {
      theta = lerp(degToRad(MAX_SWING_DEG), 0f, eased);
      if (progress >= 1f) {
        changeState(State.IDLE);
      }
    }

    calculateFootPosition();
  }

  private void changeState(State next) {
    state = next;
    timer = 0f;
  }

  private void computeRestingPose() {
    theta = 0f;
    calculateFootPosition();
  }

  @Override
  public void draw(SpriteBatch batch) {
    float rotation = (float) Math.toDegrees(theta) * (player.isPlayer1() ? 1f : -1f);

    batch.draw(
        texture,
        pos.x, pos.y,
        width / 2, height / 2,
        width, height,
        1f, 1f,
        rotation,
        0, 0,
        texture.getWidth(), texture.getHeight(),
        false, false);
  }

  /**
   * Kicks.
   */
  public boolean kick() {
    if (state == State.IDLE) {
      changeState(State.FORWARD);
      Assets.playKickingSound();
      return true;

    }
    return false;
  }

  /**
   * Resets the foot to its initial state.
   */
  public void reset() {
    state = State.IDLE;
    timer = 0f;
    computeRestingPose();
    velocity.setZero();
  }

  public boolean isKicking() {
    return state != State.IDLE;
  }

  public float getKickPower() {
    return KICK_POWER;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public void collision(Collideable other) {
    if (other instanceof Ball ball) {
      ball.collision(this);
    }
  }

  @Override
  public boolean collides(Collideable other) {
    if (other instanceof PowerupPickup) {
      return false;
    }
    if (other instanceof Goal goal) {
      return goal.collides(this);
    }

    if (other instanceof Player p && p.isPlayer1() == player.isPlayer1()) {
      return false;
    }
    return rectangleCollides(other);
  }

  private void calculateFootPosition() {
    Vector2 hip = new Vector2(
        player.getPosition().x + player.getWidth() * 0.5f,
        player.getPosition().y + player.getHeight() * 0.5f);

    float sin = (float) Math.sin(theta);
    float cos = (float) Math.cos(theta);

    float offsetX = sin * RADIUS;
    if (!player.isPlayer1()) {
      offsetX = -offsetX;
    }

    float offsetY = -cos * RADIUS;

    float x = hip.x + offsetX - width * 0.5f;
    float y = hip.y + offsetY - height * 0.5f;

    pos.set(x, y);
  }

  private static float easeInOutSine(float t) {
    return (float) (-0.5 * (Math.cos(Math.PI * t) - 1.0));
  }

  private static float lerp(float a, float b, float t) {
    return a + (b - a) * t;
  }

  private static float degToRad(float deg) {
    return (float) (deg * Math.PI / 180.0);
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
  public Vector2 getPosition() {
    return pos;
  }

  @Override
  public Vector2 getVelocity() {
    return velocity;
  }

  @Override
  public void setPosition(Vector2 pos) {
    this.pos.set(pos);
  }

  @Override
  public void setVelocity(Vector2 velocity) {
    this.velocity.set(velocity);
  }

  @Override
  public boolean setDirection(int direction) {
    return false;
  }
}
