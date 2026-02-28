package karnaughMap;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for KarnaughMap.
 */
public class KarnaughMapTest {

    /**
     * Build a 2-variable truth table for AND: f(A,B) = A AND B
     * Rows: 00->0, 01->0, 10->0, 11->1
     */
    private Integer[][] andTable2Var() {
        return new Integer[][]{
            {0, 0, 0},
            {0, 1, 0},
            {1, 0, 0},
            {1, 1, 1}
        };
    }

    /**
     * Build a 3-variable truth table for OR: f(A,B,C) = A OR B OR C
     */
    private Integer[][] orTable3Var() {
        return new Integer[][]{
            {0, 0, 0, 0},
            {0, 0, 1, 1},
            {0, 1, 0, 1},
            {0, 1, 1, 1},
            {1, 0, 0, 1},
            {1, 0, 1, 1},
            {1, 1, 0, 1},
            {1, 1, 1, 1}
        };
    }

    /**
     * Build a 4-variable truth table (all zeros).
     */
    private Integer[][] zeroTable4Var() {
        Integer[][] table = new Integer[16][5];
        for (int i = 0; i < 16; i++) {
            int x = i;
            for (int j = 3; j >= 0; j--) {
                table[i][j] = x % 2;
                x /= 2;
            }
            table[i][4] = 0;
        }
        return table;
    }

    private Set<Character> vars(Character... chars) {
        Set<Character> set = new LinkedHashSet<>();
        Collections.addAll(set, chars);
        return set;
    }

    // ---- Constructor ----

    @Test
    void constructor_twoVars_createsKMap() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertNotNull(km.KMap);
    }

    @Test
    void constructor_threeVars_createsKMap() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B', 'C'), orTable3Var());
        assertNotNull(km.KMap);
    }

    // ---- setVariables ----

    @Test
    void setVariables_evenCount_splitEvenly() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertEquals(1, km.variableUP.size());
        assertEquals(1, km.variableLEFT.size());
    }

    @Test
    void setVariables_oddCount_upGetsExtra() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B', 'C'), orTable3Var());
        // 3 vars: UP gets ceil(3/2)=2, LEFT gets 1
        assertEquals(2, km.variableUP.size());
        assertEquals(1, km.variableLEFT.size());
    }

    // ---- fillGrayUP / fillGrayLEFT ----

    @Test
    void fillGrayUP_twoVars_hasTwoValues() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        // 1 UP variable -> 2^1 = 2 values
        assertEquals(2, km.valuesUP.size());
    }

    @Test
    void fillGrayLEFT_twoVars_hasTwoValues() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        // 1 LEFT variable -> 2^1 = 2 values
        assertEquals(2, km.valuesLEFT.size());
    }

    @Test
    void fillGrayUP_threeVars_hasFourValues() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B', 'C'), orTable3Var());
        // 2 UP variables -> 2^2 = 4 values
        assertEquals(4, km.valuesUP.size());
    }

    // ---- matrixToMap ----

    @Test
    void matrixToMap_twoVars_correctEntries() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        // Row 00 -> 0
        assertEquals("0", km.mapTruthTable.get("00"));
        // Row 11 -> 1
        assertEquals("1", km.mapTruthTable.get("11"));
    }

    // ---- fillKMAP ----

    @Test
    void fillKMAP_topLeftIsSpace() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertEquals("  ", km.KMap[0][0]);
    }

    @Test
    void fillKMAP_firstRowHasGrayCodes() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        // First row (index 0) should have gray codes for UP variables
        assertNotNull(km.KMap[0][1]);
        assertNotNull(km.KMap[0][2]);
    }

    // ---- generateKMap ----

    @Test
    void generateKMap_twoVars_correctValues() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        // The KMap should have values from the truth table
        boolean foundOne = false;
        for (int i = 1; i < km.KMap.length; i++) {
            for (int j = 1; j < km.KMap[0].length; j++) {
                if ("1".equals(km.KMap[i][j])) foundOne = true;
            }
        }
        assertTrue(foundOne, "KMap should contain at least one '1' for AND function");
    }

    // ---- getKMap ----

    @Test
    void getKMap_returnsMapWithoutHeaderRow() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        String[][] map = km.getKMap();
        // Should have KMap.length - 1 rows
        assertEquals(km.KMap.length - 1, map.length);
    }

    // ---- getColumNames ----

    @Test
    void getColumNames_returnsFirstRow() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        String[] colNames = km.getColumNames();
        assertEquals(km.KMap[0].length, colNames.length);
        assertEquals("  ", colNames[0]);
    }

    // ---- getVarUP ----

    @Test
    void getVarUP_returnsNonEmpty() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        String varUp = km.getVarUP();
        assertNotNull(varUp);
        assertFalse(varUp.isEmpty());
    }

    // ---- getVarLEFT ----

    @Test
    void getVarLEFT_returnsNonEmpty() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        String varLeft = km.getVarLEFT();
        assertNotNull(varLeft);
        assertFalse(varLeft.isEmpty());
    }

    // ---- getVariableUP / getVariableLEFT ----

    @Test
    void getVariableUP_returnsCorrectList() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertNotNull(km.getVariableUP());
        assertEquals(1, km.getVariableUP().size());
    }

    @Test
    void getVariableLEFT_returnsCorrectList() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertNotNull(km.getVariableLEFT());
        assertEquals(1, km.getVariableLEFT().size());
    }

    // ---- showMap / showUP / showLEFT / show (smoke tests) ----

    @Test
    void showMap_doesNotThrow() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertDoesNotThrow(km::showMap);
    }

    @Test
    void showUP_doesNotThrow() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertDoesNotThrow(km::showUP);
    }

    @Test
    void showLEFT_doesNotThrow() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertDoesNotThrow(km::showLEFT);
    }

    @Test
    void show_doesNotThrow() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B'), andTable2Var());
        assertDoesNotThrow(km::show);
    }

    // ---- 4-variable KMap ----

    @Test
    void constructor_fourVars_createsKMap() {
        KarnaughMap km = new KarnaughMap(vars('A', 'B', 'C', 'D'), zeroTable4Var());
        assertNotNull(km.KMap);
        // 2 UP vars -> 4 columns + 1 header = 5
        // 2 LEFT vars -> 4 rows + 1 header = 5
        assertEquals(5, km.KMap.length);
        assertEquals(5, km.KMap[0].length);
    }
}
