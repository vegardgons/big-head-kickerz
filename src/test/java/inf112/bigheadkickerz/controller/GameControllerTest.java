package inf112.bigheadkickerz.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.bigheadkickerz.model.Assets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

/**
 * Test class for the GameController class.
 */
class GameControllerTest {

  private ControllableGameModel model;
  private GameController controller;

  @BeforeEach
  void setup() {
    model = mock(ControllableGameModel.class);
    controller = new GameController(model);
  }

  @Test
  void update_shouldDismissControlsAndPlayWhistleIfSpacePressed() {
    when(model.isShowingControls()).thenReturn(true);

    Input input = mock(Input.class);
    when(input.isKeyJustPressed(Input.Keys.SPACE)).thenReturn(true);
    Gdx.input = input;

    try (MockedStatic<Assets> mockedAssets = mockStatic(Assets.class)) {
      controller.update(1.0f);

      verify(model).dismissControls();
      mockedAssets.verify(Assets::playStartWhistle);
    }
  }

  @Test
  void update_shouldNotDismissControlsIfSpaceNotPressed() {
    when(model.isShowingControls()).thenReturn(true);

    Input input = mock(Input.class);
    when(input.isKeyJustPressed(Input.Keys.SPACE)).thenReturn(false);
    Gdx.input = input;

    try (MockedStatic<Assets> mockedAssets = mockStatic(Assets.class)) {
      controller.update(1.0f);

      verify(model, never()).dismissControls();
      mockedAssets.verifyNoInteractions();
    }
  }

  @Test
  void update_shouldUpdateModelIfControlsNotShowing() {
    when(model.isShowingControls()).thenReturn(false);

    controller.update(0.5f);

    verify(model).update(0.5f);
  }
}
