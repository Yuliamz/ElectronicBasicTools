package GUI;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import TruthTable.PreParser;
import TruthTable.TruthTable;
import TruthTable.TruthTableSolve;
import karnaughMap.KarnaughMap;
import LogicCiruitDraw.DrawPLA_DNF;
import LogicCiruitDraw.DrawPLA_KNF;
import LogicCiruitDraw.ExpressionDrawerParser;
import LogicCiruitDraw.LogicCircuitDialog;
import LogicCiruitDraw.PLACanvasDNF;
import LogicCiruitDraw.PLACanvasKNF;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;

public class ReductionPane {

    private volatile Thread thread;
    private static String actualExpression = "";
    private static String actualMinExpression = "";
    private static String actualMaxExpression = "";

    private final Stage ownerStage;
    private final VBox root;

    private TextField expressionField;
    private TextArea solutionArea;

    public ReductionPane(Stage ownerStage) {
        this.ownerStage = ownerStage;
        this.root = new VBox(8);
        root.setPadding(new Insets(10));
        root.getStyleClass().add("reduction-pane");

        buildUI();
        resetExpression();

        try {
            TruthTable.getTable("A+!A");
        } catch (ScriptException ex) {
            Logger.getLogger(ReductionPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildUI() {
        // Background image
        try {
            Image bgImg = new Image(getClass().getResourceAsStream("/img/JPanelReduction.jpg"));
            BackgroundImage bg = new BackgroundImage(bgImg,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
            root.setBackground(new Background(bg));
        } catch (Exception ignored) {}

        // Expression input
        expressionField = new TextField();
        expressionField.setPromptText("Ingrese una expresión booleana...");
        expressionField.setAlignment(Pos.CENTER);
        expressionField.getStyleClass().add("expression-field");
        expressionField.setContextMenu(createContextMenu(expressionField));
        VBox.setMargin(expressionField, new Insets(40, 20, 0, 20));

        // Buttons row 1
        Button btnTruthTable = new Button("Tabla de Activación");
        Button btnDNF = new Button("Compuerta AND");
        Button btnMax = new Button("Maxiterminos");

        // Buttons row 2
        Button btnMin = new Button("Miniterminos");
        Button btnKarnaugh = new Button("Mapa de Karnaugh");
        Button btnKNF = new Button("Compuerta OR");

        styleButton(btnTruthTable);
        styleButton(btnDNF);
        styleButton(btnMax);
        styleButton(btnMin);
        styleButton(btnKarnaugh);
        styleButton(btnKNF);

        HBox row1 = new HBox(10, btnTruthTable, btnDNF, btnMax);
        row1.setAlignment(Pos.CENTER);
        row1.setPadding(new Insets(0, 10, 0, 10));
        HBox.setHgrow(btnTruthTable, Priority.ALWAYS);
        HBox.setHgrow(btnDNF, Priority.ALWAYS);
        HBox.setHgrow(btnMax, Priority.ALWAYS);
        btnTruthTable.setMaxWidth(Double.MAX_VALUE);
        btnDNF.setMaxWidth(Double.MAX_VALUE);
        btnMax.setMaxWidth(Double.MAX_VALUE);

        HBox row2 = new HBox(10, btnMin, btnKarnaugh, btnKNF);
        row2.setAlignment(Pos.CENTER);
        row2.setPadding(new Insets(0, 10, 0, 10));
        HBox.setHgrow(btnMin, Priority.ALWAYS);
        HBox.setHgrow(btnKarnaugh, Priority.ALWAYS);
        HBox.setHgrow(btnKNF, Priority.ALWAYS);
        btnMin.setMaxWidth(Double.MAX_VALUE);
        btnKarnaugh.setMaxWidth(Double.MAX_VALUE);
        btnKNF.setMaxWidth(Double.MAX_VALUE);

        Separator separator = new Separator();
        VBox.setMargin(separator, new Insets(10, 0, 10, 0));

        // Solution area
        solutionArea = new TextArea();
        solutionArea.setEditable(false);
        solutionArea.setWrapText(true);
        solutionArea.setPrefHeight(130);
        solutionArea.getStyleClass().add("solution-area");
        solutionArea.setContextMenu(createContextMenu(solutionArea));
        solutionArea.setText("NOT: ! \nAND: * \nOR: +\n1 y 0 Estan disponibles\n\nEjemplo: !A!B!C!D+!ACD+!ABC*!1+A!BD+CD+ABCD*0");
        VBox.setMargin(solutionArea, new Insets(0, 20, 10, 20));

        root.getChildren().addAll(
                expressionField,
                row1,
                row2,
                separator,
                solutionArea
        );

        // Action listeners
        btnTruthTable.setOnAction(e -> onTruthTable());
        btnMax.setOnAction(e -> onMax());
        btnMin.setOnAction(e -> onMin());
        btnKarnaugh.setOnAction(e -> onKarnaugh());
        btnDNF.setOnAction(e -> onDNF());
        btnKNF.setOnAction(e -> onKNF());
    }

    private void styleButton(Button btn) {
        btn.getStyleClass().add("action-button");
    }

    private ContextMenu createContextMenu(javafx.scene.control.TextInputControl control) {
        ContextMenu cm = new ContextMenu();
        MenuItem copy = new MenuItem("Copiar");
        copy.setOnAction(e -> control.copy());
        MenuItem cut = new MenuItem("Cortar");
        cut.setOnAction(e -> control.cut());
        MenuItem paste = new MenuItem("Pegar");
        paste.setOnAction(e -> control.paste());
        cm.getItems().addAll(copy, cut, paste);
        return cm;
    }

    private void onTruthTable() {
        String text = expressionField.getText().replaceAll(" +", "");
        if (!text.isEmpty()) {
            try {
                TruthTableDialog dialog = new TruthTableDialog(ownerStage, expressionField.getText());
                dialog.showAndWait();
                setActualExpression(expressionField.getText().trim());
            } catch (RuntimeException | ScriptException ex) {
                invalidExpression(ex);
            }
        } else {
            solutionArea.setText("Ingrese una expresión");
        }
    }

    private void onMax() {
        String text = expressionField.getText().trim().replaceAll(" +", "");
        if (!text.isEmpty()) {
            solutionArea.setText("Calculando...");
            stopThread();
            if (text.equals(actualExpression) && !"".equals(actualMaxExpression)) {
                solutionArea.setText(actualMaxExpression);
            } else {
                thread = new Thread(this::calculateMax);
                if (solutionArea.getText().contains("Cal")) {
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        } else {
            solutionArea.setText("Ingrese una expresión");
        }
    }

    private void onMin() {
        String text = expressionField.getText().trim().replaceAll(" +", " ");
        if (!text.isEmpty()) {
            solutionArea.setText("Calculando...");
            stopThread();
            if (text.equals(actualExpression) && !"".equals(actualMinExpression)) {
                solutionArea.setText(actualMinExpression);
            } else {
                thread = new Thread(this::calculateMin);
                if (solutionArea.getText().contains("Cal")) {
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        } else {
            solutionArea.setText("Ingrese una expresión");
        }
    }

    private void onKarnaugh() {
        try {
            String expressionS = TruthTable.parseExpression(expressionField.getText());
            if (TruthTable.getVariables(expressionS).size() > 1) {
                KarnaughMapDialog dialog = new KarnaughMapDialog(ownerStage,
                        new KarnaughMap(TruthTable.getVariables(expressionS), TruthTable.getTable(expressionS)));
                dialog.showAndWait();
                setActualExpression(expressionField.getText().trim());
            } else {
                solutionArea.setText("Deben haber mínimo dos variables de entrada");
            }
        } catch (ScriptException | RuntimeException ex) {
            invalidExpression(ex);
        }
    }

    private void onDNF() {
        String text = expressionField.getText().trim().replaceAll(" +", " ");
        if (!text.isEmpty()) {
            solutionArea.setText("Dibujando...");
            stopThread();
            if (text.equals(actualExpression) && !actualMinExpression.equals("")) {
                drawDNF(actualMinExpression);
            } else {
                thread = new Thread(this::drawDNF);
                if (solutionArea.getText().contains("Dib")) {
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        } else {
            solutionArea.setText("Ingrese una expresión");
        }
    }

    private void onKNF() {
        String text = expressionField.getText().trim().replaceAll(" +", " ");
        if (!text.isEmpty()) {
            solutionArea.setText("Dibujando...");
            stopThread();
            if (text.equals(actualExpression) && !actualMaxExpression.equals("")) {
                drawKNF(actualMaxExpression);
            } else {
                thread = new Thread(this::drawKNF);
                if (solutionArea.getText().contains("Dib")) {
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        } else {
            solutionArea.setText("Ingrese una expresión");
        }
    }

    private void invalidExpression(Exception e) {
        Platform.runLater(() ->
            solutionArea.setText("Expresión invalida\n\nInformación:\n" + e.getMessage())
        );
        resetExpression();
        e.printStackTrace();
    }

    public static void resetExpression() {
        setActualExpression("");
        setActualMinExpression("");
        setActualMaxExpression("");
    }

    private void stopThread() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private void calculateMax() {
        try {
            Expression<String> posForm = RuleSet.toCNF(ExprParser.parse(
                    PreParser.parseToExpresion(expressionField.getText())));
            String result = PreParser.expresiontoString(posForm.toString().toUpperCase());
            Platform.runLater(() -> solutionArea.setText(result));
            actualExpression = expressionField.getText().trim();
            actualMaxExpression = result;
            actualMinExpression = "";
        } catch (Exception e) {
            invalidExpression(e);
        }
    }

    private void calculateMin() {
        try {
            String text = TruthTable.parseExpression(expressionField.getText());
            String result;
            if (TruthTable.getVariables(text).size() <= 7) {
                result = TruthTableSolve.solveMin(expressionField.getText());
            } else {
                result = PreParser.expresiontoString(
                        RuleSet.toDNF(ExprParser.parse(
                                RuleSet.toCNF(ExprParser.parse(
                                        PreParser.parseToExpresion(expressionField.getText()))).toString()
                        )).toString()).replace("*", "");
            }
            final String finalResult = result;
            Platform.runLater(() -> solutionArea.setText(finalResult));
            actualExpression = expressionField.getText().trim();
            actualMinExpression = result;
            actualMaxExpression = "";
        } catch (Exception e) {
            invalidExpression(e);
        }
    }

    private void drawDNF() {
        try {
            String exp = (TruthTable.getVariables(PreParser.parseToExpresion(expressionField.getText())).size() > 7)
                    ? PreParser.expresiontoString(RuleSet.toDNF(ExprParser.parse(
                            RuleSet.toCNF(ExprParser.parse(
                                    PreParser.parseToExpresion(expressionField.getText()))).toString())).toString())
                    : TruthTableSolve.solveMin(expressionField.getText());
            Set<Character> vars = TruthTable.getVariables(exp);
            ExpressionDrawerParser.createArrayDNF(exp, vars);
            String[] var = TruthTable.getStringVar(vars);
            final String finalExp = exp;
            Platform.runLater(() -> {
                solutionArea.setText(finalExp);
                PLACanvasDNF canvasDNF = new PLACanvasDNF(new DrawPLA_DNF(var),
                        (var.length * 50) + 112,
                        (ExpressionDrawerParser.getAl().size() * 30) + 125);
                LogicCircuitDialog dialog = new LogicCircuitDialog(ownerStage, finalExp, canvasDNF, true);
                dialog.showAndWait();
            });
            actualExpression = expressionField.getText().trim();
            actualMinExpression = exp;
            actualMaxExpression = "";
        } catch (Exception e) {
            invalidExpression(e);
        }
    }

    private void drawDNF(String minExpression) {
        try {
            Set<Character> vars = TruthTable.getVariables(minExpression);
            ExpressionDrawerParser.createArrayDNF(minExpression, vars);
            String[] var = TruthTable.getStringVar(vars);
            Platform.runLater(() -> {
                solutionArea.setText(minExpression);
                PLACanvasDNF canvasDNF = new PLACanvasDNF(new DrawPLA_DNF(var),
                        (var.length * 50) + 112,
                        (ExpressionDrawerParser.getAl().size() * 30) + 125);
                LogicCircuitDialog dialog = new LogicCircuitDialog(ownerStage, minExpression, canvasDNF, true);
                dialog.showAndWait();
            });
            actualExpression = expressionField.getText().trim();
            actualMinExpression = minExpression;
            actualMaxExpression = "";
        } catch (Exception e) {
            invalidExpression(e);
        }
    }

    private void drawKNF() {
        try {
            Expression<String> posForm = RuleSet.toCNF(ExprParser.parse(
                    PreParser.parseToExpresion(expressionField.getText())));
            String exp = PreParser.expresiontoString(posForm.toString().toUpperCase());
            Set<Character> vars = TruthTable.getVariables(exp);
            ExpressionDrawerParser.createArrayKNF(exp, vars);
            String[] var = TruthTable.getStringVar(vars);
            final String finalExp = exp;
            Platform.runLater(() -> {
                solutionArea.setText(finalExp);
                PLACanvasKNF canvasKNF = new PLACanvasKNF(new DrawPLA_KNF(var),
                        (var.length * 50) + 112,
                        (ExpressionDrawerParser.getAl().size() * 30) + 125);
                LogicCircuitDialog dialog = new LogicCircuitDialog(ownerStage, finalExp, canvasKNF, false);
                dialog.showAndWait();
            });
            actualExpression = expressionField.getText().trim();
            actualMaxExpression = exp;
            actualMinExpression = "";
        } catch (Exception e) {
            invalidExpression(e);
        }
    }

    private void drawKNF(String maxExpression) {
        Set<Character> vars = TruthTable.getVariables(maxExpression);
        ExpressionDrawerParser.createArrayKNF(maxExpression, vars);
        String[] var = TruthTable.getStringVar(vars);
        Platform.runLater(() -> {
            solutionArea.setText(maxExpression);
            PLACanvasKNF canvasKNF = new PLACanvasKNF(new DrawPLA_KNF(var),
                    (var.length * 50) + 112,
                    (ExpressionDrawerParser.getAl().size() * 30) + 125);
            LogicCircuitDialog dialog = new LogicCircuitDialog(ownerStage, maxExpression, canvasKNF, false);
            dialog.showAndWait();
        });
        actualExpression = expressionField.getText().trim();
        actualMaxExpression = maxExpression;
        actualMinExpression = "";
    }

    public static void setActualExpression(String expr) {
        actualExpression = expr;
    }

    public static void setActualMaxExpression(String expr) {
        actualMaxExpression = expr;
    }

    public static void setActualMinExpression(String expr) {
        actualMinExpression = expr;
    }

    public Node getRoot() {
        return root;
    }
}
