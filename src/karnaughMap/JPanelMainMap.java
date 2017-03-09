package karnaughMap;

import GUI.JPanelReduction;
import GUI.KarnaughMapSolver;
import TruthTable.PreParser;
import TruthTable.TruthTable;
import TruthTable.TruthTableSolve;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Julian David Grijalba Bernal
 */
public class JPanelMainMap extends JPanel {

    GridLayout gl;
    int quantityUP;
    int varQuantityUP;
    int quantityLEFT;
    int varQuantityLEFT;
    Object[][] objects;
    Map<String,Integer> map;
    
    public JPanelMainMap() {
    }

    public JPanelMainMap(int quantityUP, int quantityLEFT) {
        this.quantityUP = quantityUP;
        this.quantityLEFT = quantityLEFT;
        gl = new GridLayout(quantityLEFT+1, quantityUP+1);
        this.setLayout(gl);
        objects= new Object[quantityLEFT+1][quantityUP+1];
        varQuantityUP=this.getVariablesQuantity(quantityUP);
        varQuantityLEFT=this.getVariablesQuantity(this.quantityLEFT);
        createObjects();
        addObjects();
        map = new TreeMap<>();
    }
    
    public void addObjects(){
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (i==0 || j==0) {
                    this.add((JLabel)objects[i][j]);
                }else{
                    this.add((RenderButton)objects[i][j]);
                }
            }
        }
    }
    public void createObjects(){
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (i==0 || j==0) {
                    if (i==0 && j>0) {
                        JLabel label= new JLabel(GrayCode.getGreyCode(j-1, varQuantityUP),SwingConstants.CENTER);
                        label.setFont(new Font("Arial", Font.BOLD, 12));
                        objects[i][j]=label;
                    }else if(j==0 && i>0){
                        JLabel label= new JLabel(GrayCode.getGreyCode(i-1, varQuantityLEFT),SwingConstants.CENTER);
                        label.setFont(new Font("Arial", Font.BOLD, 12));
                        objects[i][j]=label;
                    }
                }else{
                    objects[i][j]=new RenderButton();
                }
            }
        }
        objects[0][0]=new JLabel("");
    }
   public void showValues(){
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (i==0 || j==0) {
                    System.out.print(((JLabel)objects[i][j]).getText()+" ");
                }else{
                    System.out.print(((RenderButton)objects[i][j]).getValue()+" ");
                }
            }
            System.out.println("");
        }
    }
   public Map<String,Integer> getValues(){
       map = new TreeMap<>();
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (i!=0 && j!=0) {
                    map.put(getLabelText(i, j), ((RenderButton)objects[i][j]).getValue());
                }
            }
        }
        return map;
    }
   
   public ArrayList<Integer> getFuncValues(){
       getValues();
       ArrayList<Integer> al = new ArrayList<>();
       map.entrySet().stream().forEach((entry) -> {al.add(entry.getValue());});
       return al;
   }
   
   public int getVariablesQuantity(int number){
       return (int) (Math.ceil(Math.log(number) / Math.log(2)));
   }
   
   public String getLabelText(int row, int col){
       return (((JLabel)objects[0][col]).getText()+((JLabel)objects[row][0]).getText());
   }
   
   public static void printMap(Map<String, Integer> map) {
       map.entrySet().stream().forEach((entry) -> {
           System.out.println("Key : " + entry.getKey()
                   + " Value : " + entry.getValue());
        });
   }
   
   public void recreate(int quantityUP, int quantityLEFT){
       this.removeAll();
       this.quantityUP = quantityUP;
       this.quantityLEFT = quantityLEFT;
       this.setLayout(gl=new GridLayout(quantityLEFT+1, quantityUP+1));
       objects= new Object[quantityLEFT+1][quantityUP+1];
       varQuantityUP=this.getVariablesQuantity(quantityUP);
       varQuantityLEFT=this.getVariablesQuantity(this.quantityLEFT);
       createObjects();
       addObjects();
       map = new TreeMap<>();
       this.repaint();
   }
   
   
   public void solve(int method){
       TruthTableSolve solve;
                try {
                    ArrayList<Integer> values = getFuncValues();
                    solve = new TruthTableSolve(TruthTable.getTable(values), TruthTable.setVarNames(values.size()));
                    String minWithX = TruthTableSolve.solveMin(solve.solveMinWithX());
                    String minWithOutX = TruthTableSolve.solveMin(solve.solveMinWithOutX());
                    if (method == 0) {
                        KarnaughMapSolver.setAnswer((minWithX.length()<=minWithOutX.length())?minWithX:minWithOutX);
                        JPanelReduction.setActualExpression(KarnaughMapSolver.jTextArea1.getText());
                        JPanelReduction.setActualMinExpression(KarnaughMapSolver.jTextArea1.getText());
                        JPanelReduction.setActualMaxExpression("");
                    } else {
                        String maxWithX = RuleSet.toPos(ExprParser.parse(PreParser.parseToExpresion(minWithX))).toString();
                        String maxWithOutX = RuleSet.toPos(ExprParser.parse(PreParser.parseToExpresion(minWithOutX))).toString();
                        KarnaughMapSolver.setAnswer((maxWithX.length()<=maxWithOutX.length())?maxWithX:maxWithOutX);
                        JPanelReduction.setActualExpression(KarnaughMapSolver.jTextArea1.getText());
                        JPanelReduction.setActualMaxExpression(KarnaughMapSolver.jTextArea1.getText());
                        JPanelReduction.setActualMinExpression("");
                    }
                    
                } catch (Exception ex) {
                    KarnaughMapSolver.setError(ex);
                    JPanelReduction.resetExpression();
                }
   }
   
   public void reset(){
       for (int i = 1; i < objects.length; i++) {
            for (int j = 1; j < objects[0].length; j++) {
                     ((RenderButton)objects[i][j]).setText("0");
                     ((RenderButton)objects[i][j]).setBackground(Color.WHITE);
            }
        }
   }
   
}
