package inf112.bigheadkickerz.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerControllerTest {

    private PlayerController playerController;
    private Sprite mockSprite;
    private Viewport mockViewport;

    @BeforeEach
    void setUp() {
        mockSprite = mock(Sprite.class);
        mockViewport = mock(Viewport.class);

        // Setter standardverdier for viewport
        when(mockViewport.getWorldWidth()).thenReturn(100f);
        when(mockViewport.getWorldHeight()).thenReturn(50f);

        playerController = new PlayerController(mockSprite, true, null);

        // Mock Gdx.input
        Gdx.input = mock(Input.class);
    }

    @Test
    void testMoveRightPlayer1() {
        when(Gdx.input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);

        playerController.movePlayer(mockViewport, 1f);

        verify(mockSprite).translateX(4f); // 4f er hastigheten
    }

    @Test
    void testMoveLeftPlayer1() {
        when(Gdx.input.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);

        playerController.movePlayer(mockViewport, 1f);

        verify(mockSprite).translateX(-4f);
    }

    @Test
    void testMoveRightPlayer2() {
        playerController = new PlayerController(mockSprite, false, null);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        playerController.movePlayer(mockViewport, 1f);

        verify(mockSprite).translateX(4f);
    }

    @Test
    void testMoveLeftPlayer2() {
        playerController = new PlayerController(mockSprite, false, null);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);

        playerController.movePlayer(mockViewport, 1f);

        verify(mockSprite).translateX(-4f);
    }

    @Test
    void testClampingToLeftScreenEdge() {
        when(mockSprite.getX()).thenReturn(-10f); // Utenfor venstre skjermkant
        when(mockSprite.getWidth()).thenReturn(20f);

        playerController.movePlayer(mockViewport, 1f);

        verify(mockSprite).setX(0);
    }

    @Test
    void testClampingToRightScreenEdge() {
        when(mockSprite.getX()).thenReturn(110f); // Utenfor høyre skjermkant
        when(mockSprite.getWidth()).thenReturn(20f);

        playerController.movePlayer(mockViewport, 1f);

        float expectedX = MathUtils.clamp(110f, 0, 100f - 20f);
        verify(mockSprite).setX(expectedX);
    }

    @Test
    void testNoMovementWhenNoKeysPressed() {
        playerController.movePlayer(mockViewport, 1f);

        // Sjekker at ingen horisontal bevegelse skjer
        verify(mockSprite, never()).translateX(anyFloat());
    }

    @Test
    void testLandingOnGround() {
        when(mockSprite.getY()).thenReturn(0f);

        playerController.movePlayer(mockViewport, 1f);

        assertFalse(playerController.getIsJumping());
    }

    @Test
    void testCannotJumpWhileInAir() {
        when(Gdx.input.isKeyJustPressed(Input.Keys.UP)).thenReturn(true);
        when(mockSprite.getY()).thenReturn(10f); // Spilleren er i lufta

        playerController.movePlayer(mockViewport, 1f);

        assertTrue(playerController.getIsJumping()); // Skal fortsatt være i lufta
        verify(mockSprite, never()).translateY(4.2f); // Skal ikke kunne hoppe igjen
    }

    @Test
    void testJumpingPlayer1() {
        when(Gdx.input.isKeyJustPressed(Input.Keys.UP)).thenReturn(true);
        when(mockSprite.getY()).thenReturn(1f);

        playerController.movePlayer(mockViewport, 1f);

        assertTrue(playerController.getIsJumping());
    }

    @Test
    void testJumpingPlayer2() {
        playerController = new PlayerController(mockSprite, false, null);
        when(Gdx.input.isKeyJustPressed(Input.Keys.W)).thenReturn(true);
        when(mockSprite.getY()).thenReturn(1f);

        playerController.movePlayer(mockViewport, 1f);

        assertTrue(playerController.getIsJumping());
    }
}
