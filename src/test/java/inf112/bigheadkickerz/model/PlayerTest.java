// package inf112.bigheadkickerz.model;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.never;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Input;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.utils.viewport.Viewport;
// import inf112.bigheadkickerz.controller.GameController;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// /**
// * Test class for the Player class.
// */
// class PlayerTest {

// private Player player;
// private SpriteBatch batch;
// private Viewport viewport;

// @BeforeEach
// void setUp() {
// Gdx.input = Mockito.mock(Input.class);
// Mockito.when(Gdx.input.isKeyPressed(Mockito.anyInt())).thenReturn(false);

// Texture textureMock = mock(Texture.class);
// player = new Player(textureMock, 5, 10, true);
// batch = mock(SpriteBatch.class);

// viewport = Mockito.mock(Viewport.class);
// Mockito.when(viewport.getWorldWidth()).thenReturn(10f);
// Mockito.when(viewport.getWorldHeight()).thenReturn(10f);
// }

// @Test
// void testConstructor() {
// assertNotNull(player.getPosition());
// // 0.3 because of the foot
// assertEquals(new Vector2(5, 10.3f), player.getPosition());
// assertEquals(1f, player.getWidth());
// assertEquals(1f, player.getHeight());
// assertEquals(300, player.getWeight());
// }

// @Test
// void testDraw() {
// player.draw(batch);
// verify(batch, times(1)).draw(any(Texture.class), eq(5f), eq(10.3f), eq(1f),
// eq(1f));
// }

// @Test
// void testReset() {
// // Set initial position and velocity
// player.setPosition(new Vector2(10, 20));
// player.setVelocity(new Vector2(5, 5));

// // Reset the player
// player.reset();
// assertEquals(new Vector2(5, 10.3f), player.getPosition());
// assertEquals(new Vector2(0, 0), player.getVelocity());
// }

// @Test
// void testGetters() {
// assertEquals(1f, player.getWidth());
// assertEquals(1f, player.getHeight());
// assertEquals(300, player.getWeight());
// assertEquals(new Vector2(0, 0), player.getVelocity());
// assertEquals(5, player.getJumpHeight());
// assertEquals(4, player.getMovementSpeed());
// }

// @Test
// void testCollisionWithPlayerFromAbove() {
// Player otherPlayer = mock(Player.class);
// when(otherPlayer.getPosition()).thenReturn(new Vector2(6, 2));
// when(otherPlayer.getHeight()).thenReturn(1.2f);
// when(otherPlayer.getVelocity()).thenReturn(new Vector2(1, 2));

// player.collision(otherPlayer);

// verify(otherPlayer, times(1)).setVelocity(new Vector2(0, 0));
// verify(otherPlayer, never()).setPosition(any());
// }

// @Test
// void testCollisionWithPlayerFromRight() {
// Player otherPlayer = mock(Player.class);
// when(otherPlayer.getPosition()).thenReturn(new Vector2(7, 10));
// when(otherPlayer.getWidth()).thenReturn(1f);
// when(otherPlayer.getHeight()).thenReturn(1.2f);
// when(otherPlayer.getVelocity()).thenReturn(new Vector2(2, 0));

// player.collision(otherPlayer);

// // Moves other player to the left
// verify(otherPlayer, times(1)).setPosition(new Vector2(6.5f, 10));
// }

// @Test
// void testCollisionWithGoalDoesNothing() {
// Goal goal = mock(Goal.class);
// when(goal.getPosition()).thenReturn(new Vector2(5, 10));
// when(goal.getWidth()).thenReturn(1f);
// when(goal.getHeight()).thenReturn(1.2f);

// player.collision(goal);

// verify(goal, never()).setVelocity(any());
// verify(goal, never()).setPosition(any());
// }

// @Test
// void testCollisionWithBallDoesNothing() {
// Ball ball = mock(Ball.class);
// when(ball.getPosition()).thenReturn(new Vector2(5, 10));
// when(ball.getWidth()).thenReturn(1f);
// when(ball.getHeight()).thenReturn(1f);

// player.collision(ball);

// verify(ball, never()).setVelocity(any());
// verify(ball, never()).setPosition(any());
// }

// @Test
// void testCollidesWithBall() {
// Ball ball = mock(Ball.class);
// when(ball.getPosition()).thenReturn(new Vector2(5, 10));
// when(ball.getWidth()).thenReturn(1f);
// when(ball.getHeight()).thenReturn(1f);

// assertTrue(player.collides(ball));

// when(ball.getPosition()).thenReturn(new Vector2(10, 10));
// assertFalse(player.collides(ball));
// }

// @Test
// void testCollidesWithPlayer() {
// Player otherPlayer = mock(Player.class);
// when(otherPlayer.getPosition()).thenReturn(new Vector2(5, 10));
// when(otherPlayer.getWidth()).thenReturn(1f);
// when(otherPlayer.getHeight()).thenReturn(1.2f);

// assertTrue(player.collides(otherPlayer));

// when(otherPlayer.getPosition()).thenReturn(new Vector2(10, 10));
// assertFalse(player.collides(otherPlayer));
// }

// @Test
// void testCollidesWithGoalDoesNothingFromPlayerClass() {
// Goal goal = mock(Goal.class);
// when(goal.getPosition()).thenReturn(new Vector2(5, 10));
// when(goal.getWidth()).thenReturn(1f);
// when(goal.getHeight()).thenReturn(1.2f);

// // Returns false even if the player is in the goal, because the logic is in
// the
// // Goal class
// assertFalse(player.collides(goal));
// }

// @Test
// void testChangeJumpHeight() {
// player.setJumpHeight(10);
// assertEquals(10, player.getJumpHeight());
// }

// @Test
// void testChangeMovementSpeed() {
// player.setMovementSpeed(10);
// assertEquals(10, player.getMovementSpeed());
// }

// @Test
// void testChangeWidth() {
// player.setWidth(2);
// assertEquals(2, player.getWidth());
// }

// @Test
// void testChangeHeight() {
// player.setHeight(3);
// assertEquals(3, player.getHeight());
// }

// @Test
// void testBoundariesLeftEdge() {
// player.setPosition(new Vector2(-1, 5));
// player.setPlayerController(new GameController());
// player.update(viewport, 0.016f);
// assertEquals(0, player.getPosition().x);
// }

// @Test
// void testBoundariesRightEdge() {
// player.setPosition(new Vector2(11, 5));
// player.setPlayerController(new GameController(false, player, null));
// player.update(viewport, 0.016f);
// assertEquals(10f - player.getWidth(), player.getPosition().x);
// }

// @Test
// void testBoundariesBottomEdge() {
// player.setPosition(new Vector2(5, -1));
// player.setPlayerController(new GameController(false, player, null));
// player.update(viewport, 0.016f);
// // 0.3 because of the foot
// assertEquals(0.3f, player.getPosition().y);
// }

// @Test
// void testBoundariesTopEdge() {
// player.setPosition(new Vector2(5, 11));
// player.setPlayerController(new GameController(false, player, null));
// player.update(viewport, 0.016f);
// assertEquals(10f - player.getHeight(), player.getPosition().y);
// }

// }
