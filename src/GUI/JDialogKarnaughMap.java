package GUI;

import TruthTable.TableCell.LabelEditor;
import TruthTable.TableCell.LabelRender;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import karnaughMap.KarnaughMap;

public final class JDialogKarnaughMap extends javax.swing.JDialog {

    String[][] karnaughMap;
    String[] colNames;
    String VarUP;
    String varLEFT;
    Font font;

    public JDialogKarnaughMap(java.awt.Frame parent, boolean modal, String[][] karnaughMap, String[] colNames, String VarUP, String varLEFT) {
        super(parent, "Mapa de Karnaugh", modal);
        this.karnaughMap = karnaughMap;
        this.colNames = colNames;
        this.VarUP = VarUP;
        this.varLEFT = varLEFT;
        font = new Font("Arial", Font.BOLD, 16);
        initComponents();
        this.setLayout(new BorderLayout());
        this.add(jLabelUP, BorderLayout.NORTH);
        this.add(jLabelLEFT, BorderLayout.WEST);
        setWith();
        this.add(jScrollPane1, BorderLayout.CENTER);
        pack();
    }

    public JDialogKarnaughMap(java.awt.Frame parent, KarnaughMap map) {
        super(parent, "Mapa de Karnaugh", true);
        this.karnaughMap = map.getKMap();
        this.colNames = map.getColumNames();
        this.VarUP = map.getVarUP();
        this.varLEFT = map.getVarLEFT();
        font = new Font("Arial", Font.BOLD, 16);
        initComponents();
        setWith();
        jTableKarnaughMap.setDefaultRenderer(jTableKarnaughMap.getColumnClass(1), new LabelRender());
        jTableKarnaughMap.setSelectionMode(0);
        jTableKarnaughMap.setColumnSelectionAllowed(true);
        jTableKarnaughMap.setRowSelectionAllowed(true);
        this.pack();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D d = (Graphics2D) g;
        d.setColor(getBackground().darker().darker());
        d.setStroke(new BasicStroke((float) 4));
        d.drawLine(-50, -25, 39, 63);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableKarnaughMap = new javax.swing.JTable();
        jLabelUP = new javax.swing.JLabel(this.VarUP);
        jLabelLEFT = new javax.swing.JLabel("<html><body>"+this.varLEFT+"</body></html>");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTableKarnaughMap.setModel(new javax.swing.table.DefaultTableModel(
            karnaughMap,colNames
        ){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        //jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableKarnaughMap.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableKarnaughMap.setRowSelectionAllowed(true);
        jTableKarnaughMap.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setViewportView(jTableKarnaughMap);

        jLabelUP.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelUP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelLEFT.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelLEFT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLEFT.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelLEFT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUP, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jLabelUP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLEFT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setWith() {
        int width = (colNames[1].length() * 9);
        for (int i = 0; i < jTableKarnaughMap.getColumnCount(); i++) {
            jTableKarnaughMap.getColumnModel().getColumn(i).setMinWidth(width);
            jTableKarnaughMap.getColumnModel().getColumn(i).setMaxWidth(width + 8);
            jTableKarnaughMap.getColumnModel().getColumn(i).setWidth(width);
        }
    }

    public JTable getjTable1() {
        return jTableKarnaughMap;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelLEFT;
    private javax.swing.JLabel jLabelUP;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableKarnaughMap;
    // End of variables declaration//GEN-END:variables
}
