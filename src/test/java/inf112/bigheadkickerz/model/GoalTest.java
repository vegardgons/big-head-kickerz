package inf112.bigheadkickerz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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
    goal = new Goal(textureMock, 5, 10);
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
  void setVelocityDoesNothing() {
    Vector2 newVelocity = new Vector2(1, 1);
    goal.setVelocity(newVelocity);
    assertEquals(new Vector2(0, 0), goal.getVelocity());
  }

  @Test
  void testSetPosition() {
    Vector2 newPosition = new Vector2(1, 1);
    goal.setPosition(newPosition);
    assertEquals(new Vector2(1, 1), goal.getPosition());
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

  @Test
  void testCollisionFromBelowBranch() {
    Collideable obj = mock(Collideable.class);

    when(obj.getPosition()).thenReturn(new Vector2(5.5f, 12.5f));
    when(obj.getHeight()).thenReturn(0.5f);
    when(obj.getVelocity()).thenReturn(new Vector2(0f, 2f));

    when(obj.getPosition()).thenReturn(new Vector2(5.1f, 12.5f));
    when(obj.getPosition()).thenReturn(new Vector2(1f, 12.5f));

    goal.collision(obj);
    verify(obj, times(1)).setPosition(any(Vector2.class));
    verify(obj, times(2)).setVelocity(any(Vector2.class));
  }

  @Test
  void testCollisionFromAboveBranch() {
    Collideable obj = mock(Collideable.class);
    when(obj.getPosition()).thenReturn(new Vector2(10f, 12.7f));
    when(obj.getHeight()).thenReturn(0.2f);
    when(obj.getVelocity()).thenReturn(new Vector2(0f, -3f));
    goal.collision(obj);

    verify(obj, times(1)).setPosition(any(Vector2.class));
    verify(obj, times(2)).setVelocity(any(Vector2.class));
  }
}