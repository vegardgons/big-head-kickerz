package inf112.bigheadkickerz.model;

import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.bigheadkickerz.controller.ControllableGameModel;

/**
 * GameModel holds all game state and implements ControllableGameModel
 * to allow the controller to interact with it.
 */
public class GameModel implements ControllableGameModel {
    // Game dimensions
    private static final float WIDTH = 15;
    private static final float HEIGHT = 8;

    // Game objects
    private Player player1;
    private Player player2;
    private Ball ball;
    private Collision collision;

    // Score tracking
    private int player1Score;
    private int player2Score;
    private float goalTimer;
    private static final float GOAL_DELAY = 3f;
    private boolean isGoal;

    // Viewport for game boundaries
    private FitViewport viewport;

    /**
     * Constructor initializes game objects and viewport
     */
    public GameModel() {
        viewport = new FitViewport(WIDTH, HEIGHT);

        // Initialize ball at center
        float ballX = viewport.getWorldWidth() / 2;
        float ballY = viewport.getWorldHeight() / 2 + 1.5f;
        ball = new Ball("BallImage.png", ballX, ballY);

        // Initialize players
        float player1X = viewport.getWorldWidth() / 8 * 6.5f;
        player1 = new Player("Player_1.png", player1X, 0, false, true);

        float playerWidth = player1.getWidth();
        float player2X = viewport.getWorldWidth() / 8 * (8 - 6.5f) - playerWidth;
        player2 = new Player("Player_1.png", player2X, 0, true, false);

        // Initialize collision detector
        collision = new Collision(player1, player2, ball);

        // Initialize score tracking
        player1Score = 0;
        player2Score = 0;
        goalTimer = 0;
        isGoal = false;
    }

    /**
     * Update game state
     */
    public void update(float delta) {

        if (isGoal) {
            goalTimer += delta;
            if (goalTimer >= GOAL_DELAY) {
                isGoal = false;
                goalTimer = 0;
                resetPositions();
            }

        } else {
            checkForGoal();
        }
        player1.update(viewport, delta);
        player2.update(viewport, delta);
        ball.update(viewport, delta);
        collision.checkCollision();
    }

    private boolean checkForGoal() {
        float rightGoalX = viewport.getWorldWidth() / 8 * 7.5f;
        float leftGoalX = viewport.getWorldWidth() / 8 * (8 - 7.5f);
        if (ball.getX() >= rightGoalX) {
            player2Score++;
            System.out.println("P2 scored!\n");
            goalReset();

        } else if (ball.getX() <= leftGoalX) {
            player1Score++;
            System.out.println("P1 scored!\n");
            goalReset();
        }
        return false;
    }

    private boolean goalReset() {
        System.out.println("Score:\nP1: " + player1Score + "\nP2: " + player2Score);
        isGoal = true;
        goalTimer = 0;
        return true;
    }

    /**
     * Reset positions of all game objects to their starting positions
     */
    private void resetPositions() {
        ball.reset();
        player1.reset();
        player2.reset();
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Ball getBall() {
        return this.ball;
    }

    public FitViewport getViewport() {
        return this.viewport;
    }

    /**
     * Reset ball position to center after a goal
     */

    @Override
    public int getPlayer1Score() {
        return this.player1Score;
    }

    @Override
    public int getPlayer2Score() {
        return this.player2Score;
    }

}