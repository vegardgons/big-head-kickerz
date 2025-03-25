package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.model.Goal;
import inf112.bigheadkickerz.model.Player;

/**
 * GameViewImpl is responsible for rendering all game objects
 */
public class GameViewImpl implements GameView {
    private SpriteBatch spriteBatch;
    private Field field;
    private Goal rightGoal;
    private Goal leftGoal;
    private GameModel gameModel;
    private ScoreBoard scoreBoard;

    /**
     * Constructor initializes rendering components
     */
    public GameViewImpl(GameModel gameModel) {
        this.gameModel = gameModel;
        spriteBatch = new SpriteBatch();

        // Initialize field
        field = new Field("OldTrafford.png");

        scoreBoard = new ScoreBoard();
    }

    /**
     * Render all game objects
     */
    public void render() {
        FitViewport viewport = gameModel.getViewport();

        // Clear screen
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Draw game objects
        draw(spriteBatch, viewport);

        scoreBoard.updateScores(gameModel.getPlayer1Score(), gameModel.getPlayer2Score());
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        scoreBoard.render(delta);

    }

    /**
     * Handle screen resize
     */
    public void resize(int width, int height) {
        gameModel.getViewport().update(width, height, true);
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        spriteBatch.dispose();
        scoreBoard.dispose();

    }

    @Override
    public void draw(SpriteBatch batch, Viewport viewport) {
        Player player1 = gameModel.getPlayer1();
        Player player2 = gameModel.getPlayer2();
        Ball ball = gameModel.getBall();
        Goal rightGoal = gameModel.getRightGoal();
        Goal leftGoal = gameModel.getLeftGoal();

        spriteBatch.begin();
        field.draw(spriteBatch, viewport);
        player1.draw(spriteBatch);
        player2.draw(spriteBatch);
        ball.draw(spriteBatch);
        rightGoal.draw(spriteBatch);
        leftGoal.draw(spriteBatch);
        spriteBatch.end();
    }

    /**
     * Get right goal
     * 
     * @return right goal
     */
    public Goal getRightGoal() {
        return rightGoal;
    }

    /**
     * Get left goal
     * 
     * @return left goal
     */
    public Goal getLeftGoal() {
        return leftGoal;
    }
}