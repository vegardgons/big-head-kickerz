package inf112.bigheadkickerz.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    Assets.setGoalSound(mock(Sound.class));
    Assets.setStartWhistle(mock(Sound.class));
    Assets.setJumpingSound(mock(Sound.class));
    Assets.setGameOverSound(mock(Sound.class));

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

    verify(Assets.getGoalSound()).play(0.5f);
  }

  @Test
  void playJumpingSound_shouldPlaySound() {
    Assets.playJumpingSound();

    verify(Assets.getJumpingSound()).play(0.2f);
  }

  @Test
  void playGameOverSound_shouldPlaySound() {
    Assets.playGameOverSound();

    verify(Assets.getGameOverSound()).play();
  }

  @Test
  void dispose_shouldDisposeAllMusic() {
    Assets.dispose();

    verify(Assets.getMenuMusic()).dispose();
    verify(Assets.getGoalSound()).dispose();
    verify(Assets.getStartWhistle()).dispose();
    verify(Assets.getJumpingSound()).dispose();
    verify(Assets.getGameOverSound()).dispose();
  }
}