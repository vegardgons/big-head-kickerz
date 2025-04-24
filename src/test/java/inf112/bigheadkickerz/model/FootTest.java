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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test class for the Foot class. */
class FootTest {

  private Foot foot;
  private Player player;
  private Texture texture;
  private SpriteBatch batch;
  private Viewport viewport;

  @BeforeEach
  void setUp() {
    texture = mock(Texture.class);
    player = mock(Player.class);
    batch = mock(SpriteBatch.class);
    viewport = mock(Viewport.class);

    when(player.getPosition()).thenReturn(new Vector2(5, 5));
    when(player.getWidth()).thenReturn(1f);
    when(player.getHeight()).thenReturn(2f);
    when(player.getVelocity()).thenReturn(new Vector2(0, 0));
    when(player.isPlayer1()).thenReturn(true);

    foot = new Foot(texture, player);
  }

  @Test
  void testInitialPosition() {
    Vector2 pos = foot.getPosition();
    assertEquals(5 + 0.5f - 0.4f, pos.x);
    assertEquals(0, pos.y);
  }

  @Test
  void testKickChangesState() {
    assertFalse(foot.isKicking());
    foot.kick();
    assertTrue(foot.isKicking());
  }

  @Test
  void testUpdateWhileNotKickingFollowsPlayer() {
    when(player.getPosition()).thenReturn(new Vector2(7, 3));
    foot.update(viewport, 0.1f);
    assertEquals(7 + 0.5f - 0.4f, foot.getPosition().x);
    assertEquals(3 - foot.getHeight(), foot.getPosition().y);
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
        eq(texture), anyFloat(), anyFloat(),
        eq(foot.getWidth()), eq(foot.getHeight()));
  }

  @Test
  void testDrawWhileKicking() {
    foot.kick();
    foot.update(viewport, 0.1f);
    foot.draw(batch);
    verify(batch).draw(
        eq(texture),
        anyFloat(), anyFloat(),
        eq(foot.getWidth() / 2), eq(foot.getHeight() / 2),
        eq(foot.getWidth()), eq(foot.getHeight()),
        eq(1f), eq(1f),
        eq(60f),
        anyInt(), anyInt(),
        anyInt(), anyInt(),
        eq(false), eq(false));
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
  void testCollidesWithOpposingPlayerDelegatesToPlayer() {
    Player otherPlayer = mock(Player.class);
    when(otherPlayer.isPlayer1()).thenReturn(false);
    when(otherPlayer.collides(foot)).thenReturn(true);

    assertTrue(foot.collides(otherPlayer));
    verify(otherPlayer).collides(foot);
  }
}