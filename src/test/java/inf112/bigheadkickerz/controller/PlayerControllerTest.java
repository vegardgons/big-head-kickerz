package inf112.bigheadkickerz.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import inf112.bigheadkickerz.model.Assets;
import inf112.bigheadkickerz.model.Foot;
import inf112.bigheadkickerz.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerControllerTest {

  private Player playerMock;
  private Foot footMock;
  private PlayerController playerController1;
  private PlayerController playerController2;

  @BeforeEach
  void setUp() {
    playerMock = mock(Player.class);
    footMock = mock(Foot.class);
    when(playerMock.getMovementSpeed()).thenReturn(5f);
    when(playerMock.getJumpHeight()).thenReturn(10f);
    when(playerMock.getVelocity()).thenReturn(new Vector2(0, 0));
    when(playerMock.getPosition()).thenReturn(new Vector2(0, 0));

    playerController1 = new PlayerController(true, playerMock, footMock);
    playerController2 = new PlayerController(false, playerMock, footMock);

    Gdx.input = mock(Input.class);

    Assets.setJumpingSound(mock(Sound.class));
  }

  @Test
  void testMoveLeft() {
    when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
    Vector2 newVel = playerController1.movePlayer();
    assertEquals(new Vector2(-5, 0), newVel);
  }

  @Test
  void testMoveRight() {
    when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);
    Vector2 newVel = playerController1.movePlayer();
    assertEquals(new Vector2(5, 0), newVel);
  }

  @Test
  void testJump() {
    when(Gdx.input.isKeyJustPressed(Input.Keys.W)).thenReturn(true);
    Vector2 newVel = playerController1.movePlayer();
    assertEquals(new Vector2(0, 10f), newVel);
  }

  @Test
  void testMoveLeftPlayer2() {
    when(Gdx.input.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);
    Vector2 newVel = playerController2.movePlayer();
    assertEquals(new Vector2(-5, 0), newVel);
  }

  @Test
  void testMoveRightPlayer2() {
    when(Gdx.input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);
    Vector2 newVel = playerController2.movePlayer();
    assertEquals(new Vector2(5, 0), newVel);
  }

  @Test
  void testJumpPlayer2() {
    when(Gdx.input.isKeyJustPressed(Input.Keys.UP)).thenReturn(true);
    Vector2 newVel = playerController2.movePlayer();
    assertEquals(new Vector2(0, 10f), newVel);
  }

  @Test
  void testKickPlayer1() {
    when(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)).thenReturn(true);
    playerController1.movePlayer();
    verify(footMock, times(1)).kick();
  }

  @Test
  void testKickPlayer2() {
    when(Gdx.input.isKeyJustPressed(Input.Keys.P)).thenReturn(true);
    playerController2.movePlayer();
    verify(footMock, times(1)).kick();
  }

}