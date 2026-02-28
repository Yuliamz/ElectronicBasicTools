package GUI;

import TruthTable.TruthTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.script.ScriptException;

public class TruthTableDialog extends Stage {

    public TruthTableDialog(Window owner, String expression) throws ScriptException {
        super();
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Tabla de activación");

        String aux = TruthTable.parseExpression(expression);
        Integer[][] matrix = TruthTable.getTable(aux);
        String[] colNames = TruthTable.getStringVar(aux);

        TableView<ObservableList<String>> tableView = new TableView<>();
        tableView.setEditable(false);

        for (int c = 0; c < colNames.length; c++) {
            final int colIdx = c;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colNames[c]);
            col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(colIdx)));
            col.setPrefWidth(60);
            // Highlight last column (output)
            if (c == colNames.length - 1) {
                col.getStyleClass().add("output-column");
            }
            tableView.getColumns().add(col);
        }

        for (Integer[] row : matrix) {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            for (Integer val : row) {
                rowData.add(String.valueOf(val));
            }
            tableView.getItems().add(rowData);
        }

        BorderPane pane = new BorderPane(tableView);
        pane.setPadding(new Insets(5));

        Scene scene = new Scene(pane, 480, 300);
        setScene(scene);
        setResizable(true);
    }
}
