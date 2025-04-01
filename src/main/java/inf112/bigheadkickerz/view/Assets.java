package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Class for loading and playing sound effects and music.
 * This class handles the loading of sound files and provides methods to play
 * and stop them.
 */
public class Assets {
  public static Music menuMusic;
  public static Music goalSound;
  public static Music startWhistle;

  /**
   * Loads the sound files.
   * This method loads the sound files from the assets folder.
   */
  public static void load() {
    menuMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/menu_sound.mp3"));
    goalSound = Gdx.audio.newMusic(Gdx.files.internal("assets/goal_sound.mp3"));
    startWhistle = Gdx.audio.newMusic(Gdx.files.internal("assets/start_game_whistle.mp3"));
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
   * Disposes of the sound files.
   * This method disposes of the sound files to free up resources.
   */
  public static void dispose() {
    menuMusic.dispose();
    goalSound.dispose();
    startWhistle.dispose();
  }
}
