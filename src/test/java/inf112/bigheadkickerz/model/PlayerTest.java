package inf112.bigheadkickerz.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

public class PlayerTest {

    private Player player;
    private SpriteBatch mockBatch = mock(SpriteBatch.class);

    @BeforeEach
    void setUp() {
        // Mock Gdx.files to avoid file-related errors.
        Gdx.files = mock(Files.class);
        FileHandle mockFileHandle = mock(FileHandle.class);
        when(Gdx.files.internal(anyString())).thenReturn(mockFileHandle);

        // Mock Gdx.input for input behavior
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(anyInt())).thenReturn(false);

        // Mock Gdx.app to prevent NullPointerException when calling Gdx.app.log(...)
        Gdx.app = mock(Application.class);
        doNothing().when(Gdx.app).log(anyString(), anyString());
        doNothing().when(Gdx.app).error(anyString(), anyString());
        doNothing().when(Gdx.app).debug(anyString(), anyString());

        try (MockedConstruction<Texture> mockedTexture = mockConstruction(Texture.class);
            MockedConstruction<TextureAtlas> mockedAtlas = mockConstruction(TextureAtlas.class, (atlasMock, context) -> {
                Array<TextureAtlas.AtlasRegion> dummyRegions = new Array<>();
                TextureAtlas.AtlasRegion mockRegion = mock(TextureAtlas.AtlasRegion.class);
                when(mockRegion.getTexture()).thenReturn(mock(Texture.class));
                dummyRegions.add(mockRegion);
                when(atlasMock.findRegions("kick")).thenReturn(dummyRegions);
            })) {
            player = new Player("PlayerImage.png", 5, 0, false, true);
        }
    }


    @Test
    void testInitialPosition() {
        assertEquals(5, player.getSprite().getX());
        assertEquals(0, player.getSprite().getY());
    }

    @Test
    void testMoveBy() {
        player.moveBy(2, 3);
        assertEquals(7, player.getSprite().getX());
        assertEquals(3, player.getSprite().getY());
    }

    @Test
    void testReset() {
        player.moveBy(3, 3);
        player.reset();
        assertEquals(5, player.getSprite().getX());
        assertEquals(0, player.getSprite().getY());
        assertEquals(new Vector2(0, 0), player.getVelocity());
    }

    @Test
    void testDrawCallsSpriteBatchDraw() {
        player.draw(mockBatch);
        // Verify that SpriteBatch.draw is called at least once with a non-null TextureRegion.
        verify(mockBatch, atLeastOnce()).draw(any(TextureRegion.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testGetWidthCallsSpriteGetWidth() {
        assertEquals(1, player.getWidth());
    }
}
