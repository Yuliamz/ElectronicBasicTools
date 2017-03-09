package LogicCiruitDraw;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PLACanvasDNF extends JPanel {

    DrawPLA_DNF pla;

    public PLACanvasDNF(DrawPLA_DNF drawpla) {
        pla = drawpla;
        repaint();
    }

    public void setPLA(DrawPLA_DNF drawpla) {
        pla = drawpla;
        repaint(100L);
    }

    @Override
    protected void paintComponent(Graphics g) {
            pla.drawLogicCircuit(g, 0, 30);
            g.drawString("AND", 30, (ExpressionDrawerParser.getAl().size() * 30) + 79);
            g.drawString("OR", 96, (ExpressionDrawerParser.getAl().size() * 30) + 79);
            pla.drawOneAND(g, 30, (ExpressionDrawerParser.getAl().size() * 30) + 80);
            pla.drawOneORWithOutCon(g, 90, (ExpressionDrawerParser.getAl().size() * 30) + 84, 8);
            g.setColor(Color.black);
    }

}
