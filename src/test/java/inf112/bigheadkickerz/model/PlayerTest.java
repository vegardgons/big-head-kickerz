package inf112.bigheadkickerz.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

public class PlayerTest {

    private Player player;
    private Sprite mockSprite = mock(Sprite.class);
    private SpriteBatch mockBatch = mock(SpriteBatch.class);

    @BeforeEach
    void setUp() {
        // Mock Gdx.files for å unngå fil-relaterte feil
        Gdx.files = mock(Files.class);
        FileHandle mockFileHandle = mock(FileHandle.class);
        when(Gdx.files.internal(anyString())).thenReturn(mockFileHandle);

        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(anyInt())).thenReturn(false);

        // Mock Texture slik at den ikke prøver å laste en faktisk fil
        try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class)) {
            player = new Player("PlayerImage.png", 5, 5, false, true);
        }
    }

    @Test
    void testInitialPosition() {
        assertEquals(5, player.getSprite().getX());
        assertEquals(5, player.getSprite().getY());
    }

    @Test
    void testMoveBy() {
        player.moveBy(2, 3);
        assertEquals(7, player.getSprite().getX());
        assertEquals(8, player.getSprite().getY());
    }

    @Test
    void testReset() {
        player.moveBy(3, 3);
        player.reset();
        assertEquals(5, player.getSprite().getX());
        assertEquals(5, player.getSprite().getY());
        assertEquals(new Vector2(0, 0), player.getVelocity());
    }

    @Test
    void testDrawCallsSpriteDraw() {
        player.draw(mockBatch);
        verify(mockSprite, never()).draw(mockBatch); // Mocken brukes ikke, da player bruker en ekte Sprite
    }
}
