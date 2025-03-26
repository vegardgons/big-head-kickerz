package inf112.bigheadkickerz.app;

import com.badlogic.gdx.Game;

import inf112.bigheadkickerz.view.Assets;
import inf112.bigheadkickerz.view.EndScreen;
import inf112.bigheadkickerz.view.StartScreen;
import inf112.bigheadkickerz.controller.GameController;

public class BigHeadKickerzGame extends Game {

    @Override
    public void create() {
        Assets.load();
        setScreen(new StartScreen(this));
    }

    /** Starts the game */
    public void startGame() {
        Assets.playStartWhistle();
        setScreen(new GameController(this));
    }

    /** Screen when game ends */
    public void endScreen() {
        setScreen(new EndScreen(this));
    }
}
