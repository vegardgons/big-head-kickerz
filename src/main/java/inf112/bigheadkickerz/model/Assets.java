package inf112.bigheadkickerz.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Class for loading and playing sound effects and music.
 * This class handles the loading of sound files and provides methods to play
 * and stop them.
 */
public final class Assets {
  public static final Music menuMusic = Gdx.audio.newMusic(
      Gdx.files.internal("assets/menu_sound.mp3"));
  public static final Music goalSound = Gdx.audio.newMusic(
      Gdx.files.internal("assets/goal_sound.mp3"));
  public static final Music startWhistle = Gdx.audio.newMusic(
      Gdx.files.internal("assets/start_game_whistle.mp3"));

  /**
   * Private constructor to prevent instantiation.
   */
  private Assets() {
    // Prevent instantiation
  }

  /**
   * Plays the menu music.
   * This method plays the menu music in a loop.
   */
  public static void playMenuMusic() {
    if (!menuMusic.isPlaying()) {
      menuMusic.setLooping(true);
      menuMusic.play();
    }
  }

  /**
   * Stops the menu music.
   * This method stops the menu music if it is playing.
   */
  public static void stopMenuMusic() {
    if (menuMusic.isPlaying()) {
      menuMusic.stop();
    }
  }

  /**
   * Plays the start whistle sound.
   * This method plays the start whistle sound.
   */
  public static void playStartWhistle() {
    startWhistle.play();
  }

  /**
   * Plays the goal sound.
   * This method plays the goal sound.
   */
  public static void playGoalSound() {
    goalSound.play();
  }

  /**
   * Disposes of the assets.
   * This method disposes of the music assets to free up resources.
   */
  public static void dispose() {
    menuMusic.dispose();
    goalSound.dispose();
    startWhistle.dispose();
  }
}
