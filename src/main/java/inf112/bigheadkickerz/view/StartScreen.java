package inf112.bigheadkickerz.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import inf112.bigheadkickerz.app.BigHeadKickerzGame;

public class StartScreen extends AScreen {

    private BigHeadKickerzGame game;
    private TextButton startButton;

    public StartScreen(BigHeadKickerzGame game) {
        super();
        this.game = game;
        setUpScreen();

    }

    private void setUpScreen() {
        // Create Start Button
        startButton = new TextButton("Start Game", skin);
        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.startGame();
                return true;
            }
        });

        table.add(startButton).uniform().fill();
    }

    @Override
    public void show() {
        Assets.playMenuMusic();
    }

    @Override
    public void hide() {
        Assets.stopMenuMusic();
    }
}
