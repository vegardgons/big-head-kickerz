package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import inf112.bigheadkickerz.app.BigHeadKickerzGame;

public abstract class AScreen implements Screen {

    protected Stage stage;
    protected Texture backgroundTexture;
    protected SpriteBatch batch;
    protected Skin skin;
    protected Table table;

    protected AScreen() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Load background
        backgroundTexture = new Texture("StartScreen.png");
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);

        table = new Table();
        table.setFillParent(true);
        table.center().bottom().padBottom(100);
        stage.addActor(table);
    }

    protected abstract void setUpScreen();

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        skin.dispose();
    }
}
