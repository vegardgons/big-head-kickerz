package inf112.bigheadkickerz.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GameController}.
 */
class ControllerTest {

  private ControllableGameModel model;
  private GameController controller;

  @BeforeEach
  void setUp() {

    model = mock(ControllableGameModel.class);
    controller = new GameController(model);
    Gdx.input = mock(Input.class);
  }

  @Test
  void testKeyDownPlayer1() {
    controller.keyDown(Input.Keys.W);
    verify(model).jump(true);

    controller.keyDown(Input.Keys.A);
    verify(model).setPlayerDirection(true, -1);

    controller.keyDown(Input.Keys.D);
    verify(model).setPlayerDirection(true, 1);

    controller.keyDown(Input.Keys.SPACE);
    verify(model).kick(true);
  }

  @Test
  void testKeyDownPlayer2() {
    controller.keyDown(Input.Keys.UP);
    verify(model).jump(false);

    controller.keyDown(Input.Keys.LEFT);
    verify(model).setPlayerDirection(false, -1);

    controller.keyDown(Input.Keys.RIGHT);
    verify(model).setPlayerDirection(false, 1);

    controller.keyDown(Input.Keys.P);
    verify(model).kick(false);
  }

  @Test
  void testKeyDownControls() {
    when(model.isShowingControls()).thenReturn(true);
    controller.keyDown(Input.Keys.SPACE);
    verify(model).dismissControls();
  }

  @Test
  void testNotPossibleToMoveWhenControls() {
    when(model.isShowingControls()).thenReturn(true);
    controller.keyDown(Input.Keys.W);
    verify(model, never()).jump(true);
    controller.keyDown(Input.Keys.A);
    verify(model, never()).setPlayerDirection(true, -1);
  }

  @Test
  void testKeyUpPlayer1A() {
    controller.keyUp(Input.Keys.A);
    verify(model).setPlayerDirection(true, 0);
  }

  @Test
  void testKeyUpPlayer1D() {
    controller.keyUp(Input.Keys.D);
    verify(model).setPlayerDirection(true, 0);
  }

  void testKeyUpPlayer2Left() {
    controller.keyUp(Input.Keys.LEFT);
    verify(model).setPlayerDirection(false, 0);
  }

  @Test
  void testKeyUpPlayer2Right() {
    controller.keyUp(Input.Keys.RIGHT);
    verify(model).setPlayerDirection(false, 0);
  }

  @Test
  void testKeyUpPlayer1StillHoldingA() {
    when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
    controller.keyUp(Input.Keys.A);
    verify(model, never()).setPlayerDirection(true, 0);
  }

  @Test
  void testKeyUpPlayer1StillHoldingD() {
    when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);
    controller.keyUp(Input.Keys.D);
    verify(model, never()).setPlayerDirection(true, 0);
  }

  @Test
  void testKeyUpPlayer2StillHoldingLeft() {
    when(Gdx.input.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);
    controller.keyUp(Input.Keys.LEFT);
    verify(model, never()).setPlayerDirection(false, 0);
  }

  @Test
  void testKeyUpPlayer2StillHoldingRight() {
    when(Gdx.input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);
    controller.keyUp(Input.Keys.RIGHT);
    verify(model, never()).setPlayerDirection(false, 0);
  }

}
