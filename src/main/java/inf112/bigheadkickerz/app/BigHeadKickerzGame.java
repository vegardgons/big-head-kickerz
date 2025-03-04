package inf112.bigheadkickerz.app;

import com.badlogic.gdx.Game;
import inf112.bigheadkickerz.view.StartScreen;
import inf112.bigheadkickerz.controller.GameController;

public class BigHeadKickerzGame extends Game {

    @Override
    public void create() {
        setScreen(new StartScreen(this));
    }

    public void startGame() {
        setScreen(new GameController(this));
    }
}
