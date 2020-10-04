package modelPackage;

import java.util.*;

import modelPackage.Square;

import java.awt.*;

/********************************************************************************
 * ConnectFourGame defines a class that plays a game of Connect Four.
 * 
 * 
 * @author Shane Stacy, Jacob Cramer
 * @version 2/7/2018
 *********************************************************************************/
public class ConnectFourGame {

	/*  the gameboard  */
	private int[][] gameBoard;
	
	private int lastCol;
	
	private int lastRow;
	
	/*  the size of the gameboard  */
	private int size;
	
	/*  the current player  */
	private int player;
	
	/*  the number of players  */
	private int playerCount;
	
	/*  the desired chain length of chips  */
	private int pathLength;
	
	/*  The amount of chips for a player in an incomplete but potential line  */
	private int connectLength = 0;

	/*  placeholder for space with no ownership  */
	public static final int NONE = -1;
	
	/*  the user owns this space  */
	public static final int USER = 0;
	
	/*  the computer owns this space  */
	public static final int COMPUTER = 1;

	
	/*******************************************************************
	 * ConnectFourGame constructor 
	 * 
	 * @param int pSize the length and width of the game
	 ********************************************************************/
	public ConnectFourGame(int pSize) {
		this.size = pSize;
		this.gameBoard = new int[pSize][pSize];
		this.playerCount = 2;
		this.pathLength = 4;
		this.reset();
		ArrayList lastMoves = new ArrayList<Square>();
		this.player = USER;
	}

	/*******************************************************************
	 * ConnectFourGame constructor 
	 * 
	 * 
	 * 
	 * 
	 * @param int pSize the length and width of the game
	 * @param gameBoard gameBoard the coordinate array of the game spaces
	 ********************************************************************/
	public ConnectFourGame(int pSize, int[][] gameBoard) {
		this.size = pSize;
		this.gameBoard = gameBoard;
		this.playerCount = 2;
		this.pathLength = 4;
		this.reset();
		this.player = USER;
	}

	/****************************************************************
	 * The reset method clears the gameboard 
	 ****************************************************************/
	public void reset() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				gameBoard[i][j] = NONE;
			}
		}
		this.player = USER;
	}
	
	public int getGameBoardValue(int row9, int col9) {
		return gameBoard[row9][col9];
	}

	/******************************************************************
	 * Drops a chip into the selected column and return the row it fell into.
	 * 
	 * @param int pCol the selected column
	 * @return int the row the chip fell into
	 *****************************************************************/
	public int selectCol(int pCol) {
		for (int row = size - 1; row >= 0; row--) {
			if (gameBoard[row][pCol] == -1) {
				gameBoard[row][pCol] = player;
			return row;
			}
		}
		return -1;
	}

	/******************************************************************
	 * Switches players
	 * 
	 * @return int player the current player
	 *****************************************************************/
	public int switchPlayer() {
		if(this.player == USER) {
			this.player = COMPUTER;
		}
		else {
			this.player = USER;
		}
		return this.player;
	}

	/******************************************************************
	 * Gets the current player
	 * 
	 * @return int player the current player
	 *****************************************************************/
	public int getCurrentPlayer() {
		return this.player;
	}

	/******************************************************************
	 * Checks if the current player is the computer
	 * 
	 * @return boolean true if it's the computer
	 *****************************************************************/
	public boolean playerIsComputer() {
		if(this.player == COMPUTER) {
			return true;
		}
		return false;
	}

	/******************************************************************
	 * Checks if the player is the winner
	 * 
	 * @return boolean true if the current player has won.
	 *****************************************************************/
	public boolean isWinner() {
		
		return isWinner(player);
	}

	/******************************************************************
     * Gets the winner
	 * 
	 * @return String the winner text
	 *****************************************************************/
	public String getWinner() {
		if (isWinner(USER) == true) {
			return "User";
		}
		if (isWinner(COMPUTER) == true) {
			return "Computer";
		}
		return "No one";
	}
	
	/****************************************************************
	 * The method to check if a player has won after placing their chip.
	 * 
	 * @param person
	 * @return boolean true if the specified player has won
	 ****************************************************************/
	public boolean isWinner(int person) {
	boolean winner = false;
	Square square = new Square(0,0);
	for (int row = 0; row < size; row++) {
		for (int col = 0; col < size; col++) {
			for (int n = 0; n < Square.DIRECTION.length; n++) {
				square.reset(row, col);
				winner = isConnected(square, Square.DIRECTION[n], pathLength, person);
				if (winner) {
					return true;
				}
			}
		}
	}
	return false;
	}	

	/******************************************************************
	 * Checks if the number fits in the gameboard
	 * 
	 * @param int number the number
	 * @return boolean true if within board
	 *****************************************************************/
	private boolean inRange(int number) {
		if (number >= 0 && number < size) {
			
			return true;
		}
		return false;
	}

	/******************************************************************
	 * Checks if the square is within the board
	 * 
	 * @param Square square the square
	 * @return boolean true if within board
	 *****************************************************************/
		private boolean inBounds(Square square) {
			if (inRange(square.row) && inRange(square.column)) {
				return true;
			}
				return false;	
	}

	/****************************************************************
	 * The isConnected method returns whether each Square in the
	 * specified path of Squares contains the target value, where 
	 * the specified path starts with the given square and follows
	 * the given direction, without going out of the bounds of the
	 * gameBoard.
	 *  
	 * 
	 * @param Square square the square
	 * @param Square direction the direction
	 * @param int pathLength the chip chain length
	 * @param int target the desired player number to be in that square
	 * @return boolean true if a chain was made
	 ****************************************************************/
	private boolean isConnected(Square square, Square direction, int length, int target) {
		for (int i = 0; i < length; i++) {
			if (!inBounds(square) || (gameBoard[square.row][square.column] != target)) {
				return false;
			}
			square.navigate(direction);
            
		}
		return true;
	}
	
	private boolean couldBeConnected(Square square, Square direction, int length, int target, int row, int col) {
		
		connectLength = 0;
		
		for (int i = 0; i < length; i++) {
			if (!inBounds(square) || (gameBoard[square.row][square.column] != target  &&  gameBoard[square.row][square.column] != -1)) {
				
				return false;
			}
			
			if (gameBoard[square.row][square.column] == target) {
			connectLength++;
			}
			
			if ((gameBoard[square.row][square.column] == -1)) {
				lastRow = row;
				lastCol = col;
			}
			
			square.navigate(direction);
            
		}
		if (connectLength == length - 1) {
			return true;
		}
		return false;
	}
		

	/******************************
	 * Return true if there is an empty space left.
	 * 
	 * @return boolean
	 ******************************/
	public boolean moveRemaining() {

		int emptySpaces = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (gameBoard[row][col] == -1) {
					emptySpaces++;
				}
			}
		}
		
		if (emptySpaces > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/******************************
	 * Artificial Intelligence to determine the computer's next move
	 * 
	 ******************************/
	public void moveAI() {

		boolean potentialWinner = false;
		boolean potentialLoser = false;
		
		// 1. See if you can win, if so, select that column and win.
		// player is computer
		Square square = new Square(0,0);
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				for (int n = 0; n < Square.DIRECTION.length; n++) {
					square.reset(row, col);
					potentialWinner = couldBeConnected(square, Square.DIRECTION[n], pathLength, COMPUTER, row, col);  //  SEE IF THE COMPUTER IS ABOUT TO WIN
					
					if (potentialWinner) {  //  IF SO, BREAK AWAY FROM THIS LOOP
						break;
							}
						}
				if (potentialWinner) {
					break;
					}
				}
			if (potentialWinner) {
				break;
			}
		}
		
		// 2. See if you are going to lose, if so, select the column that blocks that
		// move

		if (!potentialWinner) {  //  NO NEED TO DO THIS CODE IF THE COMPUTER CAN WIN
		Square square2 = new Square(0,0);
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				for (int n = 0; n < Square.DIRECTION.length; n++) {
					square2.reset(row, col);
					potentialLoser = couldBeConnected(square2, Square.DIRECTION[n], pathLength, USER, row, col);  //  SEE IF THE USER IS ABOUT TO WIN
					
					if (potentialLoser) {  //  IF SO, BREAK AWAY FROM THIS LOOP
						break;
							}
					
						}
				if (potentialLoser) {
					break;
					}
				}
			if (potentialLoser) {
				break;
			}
		}
		}
		// 3. If rules 1 and 2 are not in play, then develop tactics to select a column
		// that may help you in the future.
		
		//  Right now, this code just randomly puts a chip in the board provided there
		//  is an empty row available and the computer or player isn't about to win.
		
		if (potentialWinner == true) {
			
			
			selectCol(lastCol);  //  PUT THE CHIP IN THAT EMPTY SPACE TO WIN
		}
		else if (potentialLoser == true) {
			
			selectCol(lastCol);  //  PUT THE CHIP IN THAT EMPTY SPACE TO NOT LOSE
		}
		else {
			Random rand = new Random();
			
			int d = rand.nextInt(size) + 0;
			while (selectCol(d) == -1) {  //  OTHERWISE, PUT THE CHIP SOMEWHERE RANDOM.  IF UNABLE TO DO SO, KEEP TRYING.
				d = rand.nextInt(size) + 0;
			}
		}
	}

	/******************************
	 * Gets the integer in the square
	 * 
	 * @param int row the row
	 * @param int col the column
	 * @return String the value in that square
	 ******************************/
	public String getValue(int row, int col) {
		if (gameBoard[row][col] == 0) {
			return "0";
		} else if (gameBoard[row][col] == 1) {
			return "1";
		}
		return "_";
	}

	public Color getColor(int row, int col) {
		if (gameBoard[row][col] == 0) {
			return Color.red;
		} else if (gameBoard[row][col] == 1) {
			return Color.blue;
		}
		return Color.gray;
	}

	public void printGameBoardBoard() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				printSquare( r, c );
			}
			System.out.println();
		}
	}

	public void printGameBoard(int[][] board) {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				printSquare( r, c );
			}
			System.out.println();
		}
	}

	private void printSquare( int r, int c)
	{
		String s = " ";
		
		s += gameBoard[r][c] + " ";
		s = s.substring( s.length() - 3);
		
		System.out.print( s );
	}
}