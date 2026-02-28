/*
 * this class renders the buttons in the "FUNCTION" column
 * Renderiza los botones en la columna "Salida" del Solucionador de Tabla de verdad
 */

package TruthTableSolver.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class ButtonRenderer extends JButton implements TableCellRenderer {
    
    public ButtonRenderer() {     
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column){
		this.setText(value.toString());
		switch(this.getText()){
                    case "0":this.setBackground(Color.WHITE);break;
                    case "1":this.setBackground(new Color(91, 255, 152));break;
                    case "x":this.setBackground(new Color(122, 207, 255));break;
                    default:break;
                }
		return this;
    }
}
