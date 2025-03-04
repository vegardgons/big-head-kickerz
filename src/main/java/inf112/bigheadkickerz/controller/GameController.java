package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.view.GameViewImpl;

public class GameController implements Screen {
    private final BigHeadKickerzGame game;
    private final GameModel gameModel;
    private final GameViewImpl gameView;

    public GameController(BigHeadKickerzGame game) {
        this.game = game;
        this.gameModel = new GameModel();
        this.gameView = new GameViewImpl(gameModel);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        gameModel.update(delta);
        gameView.render();
    }

    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
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
        gameView.dispose();
    }
}
