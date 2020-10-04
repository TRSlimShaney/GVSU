package modelPackage;

import java.util.ArrayList;

/********************************************************************************
 * ConnectFourGame3D defines a class that plays a game of Connect Four IN SUPERIOR 3D.
 * NOTE:  THE 3D GAME DOES NOT IMPLEMENT ANY AI.  DO NOT PUT AI CODE IN THIS CLASS.  TWO PLAYERS ARE REQUIRED.
 * 
 * 
 * @author Shane Stacy, Jacob Cramer
 * @version 2/7/2018
 *********************************************************************************/
public class ConnectFourGame3D {
	
	
	/*  the gameboard  */
	private int[][][] gameBoard;
	
	/*  the gameboard size  */
	private int size;
	
	/*  the current player  */
	private int player;
	
	/*  the number of player  */
	private int playerCount;
	
	/*  the desired length of a chain of chips  */
	private int pathLength;
	
	/* the computer  */
	private static int COMPUTER = 1;
	
	/* the user  */
	private static int USER = 0;
	
	/* nobody  */
	private static int NONE = -1;

	
	/*******************************************************************
	 * ConnectFourGame3D constructor 
	 * 
	 * @param int pSize the length and width of the game
	 ********************************************************************/
	public ConnectFourGame3D(int pSize) {

		this.size = pSize;
		this.gameBoard = new int[pSize][pSize][pSize];
		this.playerCount = 2;
		this.pathLength = 4;
		this.reset();
	}

	/*******************************************************************
	 * ConnectFourGame3D constructor 
	 * 
	 * 
	 * 
	 * 
	 * @param int pSize the length and width of the game
	 * @param gameBoard bd the coordinate array of the game spaces
	 ********************************************************************/
	public ConnectFourGame3D(int pSize, int[][][] bd) {

		this.size = pSize;
		this.gameBoard = bd;
		this.playerCount = 2;
		this.pathLength = 4;
		this.reset();
	}

	/****************************************************************
	 * The reset method clears the gameboard 
	 ****************************************************************/
	public void reset() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int c = 0; c < size; c++) {
				gameBoard[i][j][c] = -1;
			}
		}
		}
		this.player = USER;
	}

	/******************************************************************
	 * Drops a chip into the selected column and return the row it fell into.
	 * 
	 * @param int row the selected row
	 * @param int col the selected column
	 * @return int the depth the chip fell into
	 *****************************************************************/
	public int selectCol(int row, int col) {

		for (int nDepth = size - 1; nDepth >= 0; nDepth--) {
			if (gameBoard[row][col][nDepth] == -1) {
				gameBoard[row][col][nDepth] = player;
			return nDepth;
			}
		}
		return -1;

	}

	/******************************************************************
	 * Switches players
	 * 
	 * @return int player the current player
	 *****************************************************************/
	public int switchplayer() {
		if(this.player == 0) {
			this.player = 1;
		}
		else if (this.player == 1) {
			this.player = 0;
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
	
	/******************************************************************
	 * Checks if the current player is the computer
	 * 
	 * @return boolean true if it's the computer
	 *****************************************************************/
	public boolean isWinner() {
		return isWinner(player);
	}

	/******************************************************************
	 * Checks if the player is the winner
	 * 
	 * @param int person the player
	 * @return boolean true if the current player has won.
	 *****************************************************************/
	public boolean isWinner(int person) {
		boolean winner = false;
		Cube cube = new Cube(0,0,0);
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				for (int depth = 0; depth < size; depth++) {
				for (int n = 0; n < Cube.DIRECTION.length; n++) {
					cube.reset(row, col, depth);
					winner = isConnectedLine(cube, Cube.DIRECTION[n], pathLength, person);
					if (winner) {
						return true;
					}
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
	 * @param Cube cube the cube
	 * @return boolean true if within board
	 *****************************************************************/
	private boolean inBounds(Cube cube) {
		if (inRange(cube.row) && inRange(cube.column) && inRange(cube.depth)) {
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
	 * @param Cube cube the cube
	 * @param Cube direction the direction
	 * @param int pathLength the chip chain length
	 * @param int target the desired player number to be in that square
	 * @return boolean true if a chain was made
	 ****************************************************************/
	private boolean isConnectedLine(Cube cube, Cube direction, int pathLength, int target) {

		for (int i = 0; i < pathLength; i++) {
			if (!inBounds(cube) || (gameBoard[cube.row][cube.column][cube.depth] != target)) {
				return false;
			}
			cube.navigate(direction);	
		}
		return true;

	}

	/******************************
	 * 
	 * 
	 * @return boolean
	 ******************************/
	public boolean moveRemaining() {

		int emptySpaces = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				for(int depth = 0; depth < size; depth++) {
					if (gameBoard[row][col][depth] == -1) {
					emptySpaces++;
					}
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
	 * Gets the value of that square.
	 * 
	 * @return int
	 ******************************/
	public int getGameBoardValue(int row9, int col9, int depth9) {
		
		return gameBoard[row9][col9][depth9];
		
	}

	/******************************
	 * Return the piece in that cube
	 * 
	 * @param int row the row
	 * @param int col the column
	 ******************************/
	public String getPiece(int row, int col) {
		String temp = "";
		for (int depth = 0; depth < size; depth++)
			if (gameBoard[row][col][depth] != -1)
				temp = gameBoard[row][col][depth] + temp;
		return temp;
	}
	
	/******************************
	 * Print the gameboard in inferior 2D
	 * 
	 * int[][] gameBoard the gameBoard
	 ******************************/
	public void printGameBoard(int[][] gameBoard) {
		for (int r = 0; r < 7; r++) {
			for (int c = 0; c < 7; c++) {
				if (gameBoard[r][c] == -1) {
					System.out.print("  ");
				} else {
					System.out.print(gameBoard[r][c] + " ");
				}
			}
			System.out.println();
		}
	}
}
