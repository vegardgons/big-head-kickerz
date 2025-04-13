package inf112.bigheadkickerz.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Music;
import com.badlogic.gdx.backends.lwjgl3.audio.Ogg.Sound;
import com.badlogic.gdx.files.FileHandle;
import inf112.bigheadkickerz.model.Assets;
import inf112.bigheadkickerz.model.GameState;
import inf112.bigheadkickerz.view.EndScreen;
import inf112.bigheadkickerz.view.GameScreen;
import inf112.bigheadkickerz.view.StartScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

/**
 * Test class for the BigHeadKickerzGame class.
 * This class tests the functionality of the BigHeadKickerzGame class, including
 * creating the game,
 * starting different game modes, and disposing of assets.
 */
class BigHeadKickerzGameTest {

  BigHeadKickerzGame game;

  @BeforeEach
  void setUp() {
    game = spy(new BigHeadKickerzGame());

    Files mockFiles = mock(Files.class);
    FileHandle mockHandle = mock(FileHandle.class);

    Gdx.files = mockFiles;
    Gdx.audio = mock(com.badlogic.gdx.Audio.class);

    when(mockFiles.internal(anyString())).thenReturn(mockHandle);

    Music mockMusic = mock(Music.class);
    Sound mockSound = mock(Sound.class);

    Gdx.audio = mock(com.badlogic.gdx.Audio.class);

    Assets.setMenuMusic(mockMusic);
    Assets.setGoalSound(mockSound);
    Assets.setStartWhistle(mockSound);
    Assets.setJumpingSound(mockSound);
    Assets.setGameOverSound(mockSound);

    doNothing().when(game).setScreen(any(Screen.class));
  }

  @Test
  void create_shouldInitAssetsAndSetStartScreen() {
    try (MockedConstruction<StartScreen> mocked = mockConstruction(StartScreen.class)) {
      game.create();

      verify(game).setScreen(any(StartScreen.class));
    }
  }

  @Test
  void startTimedMode_shouldSetGameScreenWithTimedState() {
    try (MockedConstruction<GameScreen> mocked = mockConstruction(GameScreen.class,
        (mock, context) -> {
          assertTrue(context.arguments().contains(GameState.TIMED));
        })) {
      game.startTimedMode();
      verify(game).setScreen(any(GameScreen.class));
    }
  }

  @Test
  void startFirstToSevenMode_shouldSetGameScreenWithFirstToSevenState() {
    try (MockedConstruction<GameScreen> mocked = mockConstruction(GameScreen.class,
        (mock, context) -> {
          assertTrue(context.arguments().contains(GameState.FIRST_TO_SEVEN));
        })) {
      game.startFirstToSevenMode();
      verify(game).setScreen(any(GameScreen.class));
    }
  }

  @Test
  void endScreen_shouldSetEndScreen() {
    try (MockedConstruction<EndScreen> mocked = mockConstruction(EndScreen.class)) {
      game.endScreen();
      verify(game).setScreen(any(EndScreen.class));
    }
  }

  @Test
  void dispose_shouldCallAssetsDispose() {
    Assets.setMenuMusic(mock(com.badlogic.gdx.audio.Music.class));
    Assets.setGoalSound(mock(com.badlogic.gdx.audio.Sound.class));
    Assets.setStartWhistle(mock(com.badlogic.gdx.audio.Sound.class));
    Assets.setJumpingSound(mock(com.badlogic.gdx.audio.Sound.class));
    Assets.setGameOverSound(mock(com.badlogic.gdx.audio.Sound.class));

    game.dispose();

    verify(Assets.getMenuMusic()).dispose();
    verify(Assets.getGoalSound()).dispose();
    verify(Assets.getStartWhistle()).dispose();
  }
}