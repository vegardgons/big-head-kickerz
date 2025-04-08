package inf112.bigheadkickerz.model.powerups;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.bigheadkickerz.model.Collideable;
import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PowerupPickupTest {

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
  void testCollisionWithPlayerAppliesPowerupAndSetsCollected() {
    Player player = Mockito.mock(Player.class);
    pickup.collision(player);
    assertTrue(pickup.isCollected());
    Mockito.verify(powerup).apply(player);
  }

  @Test
  void testCollisionWithPlayerOnlyAppliesOnce() {
    Player player = Mockito.mock(Player.class);
    pickup.collision(player);
    pickup.collision(player);
    Mockito.verify(powerup, Mockito.times(1)).apply(player);
  }

  @Test
  void testCollisionWithNonPlayerDoesNothing() {
    Collideable nonPlayer = Mockito.mock(Collideable.class);
    pickup.collision(nonPlayer);
    assertFalse(pickup.isCollected());
    Mockito.verify(powerup, Mockito.never()).apply(Mockito.any());
  }

  @Test
  void testCollidesReturnsTrueWhenOverlapping() {
    Collideable other = Mockito.mock(Collideable.class);
    Mockito.when(other.getPosition()).thenReturn(new Vector2(55, 55));
    Mockito.when(other.getWidth()).thenReturn(10f);
    Mockito.when(other.getHeight()).thenReturn(10f);

    assertTrue(pickup.collides(other));
  }

  @Test
  void testCollidesReturnsFalseWhenNotOverlapping() {
    Collideable other = Mockito.mock(Collideable.class);
    Mockito.when(other.getPosition()).thenReturn(new Vector2(200, 200));
    Mockito.when(other.getWidth()).thenReturn(10f);
    Mockito.when(other.getHeight()).thenReturn(10f);

    assertFalse(pickup.collides(other));
  }

  @Test
  void testCollidesOnEdgeTouchingShouldNotCollide() {
    Collideable other = Mockito.mock(Collideable.class);
    Mockito.when(other.getPosition())
        .thenReturn(new Vector2(initialPosition.x + size, initialPosition.y));
    Mockito.when(other.getWidth()).thenReturn(10f);
    Mockito.when(other.getHeight()).thenReturn(10f);

    assertFalse(pickup.collides(other));
  }

  @Test
  void testCollidesFullyContainedWithinPickup() {
    Collideable other = Mockito.mock(Collideable.class);
    Mockito.when(other.getPosition())
        .thenReturn(new Vector2(initialPosition.x + 5, initialPosition.y + 5));
    Mockito.when(other.getWidth()).thenReturn(5f);
    Mockito.when(other.getHeight()).thenReturn(5f);

    assertTrue(pickup.collides(other));
  }

  @Test
  void testCollidesFullySurroundsPickup() {
    Collideable other = Mockito.mock(Collideable.class);
    Mockito.when(other.getPosition())
        .thenReturn(new Vector2(initialPosition.x - 10, initialPosition.y - 10));
    Mockito.when(other.getWidth()).thenReturn(100f);
    Mockito.when(other.getHeight()).thenReturn(100f);

    assertTrue(pickup.collides(other));
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
    Player player = Mockito.mock(Player.class);
    pickup.collision(player);

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