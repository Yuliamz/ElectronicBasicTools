/*
 *this class renders the rowheader of the tables
 */

package TruthTableSolver.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

public class RowHeaderRenderer extends JLabel implements ListCellRenderer {
    
    public RowHeaderRenderer(JTable table) {
		JTableHeader header = table.getTableHeader();
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
    }
    @Override
    public Component getListCellRendererComponent(JList list,Object value, int index, boolean isSelected, boolean cellHasFocus){
		setText((value == null) ? "" : value.toString());
		return this;
    }
}