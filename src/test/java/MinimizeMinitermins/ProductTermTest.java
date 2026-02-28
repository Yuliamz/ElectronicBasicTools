package MinimizeMinitermins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ProductTerm.
 */
public class ProductTermTest {

    private static final char[] VARS_AB = {'a', 'b'};
    private static final char[] VARS_ABC = {'a', 'b', 'c'};
    private static final char[] VARS_EMPTY = {};

    // ---- Constructor & accessors ----

    @Test
    void constructor_setsValueAndMask() {
        ProductTerm pt = new ProductTerm(3, 3, VARS_AB);
        assertEquals(3, pt.getValue());
        assertEquals(3, pt.getMask());
    }

    @Test
    void constructor_setsVariableNames() {
        ProductTerm pt = new ProductTerm(0, 3, VARS_AB);
        assertArrayEquals(VARS_AB, pt.getVariableNames());
    }

    @Test
    void isMinterm_whenAllVarsUsed_returnsTrue() {
        // mask = 0b11 = 3 (both bits set), numVars = 2 -> minterm
        ProductTerm pt = new ProductTerm(3, 3, VARS_AB);
        assertTrue(pt.isMinterm());
    }

    @Test
    void isMinterm_whenNotAllVarsUsed_returnsFalse() {
        // mask = 0b01 = 1 (only 1 bit set), numVars = 2 -> not a minterm
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB);
        assertFalse(pt.isMinterm());
    }

    @Test
    void identity_hasZeroMask() {
        assertEquals(0, ProductTerm.identity.getMask());
        assertEquals(0, ProductTerm.identity.getValue());
    }

    // ---- coverCount ----

    @Test
    void coverCount_initiallyZero() {
        ProductTerm pt = new ProductTerm(1, 3, VARS_AB);
        assertEquals(0, pt.getCoverCount());
    }

    @Test
    void incrementCoverCount_increments() {
        ProductTerm pt = new ProductTerm(1, 3, VARS_AB);
        pt.incrementCoverCount();
        assertEquals(1, pt.getCoverCount());
        pt.incrementCoverCount();
        assertEquals(2, pt.getCoverCount());
    }

    @Test
    void clearCoverCount_resetsToZero() {
        ProductTerm pt = new ProductTerm(1, 3, VARS_AB);
        pt.incrementCoverCount();
        pt.incrementCoverCount();
        pt.clearCoverCount();
        assertEquals(0, pt.getCoverCount());
    }

    // ---- compareTo ----

    @Test
    void compareTo_moreVarsIsSmaller() {
        // compareTo returns x.numLiterals - this.numLiterals
        // so a term with more literals is "smaller" (comes first after sort)
        ProductTerm pt2 = new ProductTerm(3, 3, VARS_AB);   // 2 literals
        ProductTerm pt1 = new ProductTerm(1, 1, VARS_AB);   // 1 literal
        // pt2.compareTo(pt1): pt1.numLiterals - pt2.numLiterals = 1 - 2 = -1 < 0
        assertTrue(pt2.compareTo(pt1) < 0);
    }

    @Test
    void compareTo_equalLiterals_returnsZero() {
        ProductTerm pt1 = new ProductTerm(1, 3, VARS_AB);
        ProductTerm pt2 = new ProductTerm(2, 3, VARS_AB);
        assertEquals(0, pt1.compareTo(pt2));
    }

    // ---- equals ----

    @Test
    void equals_sameValueAndMask_returnsTrue() {
        ProductTerm pt1 = new ProductTerm(5, 7, VARS_ABC);
        ProductTerm pt2 = new ProductTerm(5, 7, VARS_ABC);
        assertTrue(pt1.equals(pt2));
    }

    @Test
    void equals_differentValue_returnsFalse() {
        ProductTerm pt1 = new ProductTerm(5, 7, VARS_ABC);
        ProductTerm pt2 = new ProductTerm(3, 7, VARS_ABC);
        assertFalse(pt1.equals(pt2));
    }

    @Test
    void equals_differentMask_returnsFalse() {
        ProductTerm pt1 = new ProductTerm(5, 7, VARS_ABC);
        ProductTerm pt2 = new ProductTerm(5, 3, VARS_ABC);
        assertFalse(pt1.equals(pt2));
    }

    // ---- covers ----

    @Test
    void covers_mintermCoveredByItself() {
        ProductTerm pt = new ProductTerm(3, 3, VARS_AB);
        assertTrue(pt.covers(pt));
    }

    @Test
    void covers_reducedTermCoversMinterm() {
        // mask=0b01 means only bit 0 is relevant; value=0b01 means bit 0 = 1
        // This covers any minterm where bit 0 = 1: e.g. 0b01=1 and 0b11=3
        ProductTerm reduced = new ProductTerm(1, 1, VARS_AB);
        ProductTerm mt1 = new ProductTerm(1, 3, VARS_AB); // a=0,b=1
        ProductTerm mt3 = new ProductTerm(3, 3, VARS_AB); // a=1,b=1
        ProductTerm mt0 = new ProductTerm(0, 3, VARS_AB); // a=0,b=0
        assertTrue(reduced.covers(mt1));
        assertTrue(reduced.covers(mt3));
        assertFalse(reduced.covers(mt0));
    }

    // ---- reduces ----

    @Test
    void reduces_differentMasks_returnsNull() {
        ProductTerm pt1 = new ProductTerm(1, 1, VARS_AB);
        ProductTerm pt2 = new ProductTerm(3, 3, VARS_AB);
        assertNull(pt1.reduces(pt2));
    }

    @Test
    void reduces_sameMaskSameBits_returnsNull() {
        // Same value -> difference = 0 -> countBits(0) = 0 != 1
        ProductTerm pt1 = new ProductTerm(3, 3, VARS_AB);
        ProductTerm pt2 = new ProductTerm(3, 3, VARS_AB);
        assertNull(pt1.reduces(pt2));
    }

    @Test
    void reduces_oneBitDifference_returnsReducedTerm() {
        // 0b01 and 0b11 differ in bit 1 only -> reduces to mask=0b01, value=0b01
        ProductTerm pt1 = new ProductTerm(1, 3, VARS_AB); // ab=01
        ProductTerm pt2 = new ProductTerm(3, 3, VARS_AB); // ab=11
        ProductTerm reduced = pt1.reduces(pt2);
        assertNotNull(reduced);
        // The reduced term should have mask with bit 1 cleared
        assertEquals(1, reduced.getMask()); // only bit 0 remains
    }

    @Test
    void reduces_twoBitDifference_returnsNull() {
        // 0b00 and 0b11 differ in 2 bits -> cannot reduce
        ProductTerm pt1 = new ProductTerm(0, 3, VARS_AB);
        ProductTerm pt2 = new ProductTerm(3, 3, VARS_AB);
        assertNull(pt1.reduces(pt2));
    }

    @Test
    void reduces_zeroMask_returnsIdentity() {
        // Both have mask=0 -> returns identity
        ProductTerm pt1 = new ProductTerm(0, 0, VARS_EMPTY);
        ProductTerm pt2 = new ProductTerm(0, 0, VARS_EMPTY);
        ProductTerm result = pt1.reduces(pt2);
        assertNotNull(result);
        assertEquals(ProductTerm.identity, result);
    }

    // ---- toString / ptString ----

    @Test
    void toString_identity_returnsOne() {
        assertEquals("1", ProductTerm.identity.toString());
    }

    @Test
    void toString_minterm_returnsVariableString() {
        // value=0b11=3, mask=0b11=3, vars={'a','b'}
        // bit 0 set in value -> b (index 0 from right = var[1])
        // bit 1 set in value -> a (index 1 from right = var[0])
        ProductTerm pt = new ProductTerm(3, 3, VARS_AB);
        String s = pt.toString();
        assertNotNull(s);
        assertFalse(s.isEmpty());
    }

    @Test
    void toString_negatedVariable_containsExclamation() {
        // value=0b00=0, mask=0b11=3, vars={'a','b'} -> !a!b
        ProductTerm pt = new ProductTerm(0, 3, VARS_AB);
        String s = pt.toString();
        assertTrue(s.contains("!"));
    }

    @Test
    void getNumLiterals_matchesMaskBitCount() {
        ProductTerm pt = new ProductTerm(5, 7, VARS_ABC); // mask=0b111 -> 3 literals
        assertEquals(3, pt.getNumLiterals());
    }
}
