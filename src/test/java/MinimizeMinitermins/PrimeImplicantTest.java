package MinimizeMinitermins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PrimeImplicant.
 */
public class PrimeImplicantTest {

    private static final char[] VARS_AB = {'a', 'b'};
    private static final char[] VARS_ABC = {'a', 'b', 'c'};

    /**
     * Helper: create a minterm (all bits in mask set).
     */
    private ProductTerm minterm(int value, char[] vars) {
        int mask = (1 << vars.length) - 1;
        return new ProductTerm(value, mask, vars);
    }

    @Test
    void constructor_coversMintermsCorrectly() {
        // mask=0b01 -> covers minterms where bit 0 = 1: values 1 and 3
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB);
        ProductTerm[] minterms = {
            minterm(0, VARS_AB),
            minterm(1, VARS_AB),
            minterm(2, VARS_AB),
            minterm(3, VARS_AB)
        };
        PrimeImplicant pi = new PrimeImplicant(pt, minterms);
        assertEquals(2, pi.getCoverCount()); // covers 1 and 3
    }

    @Test
    void constructor_noMintermsCovered_throwsException() {
        // mask=0b11, value=0b10 -> covers only minterm 2
        // but we provide only minterm 0 and 1
        ProductTerm pt = new ProductTerm(2, 3, VARS_AB);
        ProductTerm[] minterms = {
            minterm(0, VARS_AB),
            minterm(1, VARS_AB)
        };
        assertThrows(RuntimeException.class, () -> new PrimeImplicant(pt, minterms));
    }

    @Test
    void getCovers_returnsCorrectVector() {
        ProductTerm pt = new ProductTerm(3, 3, VARS_AB); // exact minterm 3
        ProductTerm[] minterms = {
            minterm(0, VARS_AB),
            minterm(1, VARS_AB),
            minterm(2, VARS_AB),
            minterm(3, VARS_AB)
        };
        PrimeImplicant pi = new PrimeImplicant(pt, minterms);
        assertEquals(1, pi.getCovers().size());
        assertEquals(3, pi.getCovers().get(0).getValue());
    }

    @Test
    void addCover_validMinterm_addsToCovers() {
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB); // covers 1 and 3
        ProductTerm mt1 = minterm(1, VARS_AB);
        ProductTerm mt3 = minterm(3, VARS_AB);
        PrimeImplicant pi = new PrimeImplicant(pt, new ProductTerm[]{mt1, mt3});
        int before = pi.getCoverCount();
        // Adding mt1 again (it's already covered, but addCover doesn't check duplicates)
        pi.addCover(mt1);
        assertEquals(before + 1, pi.getCoverCount());
    }

    @Test
    void addCover_notAMinterm_throwsException() {
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB);
        ProductTerm mt1 = minterm(1, VARS_AB);
        PrimeImplicant pi = new PrimeImplicant(pt, new ProductTerm[]{mt1});
        // A non-minterm (mask != full)
        ProductTerm nonMinterm = new ProductTerm(1, 1, VARS_AB); // only 1 bit set, numVars=2 -> not minterm
        assertThrows(RuntimeException.class, () -> pi.addCover(nonMinterm));
    }

    @Test
    void addCover_notCoveredByPI_throwsException() {
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB); // covers bit0=1: minterms 1,3
        ProductTerm mt1 = minterm(1, VARS_AB);
        PrimeImplicant pi = new PrimeImplicant(pt, new ProductTerm[]{mt1});
        // minterm 0 is NOT covered by pt (bit0=0)
        ProductTerm mt0 = minterm(0, VARS_AB);
        assertThrows(RuntimeException.class, () -> pi.addCover(mt0));
    }

    @Test
    void removeCover_removesElement() {
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB);
        ProductTerm mt1 = minterm(1, VARS_AB);
        ProductTerm mt3 = minterm(3, VARS_AB);
        PrimeImplicant pi = new PrimeImplicant(pt, new ProductTerm[]{mt1, mt3});
        assertEquals(2, pi.getCoverCount());
        pi.removeCover(mt1);
        assertEquals(1, pi.getCoverCount());
    }

    @Test
    void getImplicantString_returnsNonEmpty() {
        ProductTerm pt = new ProductTerm(3, 3, VARS_AB);
        ProductTerm[] minterms = {minterm(3, VARS_AB)};
        PrimeImplicant pi = new PrimeImplicant(pt, minterms);
        assertNotNull(pi.getImplicantString());
        assertFalse(pi.getImplicantString().isEmpty());
    }

    @Test
    void toString_containsArrow() {
        ProductTerm pt = new ProductTerm(3, 3, VARS_AB);
        ProductTerm[] minterms = {minterm(3, VARS_AB)};
        PrimeImplicant pi = new PrimeImplicant(pt, minterms);
        assertTrue(pi.toString().contains("=>"));
    }

    @Test
    void compareTo_sortsByLiterals() {
        // pt1 has 2 literals, pt2 has 1 literal
        ProductTerm pt1 = new ProductTerm(3, 3, VARS_AB);
        ProductTerm pt2 = new ProductTerm(1, 1, VARS_AB);
        ProductTerm mt3 = minterm(3, VARS_AB);
        ProductTerm mt1 = minterm(1, VARS_AB);
        PrimeImplicant pi1 = new PrimeImplicant(pt1, new ProductTerm[]{mt3});
        PrimeImplicant pi2 = new PrimeImplicant(pt2, new ProductTerm[]{mt1, mt3});
        // pi1 has more literals -> compareTo returns negative (comes first)
        assertTrue(pi1.compareTo(pi2) < 0);
    }

    @Test
    void covers_minterm_returnsTrue() {
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB); // bit0=1
        ProductTerm mt1 = minterm(1, VARS_AB);
        PrimeImplicant pi = new PrimeImplicant(pt, new ProductTerm[]{mt1});
        assertTrue(pi.covers(mt1));
    }

    @Test
    void covers_uncoveredMinterm_returnsFalse() {
        ProductTerm pt = new ProductTerm(1, 1, VARS_AB); // bit0=1
        ProductTerm mt1 = minterm(1, VARS_AB);
        ProductTerm mt0 = minterm(0, VARS_AB);
        PrimeImplicant pi = new PrimeImplicant(pt, new ProductTerm[]{mt1});
        assertFalse(pi.covers(mt0));
    }
}
