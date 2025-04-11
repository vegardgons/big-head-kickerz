package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.Collideable;
import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PowerupPickupTest {

  private Player player;
  private Ball ball;
  private Powerup powerup;
  private Texture texture;
  private PowerupPickup pickup;
  private Vector2 initialPosition;
  private final float size = 20f;

  @BeforeEach
  void setUp() {
    powerup = Mockito.mock(Powerup.class);
    texture = Mockito.mock(Texture.class);
    initialPosition = new Vector2(50, 50);
    pickup = new PowerupPickup(powerup, initialPosition, texture, size);
    player = Mockito.mock(Player.class);
    ball = Mockito.mock(Ball.class);
    when(ball.getPlayerLastTouch()).thenReturn(player);
  }

  @Test
  void testIsCollectedInitiallyFalse() {
    assertFalse(pickup.isCollected());
  }

  @Test
  void testGetWidthAndHeight() {
    assertEquals(size, pickup.getWidth());
    assertEquals(size, pickup.getHeight());
  }

  @Test
  void testGetPositionReturnsCorrectPosition() {
    assertEquals(initialPosition, pickup.getPosition());
  }

  @Test
  void testSetPositionUpdatesPosition() {
    Vector2 newPosition = new Vector2(100, 100);
    pickup.setPosition(newPosition);
    assertEquals(newPosition, pickup.getPosition());
  }

  @Test
  void testGetVelocityReturnsZeroVector() {
    assertEquals(new Vector2(0, 0), pickup.getVelocity());
  }

  @Test
  void testGetWeightReturnsOne() {
    assertEquals(1, pickup.getWeight());
  }

  @Test
  void testCollisionWithBallAppliesPowerupAndSetsCollected() {
    when(ball.getPlayerLastTouch()).thenReturn(player);
    pickup.collision(ball);
    assertTrue(pickup.isCollected());
    Mockito.verify(powerup).apply(player);
  }

  @Test
  void testCollisionWithBallOnlyAppliesOnce() {
    pickup.collision(ball);
    pickup.collision(ball);
    Mockito.verify(powerup, Mockito.times(1)).apply(player);
  }

  @Test
  void testCollisionWithNonBallDoesNothing() {
    Collideable other = Mockito.mock(Collideable.class);
    when(other.getPosition()).thenReturn(new Vector2(0, 0));
    when(other.getWidth()).thenReturn(10f);
    when(other.getHeight()).thenReturn(10f);
    pickup.collision(other);
    assertFalse(pickup.isCollected());
    Mockito.verify(powerup, Mockito.never()).apply(Mockito.any());
  }

  @Test
  void testCollidesReturnsTrueWhenBallOverlapping() {
    Mockito.when(ball.getPosition()).thenReturn(new Vector2(55, 55));
    Mockito.when(ball.getWidth()).thenReturn(10f);
    Mockito.when(ball.getHeight()).thenReturn(10f);
    assertTrue(pickup.collides(ball));
  }

  @Test
  void testCollidesReturnsFalseWhenNotOverlapping() {
    Mockito.when(ball.getPosition()).thenReturn(new Vector2(200, 200));
    Mockito.when(ball.getWidth()).thenReturn(10f);
    Mockito.when(ball.getHeight()).thenReturn(10f);

    assertFalse(pickup.collides(ball));
  }

  @Test
  void testCollidesOnEdgeTouchingShouldNotCollide() {
    Mockito.when(ball.getPosition())
        .thenReturn(new Vector2(initialPosition.x + size, initialPosition.y));
    Mockito.when(ball.getWidth()).thenReturn(10f);
    Mockito.when(ball.getHeight()).thenReturn(10f);

    assertFalse(pickup.collides(ball));
  }

  @Test
  void testCollidesFullyContainedWithinPickup() {
    Mockito.when(ball.getPosition())
        .thenReturn(new Vector2(initialPosition.x + 5, initialPosition.y + 5));
    Mockito.when(ball.getWidth()).thenReturn(5f);
    Mockito.when(ball.getHeight()).thenReturn(5f);

    assertTrue(pickup.collides(ball));
  }

  @Test
  void testCollidesFullySurroundsPickup() {
    Mockito.when(ball.getPosition())
        .thenReturn(new Vector2(initialPosition.x - 10, initialPosition.y - 10));
    Mockito.when(ball.getWidth()).thenReturn(100f);
    Mockito.when(ball.getHeight()).thenReturn(100f);

    assertTrue(pickup.collides(ball));
  }

  @Test
  void testDrawWhenNotCollected() {
    SpriteBatch batch = Mockito.mock(SpriteBatch.class);
    pickup.draw(batch);

    Mockito.verify(batch).draw(texture, initialPosition.x, initialPosition.y, size, size);
  }

  @Test
  void testDrawWhenCollectedDoesNotDraw() {
    SpriteBatch batch = Mockito.mock(SpriteBatch.class);
    pickup.collision(ball);
    pickup.draw(batch);
    Mockito.verify(batch, Mockito.never()).draw(
        Mockito.<Texture>any(),
        Mockito.anyFloat(),
        Mockito.anyFloat(),
        Mockito.anyFloat(),
        Mockito.anyFloat());
  }

  @Test
  void testUpdateDoesNothing() {
    Viewport viewport = Mockito.mock(Viewport.class);
    assertDoesNotThrow(() -> pickup.update(viewport, 0.16f));
  }
}