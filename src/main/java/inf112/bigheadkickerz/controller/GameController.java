package inf112.bigheadkickerz.controller;

import com.badlogic.gdx.Screen;
import inf112.bigheadkickerz.app.BigHeadKickerzGame;
import inf112.bigheadkickerz.model.GameModel;
import inf112.bigheadkickerz.view.GameViewImpl;
import inf112.bigheadkickerz.view.Goal;

public class GameController implements Screen {
    private final GameModel gameModel;
    private final GameViewImpl gameView;

    public GameController(BigHeadKickerzGame game) {
        this.gameModel = new GameModel(game, this);
        this.gameView = new GameViewImpl(gameModel);
        this.gameModel.initGoals();
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

    // Getters for goals
    public Goal getLeftGoal() {
        return this.gameView.getLeftGoal();
    }

    public Goal getRightGoal() {
        return this.gameView.getRightGoal();
    }
}
