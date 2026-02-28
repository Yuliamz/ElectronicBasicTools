package LogicCiruitDraw;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LogicCircuitDialog extends Stage {

    public LogicCircuitDialog(Window owner, String expression, Canvas canvas, boolean isDNF) {
        super();
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Diagrama Lógico | " + expression);

        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);
        scrollPane.setPannable(true);

        BorderPane pane = new BorderPane(scrollPane);
        pane.setPadding(new Insets(5));

        double w = Math.min(canvas.getWidth() + 40, 900);
        double h = Math.min(canvas.getHeight() + 60, 700);

        Scene scene = new Scene(pane, w, h);
        setScene(scene);
        setResizable(true);
    }
}
