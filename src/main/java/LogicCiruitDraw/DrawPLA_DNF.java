package LogicCiruitDraw;

import java.awt.Color;
import java.awt.Graphics;

public class DrawPLA_DNF extends DrawPLA {

    private static final int xx_or[] = {0, 0, 1, 3, 6, 9, 13, 16, 19, 22, 24, 24, 0};
    private static final int yy_or[] = {19, 10, 6, 3, 1, 0, 0, 1, 3, 6, 10, 19, 19};

    public DrawPLA_DNF(String[] varNames) {
        super(varNames);
    }

    public void drawOneAND(Graphics g, int i, int j) {
        int ai[] = {0, 9, 12, 14, 16, 17, 19, 19, 17, 16, 14, 12, 9, 0, 0};
        int ai1[] = {4, 4, 5, 6, 8, 10, 12, 16, 19, 21, 22, 23, 24, 24, 4};

        for (int k = 0; k < ai.length - 1; k++) {
            g.drawLine(ai[k] + i, ai1[k] + j, ai[k + 1] + i, ai1[k + 1] + j);
        }
        g.drawLine(20 + i, 15 + j, 30 + i, 15 + j);
    }

    public void drawANDs(Graphics g) {
        g.setColor(Color.BLACK);
        int i=(varNames.length + 1) * 40 + 20;
        for (int k = 0; k < ExpressionDrawerParser.getAl().size(); k++) {
            if ((deleteEmpty(ExpressionDrawerParser.getAl().get(k)) >= 2)) {
                drawOneAND(g, i, 60 + k * 30);
            }
        }
        g.setColor(Color.black);
    }

    public void drawOneOR(Graphics g, int i, int j, int k) {
        g.setColor(Color.blue);
        g.drawLine(i + 2, j + 19, i + k * 3 + 5, j + 19);
        if (k > 6) {
            i += (int) ((double) (k - 6) * 1.5D);
        }
        for (int l = 0; l < xx_or.length - 1; l++) {
            g.drawLine(xx_or[l] + i, yy_or[l] + j, xx_or[l + 1] + i, yy_or[l + 1] + j);
        }
        g.drawLine(i + 7, j + 2, i + 7, j + 19);
        g.drawLine(i + 17, j + 2, i + 17, j + 19);
        g.drawLine(i + 12, j, i + 12, j - 19);
        drawSolderDot(g, i + 12, j - 18);
        g.setColor(Color.black);
        g.drawString("SALIDA", i - 7, 15);
    }

    public void drawOneORWithOutCon(Graphics g, int i, int j, int k) {
        g.setColor(Color.blue);
        g.drawLine(i + 2, j + 19, i + k * 3 + 5, j + 19);
        if (k > 6) {
            i += (int) ((double) (k - 6) * 1.5D);
        }
        for (int l = 0; l < xx_or.length - 1; l++) {
            g.drawLine(xx_or[l] + i, yy_or[l] + j, xx_or[l + 1] + i, yy_or[l + 1] + j);
        }
        g.drawLine(i + 7, j + 2, i + 7, j + 19);
        g.drawLine(i + 17, j + 2, i + 17, j + 19);
    }

    public void drawORs(Graphics g) {
        getOutputGatexpos();
        drawOneOR(g, getXposition(), 40, super.conns_OutputGate);

    }

    public void drawOutputconns(Graphics g) {
        g.setColor(Color.black);
        int i;
        int i1 = getXposition() + super.conns_OutputGate * 3 + 2;
        int k1 = 0;
        for (int j1 = 0; j1 < ExpressionDrawerParser.getAl().size(); j1++) {
            int k = 60 + j1 * 30 + 15;
            i= drawEmpty(j1, g, k);
            g.drawLine(i, k, i1, k);
            k1++;
            int j = getXposition() + 2 + 3 * k1;
            int l = 60 + j1 * 30 + 15;
            g.drawLine(j, l, j, 60);
            drawSolderDot(g, j, l);
        }
    }

    public void getOutputGatexpos() {
        super.xpos_OutputGate = 0;
        super.xsize_OutputGate = 0;
        super.conns_OutputGate = ExpressionDrawerParser.getAl().size();
    }

    public void drawLogicCircuit(Graphics g, int i, int j) {
        drawInputs(g);
        drawANDs(g);
        drawconns(g);
        drawORs(g);
        drawOutputconns(g);
        drawInputLabels(g);
    }

}
