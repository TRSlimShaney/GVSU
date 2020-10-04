package modelPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StackTest {

	@Test
	void testConstructor() {
		Stack stack = new Stack("stack");
		assertTrue(stack.title == "stack");
	}
	
	@Test
	void testSetTitle() {
		Stack stack = new Stack("stack");
		stack.setTitle("stack2");
		assertTrue(stack.title == "stack2");
	}
	
	@Test
	void testPop() {
		Stack stack = new Stack("stack");
		stack.push("meat");
		assertTrue(stack.pop() == "meat");
	}
	
	@Test
	void testPeek() {
		Stack stack = new Stack("stack");
		stack.push("meat");
		assertTrue(stack.peek() == "meat");
	}
	
	@Test
	void testSize() {
		Stack stack = new Stack("stack");
		stack.push("a");
		assertTrue(stack.size() == 1);
	}
	
	@Test
	void testIsEmpty() {
		Stack stack = new Stack("stack");
		stack.push("b");
		assertTrue(stack.isEmpty() == false);
	}
	
}
