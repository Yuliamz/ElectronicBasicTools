package TruthTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreParser {
    
    public static final Pattern INVALID_CHARACTER = Pattern.compile("[^A-Z0-1()*.!'+]");
    public static final Pattern LEFT_RIGHT_PARENTESIS = Pattern.compile("[()]");
    public static final Pattern ILEGAL_START = Pattern.compile("^[*+.')]");
    public static final Pattern ILEGAL_END = Pattern.compile("[*+.!(]$");
    public static final Pattern AND_OR_OPERAND = Pattern.compile("[*+.][^A-Z0-1)!(]");
    public static final Pattern PREFIX_NOT_WITHOUT_VARIABLE = Pattern.compile("[!][^A-Z0-1!(]");
    public static final Pattern POSTFIX_NOT_WITHOUT_VARIABLE = Pattern.compile("[^A-Z0-1')][']");

    public static String parseToExpresion(String expression) {
        expression = expression.trim().toUpperCase().replaceAll(" +", "");
        PreParser.validateExpression(expression);
        expression=replaceOneZero(expression, getfirstLetter(expression),"&","|");
        expression= replaceAndOperand(expression, "&");
        expression = replaceOrOperand(expression, "|");
        expression=postToPrefixNOT(expression);
        expression=addAndOperand(expression, "&");
        expression = cleanNot(expression); 
        return expression;
    }

    public static String expresiontoString(String expression) {
        expression = expression.trim().toUpperCase().replaceAll(" +", "");
        expression=replaceAndOperand(expression, "*");
        expression=replaceOrOperand(expression, "+");
        expression=cleanNot(expression.replace("TRUE", "1").replace("FALSE", "0"));
        return expression;
    }
    
    public static char getfirstLetter(String aux){
        Matcher m = Pattern.compile("[A-Z]").matcher(aux);
        if (m.find()) return m.group().charAt(0);
        return 'A';
    }
    
    public static String cleanNot(String aux){
        while ((aux=aux.replace("!!!", "!").replace("!!", "")).contains("!!") || (aux=aux.replace("'''", "\'").replace("''", "")).contains("''")) {}
        return aux;
    }
    
    public static String replaceOneZero(String aux, char letter,String AND, String OR){
        return aux.replace("1", "("+letter+OR+"!"+letter+")")
                  .replace("0", "("+letter+AND+"!"+letter+")")
                  .replace("TRUE", "("+letter+OR+"!"+letter+")")
                  .replace("FALSE", "("+letter+AND+"!"+letter+")");
    }
    
    public static String replaceOrOperand(String aux, String replace){
        return  aux.replaceAll("[+|]", replace);
    }
    public static String replaceAndOperand(String aux, String replace){
        return  aux.replaceAll("[.*&]", replace).replace(")(", ")"+replace+"(");
    }
    
    public static String postToPrefixNOT(String aux){
        int prev;
        int parentesis;
            for (int i = 1; i < aux.length(); i++) {
               prev=i-1;
               if (aux.charAt(i) == '\'') {
                   if (Character.isLetter(aux.charAt(prev))) {
                       aux = aux.replace(aux.charAt(i - 1) + "'", "!" + aux.charAt(i - 1));
                    }else if(aux.charAt(prev)==')'){
                        parentesis=0;
                        for (int j = i; j > 0; j--) {
                            if (aux.charAt(j-1)==')') {
                                parentesis++;
                            }else if (aux.charAt(j-1)=='(') {
                                parentesis--;
                                if (parentesis==0) {
                                    aux=aux.substring(0, j-1)+"!"+aux.substring(j-1, i)+aux.substring(i+1);
                                    break;
                                }
                            }
                       }
                    }else{
                        throw  new RuntimeException("Negador sin variable");
                    }
               }    
            }
        return aux;
    }
    
    public static String addAndOperand(String aux, String replace){
        for (int i = 0; i < aux.length() - 1; i++) {
            if (Character.isLetter(aux.charAt(i))) {
                int j = i + 1;
                if (Character.isLetter(aux.charAt(j)) || aux.charAt(j) == '(' || aux.charAt(j) == '!') {
                    aux = aux.replace("" + aux.charAt(i) + aux.charAt(j), aux.charAt(i) + replace + aux.charAt(j));
                }
            } else if (aux.charAt(i) == ')') {
                int j = i + 1;
                if (Character.isLetter(aux.charAt(j)) || aux.charAt(j) == '!') {
                    aux = aux.substring(0, j) + replace + aux.substring(j);
                }
            }
        }
        return aux;
    }
    
    public static String preToPostfix(String aux){
        int count=0;
        for (int i = 0; i < aux.length(); i++) {
                int j = i + 1;
                if (aux.charAt(i) == '!') { 
                    if (aux.charAt(j) == '(') {
                        for (int k = j; k < aux.length(); k++) {
                            if (aux.charAt(k) == '(') {
                                count++;
                            } else if (aux.charAt(k) == ')') {
                                count--;
                                if (count == 0) {
                                    try {
                                        aux = aux.substring(0, i) + aux.substring(i+1, k+1) + "'" + aux.substring(k+1);
                                    } catch (Exception e) {
                                        aux = aux.substring(i+1, k+1) + "'" + aux.substring(k+1);
                                    }
                                }
                            }
                        }
                    }else if (Character.isLetterOrDigit(aux.charAt(j))) {
                        aux = aux.replace("!"+aux.charAt(i+1), aux.charAt(i+1)+"'");
                    }else{
                        throw  new RuntimeException("Prefijo negador sin variable");
                    }
                }
            }
        return aux;
    }
    
    public static void validateExpression(String aux){
        Matcher m;   
        if ((m=INVALID_CHARACTER.matcher(aux)).find()) throw new RuntimeException("Carcater invalido:  <Strong>"+m.group()+"</Strong>");
        if ((m=ILEGAL_START.matcher(aux)).find()) throw new RuntimeException("Inicio invalido de expresión:  <Strong>"+m.group()+"</Strong>");
        if ((m=ILEGAL_END.matcher(aux)).find()) throw new RuntimeException("Final invalido de expresión:  <Strong>"+m.group()+"</Strong>");
        
        verifyParentesisis(aux);
        
        if ((m=AND_OR_OPERAND.matcher(aux)).find())throw new RuntimeException("El operador <Strong>"+m.group().charAt(0)+"</Strong> esta seguido de un caracter invalido: <Strong>"+m.group().charAt(1)+"</Strong>");
        if ((m=PREFIX_NOT_WITHOUT_VARIABLE.matcher(aux)).find()) throw new RuntimeException("El prefijo negador <Strong>!</Strong> niega un caracter invalido: <Strong>"+m.group().charAt(1)+"</Strong>" );
        if ((m=POSTFIX_NOT_WITHOUT_VARIABLE.matcher(aux)).find()) throw new RuntimeException("El sufijo negador <Strong>'</Strong> niega un caracter invalido: <Strong>"+m.group().charAt(0)+"</Strong>" );
    }
    public static void verifyParentesisis(String aux){
        if (aux.contains("()")) throw new RuntimeException("Parentesis Vacios:  <Strong>()</Strong>");
        Matcher m=LEFT_RIGHT_PARENTESIS.matcher(aux);
        int parentesis=0;
        while(m.find()){
            if (m.group().equals(")")) {
                if (!aux.substring(0, m.start()-1).contains("(")) throw new RuntimeException("Parentesis derecho sin parentesis izquierdo:  <Strong>)</Strong>");
                parentesis--;
            }else{
                if (!aux.substring(m.start(), aux.length()).contains(")")) throw new RuntimeException("Parentesis izquierdo sin parentesis derecho:  <Strong>(</Strong>");
                parentesis++;
            }
        }
        if (parentesis>0) throw new RuntimeException("Parentesis izquierdo extra:  <Strong>(</Strong>");
        if (parentesis<0) throw new RuntimeException("Parentesis derecho extra:  <Strong>)</Strong>");
    }
    
    public static String getCleanExpression(String text) {
        return text.replaceAll("[<][A-Za-z/]*[>]", "").trim().replaceAll(" +", "");
    }
    
//    public static void main(String[] args) {
//        String exp = "(A''&&(((B'+G*H)))'||C|(D')(EF')')'";
//        String exp2 = "!(!A+!B)*!A*!BCD!E";
//        Matcher p = Pattern.compile("[(][A-Za-z0-1*.&|!']*[)]").matcher("(abcdz)(");
//        System.out.println(getCleanExpression("<html><body><head><Strong><br>)<br></Strong></head></body></html>"));
//        p.find();
//        System.out.println(p.group());
//        Matcher m = Pattern.compile("[*.+][^A-Za-z0-1!(\\s]").matcher("A*!B+C+D*");
//        Matcher m2 = Pattern.compile("^[*+.')]").matcher("+a+b*c+d");
//        if (m.find()) {
//            System.out.println(m.group());
//        }
//        if (m2.find()) {
//            System.out.println(m2.group());
//        }
//    }
}
