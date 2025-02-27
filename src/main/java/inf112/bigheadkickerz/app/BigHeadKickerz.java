package inf112.bigheadkickerz.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.Collision;
import inf112.bigheadkickerz.model.Player;
import inf112.bigheadkickerz.view.Field;
import inf112.bigheadkickerz.view.Goal;

public class BigHeadKickerz implements ApplicationListener {
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Player player1;
    private Player player2;
    private Field field;
    private Goal rightGoal;
    private Goal leftGoal;
    private Ball ball;
    private Collision collision;
    private static final float WIDTH = 15;
    private static final float HEIGHT = 8;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(WIDTH, HEIGHT);
        field = new Field("OldTrafford.png");

        float rightGoalX = viewport.getWorldWidth() / 8 * 7.2f;
        rightGoal = new Goal("GoalImage.png", rightGoalX, 0, false);

        float goalWidth = rightGoal.getWidth();
        float leftGoalX = viewport.getWorldWidth() / 8 * (8 - 7.2f) - goalWidth;
        leftGoal = new Goal("GoalImage.png", leftGoalX, 0, true);

        float player1X = viewport.getWorldWidth() / 8 * 6.5f;
        player1 = new Player("PlayerImage.png", player1X, 0, false, true);

        float playerWidth = player1.getWidth();
        float player2X = viewport.getWorldWidth() / 8 * (8 - 6.5f) - playerWidth;
        player2 = new Player("PlayerImage.png", player2X, 0, true, false);

        float ballX = viewport.getWorldWidth() / 2;
        float ballY = viewport.getWorldHeight() / 2 + 1.5f;
        ball = new Ball("BallImage.png", ballX, ballY);

        collision = new Collision(player1, player2, ball);
    }

    @Override
    public void render() {
        update();
        draw();
    }

    private void update() {
        float delta = Gdx.graphics.getDeltaTime();
        player1.update(viewport, delta);
        player2.update(viewport, delta);
        ball.update(viewport, delta);
        collision.checkCollision();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        field.draw(spriteBatch, viewport);
        player1.draw(spriteBatch);
        player2.draw(spriteBatch);
        rightGoal.draw(spriteBatch);
        leftGoal.draw(spriteBatch);
        ball.draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}