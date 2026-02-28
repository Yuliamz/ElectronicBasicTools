package LogicCiruitDraw;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PLACanvasKNF extends JPanel {

    DrawPLA_KNF pla;

    public PLACanvasKNF(DrawPLA_KNF drawpla) {
        pla = drawpla;
        repaint();
    }

    public void setPLA(DrawPLA_KNF drawpla) {
        pla = drawpla;
        repaint(100L);
    }

    @Override
    protected void paintComponent(Graphics g) {
            pla.drawLogicCircuit(g, 0, 30);
            g.drawString("AND", 30, (ExpressionDrawerParser.getAl().size() * 30) + 79);
            g.drawString("OR", 96, (ExpressionDrawerParser.getAl().size() * 30) + 79);
            pla.drawOneANDWithoutCon(g, 30, (ExpressionDrawerParser.getAl().size() * 30) + 84, 7);
            pla.drawOneORWithOutCon(g, 90, (ExpressionDrawerParser.getAl().size() * 30) + 84, 7);
            g.setColor(Color.black);
    }
}
