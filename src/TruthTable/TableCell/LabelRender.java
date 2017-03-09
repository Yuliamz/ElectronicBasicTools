package TruthTable.TableCell;

import GUI.JDialogKarnaughMap;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Julian David Grijalba Bernal
 */
public class LabelRender extends JLabel implements TableCellRenderer {

    public LabelRender() {
        this.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setText(value.toString());
        this.setForeground(Color.BLACK);
        switch (value.toString()) {
            case "0":
                this.setBackground((row % 2 == 0) ? Color.WHITE : javax.swing.UIManager.getDefaults().getColor("Table.light"));
                break;
            case "1":
                this.setBackground(new Color(91, 255, 152));
                break;
            default:
                this.setBackground((row % 2 == 0) ? Color.WHITE : javax.swing.UIManager.getDefaults().getColor("Table.light"));
                break;
        }
        if (column == 0) {
            this.setBackground((row % 2 == 0) ? Color.WHITE : javax.swing.UIManager.getDefaults().getColor("Table.light"));
        }
        if (isSelected && column > 0) {
            this.setBackground(Color.red);
            this.setForeground(Color.WHITE);
        }
        if (!table.getColumnName(column).equals("Salida")) {
            this.setToolTipText(table.getColumnName(column) + table.getValueAt(row, 0));
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < table.getColumnCount() - 1; i++) {
                builder.append(table.getValueAt(row, i));
            }
            this.setToolTipText(builder.toString());
        }

        return this;
    }
}
