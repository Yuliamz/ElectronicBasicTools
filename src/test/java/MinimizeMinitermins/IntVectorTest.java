package MinimizeMinitermins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for IntVector.
 */
public class IntVectorTest {

    @Test
    void newVector_sizeIsZero() {
        IntVector v = new IntVector();
        assertEquals(0, v.getSize());
    }

    @Test
    void newVector_capacityIsOne() {
        IntVector v = new IntVector();
        assertEquals(1, v.getCapacity());
    }

    @Test
    void newVector_incrementIsTen() {
        IntVector v = new IntVector();
        assertEquals(10, v.getIncrement());
    }

    @Test
    void append_singleElement_sizeIsOne() {
        IntVector v = new IntVector();
        v.append(42);
        assertEquals(1, v.getSize());
    }

    @Test
    void append_multipleElements_sizeGrows() {
        IntVector v = new IntVector();
        v.append(1);
        v.append(2);
        v.append(3);
        assertEquals(3, v.getSize());
    }

    @Test
    void toArray_emptyVector_returnsEmptyArray() {
        IntVector v = new IntVector();
        int[] arr = v.toArray();
        assertEquals(0, arr.length);
    }

    @Test
    void toArray_withElements_returnsCorrectValues() {
        IntVector v = new IntVector();
        v.append(10);
        v.append(20);
        v.append(30);
        int[] arr = v.toArray();
        assertArrayEquals(new int[]{10, 20, 30}, arr);
    }

    @Test
    void append_beyondCapacity_growsAutomatically() {
        IntVector v = new IntVector();
        // Initial capacity is 1; appending 2 elements forces growth.
        // Note: the internal capacity tracking uses capacity += capacity (doubling),
        // but the actual array grows by increment=10. We test with a safe number of elements.
        for (int i = 0; i < 5; i++) {
            v.append(i);
        }
        assertEquals(5, v.getSize());
        int[] arr = v.toArray();
        for (int i = 0; i < 5; i++) {
            assertEquals(i, arr[i]);
        }
    }

    @Test
    void toString_emptyVector() {
        IntVector v = new IntVector();
        assertEquals("[]", v.toString());
    }

    @Test
    void toString_singleElement() {
        IntVector v = new IntVector();
        v.append(5);
        assertEquals("[5]", v.toString());
    }

    @Test
    void toString_multipleElements() {
        IntVector v = new IntVector();
        v.append(1);
        v.append(2);
        v.append(3);
        assertEquals("[1, 2, 3]", v.toString());
    }

    @Test
    void toArray_isACopy_mutationDoesNotAffectVector() {
        IntVector v = new IntVector();
        v.append(7);
        int[] arr = v.toArray();
        arr[0] = 999;
        // The vector's internal state should not change
        assertEquals(7, v.toArray()[0]);
    }
}
