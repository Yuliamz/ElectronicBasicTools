package TruthTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author Julian David Grijalba Bernal
 */
public class TruthTable {

    public static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByExtension("js");

    public static Integer[][] getTable(ArrayList<Integer> funcValues) {
        int quantityVars = (int) Math.ceil(Math.log(funcValues.size()) / Math.log(2));
        Integer[][] table = new Integer[funcValues.size()][quantityVars + 1];
        for (int i = 0, x; i < table.length; i++) {
            x = i;
            for (int j = quantityVars - 1; j >= 0; j--) {
                table[i][j] = x % 2;
                x /= 2;
            }
            table[i][quantityVars] = funcValues.get(i);
        }
        return table;
    }

    public static Character[] setVarNames(int number) {
        int varQuantity = (int) Math.ceil(Math.log(number) / Math.log(2));
        Character[] var = new Character[varQuantity];
        for (int i = 65; i < 65 + varQuantity; i++) {
            var[i - 65] = (char) i;
        }
        return var;
    }

    public static Integer[][] getTable(String expresion) throws ScriptException {
        Set<Character> vars = getVariables(expresion);
        int quantityVars = vars.size();
        Integer[][] table = new Integer[(int) Math.pow(2, quantityVars)][quantityVars + 1];
        ArrayList<Boolean> al = new ArrayList<>();

        for (int i = 0, x; i < table.length; i++) {
            x = i;
            al.clear();
            for (int j = quantityVars - 1; j >= 0; j--) {
                table[i][j] = x % 2;
                al.add(0, table[i][j] == 1);
                x /= 2;
            }
            table[i][quantityVars] = (evaluate(expresion, al, vars)) ? 1 : 0;
        }
        return table;
    }

    public static boolean evaluate(String parsedExpression, ArrayList<Boolean> aRow, Set<Character> vaSet) throws ScriptException {
        int i = 0;
        for (Character next : vaSet) {
            ENGINE.eval(next + " = " + aRow.get(i++));
        }
        return Boolean.parseBoolean(String.valueOf(ENGINE.eval(parsedExpression)));
    }

    public static Set<Character> getVariables(String expression) {
        Set<Character> variableSet = new HashSet<>();
        String aux = expression;
        if (aux.equals("1") || aux.equals("0")) {
            variableSet.add('A');
        } else {
            for (int i = 0; i < aux.length(); i++) {
                if (Character.isLetter(aux.charAt(i))) {
                    variableSet.add(aux.charAt(i));
                }
            }
        }
        return variableSet;
    }

    public static String parseExpression(String expression) {
        String aux = expression.trim().toUpperCase().replaceAll(" +", "");
        PreParser.validateExpression(aux);
        aux = PreParser.replaceOneZero(aux, PreParser.getfirstLetter(aux),"&&","||");
        aux = aux.replace("*", "&&").replace(".", "&&").replace("+", "||").replace(")(", ")&&(");
        aux=PreParser.postToPrefixNOT(aux);
        aux=PreParser.addAndOperand(aux, "&&");
        return aux;
    }

    public static String[] getStringVar(Set<Character> SetChar) {
        String[] stringVar = new String[SetChar.size()];
        int i = 0;
        for (Iterator<Character> iterator = SetChar.iterator(); iterator.hasNext();) {
            stringVar[i++] = "" + iterator.next();
        }
        return stringVar;
    }

    public static String[] getStringVar(String expression) {
        Set<String> variableSet = new HashSet<>();
        if (expression.equals("1") || expression.equals("0")) {
            variableSet.add("A");
        } else {
            for (int i = 0; i < expression.length(); i++) {
                if (Character.isLetter(expression.charAt(i))) {
                    variableSet.add("" + expression.charAt(i));
                }
            }
        }
        String[] var = variableSet.toArray(new String[variableSet.size() + 1]);
        var[variableSet.size()] = "Salida";
        return var;
    }

    public static ScriptEngine getENGINE() {
        return ENGINE;
    }

}
