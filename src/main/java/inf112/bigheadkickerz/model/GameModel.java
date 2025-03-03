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
        player1 = new Player("PlayerImage.png", player1X, 0, false, true);
        
        float playerWidth = player1.getWidth();
        float player2X = viewport.getWorldWidth() / 8 * (8 - 6.5f) - playerWidth;
        player2 = new Player("PlayerImage.png", player2X, 0, true, false);
        
        // Initialize collision detector
        collision = new Collision(player1, player2, ball);
    }
    
    /**
     * Update game state
     */
    public void update(float delta) {
        player1.update(viewport, delta);
        player2.update(viewport, delta);
        ball.update(viewport, delta);
        collision.checkCollision();
    }
    
    // Getters
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    public Ball getBall() {
        return ball;
    }
    
    public FitViewport getViewport() {
        return viewport;
    }
}