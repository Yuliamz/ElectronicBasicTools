package karnaughMap;

import GUI.KarnaughMapSolver;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author Julian David Grijalba Bernal
 */
public final class RenderButton extends JButton{
    public ArrayList<String> values;

    public RenderButton() {
        initValues();
        this.setFont(new Font ( "Arial", Font. BOLD, 12 ));
        this.setText(values.get(0));
        this.setBackground(Color.WHITE);
        super.addActionListener(al);
    }
      
    ActionListener al = (ActionEvent e) -> {
        try {
            this.setText(values.get(values.indexOf(this.getText())+1));
        } catch (Exception ex) {
            this.setText(values.get(0));
        }
        changeColor();
        KarnaughMapSolver.solve();
    };
    
    private void initValues(){
        values = new ArrayList<>();
        values.add("0");
        values.add("1");
        values.add("x");
    }

    @Override
    public String toString() {
        return this.getText();
    }
    
    public int getValue(){
        try {
            return Integer.parseInt(this.getText());
        } catch (Exception e) {
            return 2;
        }
    }
    
    public void changeColor(){
        switch(this.getText()){
            case "0":this.setBackground(Color.WHITE);break;
            case "1":this.setBackground(new Color(91, 255, 152));break;
            case "x":this.setBackground(new Color(122, 207, 255));break;
            default:break;
        }
    }   
    
}
