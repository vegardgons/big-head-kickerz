package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.GameModel;
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

    /**
     * Constructor initializes rendering components
     */
    public GameViewImpl(GameModel gameModel) {
        this.gameModel = gameModel;
        spriteBatch = new SpriteBatch();

        // Initialize field
        field = new Field("OldTrafford.png");

        FitViewport viewport = gameModel.getViewport();

        // Initialize goals
        float rightGoalX = viewport.getWorldWidth() / 8 * 7.2f;
        rightGoal = new Goal("GoalImage.png", rightGoalX, 0, false);

        float goalWidth = rightGoal.getWidth();
        float leftGoalX = viewport.getWorldWidth() / 8 * (8 - 7.2f) - goalWidth;
        leftGoal = new Goal("GoalImage.png", leftGoalX, 0, true);
    }

    /**
     * Render all game objects
     */
    public void render() {
        FitViewport viewport = gameModel.getViewport();
        Player player1 = gameModel.getPlayer1();
        Player player2 = gameModel.getPlayer2();
        Ball ball = gameModel.getBall();

        // Clear screen
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Draw all objects
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
    }

    @Override
    public void draw(SpriteBatch batch, Viewport viewport) {

    }
}