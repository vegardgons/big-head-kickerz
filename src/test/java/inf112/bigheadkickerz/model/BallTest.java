package inf112.bigheadkickerz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/** Testclass of Ball. */
public class BallTest {

  private Ball ball;
  private SpriteBatch batch;
  private Viewport viewport;

  @BeforeEach
  void setUp() {
    Texture textureMock = mock(Texture.class);
    ball = new Ball(textureMock, 5, 10);
    batch = mock(SpriteBatch.class);

    viewport = Mockito.mock(Viewport.class);
    Mockito.when(viewport.getWorldWidth()).thenReturn(10f);
    Mockito.when(viewport.getWorldHeight()).thenReturn(10f);
  }

  @Test
  void testConstructor() {
    assertNotNull(ball);
    assertEquals(5 - ball.getWidth() / 2, ball.getPosition().x);
    assertEquals(10, ball.getPosition().y);
    assertEquals(new Vector2(0, 0), ball.getVelocity());
  }

  @Test
  void testGetters() {
    assertEquals(0.6f, ball.getWidth());
    assertEquals(0.6f, ball.getHeight());
    assertEquals(0.5f, ball.getWeight());
    assertEquals(new Vector2(0, 0), ball.getVelocity());
    assertEquals(-9.81f, ball.getGravity());
  }

  @Test
  void testDraw() {
    ball.draw(batch);
    Mockito.verify(batch).draw(Mockito.any(Texture.class), Mockito.anyFloat(), Mockito.anyFloat(),
        Mockito.anyFloat(), Mockito.anyFloat());
  }

  @Test
  void testChangeGravity() {
    assertEquals(-9.81f, ball.getGravity());
    ball.setGravity(-5.0f);
    assertEquals(-5.0f, ball.getGravity());
  }

  @Test
  void testChangeVelocity() {
    assertEquals(new Vector2(0, 0), ball.getVelocity());
    ball.setVelocity(new Vector2(1, 1));
    assertEquals(new Vector2(1, 1), ball.getVelocity());
  }

  @Test
  void testChangePosition() {
    assertEquals(new Vector2(5 - ball.getWidth() / 2, 10), ball.getPosition());
    ball.setPosition(new Vector2(1, 1));
    assertEquals(new Vector2(1, 1), ball.getPosition());
  }

  @Test
  void testBoundariesLeftEdge() {
    ball.setPosition(new Vector2(-1, 5));
    ball.update(viewport, 1);
    // Check if the ball's position is reset to 0
    assertEquals(0, ball.getPosition().x);
  }

  @Test
  void testBoundariesRightEdge() {
    ball.setPosition(new Vector2(10, 5));
    ball.update(viewport, 1);
    // Check if the ball's position is reset to viewport width - ball width
    assertEquals(viewport.getWorldWidth() - ball.getWidth(), ball.getPosition().x);
  }

  @Test
  void testBoundariesTopEdge() {
    ball.setPosition(new Vector2(5, 20));
    ball.update(viewport, 1);
    // Check if the ball's position is reset to viewport height - ball height
    assertEquals(viewport.getWorldHeight() - ball.getHeight(), ball.getPosition().y);
  }

  @Test
  void testBoundariesBottomEdge() {
    ball.setPosition(new Vector2(5, -1));
    ball.update(viewport, 1);
    // Check if the ball's position is reset to 0
    assertEquals(0, ball.getPosition().y);
  }

  @Test
  void testReset() {
    ball.setPosition(new Vector2(5, 5));
    ball.setVelocity(new Vector2(1, 1));
    ball.reset();

    assertEquals(new Vector2(5 - ball.getWidth() / 2, 10), ball.getPosition());
    assertEquals(new Vector2(0, 0), ball.getVelocity());
  }

  @Test
  void testCollidesWithGoalDoesNothing() {
    Goal goal = mock(Goal.class);
    when(goal.getPosition()).thenReturn(new Vector2(5, 10));
    when(goal.getWidth()).thenReturn(1f);
    when(goal.getHeight()).thenReturn(1.2f);

    // Returns false even if the ball is in the goal, because the logic is in the
    // Goal class
    assertFalse(ball.collides(goal));
  }

  @Test
  void testCollidesWithPlayer() {
    Player player = mock(Player.class);
    when(player.getPosition()).thenReturn(new Vector2(5, 10));
    when(player.getWidth()).thenReturn(1f);
    when(player.getHeight()).thenReturn(1.2f);

    assertTrue(ball.collides(player));
  }

  @Test
  void testCollisionGoal() {
    Goal goal = mock(Goal.class);
    when(goal.getPosition()).thenReturn(new Vector2(5, 10));
    when(goal.getWidth()).thenReturn(1f);
    when(goal.getHeight()).thenReturn(1.2f);

    ball.collision(goal);

    // Verify that the ball's velocity is not changed
    verify(goal, Mockito.never()).setVelocity(Mockito.any());
    verify(goal, Mockito.never()).setPosition(Mockito.any());
  }

  @Test
  void testCollisionWithPlayer() {
    Player player = mock(Player.class);
    when(player.getPosition()).thenReturn(new Vector2(5, 10));
    when(player.getVelocity()).thenReturn(new Vector2(1, 0));

    when(player.getWidth()).thenReturn(1f);
    when(player.getHeight()).thenReturn(1.2f);

    ball.collision(player);

    verify(player, Mockito.never()).setPosition(Mockito.any());
  }
}