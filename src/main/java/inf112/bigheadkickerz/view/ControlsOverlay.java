package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;

/**
 * ControlsOverlay is a class that displays the controls for two players
 * on a white background.
 */
public class ControlsOverlay extends ScreenAdapter {
  private final BitmapFont font;
  private final GlyphLayout layout;
  private final Texture whiteTexture;
  private final SpriteBatch spriteBatch;

  /**
   * Constructor for ControlsOverlay.
   * Initializes the font, layout, and sprite batch.
   */
  public ControlsOverlay() {
    layout = new GlyphLayout();
    spriteBatch = new SpriteBatch();

    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = 30;
    parameter.color = Color.BLUE;
    parameter.borderWidth = 1f;
    parameter.borderColor = Color.ORANGE;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
        Gdx.files.internal("fonts/Nasa21.ttf"));
    font = generator.generateFont(parameter);
    generator.dispose();

    // White background
    whiteTexture = new Texture("white.jpg");
  }

  /**
   * Draws two centered columns (Player 1 / Player 2) and a bottom line
   * ("SPACE to start"), side by side, on a white sign.
   */
  public void draw() {

    String leftColumn = "Player 1\nWASD - move\nSPACE - kick";
    String rightColumn = "Player 2\nArrow Keys - move\nP - kick";
    String bottomLine = "SPACE to start";

    layout.setText(font, leftColumn);
    float leftWidth = layout.width;
    float leftHeight = layout.height;

    layout.setText(font, rightColumn);
    float rightWidth = layout.width;
    float rightHeight = layout.height;

    float columnWidth = Math.max(leftWidth, rightWidth);

    layout.setText(font, bottomLine);
    float bottomHeight = layout.height;

    // Spacing
    float columnSpacing = 50f;
    float padding = 20f;
    float lineSpacing = 20f;
    float topHeight = Math.max(leftHeight, rightHeight);
    float signWidth = (columnWidth * 2) + columnSpacing + (padding * 2);
    float signHeight = topHeight + lineSpacing + bottomHeight + (padding * 2);

    // Center the sign on screen
    float screenW = Gdx.graphics.getWidth();
    float screenH = Gdx.graphics.getHeight();
    float x = (screenW - signWidth) / 2f;
    float y = (screenH + signHeight) / 2f;

    spriteBatch.begin();

    spriteBatch.draw(whiteTexture, x, y - signHeight, signWidth, signHeight);

    float topTextY = y - padding;
    float leftTextX = x + padding;
    font.draw(spriteBatch, leftColumn, leftTextX, topTextY,
        columnWidth, Align.center, false);

    float rightTextX = leftTextX + columnWidth + columnSpacing;
    font.draw(spriteBatch, rightColumn, rightTextX, topTextY,
        columnWidth, Align.center, false);

    float bottomWidth = layout.width;
    float bottomTextX = x + (signWidth - bottomWidth) / 2f;
    float bottomTextY = topTextY - topHeight - lineSpacing;
    font.draw(spriteBatch, bottomLine, bottomTextX, bottomTextY);
    spriteBatch.end();
  }

  /**
   * Disposes of the font and sprite batch.
   */
  public void dispose() {
    spriteBatch.dispose();
    font.dispose();
    whiteTexture.dispose();
  }
}
