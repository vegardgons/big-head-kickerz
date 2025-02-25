package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.model.Player;
import inf112.skeleton.model.Field;

public class BigHeadKickerz implements ApplicationListener {
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Player player;
    private Field field;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);


        field = new Field("background.png");
        player = new Player("PlayerImage.png", 3, 0);
    }

    @Override
    public void render() {
        update();
        draw();
    }

    private void update() {
        float delta = Gdx.graphics.getDeltaTime();
        player.update(viewport, delta);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        field.draw(spriteBatch, viewport);
        player.draw(spriteBatch);

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