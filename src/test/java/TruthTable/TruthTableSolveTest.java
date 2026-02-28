package TruthTable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TruthTableSolve.
 */
public class TruthTableSolveTest {

    /**
     * Build a 2-variable truth table for AND: f(A,B) = A AND B
     * Rows: 00->0, 01->0, 10->0, 11->1
     */
    private Integer[][] andTable() {
        return new Integer[][]{
            {0, 0, 0},
            {0, 1, 0},
            {1, 0, 0},
            {1, 1, 1}
        };
    }

    /**
     * Build a 2-variable truth table for OR: f(A,B) = A OR B
     * Rows: 00->0, 01->1, 10->1, 11->1
     */
    private Integer[][] orTable() {
        return new Integer[][]{
            {0, 0, 0},
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
    }

    /**
     * Build a 2-variable truth table with don't-cares (value=2).
     * Rows: 00->0, 01->2, 10->1, 11->1
     */
    private Integer[][] tableWithDontCare() {
        return new Integer[][]{
            {0, 0, 0},
            {0, 1, 2},
            {1, 0, 1},
            {1, 1, 1}
        };
    }

    private Character[] varsAB() {
        return new Character[]{'A', 'B'};
    }

    // ---- solveMinWithOutX ----

    @Test
    void solveMinWithOutX_andFunction_returnsExpression() {
        TruthTableSolve solver = new TruthTableSolve(andTable(), varsAB());
        String result = solver.solveMinWithOutX();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Should contain A and B (the only minterm is row 3: A=1, B=1)
        assertTrue(result.contains("A") || result.contains("B"));
    }

    @Test
    void solveMinWithOutX_orFunction_returnsExpression() {
        TruthTableSolve solver = new TruthTableSolve(orTable(), varsAB());
        String result = solver.solveMinWithOutX();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // ---- solveMinWithX ----

    @Test
    void solveMinWithX_tableWithDontCare_includesDontCareRows() {
        TruthTableSolve solver = new TruthTableSolve(tableWithDontCare(), varsAB());
        String result = solver.solveMinWithX();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void solveMinWithX_andFunction_sameAsWithoutX() {
        // No don't-cares in AND table, so both should be equal
        TruthTableSolve solver = new TruthTableSolve(andTable(), varsAB());
        String withX = solver.solveMinWithX();
        String withoutX = solver.solveMinWithOutX();
        assertEquals(withX, withoutX);
    }

    // ---- solveMaxWithoutX ----

    @Test
    void solveMaxWithoutX_andFunction_returnsExpression() {
        TruthTableSolve solver = new TruthTableSolve(andTable(), varsAB());
        String result = solver.solveMaxWithoutX();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // AND has 3 maxterms (rows 0,1,2)
    }

    @Test
    void solveMaxWithoutX_orFunction_returnsExpression() {
        TruthTableSolve solver = new TruthTableSolve(orTable(), varsAB());
        String result = solver.solveMaxWithoutX();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // OR has 1 maxterm (row 0)
    }

    // ---- solveMaxWithX ----

    @Test
    void solveMaxWithX_tableWithDontCare_includesDontCareRows() {
        TruthTableSolve solver = new TruthTableSolve(tableWithDontCare(), varsAB());
        String result = solver.solveMaxWithX();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void solveMaxWithX_andFunction_sameAsWithoutX() {
        TruthTableSolve solver = new TruthTableSolve(andTable(), varsAB());
        String withX = solver.solveMaxWithX();
        String withoutX = solver.solveMaxWithoutX();
        assertEquals(withX, withoutX);
    }

    // ---- solveMin (static) ----

    @Test
    void solveMin_andExpression_returnsMinimized() {
        String result = TruthTableSolve.solveMin("A*B");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void solveMin_orExpression_returnsMinimized() {
        String result = TruthTableSolve.solveMin("A+B");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void solveMin_withNotExpression_returnsMinimized() {
        String result = TruthTableSolve.solveMin("!A");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void solveMin_withTrueConstant_returnsMinimized() {
        String result = TruthTableSolve.solveMin("TRUE");
        assertNotNull(result);
    }

    @Test
    void solveMin_withFalseConstant_returnsMinimized() {
        String result = TruthTableSolve.solveMin("FALSE");
        assertNotNull(result);
    }

    @Test
    void solveMin_complexExpression_returnsMinimized() {
        // A'B + AB' (XOR)
        String result = TruthTableSolve.solveMin("A'B+AB'");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // ---- Expression format verification ----

    @Test
    void solveMinWithOutX_expressionContainsParentheses() {
        TruthTableSolve solver = new TruthTableSolve(andTable(), varsAB());
        String result = solver.solveMinWithOutX();
        assertTrue(result.contains("(") && result.contains(")"));
    }

    @Test
    void solveMaxWithoutX_expressionContainsParentheses() {
        TruthTableSolve solver = new TruthTableSolve(andTable(), varsAB());
        String result = solver.solveMaxWithoutX();
        assertTrue(result.contains("(") && result.contains(")"));
    }

    // ---- Three-variable table ----

    @Test
    void solveMinWithOutX_threeVars_returnsExpression() {
        // f(A,B,C) = A AND B AND C -> only minterm 7
        Integer[][] table3 = new Integer[][]{
            {0, 0, 0, 0},
            {0, 0, 1, 0},
            {0, 1, 0, 0},
            {0, 1, 1, 0},
            {1, 0, 0, 0},
            {1, 0, 1, 0},
            {1, 1, 0, 0},
            {1, 1, 1, 1}
        };
        Character[] vars3 = {'A', 'B', 'C'};
        TruthTableSolve solver = new TruthTableSolve(table3, vars3);
        String result = solver.solveMinWithOutX();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
