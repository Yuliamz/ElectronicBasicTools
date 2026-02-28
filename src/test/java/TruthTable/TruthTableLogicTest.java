package TruthTable;

import org.junit.jupiter.api.Test;
import javax.script.ScriptException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TruthTable (logic package).
 */
public class TruthTableLogicTest {

    // ---- getVariables ----

    @Test
    void getVariables_simpleExpression_returnsCorrectVars() {
        Set<Character> vars = TruthTable.getVariables("A+B");
        assertTrue(vars.contains('A'));
        assertTrue(vars.contains('B'));
        assertEquals(2, vars.size());
    }

    @Test
    void getVariables_constantOne_returnsA() {
        Set<Character> vars = TruthTable.getVariables("1");
        assertTrue(vars.contains('A'));
        assertEquals(1, vars.size());
    }

    @Test
    void getVariables_constantZero_returnsA() {
        Set<Character> vars = TruthTable.getVariables("0");
        assertTrue(vars.contains('A'));
        assertEquals(1, vars.size());
    }

    @Test
    void getVariables_singleVar_returnsOneVar() {
        Set<Character> vars = TruthTable.getVariables("C");
        assertTrue(vars.contains('C'));
        assertEquals(1, vars.size());
    }

    // ---- setVarNames ----

    @Test
    void setVarNames_forFourRows_returnsTwoVars() {
        Character[] vars = TruthTable.setVarNames(4);
        assertEquals(2, vars.length);
        assertEquals('A', vars[0]);
        assertEquals('B', vars[1]);
    }

    @Test
    void setVarNames_forEightRows_returnsThreeVars() {
        Character[] vars = TruthTable.setVarNames(8);
        assertEquals(3, vars.length);
    }

    // ---- getTable(ArrayList) ----

    @Test
    void getTable_fromFuncValues_correctDimensions() {
        ArrayList<Integer> funcValues = new ArrayList<>(Arrays.asList(0, 1, 1, 0));
        Integer[][] table = TruthTable.getTable(funcValues);
        assertEquals(4, table.length);
        assertEquals(3, table[0].length); // 2 vars + 1 output
    }

    @Test
    void getTable_fromFuncValues_correctValues() {
        ArrayList<Integer> funcValues = new ArrayList<>(Arrays.asList(0, 1, 1, 0));
        Integer[][] table = TruthTable.getTable(funcValues);
        // Row 0: 00 -> 0
        assertEquals(0, table[0][0]);
        assertEquals(0, table[0][1]);
        assertEquals(0, table[0][2]);
        // Row 1: 01 -> 1
        assertEquals(0, table[1][0]);
        assertEquals(1, table[1][1]);
        assertEquals(1, table[1][2]);
    }

    // ---- getTable(String) ----

    @Test
    void getTable_fromExpression_andFunction() throws ScriptException {
        String expr = PreParser.parseToExpresion("A*B");
        Set<Character> vars = TruthTable.getVariables(expr);
        Integer[][] table = TruthTable.getTable(expr);
        assertEquals((int) Math.pow(2, vars.size()), table.length);
    }

    @Test
    void getTable_fromExpression_orFunction() throws ScriptException {
        String expr = PreParser.parseToExpresion("A+B");
        Integer[][] table = TruthTable.getTable(expr);
        assertEquals(4, table.length);
    }

    // ---- evaluate ----

    @Test
    void evaluate_andExpression_correctResults() throws ScriptException {
        String expr = PreParser.parseToExpresion("A*B");
        Set<Character> vars = TruthTable.getVariables(expr);
        ArrayList<Boolean> row00 = new ArrayList<>(Arrays.asList(false, false));
        ArrayList<Boolean> row11 = new ArrayList<>(Arrays.asList(true, true));
        assertFalse(TruthTable.evaluate(expr, row00, vars));
        assertTrue(TruthTable.evaluate(expr, row11, vars));
    }

    // ---- getStringVar(Set) ----

    @Test
    void getStringVar_fromSet_returnsCorrectSize() {
        Set<Character> vars = new LinkedHashSet<>(Arrays.asList('A', 'B'));
        String[] result = TruthTable.getStringVar(vars);
        assertEquals(2, result.length);
    }

    // ---- getStringVar(String) ----

    @Test
    void getStringVar_fromExpression_includesSalidaAtEnd() {
        String[] result = TruthTable.getStringVar("A+B");
        // Last element should be "Salida"
        assertEquals("Salida", result[result.length - 1]);
    }

    @Test
    void getStringVar_fromConstantOne_returnsAAndSalida() {
        String[] result = TruthTable.getStringVar("1");
        assertEquals("Salida", result[result.length - 1]);
    }

    @Test
    void getStringVar_fromConstantZero_returnsAAndSalida() {
        String[] result = TruthTable.getStringVar("0");
        assertEquals("Salida", result[result.length - 1]);
    }

    // ---- parseExpression ----

    @Test
    void parseExpression_simpleAnd_convertsToDoubleAmpersand() {
        String result = TruthTable.parseExpression("A*B");
        assertTrue(result.contains("&&"));
    }

    @Test
    void parseExpression_simpleOr_convertsToDoublePipe() {
        String result = TruthTable.parseExpression("A+B");
        assertTrue(result.contains("||"));
    }

    @Test
    void parseExpression_withNot_convertsToPrefix() {
        String result = TruthTable.parseExpression("A'");
        assertTrue(result.contains("!"));
    }

    @Test
    void parseExpression_implicitAnd_insertsDoubleAmpersand() {
        String result = TruthTable.parseExpression("AB");
        assertTrue(result.contains("&&"));
    }

    @Test
    void parseExpression_withParentheses_handlesCorrectly() {
        String result = TruthTable.parseExpression("(A+B)*C");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
