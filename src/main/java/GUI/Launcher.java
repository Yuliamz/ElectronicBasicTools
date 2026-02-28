package GUI;

/**
 * Launcher class that does NOT extend javafx.application.Application.
 * This is required so that the JVM launcher does not enforce the JavaFX
 * module-path requirement when running via "java -jar".
 * The fat JAR bundles the JavaFX platform-specific native libraries, so
 * they are loaded automatically from the classpath at runtime.
 */
public class Launcher {
    public static void main(String[] args) {
        MainApp.main(args);
    }
}
