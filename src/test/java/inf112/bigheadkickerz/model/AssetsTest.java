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
public class AssetsTest {

  @BeforeEach
  void setupMocks() {
    Assets.menuMusic = mock(Music.class);
    Assets.goalSound = mock(Music.class);
    Assets.startWhistle = mock(Music.class);
  }

  @Test
  void playMenuMusic_shouldPlayIfNotPlaying() {
    when(Assets.menuMusic.isPlaying()).thenReturn(false);

    Assets.playMenuMusic();

    verify(Assets.menuMusic).setLooping(true);
    verify(Assets.menuMusic).play();
  }

  @Test
  void playMenuMusic_shouldNotPlayIfAlreadyPlaying() {
    when(Assets.menuMusic.isPlaying()).thenReturn(true);

    Assets.playMenuMusic();

    verify(Assets.menuMusic, never()).setLooping(true);
    verify(Assets.menuMusic, never()).play();
  }

  @Test
  void stopMenuMusic_shouldStopIfPlaying() {
    when(Assets.menuMusic.isPlaying()).thenReturn(true);

    Assets.stopMenuMusic();

    verify(Assets.menuMusic).stop();
  }

  @Test
  void stopMenuMusic_shouldNotStopIfNotPlaying() {
    when(Assets.menuMusic.isPlaying()).thenReturn(false);

    Assets.stopMenuMusic();

    verify(Assets.menuMusic, never()).stop();
  }

  @Test
  void playStartWhistle_shouldPlaySound() {
    Assets.playStartWhistle();

    verify(Assets.startWhistle).play();
  }

  @Test
  void playGoalSound_shouldPlaySound() {
    Assets.playGoalSound();

    verify(Assets.goalSound).play();
  }

  @Test
  void dispose_shouldDisposeAllMusic() {
    Assets.dispose();

    verify(Assets.menuMusic).dispose();
    verify(Assets.goalSound).dispose();
    verify(Assets.startWhistle).dispose();
  }
}