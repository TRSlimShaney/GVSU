package modelPackage;

/********************************************************************************
 * Square defines a class to encapsulate the location of a square, perhaps on
 * some game board, as a row and column pair.
 * 
 * Four static fields are included for navigation in four specific directions.
 * These four directions can also be identified by index into the given static
 * DIRECTION array.
 * 
 * @author Shane Stacy, Jacob Cramer
 * @version 1/24/2018
 *
 *********************************************************************************/
public class Square {

	/** Two fields to identify a Square position */
	public int row;
	public int column;

	/** static fields to define navigation directions */
	public static final Square ROW = new Square(1, 0);
	public static final Square COLUMN = new Square(0, 1);
	public static final Square DIAGONAL_RIGHT = new Square(1, 1);
	public static final Square DIAGONAL_LEFT = new Square(1, -1);

	/** static field to define an array of navigation directions */
	public static final Square[] DIRECTION = { ROW, COLUMN, DIAGONAL_RIGHT, DIAGONAL_LEFT };

	
	/*******************************************************************
	 * Square constructor initializes the location fields:
	 *          row and column.
	 * 
	 * @param r
	 * @param c
	 ********************************************************************/
	public Square(int r, int c) {
		this.reset(r, c);
	}

	/****************************************************************
	 * The reset method resets the row and column fields with the
	 * r and c parameter values for a new position, perhaps on some 
	 * game board.
	 * 
	 * @param r
	 * @param c
	 ****************************************************************/
	public void reset(int r, int c) {
		this.row = r;
		this.column = c;
	}

	/*******************************************************************************
	 * The navigate method changes this square by adding to its row and column 
	 * fields the values of the row and column fields of the change square,
	 * respectively.
	 * 
	 * @param change
	 *******************************************************************************/
	public void navigate(Square change) {
		this.row += change.row;
		this.column += change.column;
	}

	/*******************************************************************************
	 * The toString method returns a string with the format: (r, c)
	 * 
	 * @return
	 *******************************************************************************/
	public String toString() {
		return "(" + this.row + "," + this.column + ")";
	}

	/*******************************************************************************
	 * The printReferences method prints a trace of the square references under 
	 * navigation.
	 * 
	 * @param direction
	 * @param times
	 *******************************************************************************/
	public void printReferences(Square direction, int pathLength) {
		for (int n = 0; n < pathLength; n++) {
			System.out.print(this.toString());
			this.navigate(direction);
		}
		System.out.println();
	}

	/***************************************************************
	 * This main method uses the static navigation direction fields
	 * to print a sample of navigation paths.
	 * 
	 * @param args
	 ***************************************************************/
//	public static void main(String[] args) {
//
//		Square square = new Square(0, 0);
//
//		square.reset(0, 0);
//		System.out.print("Go ROW:\t\t   ");
//		square.printReferences(ROW, 4);
//
//		square.reset(0, 4);
//		System.out.print("Go COLUMN:\t   ");
//		square.printReferences(COLUMN, 4);
//
//		square.reset(0, 0);
//		System.out.print("Go DIAGONAL_RIGHT: ");
//		square.printReferences(DIAGONAL_RIGHT, 4);
//
//		square.reset(0, 4);
//		System.out.print("Go DIAGONAL_LEFT:  ");
//		square.printReferences(DIAGONAL_LEFT, 4);
//
//		square.reset(0, 4);
//		System.out.print("Go DIAGONAL_LEFT:  ");
//		square.printReferences(DIAGONAL_LEFT, 7);
//
//		System.out.println();
//
//		String[] directions = { "Go ROW:\t\t   ", "Go COLUMN:\t   ", "Go DIAGONAL_RIGHT: ", "Go DIAGONAL_LEFT:  " };
//		Square[] c = { new Square(0, 0), new Square(0, 4), new Square(0, 0), new Square(0, 4) };
//
//		for (int n = 0; n < directions.length; n++) {
//			System.out.print(directions[n]);
//			c[n].printReferences(Square.DIRECTION[n], 4);
//		}
//	}
}