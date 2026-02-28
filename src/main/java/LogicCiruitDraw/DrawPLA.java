package LogicCiruitDraw;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class DrawPLA {

    protected String[] varNames;
    protected int xpos_OutputGate;
    protected int xsize_OutputGate;
    protected int conns_OutputGate;

    DrawPLA(String[] varNames) {
        this.varNames = varNames;
    }

    public void drawSolderDot(GraphicsContext gc, int i, int j) {
        gc.fillRect(i - 1, j - 1, 3, 3);
    }

    public void drawInputLabels(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        for (int j = 0; j < varNames.length; j++) {
            gc.fillText(varNames[varNames.length - j - 1], (j + 1) * 40 + 5, 15);
        }
    }

    public void drawOneInput(GraphicsContext gc, int i, int j) {
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        drawSolderDot(gc, i + 10, j + 1);
        gc.strokeLine(i + 10, j, i + 10, j + 40);
        gc.strokeLine(i + 10, j + 10, i + 25, j + 10);
        drawSolderDot(gc, i + 10, j + 10);
        gc.strokeLine(i + 25, j + 10, i + 25, j + 20);
        gc.setStroke(Color.BLUE);
        gc.setFill(Color.BLUE);
        gc.strokeLine(i + 16, j + 20, i + 34, j + 20);
        gc.strokeLine(i + 16, j + 20, i + 25, j + 32);
        gc.strokeLine(i + 34, j + 20, i + 25, j + 32);
        gc.fillRect(i + 23, j + 34, 5, 3);
        gc.fillRect(i + 24, j + 33, 3, 5);
        gc.strokeLine(i + 25, j + 33, i + 25, j + 39);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
    }

    public void drawconns(GraphicsContext gc) {
        int i1 = ((varNames.length + 1) * 40 + 20) - 2;
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        int l1 = 60 + ExpressionDrawerParser.getAl().size() * 30;
        for (int i2 = 0; i2 < varNames.length; i2++) {
            int j = 10 + (varNames.length - i2) * 40;
            gc.strokeLine(j, 60, j, l1);
            j += 15;
            gc.strokeLine(j, 60, j, l1);
        }

        int k, l, aux;
        for (int i = 0; i < ExpressionDrawerParser.getAl().size(); i++) {
            aux = 0;
            if (deleteEmpty(ExpressionDrawerParser.getAl().get(i)) >= 2) {
                for (int j = 0; j < ExpressionDrawerParser.getAl().get(i).size(); j++) {
                    switch (ExpressionDrawerParser.getAl().get(i).get(j)) {
                        case 0:
                            k = 25 + (varNames.length - j) * 40;
                            l = 60 + i * 30 + 5 + aux++ * 3;
                            drawSolderDot(gc, k, l);
                            gc.strokeLine(k, l, i1, l);
                            break;
                        case 1:
                            l = 10 + (varNames.length - j) * 40;
                            k = 60 + i * 30 + 5 + aux++ * 3;
                            drawSolderDot(gc, l, k);
                            gc.strokeLine(l, k, i1, k);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void drawInputs(GraphicsContext gc) {
        for (int k = 0; k < varNames.length; k++) {
            drawOneInput(gc, (varNames.length - k) * 40, 20);
        }
    }

    public int getXposition() {
        return 110 + (varNames.length * 40);
    }

    public static int deleteEmpty(ArrayList<Integer> ar) {
        return ar.toString().replaceAll("\\W", "").replace("2", "").trim().length();
    }

    public int drawEmpty(int j1, GraphicsContext gc, int k) {
        int i = (varNames.length + 1) * 40 + 50;
        if (!(deleteEmpty(ExpressionDrawerParser.getAl().get(j1)) >= 2)) {
            int indexOf0 = ExpressionDrawerParser.getAl().get(j1).indexOf(0);
            int indexOf1 = ExpressionDrawerParser.getAl().get(j1).indexOf(1);
            if (indexOf0 == -1 && indexOf1 == -1) {
                i = (ExpressionDrawerParser.getExpresion().equals("1")) ? 50 : 65;
                varNames[0] = "1";
            } else {
                i = (indexOf0 != -1) ? 25 + (varNames.length - indexOf0) * 40 : 10 + (varNames.length - indexOf1) * 40;
            }
            drawSolderDot(gc, i, k);
        }
        return i;
    }
}
