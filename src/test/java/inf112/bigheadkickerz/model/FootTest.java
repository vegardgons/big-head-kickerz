package inf112.bigheadkickerz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * Updated tests for the re‑implemented {@link Foot}.
 */
class FootTest {

  private static final float EPS = 1e-4f;

  private Foot foot;
  private Player player;
  private Texture texture;
  private SpriteBatch batch;
  private Viewport viewport;

  @BeforeEach
  void setUp() {
    texture = mock(Texture.class);
    when(texture.getWidth()).thenReturn(16);
    when(texture.getHeight()).thenReturn(16);

    player = mock(Player.class);
    when(player.getPosition()).thenReturn(new Vector2(5f, 5f));
    when(player.getWidth()).thenReturn(1f);
    when(player.getHeight()).thenReturn(2f);
    when(player.getVelocity()).thenReturn(new Vector2(0f, 0f));
    when(player.isPlayer1()).thenReturn(true);

    foot = new Foot(texture, player);

    batch = mock(SpriteBatch.class);
    viewport = mock(Viewport.class);
    Assets.setKickingSound(Mockito.mock(Sound.class));
  }

  @Test
  void testInitialPosition() {
    Vector2 pos = foot.getPosition();

    float hipX = 5f + 0.5f; // player.x + width/2
    float expectedX = hipX - foot.getWidth() / 2f;

    float hipY = 5f + 1f; // player.y + height/2 (height=2)
    float expectedY = hipY - 0.65f /* RADIUS */ - foot.getHeight() / 2f;

    assertEquals(expectedX, pos.x, EPS);
    assertEquals(expectedY, pos.y, EPS);
  }

  @Test
  void testKickChangesState() {
    assertFalse(foot.isKicking());
    foot.kick();
    assertTrue(foot.isKicking());
  }

  @Test
  void testUpdateWhileNotKickingFollowsPlayer() {
    // Move player somewhere else
    when(player.getPosition()).thenReturn(new Vector2(7f, 3f));

    foot.update(viewport, 0.1f);

    float hipX = 7f + 0.5f;
    float expectedX = hipX - foot.getWidth() / 2f;

    float hipY = 3f + 1f; // 3 + height/2
    float expectedY = hipY - 0.65f - foot.getHeight() / 2f;

    assertEquals(expectedX, foot.getPosition().x, EPS);
    assertEquals(expectedY, foot.getPosition().y, EPS);
  }

  @Test
  void testUpdateWhileKickingMovesFoot() {
    foot.kick();
    Vector2 oldPos = new Vector2(foot.getPosition());
    foot.update(viewport, 0.1f);
    Vector2 newPos = foot.getPosition();
    assertNotEquals(oldPos, newPos);
  }

  @Test
  void testDrawWhileNotKicking() {
    foot.draw(batch);

    verify(batch).draw(
        eq(texture),
        anyFloat(), anyFloat(), // x, y
        eq(foot.getWidth() / 2f), eq(foot.getHeight() / 2f),
        eq(foot.getWidth()), eq(foot.getHeight()),
        eq(1f), eq(1f), // scaleX, scaleY
        eq(0f), // rotation degrees
        anyInt(), anyInt(), anyInt(), anyInt(), // srcX,Y,W,H
        eq(false), eq(false)); // flipX, flipY
  }

  @Test
  void testDrawWhileKicking() {
    foot.kick();
    foot.update(viewport, 0.1f); // mid‑swing

    ArgumentCaptor<Float> rotationCap = ArgumentCaptor.forClass(Float.class);

    foot.draw(batch);

    verify(batch).draw(
        eq(texture),
        anyFloat(), anyFloat(),
        anyFloat(), anyFloat(),
        anyFloat(), anyFloat(),
        anyFloat(), anyFloat(), // scaleX, scaleY captured as any
        rotationCap.capture(), // rotation
        anyInt(), anyInt(), anyInt(), anyInt(),
        eq(false), eq(false));

    assertNotEquals(0f, rotationCap.getValue(), EPS);
  }

  @Test
  void testResetResetsFoot() {
    foot.kick();
    foot.update(viewport, 0.1f);
    foot.reset();
    assertEquals(new Vector2(foot.getPosition()), foot.getPosition());
    assertEquals(new Vector2(0, 0), foot.getVelocity());
  }

  @Test
  void testCollisionWithBallCallsBallCollision() {
    Ball ball = mock(Ball.class);
    foot.collision(ball);
    verify(ball).collision(foot);
  }

  @Test
  void testCollidesWithSameTeamPlayerReturnsFalse() {
    Player otherPlayer = mock(Player.class);
    when(otherPlayer.isPlayer1()).thenReturn(true);

    assertFalse(foot.collides(otherPlayer));
  }

  @Test
  void testCollidesWithOpposingPlayerBoundingBox() {
    Player opponent = mock(Player.class);
    when(opponent.isPlayer1()).thenReturn(false); // opposite side
    when(opponent.getWidth()).thenReturn(1f);
    when(opponent.getHeight()).thenReturn(2f);

    // Place opponent overlapping foot
    when(opponent.getPosition()).thenReturn(new Vector2(foot.getPosition()));
    assertTrue(foot.collides(opponent));

    // Move opponent away – now should not collide
    when(opponent.getPosition()).thenReturn(new Vector2(20f, 20f));
    assertFalse(foot.collides(opponent));
  }
}