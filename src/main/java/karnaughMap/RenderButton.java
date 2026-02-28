package karnaughMap;

import GUI.KarnaughMapPane;
import javafx.scene.control.Button;
import java.util.ArrayList;

/**
 * JavaFX button for Karnaugh map cells that cycles through 0, 1, x values.
 */
public final class RenderButton extends Button {

    public ArrayList<String> values;

    public RenderButton() {
        initValues();
        this.setText(values.get(0));
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        this.getStyleClass().add("karnaugh-cell");
        applyStyle();
        this.setOnAction(e -> {
            try {
                this.setText(values.get(values.indexOf(this.getText()) + 1));
            } catch (Exception ex) {
                this.setText(values.get(0));
            }
            applyStyle();
            KarnaughMapPane.solve();
        });
    }

    private void initValues() {
        values = new ArrayList<>();
        values.add("0");
        values.add("1");
        values.add("x");
    }

    @Override
    public String toString() {
        return this.getText();
    }

    public int getValue() {
        try {
            return Integer.parseInt(this.getText());
        } catch (Exception e) {
            return 2;
        }
    }

    public void applyStyle() {
        this.getStyleClass().removeAll("cell-0", "cell-1", "cell-x");
        switch (this.getText()) {
            case "0" -> this.getStyleClass().add("cell-0");
            case "1" -> this.getStyleClass().add("cell-1");
            case "x" -> this.getStyleClass().add("cell-x");
        }
    }
}
