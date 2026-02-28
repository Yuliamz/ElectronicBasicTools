package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow(primaryStage);
        Scene scene = new Scene(mainWindow.getRoot(), 560, 560);
        scene.getStylesheets().add(getClass().getResource("/css/style.css") != null
                ? getClass().getResource("/css/style.css").toExternalForm()
                : "");
        primaryStage.setTitle("ElectronicBasicTools");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(540);
        primaryStage.setMinHeight(512);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
