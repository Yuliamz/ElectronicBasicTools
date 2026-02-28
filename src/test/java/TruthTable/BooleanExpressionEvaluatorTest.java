package TruthTable;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BooleanExpressionEvaluator.
 */
public class BooleanExpressionEvaluatorTest {

    private Map<Character, Boolean> vars(Object... pairs) {
        Map<Character, Boolean> map = new HashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put((Character) pairs[i], (Boolean) pairs[i + 1]);
        }
        return map;
    }

    // ---- Simple literals ----

    @Test
    void evaluate_trueKeyword_returnsTrue() {
        assertTrue(BooleanExpressionEvaluator.evaluate("true", new HashMap<>()));
    }

    @Test
    void evaluate_falseKeyword_returnsFalse() {
        assertFalse(BooleanExpressionEvaluator.evaluate("false", new HashMap<>()));
    }

    // ---- Single variable ----

    @Test
    void evaluate_singleVarTrue() {
        assertTrue(BooleanExpressionEvaluator.evaluate("A", vars('A', true)));
    }

    @Test
    void evaluate_singleVarFalse() {
        assertFalse(BooleanExpressionEvaluator.evaluate("A", vars('A', false)));
    }

    // ---- NOT operator ----

    @Test
    void evaluate_notTrue_returnsFalse() {
        assertFalse(BooleanExpressionEvaluator.evaluate("!A", vars('A', true)));
    }

    @Test
    void evaluate_notFalse_returnsTrue() {
        assertTrue(BooleanExpressionEvaluator.evaluate("!A", vars('A', false)));
    }

    @Test
    void evaluate_doubleNot_returnsOriginal() {
        assertTrue(BooleanExpressionEvaluator.evaluate("!!A", vars('A', true)));
        assertFalse(BooleanExpressionEvaluator.evaluate("!!A", vars('A', false)));
    }

    // ---- AND operator ----

    @Test
    void evaluate_andTrueTrue_returnsTrue() {
        assertTrue(BooleanExpressionEvaluator.evaluate("A && B", vars('A', true, 'B', true)));
    }

    @Test
    void evaluate_andTrueFalse_returnsFalse() {
        assertFalse(BooleanExpressionEvaluator.evaluate("A && B", vars('A', true, 'B', false)));
    }

    @Test
    void evaluate_andFalseFalse_returnsFalse() {
        assertFalse(BooleanExpressionEvaluator.evaluate("A && B", vars('A', false, 'B', false)));
    }

    // ---- OR operator ----

    @Test
    void evaluate_orTrueTrue_returnsTrue() {
        assertTrue(BooleanExpressionEvaluator.evaluate("A || B", vars('A', true, 'B', true)));
    }

    @Test
    void evaluate_orTrueFalse_returnsTrue() {
        assertTrue(BooleanExpressionEvaluator.evaluate("A || B", vars('A', true, 'B', false)));
    }

    @Test
    void evaluate_orFalseFalse_returnsFalse() {
        assertFalse(BooleanExpressionEvaluator.evaluate("A || B", vars('A', false, 'B', false)));
    }

    // ---- Parentheses ----

    @Test
    void evaluate_parenthesizedOr_withAnd() {
        // (A || B) && C
        assertTrue(BooleanExpressionEvaluator.evaluate("(A || B) && C",
                vars('A', true, 'B', false, 'C', true)));
        assertFalse(BooleanExpressionEvaluator.evaluate("(A || B) && C",
                vars('A', false, 'B', false, 'C', true)));
    }

    @Test
    void evaluate_nestedParentheses() {
        // ((A && B) || C)
        assertTrue(BooleanExpressionEvaluator.evaluate("((A && B) || C)",
                vars('A', false, 'B', false, 'C', true)));
        assertFalse(BooleanExpressionEvaluator.evaluate("((A && B) || C)",
                vars('A', false, 'B', false, 'C', false)));
    }

    // ---- Complex expressions ----

    @Test
    void evaluate_complexExpression_xorEquivalent() {
        // A XOR B = (A && !B) || (!A && B)
        String xor = "(A && !B) || (!A && B)";
        assertFalse(BooleanExpressionEvaluator.evaluate(xor, vars('A', false, 'B', false)));
        assertTrue(BooleanExpressionEvaluator.evaluate(xor, vars('A', false, 'B', true)));
        assertTrue(BooleanExpressionEvaluator.evaluate(xor, vars('A', true, 'B', false)));
        assertFalse(BooleanExpressionEvaluator.evaluate(xor, vars('A', true, 'B', true)));
    }

    @Test
    void evaluate_notWithParentheses() {
        // !(A && B)
        assertFalse(BooleanExpressionEvaluator.evaluate("!(A && B)", vars('A', true, 'B', true)));
        assertTrue(BooleanExpressionEvaluator.evaluate("!(A && B)", vars('A', true, 'B', false)));
    }

    // ---- Error cases ----

    @Test
    void evaluate_unknownVariable_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> BooleanExpressionEvaluator.evaluate("Z", vars('A', true)));
    }

    @Test
    void evaluate_missingClosingParen_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> BooleanExpressionEvaluator.evaluate("(A && B", vars('A', true, 'B', true)));
    }

    @Test
    void evaluate_unexpectedCharacter_throwsException() {
        // An expression starting with an unexpected character throws
        assertThrows(IllegalArgumentException.class,
                () -> BooleanExpressionEvaluator.evaluate("@A", vars('A', true)));
    }

    @Test
    void evaluate_emptyExpression_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> BooleanExpressionEvaluator.evaluate("", new HashMap<>()));
    }

    // ---- Whitespace handling ----

    @Test
    void evaluate_withLeadingTrailingSpaces() {
        assertTrue(BooleanExpressionEvaluator.evaluate("  A  ", vars('A', true)));
    }

    @Test
    void evaluate_withSpacesAroundOperators() {
        assertTrue(BooleanExpressionEvaluator.evaluate("A  ||  B", vars('A', false, 'B', true)));
    }

    // ---- Case insensitivity for variables ----

    @Test
    void evaluate_lowercaseVariable_treatedAsUppercase() {
        // The evaluator calls Character.toUpperCase(c) for variables
        assertTrue(BooleanExpressionEvaluator.evaluate("a", vars('A', true)));
    }
}
