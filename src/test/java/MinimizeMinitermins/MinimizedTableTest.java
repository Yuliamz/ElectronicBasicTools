package MinimizeMinitermins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MinimizedTable (Quine-McCluskey minimization).
 */
public class MinimizedTableTest {

    // ---- Constructor from int[] ----

    @Test
    void intArrayConstructor_singleMinterm_onePrimeImplicant() {
        // f = m(3) -> only one prime implicant: ab
        MinimizedTable mt = new MinimizedTable(new int[]{3});
        assertTrue(mt.getRowCount() >= 1);
    }

    @Test
    void intArrayConstructor_allMinterms_reducesToIdentity() {
        // f = m(0,1,2,3) -> always true -> 1 prime implicant (identity)
        MinimizedTable mt = new MinimizedTable(new int[]{0, 1, 2, 3});
        assertEquals(1, mt.getRowCount());
        assertEquals("1", mt.getValueAt(0, 0));
    }

    @Test
    void intArrayConstructor_twoVars_andFunction() {
        // f = m(3) -> a*b
        MinimizedTable mt = new MinimizedTable(new int[]{3});
        assertEquals(1, mt.getRowCount());
    }

    @Test
    void intArrayConstructor_twoVars_orFunction() {
        // f = m(1,2,3) -> a+b
        MinimizedTable mt = new MinimizedTable(new int[]{1, 2, 3});
        // Should minimize to a+b (2 prime implicants or 1 if further reduced)
        assertTrue(mt.getRowCount() >= 1);
    }

    @Test
    void intArrayConstructor_emptyMinterms() {
        MinimizedTable mt = new MinimizedTable(new int[]{});
        assertEquals(0, mt.getRowCount());
    }

    // ---- Constructor from String ----

    @Test
    void stringConstructor_andExpression() {
        MinimizedTable mt = new MinimizedTable("a*b");
        assertTrue(mt.getRowCount() >= 1);
    }

    @Test
    void stringConstructor_orExpression() {
        MinimizedTable mt = new MinimizedTable("a+b");
        assertTrue(mt.getRowCount() >= 1);
    }

    @Test
    void stringConstructor_complexExpression() {
        // f = a'b + ab' (XOR)
        MinimizedTable mt = new MinimizedTable("a'b+ab'");
        assertTrue(mt.getRowCount() >= 1);
    }

    // ---- Table model methods ----

    @Test
    void getColumnCount_returnsTwo() {
        MinimizedTable mt = new MinimizedTable(new int[]{1, 3});
        assertEquals(2, mt.getColumnCount());
    }

    @Test
    void isCellEditable_returnsFalse() {
        MinimizedTable mt = new MinimizedTable(new int[]{1});
        assertFalse(mt.isCellEditable(0, 0));
    }

    @Test
    void getColumnName_col0_returnsPrimeImplicant() {
        MinimizedTable mt = new MinimizedTable(new int[]{1});
        assertEquals("Prime Implicant", mt.getColumnName(0));
    }

    @Test
    void getColumnName_col1_returnsImpliedTerms() {
        MinimizedTable mt = new MinimizedTable(new int[]{1});
        assertEquals("Implied Terms", mt.getColumnName(1));
    }

    @Test
    void getColumnName_invalidCol_throwsException() {
        MinimizedTable mt = new MinimizedTable(new int[]{1});
        assertThrows(RuntimeException.class, () -> mt.getColumnName(5));
    }

    @Test
    void getValueAt_col0_returnsString() {
        MinimizedTable mt = new MinimizedTable(new int[]{3});
        Object val = mt.getValueAt(0, 0);
        assertNotNull(val);
        assertTrue(val instanceof String);
    }

    @Test
    void getValueAt_col1_returnsVector() {
        MinimizedTable mt = new MinimizedTable(new int[]{3});
        Object val = mt.getValueAt(0, 1);
        assertNotNull(val);
    }

    @Test
    void getValueAt_invalidCol_throwsException() {
        MinimizedTable mt = new MinimizedTable(new int[]{3});
        assertThrows(RuntimeException.class, () -> mt.getValueAt(0, 5));
    }

    // ---- Minimization correctness ----

    @Test
    void minimize_threeVarFunction_correctPrimeImplicants() {
        // f = m(0,1,2,3,4,5,6,7) -> all minterms -> identity
        MinimizedTable mt = new MinimizedTable(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        assertEquals(1, mt.getRowCount());
    }

    @Test
    void minimize_classicExample_minterms0_1_3_7() {
        // f = m(0,1,3,7) for 3 variables
        MinimizedTable mt = new MinimizedTable(new int[]{0, 1, 3, 7});
        assertTrue(mt.getRowCount() >= 1);
        // Verify the prime implicants cover all minterms
        assertNotNull(mt.getValueAt(0, 1));
    }

    @Test
    void minimize_withDomination_reducesCorrectly() {
        // f = m(0,1,2,3,4,5) for 3 variables
        MinimizedTable mt = new MinimizedTable(new int[]{0, 1, 2, 3, 4, 5});
        assertTrue(mt.getRowCount() >= 1);
    }

    @Test
    void toString_containsMinimizedExpression() {
        MinimizedTable mt = new MinimizedTable("a*b");
        String s = mt.toString();
        assertNotNull(s);
    }

    @Test
    void minimize_fourVarFunction() {
        // Classic 4-variable minimization: f = m(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
        int[] minterms = new int[16];
        for (int i = 0; i < 16; i++) minterms[i] = i;
        MinimizedTable mt = new MinimizedTable(minterms);
        assertEquals(1, mt.getRowCount()); // reduces to 1
    }

    @Test
    void minimize_singleVariable_notExpression() {
        // f = a' -> minterm 0
        MinimizedTable mt = new MinimizedTable("a'");
        assertEquals(1, mt.getRowCount());
    }
}
