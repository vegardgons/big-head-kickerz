package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import inf112.bigheadkickerz.app.BigHeadKickerzGame;

public class EndScreen extends AScreen {

    private BigHeadKickerzGame game;
    private TextButton playAgainButton;
    private TextButton exitButton;

    public EndScreen(BigHeadKickerzGame game) {
        super();
        this.game = game;
        setUpScreen();
    }

    protected void setUpScreen() {
        // Create Play Again Button
        playAgainButton = new TextButton("Play Again", skin);
        playAgainButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.startGame();
                return true;
            }
        });

        // Create Exit Button
        exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        table.add(playAgainButton).uniform().fill().padRight(10);
        table.add(exitButton).uniform().fill();
    }

}
