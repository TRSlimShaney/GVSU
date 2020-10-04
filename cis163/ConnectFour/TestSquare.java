package modelPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSquare {

	@Test
	void testConstructor() {
		Square s = new Square (1,4);
		assertEquals(s.toString(), "(1,4)");
	}
	
	@Test  // Switch row and column
	void testNavigateRowAndColumn() {
		Square s = new Square (0,0);
		Square downRight = new Square(1,1);
		s.navigate(downRight);
		
		assertEquals(s.toString(), "(1,1)");
		
		
	}
	
	@Test  //  Switch rows
	void testNavigateRow() {
		Square s = new Square (0,0);
		Square down = new Square(1,0);
		s.navigate(down);
		
		assertEquals(s.toString(), "(1,0)");
		
	}
	
	@Test  //  Switch columns
	void testNavigateColumn() {
		Square s = new Square (0,0);
		Square right = new Square(0,1);
		s.navigate(right);
			
		assertEquals(s.toString(), "(0,1)");
		
	}
	
	@Test
	void testNavigateDiagonalRight() {
		Square s = new Square (4,0);
		Square upRight = new Square(-1,1);
		s.navigate(upRight);
		
		assertEquals(s.toString(), "(3,1)");
		
		
	}
	
	@Test
	void testNavigateDiagonalLeft() {
		Square s = new Square (4,4);
		Square upRight = new Square(-1,-1);
		s.navigate(upRight);
		
		assertEquals(s.toString(), "(3,3)");
		
		
	}

}
