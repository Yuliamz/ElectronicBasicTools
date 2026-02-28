package LogicCiruitDraw;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

public class DialogLogicalCircuit extends JDialog {

    JScrollPane jScrollPane;

    public DialogLogicalCircuit(String expression, JFrame owner) {
        super(owner, "Diagrama L\u00f3gico | " + expression, true);
        jScrollPane = new JScrollPane();
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBorder(null);
    }

    public void setPLADNF(PLACanvasDNF drawpla) {
        jScrollPane.setViewportView(drawpla);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        this.add(jScrollPane, BorderLayout.CENTER);
        pack();
    }

    public void setPLAKNF(PLACanvasKNF drawpla) {
        jScrollPane.setViewportView(drawpla);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        this.add(jScrollPane, BorderLayout.CENTER);
        pack();
    }
}
