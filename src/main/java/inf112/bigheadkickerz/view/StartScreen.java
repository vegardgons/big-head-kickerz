package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import inf112.bigheadkickerz.app.BigHeadKickerzGame;

public class StartScreen implements Screen {

    private final BigHeadKickerzGame game;
    private Stage stage;
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Skin skin;
    private TextButton startButton;

    public StartScreen(BigHeadKickerzGame game) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        // Load background
        backgroundTexture = new Texture("StartScreen.png");
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Create Start Button
        startButton = new TextButton("Start Game", skin);
        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.startGame();
                return true;
            }
        });

        // Position elements
        Table table = new Table();
        table.setFillParent(true);
        table.center().bottom().padBottom(100);
        table.add(startButton).width(200).height(80);
        stage.addActor(table);
    }

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
