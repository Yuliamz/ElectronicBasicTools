package karnaughMap;

import GUI.KarnaughMapPane;
import GUI.ReductionPane;
import TruthTable.PreParser;
import TruthTable.TruthTable;
import TruthTable.TruthTableSolve;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * JavaFX grid panel for the Karnaugh map interactive cells.
 */
public class JPanelMainMap extends GridPane {

    int quantityUP;
    int varQuantityUP;
    int quantityLEFT;
    int varQuantityLEFT;
    Object[][] objects;
    Map<String, Integer> map;

    public JPanelMainMap() {
        this.setGridLinesVisible(true);
    }

    public JPanelMainMap(int quantityUP, int quantityLEFT) {
        this.quantityUP = quantityUP;
        this.quantityLEFT = quantityLEFT;
        this.setGridLinesVisible(true);
        objects = new Object[quantityLEFT + 1][quantityUP + 1];
        varQuantityUP = getVariablesQuantity(quantityUP);
        varQuantityLEFT = getVariablesQuantity(quantityLEFT);
        createObjects();
        addObjects();
        map = new TreeMap<>();
    }

    public void addObjects() {
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (i == 0 || j == 0) {
                    Label lbl = (Label) objects[i][j];
                    GridPane.setHalignment(lbl, HPos.CENTER);
                    GridPane.setValignment(lbl, VPos.CENTER);
                    GridPane.setHgrow(lbl, Priority.ALWAYS);
                    GridPane.setVgrow(lbl, Priority.ALWAYS);
                    this.add(lbl, j, i);
                } else {
                    RenderButton btn = (RenderButton) objects[i][j];
                    GridPane.setHgrow(btn, Priority.ALWAYS);
                    GridPane.setVgrow(btn, Priority.ALWAYS);
                    this.add(btn, j, i);
                }
            }
        }
    }

    public void createObjects() {
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (i == 0 || j == 0) {
                    if (i == 0 && j > 0) {
                        Label label = new Label(GrayCode.getGreyCode(j - 1, varQuantityUP));
                        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        label.setMaxWidth(Double.MAX_VALUE);
                        label.setMaxHeight(Double.MAX_VALUE);
                        objects[i][j] = label;
                    } else if (j == 0 && i > 0) {
                        Label label = new Label(GrayCode.getGreyCode(i - 1, varQuantityLEFT));
                        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        label.setMaxWidth(Double.MAX_VALUE);
                        label.setMaxHeight(Double.MAX_VALUE);
                        objects[i][j] = label;
                    }
                } else {
                    objects[i][j] = new RenderButton();
                }
            }
        }
        objects[0][0] = new Label("");
    }

    public Map<String, Integer> getValues() {
        map = new TreeMap<>();
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (i != 0 && j != 0) {
                    map.put(getLabelText(i, j), ((RenderButton) objects[i][j]).getValue());
                }
            }
        }
        return map;
    }

    public ArrayList<Integer> getFuncValues() {
        getValues();
        ArrayList<Integer> al = new ArrayList<>();
        map.entrySet().forEach(entry -> al.add(entry.getValue()));
        return al;
    }

    public int getVariablesQuantity(int number) {
        return (int) (Math.ceil(Math.log(number) / Math.log(2)));
    }

    public String getLabelText(int row, int col) {
        return (((Label) objects[0][col]).getText() + ((Label) objects[row][0]).getText());
    }

    public void recreate(int quantityUP, int quantityLEFT) {
        this.getChildren().clear();
        this.quantityUP = quantityUP;
        this.quantityLEFT = quantityLEFT;
        objects = new Object[quantityLEFT + 1][quantityUP + 1];
        varQuantityUP = getVariablesQuantity(quantityUP);
        varQuantityLEFT = getVariablesQuantity(quantityLEFT);
        createObjects();
        addObjects();
        map = new TreeMap<>();
    }

    public void solve(int method) {
        try {
            ArrayList<Integer> vals = getFuncValues();
            TruthTableSolve solve = new TruthTableSolve(
                    TruthTable.getTable(vals),
                    TruthTable.setVarNames(vals.size()));
            String minWithX = TruthTableSolve.solveMin(solve.solveMinWithX());
            String minWithOutX = TruthTableSolve.solveMin(solve.solveMinWithOutX());
            if (method == 0) {
                String best = (minWithX.length() <= minWithOutX.length()) ? minWithX : minWithOutX;
                KarnaughMapPane.setAnswer(best);
                ReductionPane.setActualExpression(best);
                ReductionPane.setActualMinExpression(best);
                ReductionPane.setActualMaxExpression("");
            } else {
                String maxWithX = RuleSet.toPos(ExprParser.parse(PreParser.parseToExpresion(minWithX))).toString();
                String maxWithOutX = RuleSet.toPos(ExprParser.parse(PreParser.parseToExpresion(minWithOutX))).toString();
                String best = (maxWithX.length() <= maxWithOutX.length()) ? maxWithX : maxWithOutX;
                KarnaughMapPane.setAnswer(best);
                ReductionPane.setActualExpression(best);
                ReductionPane.setActualMaxExpression(best);
                ReductionPane.setActualMinExpression("");
            }
        } catch (Exception ex) {
            KarnaughMapPane.setError(ex);
            ReductionPane.resetExpression();
        }
    }

    public void reset() {
        for (int i = 1; i < objects.length; i++) {
            for (int j = 1; j < objects[0].length; j++) {
                RenderButton btn = (RenderButton) objects[i][j];
                btn.setText("0");
                btn.getStyleClass().removeAll("cell-0", "cell-1", "cell-x");
                btn.getStyleClass().add("cell-0");
            }
        }
    }
}
