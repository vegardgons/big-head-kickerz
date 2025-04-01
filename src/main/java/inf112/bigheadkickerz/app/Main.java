package inf112.bigheadkickerz.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import org.lwjgl.system.Configuration;

/**
 * Main class for the Big Head Kickerz game.
 * This class initializes the game and sets the initial screen.
 */
public class Main {
  public static final int WINDOW_WIDTH = 1350;
  public static final int WINDOW_HEIGHT = 720;

  /**
   * Main method to start the game.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    System.out.println("Big Head Kickerz!");
    if (SharedLibraryLoader.os == Os.MacOsX) {
      Configuration.GLFW_LIBRARY_NAME.set("glfw_async");
    }
    Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
    cfg.setForegroundFPS(60);
    cfg.setTitle("Big Head Kickerz!");
    cfg.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
    cfg.setResizable(false);
    cfg.useVsync(true);

    new Lwjgl3Application(new BigHeadKickerzGame(), cfg);
  }
}
