package inf112.bigheadkickerz.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for loading and playing sound effects and music.
 * This class handles the loading of sound files and provides methods to play
 * and stop them.
 */
public final class Assets {
  private static Music menuMusic;
  private static Sound goalSound;
  private static Sound startWhistle;
  private static Sound gameOverSound;
  private static Sound jumpingSound;

  private static final String throwMessage = "Cannot be null";

  private static final Map<String, Texture> textureCache = new HashMap<>();

  /**
   * Initializes the assets.
   * This method loads the music files from the assets folder.
   */
  public static void init() {
    try {
      menuMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/menu_sound.mp3"));
      goalSound = Gdx.audio.newSound(Gdx.files.internal("assets/goal_sound.mp3"));
      startWhistle = Gdx.audio.newSound(Gdx.files.internal("assets/start_game_whistle.mp3"));
      gameOverSound = Gdx.audio.newSound(Gdx.files.internal("assets/game_finished_sound.mp3"));
      jumpingSound = Gdx.audio.newSound(Gdx.files.internal("assets/jumping_sound.wav"));

    } catch (Exception e) {
      Gdx.app.error("Assets", "Error loading audio files", e);
    }
  }

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
   * Plays the sound effect for when a goal is scored.
   */
  public static void playGoalSound() {
    goalSound.play(0.5f);
  }

  /**
   * Plays the Game sound.
   * This method plays the goal sound.
   */
  public static void playGameOverSound() {
    gameOverSound.play();
  }

  /**
   * Plays the jumpingsound.
   * This method plays the goal sound.
   */
  public static void playJumpingSound() {
    jumpingSound.play(0.2f);
  }

  /**
   * Releases all audio resources from memory.
   * Should be called when shutting down the game or transitioning between major
   * game states.
   */
  public static void dispose() {
    menuMusic.dispose();
    goalSound.dispose();
    startWhistle.dispose();
    jumpingSound.dispose();
    gameOverSound.dispose();
  }

  /**
   * Getter methods for accessing game audio assets.
   * These methods provide access to various sound effects and music used in the
   * game.
   */
  public static Music getMenuMusic() {
    return menuMusic;
  }

  public static Sound getGoalSound() {
    return goalSound;
  }

  public static Sound getStartWhistle() {
    return startWhistle;
  }

  public static Sound getGameOverSound() {
    return gameOverSound;
  }

  public static Sound getJumpingSound() {
    return jumpingSound;
  }

  /**
   * Setter methods for updating game audio assets.
   * These methods allow updating the game's audio resources during runtime.
   *
   * @param music The new Music asset to be used
   */
  public static void setMenuMusic(Music music) {
    if (music == null) {
      throw new IllegalArgumentException(throwMessage);
    }
    menuMusic = music;
  }

  public static void setGoalSound(Sound sound) {
    if (sound == null) {
      throw new IllegalArgumentException(throwMessage);
    }
    goalSound = sound;
  }

  public static void setStartWhistle(Sound sound) {
    if (sound == null) {
      throw new IllegalArgumentException(throwMessage);
    }
    startWhistle = sound;
  }

  public static void setGameOverSound(Sound sound) {
    if (sound == null) {
      throw new IllegalArgumentException(throwMessage);
    }
    gameOverSound = sound;
  }

  public static void setJumpingSound(Sound sound) {
    if (sound == null) {
      throw new IllegalArgumentException(throwMessage);
    }
    jumpingSound = sound;
  }

  /**
   * Gets the ball texture from cache or loads it if not present.
   *
   * @return The ball texture
   */
  public static Texture getBallTexture() {
    return getTexture("BallImage.png");
  }

  /**
   * Gets the player 1 texture from cache or loads it if not present.
   *
   * @return The player 1 texture
   */
  public static Texture getPlayer1Texture() {
    return getTexture("Player1.png");
  }

  /**
   * Gets the player 2 texture from cache or loads it if not present.
   *
   * @return The player 2 texture
   */
  public static Texture getPlayer2Texture() {
    return getTexture("Player2.png");
  }

  /**
   * Gets the left goal texture from cache or loads it if not present.
   *
   * @return The left goal texture
   */
  public static Texture getLeftGoalTexture() {
    return getTexture("GoalLeft.png");
  }

  /**
   * Gets the right goal texture from cache or loads it if not present.
   *
   * @return The right goal texture
   */
  public static Texture getRightGoalTexture() {
    return getTexture("GoalRight.png");
  }

  /**
   * Helper method to get or load textures from cache.
   *
   * @param path The file path of the texture
   * @return The cached or newly loaded texture
   */
  public static Texture getTexture(String path) {
    return textureCache.computeIfAbsent(path, k -> new Texture(Gdx.files.internal(k)));
  }

  /**
   * Disposes of all cached textures and clears the cache.
   * Should be called when shutting down the game.
   */
  public static void disposeTextures() {
    textureCache.values().forEach(Texture::dispose);
    textureCache.clear();
  }

}