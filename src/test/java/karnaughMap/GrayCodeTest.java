package karnaughMap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GrayCode.
 */
public class GrayCodeTest {

    // ---- 1-bit Gray code ----

    @Test
    void getGreyCode_oneBit_zero() {
        assertEquals("0", GrayCode.getGreyCode(0, 1));
    }

    @Test
    void getGreyCode_oneBit_one() {
        assertEquals("1", GrayCode.getGreyCode(1, 1));
    }

    // ---- 2-bit Gray code ----
    // Standard 2-bit Gray code: 00, 01, 11, 10

    @Test
    void getGreyCode_twoBits_zero() {
        assertEquals("00", GrayCode.getGreyCode(0, 2));
    }

    @Test
    void getGreyCode_twoBits_one() {
        assertEquals("01", GrayCode.getGreyCode(1, 2));
    }

    @Test
    void getGreyCode_twoBits_two() {
        assertEquals("11", GrayCode.getGreyCode(2, 2));
    }

    @Test
    void getGreyCode_twoBits_three() {
        assertEquals("10", GrayCode.getGreyCode(3, 2));
    }

    // ---- 3-bit Gray code ----
    // Standard 3-bit Gray code: 000, 001, 011, 010, 110, 111, 101, 100

    @Test
    void getGreyCode_threeBits_zero() {
        assertEquals("000", GrayCode.getGreyCode(0, 3));
    }

    @Test
    void getGreyCode_threeBits_one() {
        assertEquals("001", GrayCode.getGreyCode(1, 3));
    }

    @Test
    void getGreyCode_threeBits_two() {
        assertEquals("011", GrayCode.getGreyCode(2, 3));
    }

    @Test
    void getGreyCode_threeBits_three() {
        assertEquals("010", GrayCode.getGreyCode(3, 3));
    }

    @Test
    void getGreyCode_threeBits_four() {
        assertEquals("110", GrayCode.getGreyCode(4, 3));
    }

    @Test
    void getGreyCode_threeBits_five() {
        assertEquals("111", GrayCode.getGreyCode(5, 3));
    }

    @Test
    void getGreyCode_threeBits_six() {
        assertEquals("101", GrayCode.getGreyCode(6, 3));
    }

    @Test
    void getGreyCode_threeBits_seven() {
        assertEquals("100", GrayCode.getGreyCode(7, 3));
    }

    // ---- 4-bit Gray code (partial) ----

    @Test
    void getGreyCode_fourBits_zero() {
        assertEquals("0000", GrayCode.getGreyCode(0, 4));
    }

    @Test
    void getGreyCode_fourBits_eight() {
        // 8 >= 2^(4-1)=8, so: "1" + getGreyCode(2^4 - 8 - 1, 3) = "1" + getGreyCode(7, 3) = "1100"
        assertEquals("1100", GrayCode.getGreyCode(8, 4));
    }

    // ---- Consecutive codes differ by exactly one bit ----

    @Test
    void getGreyCode_consecutiveCodes_differByOneBit() {
        int numBits = 3;
        int count = (int) Math.pow(2, numBits);
        for (int i = 0; i < count - 1; i++) {
            String code1 = GrayCode.getGreyCode(i, numBits);
            String code2 = GrayCode.getGreyCode(i + 1, numBits);
            int diff = 0;
            for (int j = 0; j < code1.length(); j++) {
                if (code1.charAt(j) != code2.charAt(j)) diff++;
            }
            assertEquals(1, diff, "Codes " + code1 + " and " + code2 + " should differ by 1 bit");
        }
    }

    // ---- Length of code ----

    @Test
    void getGreyCode_lengthMatchesNumBits() {
        for (int bits = 1; bits <= 4; bits++) {
            for (int i = 0; i < (int) Math.pow(2, bits); i++) {
                String code = GrayCode.getGreyCode(i, bits);
                assertEquals(bits, code.length(),
                        "Code for " + i + " with " + bits + " bits should have length " + bits);
            }
        }
    }
}
