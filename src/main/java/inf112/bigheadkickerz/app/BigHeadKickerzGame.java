package inf112.bigheadkickerz.app;

import com.badlogic.gdx.Game;

import inf112.bigheadkickerz.view.Assets;
import inf112.bigheadkickerz.view.EndScreen;
import inf112.bigheadkickerz.view.StartScreen;
import inf112.bigheadkickerz.controller.GameController;
import inf112.bigheadkickerz.model.GameState;

public class BigHeadKickerzGame extends Game {

    @Override
    public void create() {
        Assets.load();
        setScreen(new StartScreen(this));
    }

    /** Starts timed mode */
    public void startTimedMode() {
        setScreen(new GameController(this, GameState.TIMED));
    }

    /** Starts first to seven mode */
    public void startFirstToSevenMode() {
        setScreen(new GameController(this, GameState.FIRST_TO_SEVEN));
    }

    /** Screen when game ends */
    public void endScreen() {
        setScreen(new EndScreen(this));
    }
}
