package MinimizeMinitermins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BitManipulation utility class.
 */
public class BitManipulationTest {

    // ---- countBits ----

    @Test
    void countBits_zero_returnsZero() {
        assertEquals(0, BitManipulation.countBits(0));
    }

    @Test
    void countBits_one_returnsOne() {
        assertEquals(1, BitManipulation.countBits(1));
    }

    @Test
    void countBits_allOnes_returns32() {
        assertEquals(32, BitManipulation.countBits(-1)); // 0xFFFFFFFF
    }

    @Test
    void countBits_powerOfTwo_returnsOne() {
        assertEquals(1, BitManipulation.countBits(8));   // 1000
        assertEquals(1, BitManipulation.countBits(16));  // 10000
        assertEquals(1, BitManipulation.countBits(1024));
    }

    @Test
    void countBits_arbitraryValue() {
        // 0b1010 = 10 -> 2 bits set
        assertEquals(2, BitManipulation.countBits(10));
        // 0b1111 = 15 -> 4 bits set
        assertEquals(4, BitManipulation.countBits(15));
        // 0b1010_1010 = 170 -> 4 bits set
        assertEquals(4, BitManipulation.countBits(170));
    }

    // ---- reverseBits ----

    @Test
    void reverseBits_singleBit_unchanged() {
        // reversing 1 bit of value 1 should give 1
        assertEquals(1, BitManipulation.reverseBits(1, 1));
        // reversing 1 bit of value 0 should give 0
        assertEquals(0, BitManipulation.reverseBits(0, 1));
    }

    @Test
    void reverseBits_twoBits_swapped() {
        // 0b10 reversed in 2 bits -> 0b01 = 1
        assertEquals(1, BitManipulation.reverseBits(2, 2));
        // 0b01 reversed in 2 bits -> 0b10 = 2
        assertEquals(2, BitManipulation.reverseBits(1, 2));
        // 0b11 reversed in 2 bits -> 0b11 = 3
        assertEquals(3, BitManipulation.reverseBits(3, 2));
    }

    @Test
    void reverseBits_fourBits() {
        // 0b1000 = 8 reversed in 4 bits -> 0b0001 = 1
        assertEquals(1, BitManipulation.reverseBits(8, 4));
        // 0b0001 = 1 reversed in 4 bits -> 0b1000 = 8
        assertEquals(8, BitManipulation.reverseBits(1, 4));
        // 0b1010 = 10 reversed in 4 bits -> 0b0101 = 5
        assertEquals(5, BitManipulation.reverseBits(10, 4));
        // 0b1100 = 12 reversed in 4 bits -> 0b0011 = 3
        assertEquals(3, BitManipulation.reverseBits(12, 4));
    }

    @Test
    void reverseBits_preservesHighBits() {
        // High bits beyond numBits should be preserved (masked out in result)
        // source = 0b1_0001 = 17, numBits = 4
        // The rightmost 4 bits are 0001, reversed = 1000 = 8
        // High bit (bit 4) is preserved: result = 0b1_1000 = 24
        assertEquals(24, BitManipulation.reverseBits(17, 4));
    }

    @Test
    void reverseBits_threeBits() {
        // 0b110 = 6 reversed in 3 bits -> 0b011 = 3
        assertEquals(3, BitManipulation.reverseBits(6, 3));
        // 0b100 = 4 reversed in 3 bits -> 0b001 = 1
        assertEquals(1, BitManipulation.reverseBits(4, 3));
    }
}
