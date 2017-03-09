package TruthTable.TableCell;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author Julian David Grijalba Bernal
 */
public class LabelEditor extends DefaultCellEditor{

    private String label;
    
    public LabelEditor(JCheckBox checkBox) {
        super(checkBox);
        JLabel jLabel = new JLabel();
        jLabel.setEnabled(false);
        this.editorComponent=jLabel;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) {
        label=value.toString();
        JLabel jLabel = new JLabel(label);
        return jLabel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return this.label;
    }
}
