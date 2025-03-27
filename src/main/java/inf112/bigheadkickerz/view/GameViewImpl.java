package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.model.Goal;
import inf112.bigheadkickerz.model.Player;

/**
 * GameViewImpl is responsible for rendering all game objects
 */
public class GameViewImpl extends AScreen {
    private SpriteBatch spriteBatch;
    private Field field;
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
        scoreBoard = gameModel.getScoreBoard();
    }

    /**
     * Render all game objects
     */
    public void render() {
        FitViewport viewport = gameModel.getViewport();
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        draw(spriteBatch, viewport);

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
        field.dispose();
    }

    public void draw(SpriteBatch batch, Viewport viewport) {
        Player player1 = gameModel.getPlayer2();
        Player player2 = gameModel.getPlayer1();
        Ball ball = gameModel.getBall();
        Goal rightGoal = gameModel.getRightGoal();
        Goal leftGoal = gameModel.getLeftGoal();

        batch.begin();
        field.draw(batch, viewport);
        player1.draw(batch);
        player2.draw(batch);
        ball.draw(batch);
        leftGoal.draw(batch);
        rightGoal.draw(batch);
        batch.end();
        if (gameModel.getGameState() == GameState.TIMED) {
            scoreBoard.render(batch, gameModel.getPlayer1Score(), gameModel.getPlayer2Score(),
                    gameModel.getRemainingTime());
        } else if (gameModel.getGameState() == GameState.FIRST_TO_SEVEN) {
            scoreBoard.render(batch, gameModel.getPlayer1Score(), gameModel.getPlayer2Score(), 0);
        }

    }

}