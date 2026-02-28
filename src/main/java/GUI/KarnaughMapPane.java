package GUI;

import TruthTable.PreParser;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import karnaughMap.JPanelMainMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KarnaughMapPane {

    private List<Character> variableUP;
    private List<Character> variableLEFT;
    public static Set<Character> variables;

    private static JPanelMainMap mapGrid;
    private static ComboBox<String> methodCombo;
    private static TextArea resultArea;

    private final BorderPane root;
    private Label labelUP;
    private Label labelLEFT;
    private Spinner<Integer> spinner;

    public KarnaughMapPane() {
        variableUP = new ArrayList<>();
        variableLEFT = new ArrayList<>();
        variables = new HashSet<>();
        setVarNames(3);

        root = new BorderPane();
        root.setPadding(new Insets(5));

        buildUI();
    }

    private void buildUI() {
        // Background image
        try {
            Image bgImg = new Image(getClass().getResourceAsStream("/img/JPanelKarnaughSolver.png"));
            BackgroundImage bg = new BackgroundImage(bgImg,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
            root.setBackground(new Background(bg));
        } catch (Exception ignored) {}

        // Variable labels
        labelUP = new Label();
        labelUP.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        labelUP.setAlignment(Pos.CENTER);
        labelUP.setMaxWidth(Double.MAX_VALUE);
        labelUP.getStyleClass().add("karnaugh-label");

        labelLEFT = new Label();
        labelLEFT.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        labelLEFT.setAlignment(Pos.CENTER);
        labelLEFT.setRotate(-90);
        labelLEFT.getStyleClass().add("karnaugh-label");

        // Map grid
        mapGrid = new JPanelMainMap();
        mapGrid.recreate(powerOfTwo(variableUP.size()), powerOfTwo(variableLEFT.size()));
        mapGrid.setGridLinesVisible(true);

        updateLabels();

        ScrollPane mapScroll = new ScrollPane(mapGrid);
        mapScroll.setFitToWidth(true);
        mapScroll.setFitToHeight(true);

        // Top: label UP
        root.setTop(labelUP);
        BorderPane.setMargin(labelUP, new Insets(2, 0, 2, 0));

        // Left: label LEFT
        root.setLeft(labelLEFT);
        BorderPane.setMargin(labelLEFT, new Insets(0, 4, 0, 4));

        // Center: map
        root.setCenter(mapScroll);

        // Bottom controls
        Label lblVarCount = new Label("Cantidad de Variables: ");
        spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 7, 3));
        spinner.setEditable(false);
        spinner.setPrefWidth(70);
        spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            setVarNames(newVal);
            mapGrid.recreate(powerOfTwo(variableUP.size()), powerOfTwo(variableLEFT.size()));
            updateLabels();
            resultArea.setText("");
        });

        Label lblMethod = new Label("Reducción Por:");
        methodCombo = new ComboBox<>();
        methodCombo.getItems().addAll("Miniterminos", "Maxiterminos");
        methodCombo.setValue("Miniterminos");
        methodCombo.setOnAction(e -> {
            resultArea.setText("Solucionando...");
            Thread t = new Thread(() -> solve());
            if (resultArea.getText().contains("Sol")) {
                t.setDaemon(true);
                t.start();
            }
        });

        Button resetBtn = new Button("Reiniciar");
        resetBtn.setOnAction(e -> {
            mapGrid.reset();
            resultArea.setText("");
        });

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefHeight(60);
        resultArea.getStyleClass().add("solution-area");

        HBox controlsRow = new HBox(10,
                lblVarCount, spinner,
                lblMethod, methodCombo,
                resetBtn
        );
        controlsRow.setAlignment(Pos.CENTER_LEFT);
        controlsRow.setPadding(new Insets(5, 5, 5, 5));

        VBox bottomBox = new VBox(5, controlsRow, resultArea);
        root.setBottom(bottomBox);
        BorderPane.setMargin(bottomBox, new Insets(5, 0, 0, 0));
    }

    private void updateLabels() {
        labelUP.setText(getVarUP());
        labelLEFT.setText(getVarLEFT());
    }

    public String getVarUP() {
        StringBuilder sb = new StringBuilder();
        for (Character c : variableUP) {
            sb.append(" ").append(c);
        }
        return sb.toString();
    }

    public String getVarLEFT() {
        StringBuilder sb = new StringBuilder();
        for (Character c : variableLEFT) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    public void setVarNames(int number) {
        variables = new HashSet<>();
        variableUP = new ArrayList<>();
        variableLEFT = new ArrayList<>();
        for (int i = 65; i < 65 + number; i++) {
            variables.add((char) i);
        }
        setVariables();
    }

    public void setVariables() {
        List<Character> listVariables = new ArrayList<>(variables);
        int count = listVariables.size();
        if (count % 2 == 0) {
            variableUP = listVariables.subList(0, count / 2);
            variableLEFT = listVariables.subList(count / 2, count);
        } else {
            variableUP = listVariables.subList(0, (count / 2) + 1);
            variableLEFT = listVariables.subList((count / 2) + 1, count);
        }
    }

    public static int powerOfTwo(int number) {
        return (int) Math.pow(2, number);
    }

    public static void setAnswer(String text) {
        String result = PreParser.expresiontoString(text).replace("*", "");
        Platform.runLater(() -> resultArea.setText(result));
    }

    public static void setError(Exception e) {
        Platform.runLater(() -> resultArea.setText("Indeterminado"));
        e.printStackTrace();
    }

    public static void solve() {
        String selected = methodCombo.getValue();
        if ("Miniterminos".equals(selected)) {
            mapGrid.solve(0);
        } else {
            mapGrid.solve(1);
        }
    }

    public Node getRoot() {
        return root;
    }
}
