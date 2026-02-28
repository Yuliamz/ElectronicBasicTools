package LogicCiruitDraw;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PLACanvasDNF extends Canvas {

    private final DrawPLA_DNF pla;

    public PLACanvasDNF(DrawPLA_DNF drawpla, double width, double height) {
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
        pla.drawOneAND(gc, 30, (ExpressionDrawerParser.getAl().size() * 30) + 80);
        pla.drawOneORWithOutCon(gc, 90, (ExpressionDrawerParser.getAl().size() * 30) + 84, 8);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
    }
}
