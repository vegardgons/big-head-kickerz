package inf112.bigheadkickerz.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
  void testKeyDownPlayer1W() {
    controller.keyDown(Input.Keys.W);
    verify(model).jump(true);
  }

  @Test
  void testKeyDownPlayer1A() {
    controller.keyDown(Input.Keys.A);
    verify(model).setPlayerDirection(true, -1);
  }

  @Test
  void testKeyDownPlayer1D() {
    controller.keyDown(Input.Keys.D);
    verify(model).setPlayerDirection(true, 1);
  }

  @Test
  void testKeyDownPlayer1Space() {
    controller.keyDown(Input.Keys.SPACE);
    verify(model).kick(true);
  }

  @Test
  void testKeyDownPlayer2Up() {
    controller.keyDown(Input.Keys.UP);
    verify(model).jump(false);
  }

  @Test
  void testKeyDownPlayer2Left() {
    controller.keyDown(Input.Keys.LEFT);
    verify(model).setPlayerDirection(false, -1);
  }

  @Test
  void testKeyDownPlayer2Right() {
    controller.keyDown(Input.Keys.RIGHT);
    verify(model).setPlayerDirection(false, 1);
  }

  @Test
  void testKeyDownPlayer2P() {
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

  @Test
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
  void testKeyDownNoHit() {
    assertFalse(controller.keyDown(Input.Keys.X));
  }

  @Test
  void testKeyUpNoHit() {
    assertFalse(controller.keyUp(Input.Keys.X));
  }

}
