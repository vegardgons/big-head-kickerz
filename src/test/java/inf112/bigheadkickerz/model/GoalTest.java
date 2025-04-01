package inf112.bigheadkickerz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoalTest {

  private Goal goal;
  private SpriteBatch batch;

  @BeforeEach
  void setUp() {
    Texture textureMock = mock(Texture.class);
    goal = new Goal(textureMock, 5, 10, true);
    batch = mock(SpriteBatch.class);
  }

  @Test
  void testConstructor() {
    assertNotNull(goal.getPosition());
    assertEquals(new Vector2(5, 10), goal.getPosition());
    assertEquals(1.6f, goal.getWidth());
    assertEquals(3f, goal.getHeight());
    assertEquals(1000, goal.getWeight());
  }

  @Test
  void testDraw() {
    goal.draw(batch);
    verify(batch, times(1)).draw(any(Texture.class), eq(5f), eq(10f), eq(1.6f), eq(3f));
  }

  @Test
  void testGetters() {
    assertEquals(1.6f, goal.getWidth());
    assertEquals(3f, goal.getHeight());
    assertEquals(1000, goal.getWeight());
    assertEquals(new Vector2(0, 0), goal.getVelocity());
  }

  @Test
  void testCollisionWithBallFromBelow() {
    Ball ball = mock(Ball.class);
    when(ball.getPosition()).thenReturn(new Vector2(6, 2));
    when(ball.getHeight()).thenReturn(0.5f);
    when(ball.getVelocity()).thenReturn(new Vector2(1, 2));

    goal.collision(ball);

    verify(ball, times(1)).setVelocity(new Vector2(1, 0));
    verify(ball, never()).setPosition(any());
  }

  @Test
  void testCollisionWithBallFromAbove() {
    Ball ball = mock(Ball.class);
    when(ball.getPosition()).thenReturn(new Vector2(6, 3));
    when(ball.getHeight()).thenReturn(0.5f);
    when(ball.getVelocity()).thenReturn(new Vector2(1, -2));

    goal.collision(ball);

    verify(ball, times(1)).setVelocity(new Vector2(1, -2 * (-0.8f)));
  }

  @Test
  void testCollisionWithPlayerFromBelow() {
    Player player = mock(Player.class);

    when(player.getPosition()).thenReturn(new Vector2(6, 2));
    when(player.getHeight()).thenReturn(0.5f);
    when(player.getVelocity()).thenReturn(new Vector2(1, 2));

    goal.collision(player);

    verify(player, times(1)).setVelocity(new Vector2(1, 0));
  }

  @Test
  void testCollisionWithPlayerFromAbove() {
    Player player = mock(Player.class);

    when(player.getPosition()).thenReturn(new Vector2(6, 3));
    when(player.getHeight()).thenReturn(0.5f);
    when(player.getVelocity()).thenReturn(new Vector2(1, -2));

    goal.collision(player);

    verify(player, times(1)).setPosition(new Vector2(6, 3 - 0.15f));
    verify(player, times(1)).setVelocity(new Vector2(1, 0));
  }

  @Test
  void setVelocityDoesNothing() {
    Vector2 newVelocity = new Vector2(1, 1);
    goal.setVelocity(newVelocity);
    assertEquals(new Vector2(0, 0), goal.getVelocity());
  }

  @Test
  void setPositionDoesNothing() {
    Vector2 newPosition = new Vector2(1, 1);
    goal.setPosition(newPosition);
    assertEquals(new Vector2(5, 10), goal.getPosition());
  }

  @Test
  void testHitCrossbarFromBelow() {
    Collideable mockCollideable = mock(Collideable.class);

    when(mockCollideable.getPosition()).thenReturn(new Vector2(6.1f, 2.6f));
    when(mockCollideable.getHeight()).thenReturn(0.4f);
    when(mockCollideable.getWidth()).thenReturn(0.4f);
    when(mockCollideable.getVelocity()).thenReturn(new Vector2(1, 2));

    assertTrue(goal.collides(mockCollideable));
  }

  @Test
  void testHitCrossbarFromAbove() {
    Collideable mockCollideable = mock(Collideable.class);

    when(mockCollideable.getPosition()).thenReturn(new Vector2(6.1f, 3f));
    when(mockCollideable.getHeight()).thenReturn(0.4f);
    when(mockCollideable.getWidth()).thenReturn(0.4f);
    when(mockCollideable.getVelocity()).thenReturn(new Vector2(1, -2));

    assertTrue(goal.collides(mockCollideable));
  }
}
