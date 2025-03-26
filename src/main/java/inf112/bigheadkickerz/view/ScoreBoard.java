package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * ScoreBoard displays the current game score at the top of the screen
 * using a custom font for better scaling
 */
public class ScoreBoard {
    private final BitmapFont font;
    private final GlyphLayout layout;
    private SpriteBatch spriteBatch;

    /**
     * Constructor initializes rendering components with a custom font
     */
    public ScoreBoard() {
        this.layout = new GlyphLayout();
        this.spriteBatch = new SpriteBatch();
        // Generate a custom font using FreeType
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Nasa21.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = (int) (Gdx.graphics.getHeight() * 0.15); // 5% of screen height
        parameter.color = Color.WHITE;
        parameter.borderWidth = 4f;
        parameter.borderColor = Color.BLACK;
        this.font = generator.generateFont(parameter);
        generator.dispose();
    }

    /**
     * Render the scoreboard
     */
    public void render(SpriteBatch batch, int player1Score, int player2Score) {
        String scoreText = player1Score + " - " + player2Score;

        // Calculate text dimensions
        layout.setText(font, scoreText);
        float textWidth = layout.width;

        // Position text at the top center of the screen
        float x = (Gdx.graphics.getWidth() - textWidth) / 2;
        float y = Gdx.graphics.getHeight() - 20; // 20 pixels from the top

        spriteBatch.begin();
        font.draw(spriteBatch, scoreText, x, y);
        spriteBatch.end();
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        font.dispose();
    }
}