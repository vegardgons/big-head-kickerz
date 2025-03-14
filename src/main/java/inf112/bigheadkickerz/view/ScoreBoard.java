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
    private final SpriteBatch batch;
    private final BitmapFont font;
    private int player1Score;
    private int player2Score;
    private final GlyphLayout layout;

    /**
     * Constructor initializes rendering components with a custom font
     */
    public ScoreBoard() {
        this.batch = new SpriteBatch();
        this.layout = new GlyphLayout();

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

        // Initialize scores
        player1Score = 0;
        player2Score = 0;
    }

    /**
     * Update the current scores
     */
    public void updateScores(int player1Score, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    /**
     * Render the scoreboard
     */
    public void render(float delta) {
        // Prepare score text
        String scoreText = player2Score + " - " + player1Score;

        // Calculate text dimensions
        layout.setText(font, scoreText);
        float textWidth = layout.width;

        // Position text at the top center of the screen
        float x = (Gdx.graphics.getWidth() - textWidth) / 2;
        float y = Gdx.graphics.getHeight() - 20; // 20 pixels from the top

        // Draw scoreboard
        batch.begin();
        font.draw(batch, scoreText, x, y);
        batch.end();
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}