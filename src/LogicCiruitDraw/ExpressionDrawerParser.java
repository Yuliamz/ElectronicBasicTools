package LogicCiruitDraw;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Julian David Grijalba Bernal
 */
public class ExpressionDrawerParser {

    private static ArrayList<ArrayList<Integer>> al = new ArrayList<>();
    private static String expresion;

    public static ArrayList<ArrayList<Integer>> getAl() {
//        al = new ArrayList<>();
//        ArrayList<Integer> a1 = new ArrayList<>();
//        a1.add(1);
//        a1.add(0);
//        a1.add(2);
//        a1.add(2);
//        al.add(a1);
//        ArrayList<Integer> a2 = new ArrayList<>();
//        a2.add(1);
//        a2.add(1);
//        a2.add(0);
//        a2.add(0);
//        al.add(a2);
//        ArrayList<Integer> a3 = new ArrayList<>();
//        a3.add(1);
//        a3.add(0);
//        a3.add(2);
//        a3.add(2);
//        al.add(a3);
//        ArrayList<Integer> a4 = new ArrayList<>();
//        a4.add(2);
//        a4.add(0);
//        a4.add(0);
//        a4.add(2);
//        al.add(a4);
//        ArrayList<Integer> a5 = new ArrayList<>();
//        a5.add(1);
//        a5.add(1);
//        a5.add(1);
//        a5.add(0);
//        al.add(a5);
        return al;
    }

    private static void resetArray() {
        setAl(new ArrayList<>());
    }

    public static void createArrayDNF(String expresion, Set<Character> setVar) {
        resetArray();
        ExpressionDrawerParser.expresion=expresion;
            Character[] charVar = setVar.toArray(new Character[setVar.size()]);
            String [] minexpressions = expresion.replace("+", "-").split("-");
            for (String minExpresion : minexpressions) {
                getAl().add(getExpression(minExpresion.trim(), charVar));
            }
    }

    public static void createArrayKNF(String expresion, Set<Character> setVar) {
        resetArray();
        ExpressionDrawerParser.expresion=expresion;
            Character[] charVar = setVar.toArray(new Character[setVar.size()]);
            int j;
            for (int i = 0; i < expresion.length()-1; i++) {
                if (Character.isLetter(expresion.charAt(i))) {
                    j = i + 1;
                    if (expresion.charAt(j) == '(') {
                        expresion = expresion.replace("" + expresion.charAt(i) + expresion.charAt(j), expresion.charAt(i) + "*" + expresion.charAt(j));
                    }else if(Character.isLetter(expresion.charAt(j))){
                        expresion=expresion.replace(""+expresion.charAt(i)+expresion.charAt(j), expresion.charAt(i)+"*"+expresion.charAt(j));
                    }
                } else if (expresion.charAt(i) == ')') {
                    j = i + 1;
                    if (Character.isLetter(expresion.charAt(i)) || expresion.charAt(i) == '!') {
                        expresion = expresion.replace("" + expresion.charAt(i) + expresion.charAt(j), expresion.charAt(i) + "*" + expresion.charAt(j));
                    }
                }
            }
            expresion = expresion.replace("(", "")
                    .replace(")", "")
                    .replace("*", "-");

            String[] maxExpressions = expresion.split("-");

            for (String maxExpresion : maxExpressions) {
                getAl().add(getExpression(maxExpresion.trim(), charVar));
            }
        

    }

    private static ArrayList<Integer> getExpression(String expresion, Character[] charVar) {
        ArrayList<Integer> array = new ArrayList<>();
        for (Character charVar1 : charVar) {
                    if (expresion.contains("" + charVar1)) {
                        try {
                            if (expresion.charAt(expresion.indexOf(charVar1) - 1) == '!') {
                                array.add(0);
                            } else {
                                array.add(1);
                            }
                        } catch (Exception e) {
                            array.add(1);
                        }
                    } else {
                        array.add(2);
                    }
                }
        return array;
    }

    

    public static void setAl(ArrayList<ArrayList<Integer>> aAl) {
        al = aAl;
    }

    public static String getExpresion() {
        return expresion;
    }
    
    


}
