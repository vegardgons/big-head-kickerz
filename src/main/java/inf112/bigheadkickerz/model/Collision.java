package inf112.bigheadkickerz.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collision {

    // World boundary constants
    private static final float WORLD_WIDTH = 15f;

    private Player player1;
    private Player player2;
    private Ball ball;

    public Collision(Player player1, Player player2, Ball ball) {
        this.player1 = player1;
        this.player2 = player2;
        this.ball = ball;
    }

    public void checkCollision() {
        // Check for ball-player collisions
        checkBallPlayerCollision(player1);
        checkBallPlayerCollision(player2);

        // Check for player-player collision
        checkPlayerPlayerCollision();
    }

    /**
     * Checks and handles collision between ball and a player
     * 
     * @param player the player to check collision with
     */
    private void checkBallPlayerCollision(Player player) {
        Sprite ballSprite = ball.getSprite();
        Sprite playerSprite = player.getSprite();

        if (overlaps(ballSprite, playerSprite)) {
            // Calculate collision response
            Vector2 ballCenter = new Vector2(
                    ballSprite.getX() + ballSprite.getWidth() / 2,
                    ballSprite.getY() + ballSprite.getHeight() / 2);

            Vector2 playerCenter = new Vector2(
                    playerSprite.getX() + playerSprite.getWidth() / 2,
                    playerSprite.getY() + playerSprite.getHeight() / 2);

            // Direction vector from player to ball
            Vector2 direction = new Vector2(
                    ballCenter.x - playerCenter.x,
                    ballCenter.y - playerCenter.y).nor(); // Normalize

            // Get player velocity
            Vector2 playerVelocity = player.getVelocity();
            float playerSpeed = playerVelocity.len();

            // Ensures that the ball is moved even though the player moves slowly
            // Can be changed to change the touch of the player
            float impulseMultiplier = Math.max(playerSpeed, 0.1f); // juster 0.5f etter behov

            // Check if ball is near ground or in corner
            boolean nearGround = ballSprite.getY() < 0.1f;
            boolean atLeftEdge = ballSprite.getX() < 0.1f;
            boolean atRightEdge = ballSprite.getX() > (WORLD_WIDTH - ballSprite.getWidth() - 0.1f);

            // Adjust impulse for ground/corner cases
            float xImpulse = direction.x;
            float yImpulse = direction.y;

            // Prevent pushing through ground in corners
            if (nearGround && (atLeftEdge || atRightEdge)) {
                // Redirect force more upward when in corners
                if (yImpulse < 0.3f) {
                    yImpulse = Math.max(yImpulse, 0.3f);
                }
                xImpulse *= 0.5f; // Reduce horizontal force in corners
            }

            // Apply modified impulse to ball
            ball.applyImpulse(xImpulse * impulseMultiplier, yImpulse * impulseMultiplier);

            // Move ball outside of collision
            float overlap = getOverlapDistance(ballSprite, playerSprite);

            // Calculate new position
            float newX = ballSprite.getX() + direction.x * overlap;
            float newY = ballSprite.getY() + direction.y * overlap;

            // Ensure the ball doesn't go below ground
            newY = Math.max(0, newY);

            // Set the adjusted position
            ballSprite.setPosition(newX, newY);
        }
    }

    /**
     * Checks and handles collision between players
     */
    private void checkPlayerPlayerCollision() {
        Sprite player1Sprite = player1.getSprite();
        Sprite player2Sprite = player2.getSprite();

        if (overlaps(player1Sprite, player2Sprite)) {
            // Calculate collision response
            Vector2 player1Center = new Vector2(
                    player1Sprite.getX() + player1Sprite.getWidth() / 2,
                    player1Sprite.getY() + player1Sprite.getHeight() / 2);

            Vector2 player2Center = new Vector2(
                    player2Sprite.getX() + player2Sprite.getWidth() / 2,
                    player2Sprite.getY() + player2Sprite.getHeight() / 2);

            // Direction vector from player2 to player1
            Vector2 direction = new Vector2(
                    player1Center.x - player2Center.x,
                    player1Center.y - player2Center.y).nor(); // Normalize

            // Calculate overlap
            float overlap = getOverlapDistance(player1Sprite, player2Sprite);

            // Move players away from each other
            player1.moveBy(direction.x * overlap * 0.5f, 0.005f);
            player2.moveBy(-direction.x * overlap * 0.5f, 0.005f);
        }
    }

    /**
     * Checks if two sprites overlap
     */
    private boolean overlaps(Sprite sprite1, Sprite sprite2) {
        Rectangle rect1 = sprite1.getBoundingRectangle();
        Rectangle rect2 = sprite2.getBoundingRectangle();
        return rect1.overlaps(rect2);
    }

    /**
     * Gets the overlap distance between two sprites
     */
    private float getOverlapDistance(Sprite sprite1, Sprite sprite2) {
        Rectangle rect1 = sprite1.getBoundingRectangle();
        Rectangle rect2 = sprite2.getBoundingRectangle();

        // Calculate centers
        float centerX1 = rect1.x + rect1.width / 2;
        float centerY1 = rect1.y + rect1.height / 2;
        float centerX2 = rect2.x + rect2.width / 2;
        float centerY2 = rect2.y + rect2.height / 2;

        // Calculate the vector between centers
        float dx = centerX1 - centerX2;
        float dy = centerY1 - centerY2;

        // Calculate the minimum distance to separate sprites
        float minDistX = (rect1.width + rect2.width) / 2;
        float minDistY = (rect1.height + rect2.height) / 2;

        // Calculate actual distance
        float actualDist = (float) Math.sqrt(dx * dx + dy * dy);

        // Return the minimum distance to separate the sprites
        return Math.max(0, Math.min(minDistX, minDistY) - actualDist);
    }

}
