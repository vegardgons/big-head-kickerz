// package inf112.bigheadkickerz.model;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.Sprite;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.utils.viewport.Viewport;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.MockedConstruction;

// public class BallTest {

// private Ball ball;
// private Sprite mockSprite = mock(Sprite.class);
// private SpriteBatch mockBatch = mock(SpriteBatch.class);
// private Viewport mockViewport = mock(Viewport.class);

// @BeforeEach
// void setUp() {
// // Mock Texture for the ball to avoid file-related issues
// try (MockedConstruction<Texture> mockedTexture =
// mockConstruction(Texture.class)) {
// // When creating the ball, the mocked Texture will be used
// ball = new Ball("BallImage.png", 5, 5, 1);
// ball.getSprite().setPosition(5, 5);
// }
// }

// @Test
// void testInitialPosition() {
// assertEquals(5, ball.getSprite().getX(), 0.1f);
// assertEquals(5, ball.getSprite().getY(), 0.1f);
// float expectedSize = ball.getSprite().getX() + ball.getSprite().getWidth() /
// 2; // Center of sprite
// assertEquals(expectedSize, ball.getX(), 0.1f);
// }

// @Test
// void testApplyImpulse() {
// ball.applyImpulse(3, 4);
// assertEquals(30, ball.getVelocityX(), 0.1f); // Impulse factor should be
// applied (x * 10)
// assertEquals(40, ball.getVelocityY(), 0.1f); // Impulse factor should be
// applied (y * 10)
// }

// @Test
// void testSetPosition() {
// ball.setPosition(10, 10);
// assertEquals(9.7, ball.getSprite().getX(), 0.1f); // Position should be set
// to 10, but sprite is centered
// assertEquals(10, ball.getSprite().getY(), 0.1f);
// }

// @Test
// void testReset() {
// ball.applyImpulse(5, 5); // Apply some velocity to the ball
// ball.reset(); // Reset the ball to its initial position

// assertEquals(4.7, ball.getSprite().getX(), 0.1f); // Initial position X,
// sprite is centered
// assertEquals(5, ball.getSprite().getY(), 0.1f); // Initial position Y
// assertEquals(0, ball.getVelocityX(), 0.1f); // Velocity should be reset to 0
// assertEquals(0, ball.getVelocityY(), 0.1f); // Velocity should be reset to 0
// }

// @Test
// void testUpdate() {
// when(mockViewport.getWorldWidth()).thenReturn(800f);
// when(mockViewport.getWorldHeight()).thenReturn(600f);

// ball.setVelocity(10, 10);
// float initialX = ball.getSprite().getX();
// float initialY = ball.getSprite().getY();

// ball.update(mockViewport, 0.016f); // Assume 60 FPS, deltaTime = 1/60

// assertTrue(ball.getSprite().getX() > initialX); // Horizontal velocity should
// move the ball
// assertTrue(ball.getSprite().getY() > initialY); // Vertical velocity should
// move the ball
// }

// @Test
// void testBounceOffWalls() {
// when(mockViewport.getWorldWidth()).thenReturn(800f);
// when(mockViewport.getWorldHeight()).thenReturn(600f);

// ball.setPosition(0, 300); // Place ball at the left wall
// ball.setVelocity(-10, 0); // Set a negative velocity (moving left)

// ball.update(mockViewport, 0.016f); // Update with deltaTime

// assertTrue(ball.getVelocityX() > 0); // Ball should bounce and change
// direction
// }

// @Test
// void testDrawCallsSpriteDraw() {
// ball.draw(mockBatch);
// verify(mockSprite, never()).draw(mockBatch);
// }

// @Test
// void testLeftWallCollision() {
// when(mockViewport.getWorldWidth()).thenReturn(800f);
// when(mockViewport.getWorldHeight()).thenReturn(600f);

// ball.setPosition(0, 300); // Place ball at the left wall
// ball.setVelocity(-10, 0); // Set a negative velocity (moving left)

// ball.update(mockViewport, 0.1f); // Update with deltaTime

// assertTrue(ball.getVelocityX() > 0); // Ball should bounce and change
// direction
// }

// @Test
// void testRightWallCollision() {
// when(mockViewport.getWorldWidth()).thenReturn(800f);
// when(mockViewport.getWorldHeight()).thenReturn(600f);

// ball.setPosition(800, 300); // Place ball at the right wall
// ball.setVelocity(10, 0); // Set a positive velocity (moving right)

// ball.update(mockViewport, 0.1f); // Update with deltaTime

// assertTrue(ball.getVelocityX() < 0); // Ball should bounce and change
// direction
// }

// @Test
// void testRoofCollision() {
// when(mockViewport.getWorldWidth()).thenReturn(800f);
// when(mockViewport.getWorldHeight()).thenReturn(600f);

// ball.setPosition(400, 600); // Place ball at the roof
// ball.setVelocity(0, 10); // Set a positive velocity (moving up)

// ball.update(mockViewport, 0.1f); // Update with deltaTime

// assertTrue(ball.getVelocityY() < 0); // Ball should bounce and change
// direction
// }

// @Test
// void testGroundCollision() {
// when(mockViewport.getWorldWidth()).thenReturn(800f);
// when(mockViewport.getWorldHeight()).thenReturn(600f);

// ball.setPosition(400, 0); // Place ball at the ground
// ball.setVelocity(0, -10); // Set a negative velocity (moving down)

// ball.update(mockViewport, 0.1f); // Update with deltaTime

// assertTrue(ball.getVelocityY() > 0); // Ball should bounce and change
// direction
// }

// @Test
// void testVelocityStopsOnGround() {
// when(mockViewport.getWorldWidth()).thenReturn(800f);
// when(mockViewport.getWorldHeight()).thenReturn(600f);

// ball.setPosition(400, 0); // Place ball at the ground
// ball.setVelocity(0, -0.05f); // Set a negative velocity (moving down)

// // Update 10 times
// for (int i = 0; i < 10; i++) {
// ball.update(mockViewport, 0.016f); // 60 FPS (delta = 1/60)
// }

// assertEquals(0, ball.getVelocityY(), 0.01f); // Ball should stop moving on
// the ground
// }

// }