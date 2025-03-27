package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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

    private void setUpScreen() {
        playAgainButton = new TextButton("Play Again", skin);
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StartScreen(game));
            }
        });

        // Create Exit Button
        exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(playAgainButton).uniform().fill().padRight(10);
        table.add(exitButton).uniform().fill();
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
