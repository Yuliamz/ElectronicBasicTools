package GUI;

import TruthTable.PreParser;
import TruthTable.TruthTable;
import TruthTable.TruthTableSolve;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TruthTablePane {

    public static int number_of_terms = 2;
    public static ArrayList<Integer> values = new ArrayList<>();
    private static TextArea resultArea = new TextArea();
    private static int sum_of_products_or_product_of_sums = 0;

    private final String[] terms_names = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private final BorderPane root;
    private TableView<ObservableList<String>> tableView;
    private Spinner<Integer> spinner;

    public TruthTablePane() {
        root = new BorderPane();
        root.setPadding(new Insets(10));
        setAndClearValuesArray();
        buildUI();
        resultArea.setText("Genera una funcion a partir de una tabla de verdad\n"
                + "Acepta terminos indiferentes(x)\n"
                + "Establezca la cantidad de variable de entrada\n"
                + "Haga click y elija la salida correspondiente para la fila elegida\n"
                + "Eliga el metodo de representación (Maxiterminos o Miniterminos)");
    }

    private void buildUI() {
        // Table
        tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.getStyleClass().add("truth-table");
        refreshTable();

        ScrollPane tableScroll = new ScrollPane(tableView);
        tableScroll.setFitToWidth(true);
        tableScroll.setFitToHeight(true);

        // Controls on the right
        Label lblVarCount = new Label("Cantidad de variables");
        spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 8, number_of_terms));
        spinner.setEditable(false);
        spinner.setPrefWidth(80);
        spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            number_of_terms = newVal;
            setAndClearValuesArray();
            refreshTable();
            if (!resultArea.getText().contains("Genera una")) {
                resultArea.setText("");
            }
        });

        ComboBox<String> methodCombo = new ComboBox<>(
                FXCollections.observableArrayList("Miniterminos", "Maxiterminos"));
        methodCombo.setValue("Miniterminos");
        methodCombo.setOnAction(e -> {
            sum_of_products_or_product_of_sums = methodCombo.getValue().equals("Miniterminos") ? 0 : 1;
            resultArea.setText("Solucionando...");
            Thread t = new Thread(() -> solveTable());
            if (resultArea.getText().contains("Sol")) {
                t.setDaemon(true);
                t.start();
            }
        });

        Button clearBtn = new Button("Reiniciar salida");
        clearBtn.setOnAction(e -> {
            setAndClearValuesArray();
            refreshTable();
            resultArea.setText("");
        });
        clearBtn.setMaxWidth(Double.MAX_VALUE);

        VBox controls = new VBox(10,
                lblVarCount,
                spinner,
                new Label("Método:"),
                methodCombo,
                clearBtn
        );
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.TOP_CENTER);
        controls.setPrefWidth(160);

        // Result area
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefHeight(80);
        resultArea.getStyleClass().add("solution-area");

        HBox tableAndControls = new HBox(10, tableScroll, controls);
        HBox.setHgrow(tableScroll, Priority.ALWAYS);

        root.setCenter(tableAndControls);
        root.setBottom(resultArea);
        BorderPane.setMargin(resultArea, new Insets(8, 0, 0, 0));
    }

    private void refreshTable() {
        tableView.getColumns().clear();
        tableView.getItems().clear();

        int rows = (int) Math.pow(2, number_of_terms);

        // Output column (clickable cycling: 0 -> 1 -> x -> 0)
        TableColumn<ObservableList<String>, String> outputCol = new TableColumn<>("Salida");
        outputCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        outputCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button();
            {
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setOnAction(e -> {
                    int row = getIndex();
                    int cur = values.get(row);
                    int next = (cur + 1) % 3;
                    values.set(row, next);
                    String[] labels = {"0", "1", "x"};
                    btn.setText(labels[next]);
                    styleOutputButton(btn, labels[next]);
                    getTableView().getItems().get(row).set(0, labels[next]);
                    solveTableAsync();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    btn.setText(item);
                    styleOutputButton(btn, item);
                    setGraphic(btn);
                }
            }
        });
        outputCol.setPrefWidth(70);
        tableView.getColumns().add(outputCol);

        // Variable columns
        for (int v = 0; v < number_of_terms; v++) {
            final int varIdx = v + 1;
            TableColumn<ObservableList<String>, String> varCol = new TableColumn<>(terms_names[v]);
            varCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(varIdx)));
            varCol.setPrefWidth(50);
            tableView.getColumns().add(varCol);
        }

        // Populate rows
        for (int i = 0; i < rows; i++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            // Output value
            int outVal = values.get(i);
            String[] labels = {"0", "1", "x"};
            row.add(labels[outVal]);
            // Variable values (binary)
            int aux = i;
            String[] bits = new String[number_of_terms];
            for (int j = number_of_terms - 1; j >= 0; j--) {
                bits[j] = String.valueOf(aux % 2);
                aux /= 2;
            }
            for (String bit : bits) {
                row.add(bit);
            }
            tableView.getItems().add(row);
        }
    }

    private void styleOutputButton(Button btn, String value) {
        btn.getStyleClass().removeAll("output-0", "output-1", "output-x");
        switch (value) {
            case "0" -> btn.getStyleClass().add("output-0");
            case "1" -> btn.getStyleClass().add("output-1");
            case "x" -> btn.getStyleClass().add("output-x");
        }
    }

    private void solveTableAsync() {
        Thread t = new Thread(() -> solveTable());
        t.setDaemon(true);
        t.start();
    }

    public static void setAndClearValuesArray() {
        values = new ArrayList<>();
        int q = (int) Math.pow(2, number_of_terms);
        for (int i = 0; i < q; i++) {
            values.add(0);
        }
    }

    public static void setAnswer(String solution) {
        String text = PreParser.expresiontoString(solution).replace("*", "");
        Platform.runLater(() -> resultArea.setText(text));
    }

    public static void solveTable() {
        try {
            TruthTableSolve solve = new TruthTableSolve(
                    TruthTable.getTable(values),
                    TruthTable.setVarNames(values.size()));
            String solMin = TruthTableSolve.solveMin(solve.solveMinWithOutX());
            String solMinX = TruthTableSolve.solveMin(solve.solveMinWithX());
            if (sum_of_products_or_product_of_sums == 0) {
                String best = (solMin.length() <= solMinX.length()) ? solMin : solMinX;
                setAnswer(best);
                ReductionPane.setActualExpression(best);
                ReductionPane.setActualMinExpression(best);
                ReductionPane.setActualMaxExpression("");
            } else {
                String solX = RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(solMinX))).toString();
                String sol = RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(solMin))).toString();
                String best = (sol.length() <= solX.length()) ? sol : solX;
                setAnswer(best);
                ReductionPane.setActualExpression(best);
                ReductionPane.setActualMaxExpression(best);
                ReductionPane.setActualMinExpression("");
            }
        } catch (Exception ex) {
            Platform.runLater(() -> resultArea.setText("Indeterminado"));
            ReductionPane.resetExpression();
        }
    }

    public Node getRoot() {
        return root;
    }
}
