package TruthTable;

import java.util.Map;

/**
 * Pure Java boolean expression evaluator to replace the Nashorn JavaScript engine
 * (which was removed in JDK 15+).
 *
 * Supports expressions using:
 *   - Variables: single uppercase letters (A-Z)
 *   - AND operator: &&
 *   - OR operator: ||
 *   - NOT operator: ! (prefix)
 *   - Parentheses: ( )
 *   - Boolean literals: true, false
 *
 * @author JDK 25 migration
 */
public class BooleanExpressionEvaluator {

    private final String expression;
    private final Map<Character, Boolean> variables;
    private int pos;

    private BooleanExpressionEvaluator(String expression, Map<Character, Boolean> variables) {
        this.expression = expression.trim();
        this.variables = variables;
        this.pos = 0;
    }

    /**
     * Evaluates a boolean expression with the given variable bindings.
     *
     * @param expression the boolean expression (using &&, ||, !, parentheses, and variable names)
     * @param variables  map of variable name (Character) to boolean value
     * @return the result of evaluating the expression
     */
    public static boolean evaluate(String expression, Map<Character, Boolean> variables) {
        BooleanExpressionEvaluator evaluator = new BooleanExpressionEvaluator(expression, variables);
        boolean result = evaluator.parseOr();
        return result;
    }

    // Grammar:
    //   or  := and ('||' and)*
    //   and := not ('&&' not)*
    //   not := '!' not | atom
    //   atom := '(' or ')' | 'true' | 'false' | VARIABLE

    private boolean parseOr() {
        boolean result = parseAnd();
        while (pos < expression.length()) {
            skipWhitespace();
            if (pos + 1 < expression.length() && expression.charAt(pos) == '|' && expression.charAt(pos + 1) == '|') {
                pos += 2;
                boolean right = parseAnd();
                result = result || right;
            } else {
                break;
            }
        }
        return result;
    }

    private boolean parseAnd() {
        boolean result = parseNot();
        while (pos < expression.length()) {
            skipWhitespace();
            if (pos + 1 < expression.length() && expression.charAt(pos) == '&' && expression.charAt(pos + 1) == '&') {
                pos += 2;
                boolean right = parseNot();
                result = result && right;
            } else {
                break;
            }
        }
        return result;
    }

    private boolean parseNot() {
        skipWhitespace();
        if (pos < expression.length() && expression.charAt(pos) == '!') {
            pos++;
            return !parseNot();
        }
        return parseAtom();
    }

    private boolean parseAtom() {
        skipWhitespace();
        if (pos >= expression.length()) {
            throw new IllegalArgumentException("Unexpected end of expression at position " + pos);
        }

        char c = expression.charAt(pos);

        if (c == '(') {
            pos++; // consume '('
            boolean result = parseOr();
            skipWhitespace();
            if (pos >= expression.length() || expression.charAt(pos) != ')') {
                throw new IllegalArgumentException("Expected ')' at position " + pos);
            }
            pos++; // consume ')'
            return result;
        }

        // Check for 'true'
        if (pos + 4 <= expression.length() && expression.substring(pos, pos + 4).equals("true")) {
            pos += 4;
            return true;
        }

        // Check for 'false'
        if (pos + 5 <= expression.length() && expression.substring(pos, pos + 5).equals("false")) {
            pos += 5;
            return false;
        }

        // Variable (single uppercase letter)
        if (Character.isLetter(c)) {
            pos++;
            Boolean value = variables.get(Character.toUpperCase(c));
            if (value == null) {
                throw new IllegalArgumentException("Unknown variable: " + c);
            }
            return value;
        }

        throw new IllegalArgumentException("Unexpected character '" + c + "' at position " + pos + " in expression: " + expression);
    }

    private void skipWhitespace() {
        while (pos < expression.length() && Character.isWhitespace(expression.charAt(pos))) {
            pos++;
        }
    }
}
