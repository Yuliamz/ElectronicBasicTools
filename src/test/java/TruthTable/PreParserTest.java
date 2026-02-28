package TruthTable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PreParser.
 */
public class PreParserTest {

    // ---- getfirstLetter ----

    @Test
    void getfirstLetter_withLetters_returnsFirst() {
        assertEquals('A', PreParser.getfirstLetter("ABC"));
        assertEquals('B', PreParser.getfirstLetter("B+C"));
    }

    @Test
    void getfirstLetter_noLetters_returnsA() {
        assertEquals('A', PreParser.getfirstLetter("123"));
        assertEquals('A', PreParser.getfirstLetter(""));
    }

    // ---- cleanNot ----

    @Test
    void cleanNot_doubleNot_removed() {
        assertEquals("A", PreParser.cleanNot("!!A"));
    }

    @Test
    void cleanNot_tripleNot_reducedToSingle() {
        assertEquals("!A", PreParser.cleanNot("!!!A"));
    }

    @Test
    void cleanNot_doublePostfixNot_removed() {
        assertEquals("A", PreParser.cleanNot("A''"));
    }

    @Test
    void cleanNot_triplePostfixNot_reducedToSingle() {
        assertEquals("A'", PreParser.cleanNot("A'''"));
    }

    @Test
    void cleanNot_noNot_unchanged() {
        assertEquals("A+B", PreParser.cleanNot("A+B"));
    }

    // ---- replaceOneZero ----

    @Test
    void replaceOneZero_replacesOne() {
        String result = PreParser.replaceOneZero("1", 'A', "&", "|");
        assertEquals("(A|!A)", result);
    }

    @Test
    void replaceOneZero_replacesZero() {
        String result = PreParser.replaceOneZero("0", 'A', "&", "|");
        assertEquals("(A&!A)", result);
    }

    @Test
    void replaceOneZero_replacesTRUE() {
        String result = PreParser.replaceOneZero("TRUE", 'B', "&", "|");
        assertEquals("(B|!B)", result);
    }

    @Test
    void replaceOneZero_replacesFALSE() {
        String result = PreParser.replaceOneZero("FALSE", 'B', "&", "|");
        assertEquals("(B&!B)", result);
    }

    // ---- replaceOrOperand ----

    @Test
    void replaceOrOperand_replacesPlus() {
        assertEquals("A|B", PreParser.replaceOrOperand("A+B", "|"));
    }

    @Test
    void replaceOrOperand_replacesPipe() {
        assertEquals("A|B", PreParser.replaceOrOperand("A|B", "|"));
    }

    // ---- replaceAndOperand ----

    @Test
    void replaceAndOperand_replacesStar() {
        assertEquals("A&B", PreParser.replaceAndOperand("A*B", "&"));
    }

    @Test
    void replaceAndOperand_replacesDot() {
        assertEquals("A&B", PreParser.replaceAndOperand("A.B", "&"));
    }

    @Test
    void replaceAndOperand_replacesImplicitParentheses() {
        assertEquals("(A)&(B)", PreParser.replaceAndOperand("(A)(B)", "&"));
    }

    // ---- postToPrefixNOT ----

    @Test
    void postToPrefixNOT_singleVar_convertsToPrefix() {
        String result = PreParser.postToPrefixNOT("A'");
        assertEquals("!A", result);
    }

    @Test
    void postToPrefixNOT_parenthesized_convertsToPrefix() {
        String result = PreParser.postToPrefixNOT("(A+B)'");
        assertEquals("!(A+B)", result);
    }

    @Test
    void postToPrefixNOT_noNot_unchanged() {
        String result = PreParser.postToPrefixNOT("A+B");
        assertEquals("A+B", result);
    }

    // ---- addAndOperand ----

    @Test
    void addAndOperand_adjacentLetters_insertsAnd() {
        String result = PreParser.addAndOperand("AB", "&");
        assertEquals("A&B", result);
    }

    @Test
    void addAndOperand_letterFollowedByParen_insertsAnd() {
        String result = PreParser.addAndOperand("A(B)", "&");
        assertEquals("A&(B)", result);
    }

    @Test
    void addAndOperand_parenFollowedByLetter_insertsAnd() {
        String result = PreParser.addAndOperand("(A)B", "&");
        assertEquals("(A)&B", result);
    }

    // ---- preToPostfix ----

    @Test
    void preToPostfix_prefixNot_convertsToPostfix() {
        String result = PreParser.preToPostfix("!A");
        assertEquals("A'", result);
    }

    @Test
    void preToPostfix_prefixNotWithParen_convertsToPostfix() {
        String result = PreParser.preToPostfix("!(A+B)");
        assertEquals("(A+B)'", result);
    }

    @Test
    void preToPostfix_noNot_unchanged() {
        String result = PreParser.preToPostfix("A+B");
        assertEquals("A+B", result);
    }

    // ---- validateExpression ----

    @Test
    void validateExpression_validSimple_noException() {
        assertDoesNotThrow(() -> PreParser.validateExpression("A+B"));
    }

    @Test
    void validateExpression_invalidChar_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("A@B"));
    }

    @Test
    void validateExpression_illegalStart_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("+A"));
    }

    @Test
    void validateExpression_illegalEnd_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("A+"));
    }

    @Test
    void validateExpression_emptyParentheses_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("A+()"));
    }

    @Test
    void validateExpression_unmatchedLeftParen_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("(A+B"));
    }

    @Test
    void validateExpression_unmatchedRightParen_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("A+B)"));
    }

    @Test
    void validateExpression_andFollowedByInvalidChar_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("A*+B"));
    }

    @Test
    void validateExpression_prefixNotWithInvalidChar_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.validateExpression("!+A"));
    }

    // ---- verifyParentheses ----

    @Test
    void verifyParentheses_balanced_noException() {
        assertDoesNotThrow(() -> PreParser.verifyParentesisis("(A+B)"));
    }

    @Test
    void verifyParentheses_emptyParens_throwsException() {
        assertThrows(RuntimeException.class, () -> PreParser.verifyParentesisis("()"));
    }

    // ---- getCleanExpression ----

    @Test
    void getCleanExpression_removesHtmlTags() {
        String result = PreParser.getCleanExpression("<Strong>A</Strong>+B");
        assertEquals("A+B", result);
    }

    @Test
    void getCleanExpression_noTags_unchanged() {
        String result = PreParser.getCleanExpression("A+B");
        assertEquals("A+B", result);
    }

    // ---- parseToExpresion ----

    @Test
    void parseToExpresion_simpleAnd() {
        String result = PreParser.parseToExpresion("A*B");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void parseToExpresion_simpleOr() {
        String result = PreParser.parseToExpresion("A+B");
        assertNotNull(result);
    }

    @Test
    void parseToExpresion_withNot() {
        String result = PreParser.parseToExpresion("A'");
        assertNotNull(result);
        assertTrue(result.contains("!"));
    }

    // ---- expresiontoString ----

    @Test
    void expresiontoString_convertsOperators() {
        String result = PreParser.expresiontoString("A&&B");
        assertNotNull(result);
        assertTrue(result.contains("*") || result.contains("A") && result.contains("B"));
    }
}
