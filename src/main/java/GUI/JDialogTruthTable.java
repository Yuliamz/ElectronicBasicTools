package GUI;

import TruthTable.TableCell.LabelRender;
import TruthTable.TruthTable;
import java.awt.Frame;
import javax.script.ScriptException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;


public final class JDialogTruthTable extends javax.swing.JDialog {
	private JScrollPane jScrollPane1;
        private JTable jTable1;

    public JDialogTruthTable( Frame owner, String expresion) throws ScriptException {
        super(owner,"Tabla de activación",true);
        String aux=TruthTable.parseExpression(expresion);
        initComponents(TruthTable.getTable(aux), TruthTable.getStringVar(aux));
        this.setLocationRelativeTo(owner);
        
    }
    

    private void initComponents(Integer[][] matriz, String[] columNames) {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new JTable();        
        jTable1.setModel(new DefaultTableModel(matriz, columNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setSelectionMode(0);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-1).setCellRenderer(new LabelRender());
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-1).setMinWidth(20);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount()-1).setWidth(20);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
        );
        pack();
    }
}
