package MinimizeMinitermins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CharStack.
 */
public class CharStackTest {

    @Test
    void newStack_isEmpty() {
        CharStack stack = new CharStack();
        assertTrue(stack.isEmpty());
    }

    @Test
    void push_thenIsNotEmpty() {
        CharStack stack = new CharStack();
        stack.push('a');
        assertFalse(stack.isEmpty());
    }

    @Test
    void push_thenPop_returnsValue() {
        CharStack stack = new CharStack();
        stack.push('x');
        assertEquals('x', stack.pop());
    }

    @Test
    void pushMultiple_popInLIFOOrder() {
        CharStack stack = new CharStack();
        stack.push('a');
        stack.push('b');
        stack.push('c');
        assertEquals('c', stack.pop());
        assertEquals('b', stack.pop());
        assertEquals('a', stack.pop());
    }

    @Test
    void pop_emptyStack_throwsRuntimeException() {
        CharStack stack = new CharStack();
        assertThrows(RuntimeException.class, stack::pop);
    }

    @Test
    void peek_emptyStack_returnsNullChar() {
        CharStack stack = new CharStack();
        assertEquals('\0', stack.peek());
    }

    @Test
    void peek_nonEmptyStack_returnsTopWithoutRemoving() {
        CharStack stack = new CharStack();
        stack.push('z');
        assertEquals('z', stack.peek());
        // peek should not remove
        assertEquals('z', stack.peek());
        assertFalse(stack.isEmpty());
    }

    @Test
    void toString_emptyStack() {
        CharStack stack = new CharStack();
        assertEquals("[]", stack.toString());
    }

    @Test
    void toString_withElements() {
        CharStack stack = new CharStack();
        stack.push('a');
        stack.push('b');
        assertEquals("[ab]", stack.toString());
    }

    @Test
    void push_beyondInitialCapacity_growsCorrectly() {
        CharStack stack = new CharStack();
        // Initial capacity is 10; push 15 elements to force resize
        for (int i = 0; i < 15; i++) {
            stack.push((char) ('a' + i));
        }
        // Pop all in LIFO order
        for (int i = 14; i >= 0; i--) {
            assertEquals((char) ('a' + i), stack.pop());
        }
        assertTrue(stack.isEmpty());
    }

    @Test
    void isEmpty_afterPushAndPop_isTrue() {
        CharStack stack = new CharStack();
        stack.push('q');
        stack.pop();
        assertTrue(stack.isEmpty());
    }
}
