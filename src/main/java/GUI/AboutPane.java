package GUI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class AboutPane {

    private final StackPane root;

    public AboutPane() {
        root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("about-pane");

        try {
            Image img = new Image(getClass().getResourceAsStream("/img/About.png"));
            ImageView iv = new ImageView(img);
            iv.setPreserveRatio(true);
            iv.setFitWidth(500);
            root.getChildren().add(iv);
        } catch (Exception e) {
            // fallback: empty pane
        }
    }

    public Node getRoot() {
        return root;
    }
}
