package inf112.bigheadkickerz.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.bigheadkickerz.app.BigHeadKickerzGame;

/**
 * Class to represent the start screen of the game.
 */
public class StartScreen extends AScreen {

  private final BigHeadKickerzGame game;

  /**
   * Constructor for the StartScreen.
   *
   * @param game The game instance
   */
  public StartScreen(BigHeadKickerzGame game) {
    super();
    this.game = game;
    setUpScreen();

  }

  private void setUpScreen() {
    createTimedButton();
    createFirstToSevenButton();
  }

  private void createTimedButton() {
    TextButton timedButton = new TextButton("Timed", skin);
    timedButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.startTimedMode();
        dispose();
      }
    });
    table.add(timedButton).uniform().fill();
  }

  private void createFirstToSevenButton() {
    TextButton firstToSevenButton = new TextButton("First To Seven", skin);
    firstToSevenButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.startFirstToSevenMode();
        dispose();
      }
    });
    table.add(firstToSevenButton).uniform().fill();
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
