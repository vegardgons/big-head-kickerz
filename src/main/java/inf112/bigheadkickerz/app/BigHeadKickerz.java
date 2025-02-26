package inf112.bigheadkickerz.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.bigheadkickerz.model.Ball;
import inf112.bigheadkickerz.model.Field;
import inf112.bigheadkickerz.model.Player;

public class BigHeadKickerz implements ApplicationListener {
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Player player;
    private Field field;
    private Ball ball;
    private static final float WIDTH = 8;
    private static final float HEIGHT = 5;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(WIDTH, HEIGHT);
        field = new Field("OldTrafford.png");
        player = new Player("PlayerImage.png");
        float startX = viewport.getWorldWidth() / 2;
        float startY = viewport.getWorldHeight() / 2 + 1.5f;
        ball = new Ball("BallImage.png", startX, startY);
    }

    @Override
    public void render() {
        update();
        draw();
    }

    private void update() {
        float delta = Gdx.graphics.getDeltaTime();
        player.update(viewport, delta);
        ball.update(viewport, delta);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        field.draw(spriteBatch, viewport);
        player.draw(spriteBatch);
        ball.draw(spriteBatch, viewport);

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