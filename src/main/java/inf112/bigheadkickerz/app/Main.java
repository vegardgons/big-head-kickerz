package inf112.bigheadkickerz.app;

import org.lwjgl.system.Configuration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class Main {
    public static final int WINDOW_WIDTH = 1350, WINDOW_HEIGHT = 720;

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
