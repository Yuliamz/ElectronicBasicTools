package LogicCiruitDraw;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PLACanvasKNF extends Canvas {

    private final DrawPLA_KNF pla;

    public PLACanvasKNF(DrawPLA_KNF drawpla, double width, double height) {
        super(width, height);
        this.pla = drawpla;
        draw();
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        pla.drawLogicCircuit(gc, 0, 30);
        gc.setFill(Color.BLACK);
        gc.fillText("AND", 30, (ExpressionDrawerParser.getAl().size() * 30) + 79);
        gc.fillText("OR", 96, (ExpressionDrawerParser.getAl().size() * 30) + 79);
        pla.drawOneANDWithoutCon(gc, 30, (ExpressionDrawerParser.getAl().size() * 30) + 84, 7);
        pla.drawOneORWithOutCon(gc, 90, (ExpressionDrawerParser.getAl().size() * 30) + 84, 7);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
    }
}
