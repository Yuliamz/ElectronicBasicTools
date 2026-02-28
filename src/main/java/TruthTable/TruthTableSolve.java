package TruthTable;

import MinimizeMinitermins.MinimizedTable;

/**
 *
 * @author Julian David Grijalba Bernal
 */
public class TruthTableSolve {
    
    private final Integer[][] truthTable;
    private final Character[] varNames;
    private StringBuilder stringSolver;
    private StringBuilder stringExpression;
    

    public TruthTableSolve(Integer[][] truthTable, Character[] varNames) {
        this.truthTable = truthTable;
        this.varNames = varNames;
        stringSolver = new StringBuilder();
        stringExpression= new StringBuilder();
    }

    
    public String solveMaxWithX(){
        stringSolver = new StringBuilder();
        for (int i = 0; i < truthTable.length; i++) {
            if (truthTable[i][truthTable[0].length-1]==0 || truthTable[i][truthTable[0].length-1]==2) {
                stringSolver.append(getExpressionMax(i));
            }
        }
        return stringSolver.substring(0, stringSolver.length()-2);
    }
    public String solveMaxWithoutX(){
        stringSolver = new StringBuilder();
        for (int i = 0; i < truthTable.length; i++) {
            if (truthTable[i][truthTable[0].length-1]==0) {
                stringSolver.append(getExpressionMax(i));
            }
        }
        return stringSolver.substring(0, stringSolver.length()-2);
    }
    public String solveMinWithX(){
        stringSolver = new StringBuilder();
        for (int i = 0; i < truthTable.length; i++) {
            if (truthTable[i][truthTable[0].length-1]==1 || truthTable[i][truthTable[0].length-1]==2) {
                stringSolver.append(getExpressionMin(i));
            }
        }
        return stringSolver.substring(0, stringSolver.length()-2);
    }
    public String solveMinWithOutX(){
        stringSolver = new StringBuilder();
        for (int i = 0; i < truthTable.length; i++) {
            if (truthTable[i][truthTable[0].length-1]==1) {
                stringSolver.append(getExpressionMin(i));
            }
        }
        return stringSolver.substring(0, stringSolver.length()-2);
    }
    
    private String getExpressionMax(int row){
        stringExpression = new StringBuilder();
        for (int i = 0; i < truthTable[row].length-1; i++) {            
            stringExpression.append((truthTable[row][i]==1) ? (" | !"+varNames[i]) : " | "+varNames[i]);
        }
        return " ("+stringExpression.substring(3)+") &";
    }
	
    private String getExpressionMin(int row){
        stringExpression = new StringBuilder();
        for (int i = 0; i < truthTable[row].length-1; i++) {
            stringExpression.append((truthTable[row][i]==1) ? (" & "+varNames[i]) : " & !"+varNames[i]);
        }
        return " ("+stringExpression.substring(3)+") |";
    }
    
    public static String solveMin(String exp){
          String aux = exp.trim().toUpperCase().replaceAll(" +", "")
                  .replace("*", "")
                  .replace("&", "")
                  .replace(".", "")
                  .replace("TRUE", "(A+!A)")
                  .replace("FALSE", "(A!A)")
                  .replace("||", "+")
                  .replace("|", "+");
          aux=PreParser.cleanNot(aux);
          if (aux.contains("!")) aux=PreParser.preToPostfix(aux);
          return PreParser.expresiontoString(new MinimizedTable(aux).toString());
          }
        }
