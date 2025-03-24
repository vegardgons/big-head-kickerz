package inf112.bigheadkickerz.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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
    private ShapeRenderer shapeRenderer;

    /**
     * Constructor initializes rendering components
     */
    public GameViewImpl(GameModel gameModel) {
        this.gameModel = gameModel;
        spriteBatch = new SpriteBatch();

        // Initialize field
        field = new Field("OldTrafford.png");

        scoreBoard = new ScoreBoard();
        shapeRenderer = new ShapeRenderer();
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

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.RED);

        // // Hent rektangelet
        // Rectangle rectangle = gameModel.getBallSprite().getBoundingRectangle();
        // Rectangle rectangle2 = gameModel.getPlayer1Sprite().getBoundingRectangle();
        // Rectangle rectangle3 = gameModel.getPlayer2Sprite().getBoundingRectangle();

        // // Tegn rektangelet
        // shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width,
        // rectangle.height);
        // shapeRenderer.rect(rectangle2.x, rectangle2.y, rectangle2.width,
        // rectangle2.height);
        // shapeRenderer.rect(rectangle3.x, rectangle3.y, rectangle3.width,
        // rectangle3.height);

        shapeRenderer.end();

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