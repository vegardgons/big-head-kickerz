package inf112.bigheadkickerz.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music; 

public class Assets {
  public static Music menuMusic;
  public static Music goalSound; 
  public static Music startWhistle;    

  public static void load() {
    menuMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/menu_sound.mp3"));
    goalSound = Gdx.audio.newMusic(Gdx.files.internal("assets/goal_sound.mp3"));
    startWhistle = Gdx.audio.newMusic(Gdx.files.internal("assets/start_game_whistle.mp3"));
  }

  public static void playMenuMusic() {
    if (!menuMusic.isPlaying()) {
      menuMusic.setLooping(true);
      menuMusic.play();
    }
  }

  public static void stopMenuMusic() {
    if (menuMusic.isPlaying()) {
      menuMusic.stop();
    }
  }

  public static void playStartWhistle() {
    startWhistle.play();
  }

  public static void playGoalSound() {
    goalSound.play();
  }

  public static void dispose() {
    menuMusic.dispose();
    goalSound.dispose();
    startWhistle.dispose();
  }
}
