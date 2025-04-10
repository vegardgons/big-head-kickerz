package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * ScoreBoard displays the current game score at the top of the screen
 * using a custom font for better scaling.
 */
public class ScoreBoard extends ScreenAdapter {
  private final BitmapFont font;
  private final GlyphLayout layout;
  private final SpriteBatch spriteBatch;

  /**
   * Constructor initializes rendering components with a custom font.
   */
  ScoreBoard() {
    this.layout = new GlyphLayout();
    this.spriteBatch = new SpriteBatch();
    // Generate a custom font using FreeType
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = (int) (Gdx.graphics.getHeight() * 0.15); // 5% of screen height
    parameter.color = Color.WHITE;
    parameter.borderWidth = 4f;
    parameter.borderColor = Color.BLACK;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
        Gdx.files.internal("fonts/Nasa21.ttf"));
    this.font = generator.generateFont(parameter);
    generator.dispose();
  }

  /**
   * Draw player1 score.
   * This method draws the player1 score at the top left corner of the screen.
   *
   * @param player1Score the score of player1
   */
  void drawPlayer1Score(int player1Score) {
    String score = String.valueOf(player1Score);
    layout.setText(font, score);

    float x = 20;
    float y = (float) Gdx.graphics.getHeight() - 20; // 20 pixels from the top

    spriteBatch.begin();
    font.draw(spriteBatch, score, x, y);
    spriteBatch.end();
  }

  /**
   * Draw player2 score.
   *
   * @param player2Score the score of player2
   */
  void drawPlayer2Score(int player2Score) {
    String score = String.valueOf(player2Score);
    layout.setText(font, score);
    float textWidth = layout.width;

    float x = (Gdx.graphics.getWidth() - textWidth) - 20;
    float y = (float) Gdx.graphics.getHeight() - 20;

    spriteBatch.begin();
    font.draw(spriteBatch, score, x, y);
    spriteBatch.end();
  }

  /**
   * Draw timer.
   *
   * @param remainingTime time left in the game
   */
  void drawTimer(float remainingTime) {
    String time = String.valueOf((int) remainingTime);
    layout.setText(font, time);
    float textWidth = layout.width;

    float x = (Gdx.graphics.getWidth() - textWidth) / 2;
    float y = (float) Gdx.graphics.getHeight() - 20;

    spriteBatch.begin();
    font.draw(spriteBatch, time, x, y);
    spriteBatch.end();
  }

  /**
   * Draw goal text when goal or game over.
   *
   * @param text the text to be displayed
   */
  void drawGoalOrGameOverText(String text) {
    if (text == null) {
      return;
    }

    layout.setText(font, text);
    float textWidth = layout.width;
    float textHeight = layout.height;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    float x = (screenWidth - textWidth) / 2;
    float y = (screenHeight + textHeight) / 1.5f;

    spriteBatch.begin();
    font.draw(spriteBatch, text, x, y);
    spriteBatch.end();
  }

  @Override
  public void dispose() {
    font.dispose();
    spriteBatch.dispose();
  }
}