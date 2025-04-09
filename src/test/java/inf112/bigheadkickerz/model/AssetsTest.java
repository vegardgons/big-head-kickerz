package inf112.bigheadkickerz.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.audio.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Assets class.
 * This class tests the functionality of the Assets class, including playing and
 * stopping music,
 * and disposing of assets.
 */
class AssetsTest {

  @BeforeEach
  void setupMocks() {
    Assets.setMenuMusic(mock(Music.class));
    Assets.setGoalSound(mock(Music.class));
    Assets.setStartWhistle(mock(Music.class));
  }

  @Test
  void playMenuMusic_shouldPlayIfNotPlaying() {
    when(Assets.getMenuMusic().isPlaying()).thenReturn(false);

    Assets.playMenuMusic();

    verify(Assets.getMenuMusic()).setLooping(true);
    verify(Assets.getMenuMusic()).play();
  }

  @Test
  void playMenuMusic_shouldNotPlayIfAlreadyPlaying() {
    when(Assets.getMenuMusic().isPlaying()).thenReturn(true);

    Assets.playMenuMusic();

    verify(Assets.getMenuMusic(), never()).setLooping(true);
    verify(Assets.getMenuMusic(), never()).play();
  }

  @Test
  void stopMenuMusic_shouldStopIfPlaying() {
    when(Assets.getMenuMusic().isPlaying()).thenReturn(true);

    Assets.stopMenuMusic();

    verify(Assets.getMenuMusic()).stop();
  }

  @Test
  void stopMenuMusic_shouldNotStopIfNotPlaying() {
    when(Assets.getMenuMusic().isPlaying()).thenReturn(false);

    Assets.stopMenuMusic();

    verify(Assets.getMenuMusic(), never()).stop();
  }

  @Test
  void playStartWhistle_shouldPlaySound() {
    Assets.playStartWhistle();

    verify(Assets.getStartWhistle()).play();
  }

  @Test
  void playGoalSound_shouldPlaySound() {
    Assets.playGoalSound();

    verify(Assets.getGoalSound()).play();
  }

  @Test
  void dispose_shouldDisposeAllMusic() {
    Assets.dispose();

    verify(Assets.getMenuMusic()).dispose();
    verify(Assets.getGoalSound()).dispose();
    verify(Assets.getStartWhistle()).dispose();
  }
}