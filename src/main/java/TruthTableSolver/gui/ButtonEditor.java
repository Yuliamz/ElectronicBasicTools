package TruthTableSolver.gui;

import GUI.JpanelTruthTableSolver;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    public ArrayList<String> labelOption;
    private int clicked_button_number = 0;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        JpanelTruthTableSolver.setAndClearValuesArray();
        this.button = new JButton();
        this.button.setOpaque(true);
        this.initValues();
        this.button.addActionListener((ActionEvent e) -> {super.fireEditingStopped();});
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) {
        this.clicked_button_number = row;
        this.label = value.toString();
        this.button.setText(this.label);
        this.isPushed = true;
        return this.button;
    }

    @Override
    public Object getCellEditorValue() {
        if (this.isPushed) {
            try {
                this.label=this.labelOption.get(this.labelOption.indexOf(this.label)+1);   
            } catch (Exception ex) {
                this.label="0";
            }
            this.setValue();
            JpanelTruthTableSolver.solveTable();
        }
        this.isPushed = false;
        return this.label;
    }

    private void setValue(){
        switch(this.label){
            case "0":JpanelTruthTableSolver.values.set(this.clicked_button_number, 0);break;
            case "1":JpanelTruthTableSolver.values.set(this.clicked_button_number, 1);break;
            case "x":JpanelTruthTableSolver.values.set(this.clicked_button_number, 2);break;   
            default:break;
        }
    }
    
    @Override
    public boolean stopCellEditing() {
        this.isPushed = false;
        return super.stopCellEditing();
    }

    private void initValues() {
        this.labelOption = new ArrayList<>();
        this.labelOption.add("0");
        this.labelOption.add("1");
        this.labelOption.add("x");
    }
}
