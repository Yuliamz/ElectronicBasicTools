package GUI;

import karnaughMap.KarnaughMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class KarnaughMapDialog extends Stage {

    public KarnaughMapDialog(Window owner, KarnaughMap map) {
        super();
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Mapa de Karnaugh");

        String[][] kMap = map.getKMap();
        String[] colNames = map.getColumNames();
        String varUP = map.getVarUP();
        String varLEFT = map.getVarLEFT();

        // Table
        TableView<ObservableList<String>> tableView = new TableView<>();
        tableView.setEditable(false);

        for (int c = 0; c < colNames.length; c++) {
            final int colIdx = c;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colNames[c]);
            col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(colIdx)));
            int colWidth = Math.max(colNames[c].length() * 9 + 8, 50);
            col.setPrefWidth(colWidth);
            col.setMinWidth(colWidth);
            tableView.getColumns().add(col);
        }

        for (String[] row : kMap) {
            ObservableList<String> rowData = FXCollections.observableArrayList(row);
            tableView.getItems().add(rowData);
        }

        // Labels
        Label lblUP = new Label(varUP);
        lblUP.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblUP.setAlignment(Pos.CENTER_LEFT);
        lblUP.setMaxWidth(Double.MAX_VALUE);

        // varLEFT may contain HTML <br> tags from KarnaughMap.getVarLEFT()
        Label lblLEFT = new Label(varLEFT.replace("<br>", "\n").replace(" ", "").trim());
        lblLEFT.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblLEFT.setAlignment(Pos.TOP_RIGHT);
        lblLEFT.setRotate(-90);

        // Diagonal line canvas (top-left corner decoration)
        Canvas diagonalCanvas = new Canvas(40, 40);
        GraphicsContext gc = diagonalCanvas.getGraphicsContext2D();
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeLine(0, 0, 40, 40);

        StackPane cornerPane = new StackPane(diagonalCanvas);
        cornerPane.setPrefSize(40, 40);

        BorderPane layout = new BorderPane();
        layout.setTop(lblUP);
        layout.setLeft(lblLEFT);
        layout.setCenter(new ScrollPane(tableView));
        layout.setPadding(new Insets(5));
        BorderPane.setMargin(lblUP, new Insets(0, 0, 4, 0));
        BorderPane.setMargin(lblLEFT, new Insets(0, 4, 0, 0));

        Scene scene = new Scene(layout, 500, 400);
        setScene(scene);
        setResizable(true);
    }
}
