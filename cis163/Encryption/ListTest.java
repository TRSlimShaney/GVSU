package modelPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ListTest {

	@Test
	void testConstructor() {
		List list = new List("list");
		assertTrue(list.getTitle().equals("list"));
	}
	
	@Test
	void testSetTitle() {
		List list = new List("list");
		list.setTitle("list2");
		assertTrue(list.getTitle().equals("list2"));
	}
	
	@Test
	void testIsEmpty() {
		List list = new List("list");
		assertTrue(list.isEmpty());
	}
	
	@Test
	void testCursorIsFirst() {
		List list = new List("list");
		assertTrue(list.cursorIsFirst());
	}
	
	@Test
	void testCursorToFirst() {
		List list = new List("list");
		list.cursorToNext();
		list.cursorToFirst();
		assertTrue(list.cursorIsFirst());
	}
	
	@Test
	void testGetFirst() {
		List list = new List("list");
		list.add("hello");
		assertTrue(list.getFirst().equals("hello"));
	}
	
	@Test
	void testCursorIsAtTheEnd() {
		List list = new List("list");
		list.add("hello");
		list.cursorToNext();
		assertTrue(list.cursorIsAtTheEnd());
	}
	
	@Test
	void testCursorIsLast() {
		List list = new List("list");
		list.add("hello");
		assertTrue(list.cursorIsLast());
	}
	
	@Test
	void testGetLast() {
		List list = new List("list");
		list.add("hello");
		list.add("hello2");
		assertTrue(list.getLast().equals("hello"));
	}
	
	@Test
	void testCursorHasNext() {
		List list = new List("list");
		list.add("hello");
		list.add("hi");
		list.add("test");
		assertFalse(list.getNext().equals(null));
	}
	
	@Test
	void testGetNext() {
		List list = new List("list");
		list.add("hello");
		list.add("hello2");
		assertTrue(list.getNext().equals("hello2"));
	}
	
	@Test
	void testCursorHasPrevious() {
		List list = new List("list");
		assertTrue(list.cursorHasPrevious() == false);
	}
	
	@Test
	void testAdd() {
		List list = new List("list");
		list.add("hello");
		assertTrue(list.get(0).equals("hello"));
	}
	
	@Test
	void testAddAll() {
		List list = new List("list");
		List list2 = new List("list2");
		list2.add("hello");
		list.addAll(0, list2);
		assertTrue(list.get(0).equals("hello"));
	}
	
	@Test
	void testIndexOf() {
		List list = new List("list");
		list.add("hello");
		assertTrue(list.indexOf("hello") == 0);
	}
	
	@Test
	void testLastIndexOf() {
		List list = new List("list");
		list.add("hello");
		list.add("hello");
		assertTrue(list.lastIndexOf("hello") == 1);
	}
	
	@Test
	void testGetIndex() {
		List list = new List("list");
		assertTrue(list.getIndex() == 0);
	}
	
	@Test
	void testRemove() {
		List list = new List("list");
		list.add("hello");
		list.remove(0);
		assertTrue(list.isEmpty());
	}

	@Test
	void testSize() {
		List list = new List("list");
		list.add("hello");
		list.add("hello");
		assertTrue(list.size() == 2);
	}

}
