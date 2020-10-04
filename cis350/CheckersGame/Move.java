package modelPackage;

import javax.swing.ImageIcon;
import java.util.Scanner;
/*************************************************************************************
 * Move is a class definition for a checkers piece move.  Two board squares are involved
 * in a move:
 *      1) a from square
 *      2) a to square
 * 
 * Let the checkers piece being moved be denoted as the fromPiece, and let the checkers piece
 * that is removed, if one is in fact removed, be denoted as the toPiece.
 * 
 * The information stored by a Move is sufficient for a move undo.
 * 
 * @author 
 *
 *************************************************************************************/
public class Move {
    public Location from;           // the "from" square in a move
    public Location to;             // the "to" square in a move
    public Location mid; 			 // Middle location of both the other locations

    public ICheckersPiece fromPiece; // stores the "from" chess piece in the move
    public ICheckersPiece toPiece;   // stores the "to" chess piece, if one exists, in the move
    public ICheckersPiece midPiece;  // stores the "in betweeen" chess piece, if one exists
   


    /*************************************************************
     * The Move constructor initializes the two locations in a
     * move, the "from" square location and the "to" square
     * location.
     * 
     * @param from
     * @param to
     *************************************************************/
    public Move(Location from, Location to) {
        this.from = new Location(from); // Important to make a new Location
        this.to   = new Location(to);
       }

    /*************************************************************
     * The Move constructor initializes the two squares in a move
     * from a string description of the move.
     * 
     * @param str
     *************************************************************/
    public Move(String str) {
        this.from = new Location(str.substring(0, 2)); // Important to make a new Location
        this.to   = new Location(str.substring(2, 4));
    }

    /*********************************************************
     *  The toString method returns a string description of
     *  the move.
     *  
     *  @return String the description of the move.
     *********************************************************/
    public String toString() {
        return " " + from.toString() + " " + to.toString();
    }

    /*********************************************************************
     * Checks if the location is within range of the board given one
     * integer value.
     * @param n
     * @return boolean is location within range of the board?
     *********************************************************************/
    private boolean isWithinRange(int n)
    {
        return 0 <= n && n < 8;
    }

    /*********************************************************************
     * Checks if the location is on the board.
     * 
     * @param s the location in question
     * @return boolean is the location on the board?
     *********************************************************************/
    private boolean isOnTheBoard(Location s)
    {
        if (s.row > 7 || s.row < 0 || s.column > 7 || s.column < 0) {
        	return false;
        }
        return true;
    }

    /**********************************************************************
     *  A valid Move consists of a move from any square on the board,
     *  denoted by "from", to some other square, on the board, denoted
     *  by "to"; i.e. the squares must be two distinctly different 
     *  locations.
     *  
     *  @return boolean is it a move to a different location?
     **********************************************************************/
    private boolean isMoveToDifferentLocation() {
        if (from != to && isOnTheBoard(from) && isOnTheBoard(to)) {
        	return true;
        }
        return false;
    }

    /**************************************************************************
     *  The squareIsOccupied method returns whether a square contains a piece.
     *    
     *  @param square
     *  @param board
     *  @return boolean does the square contain a piece?
     **************************************************************************/
    private boolean squareIsOccupied(Location square, ICheckersPiece[][] board ) {
        
        if (board[square.row][square.column] == null) {
        	return false;
        }
        return true;
    }

    /**************************************************************************
     *  The isMoveOverEmptySquares method returns : 
     *      1) true - if all squares exclusively between the "from" square and
     *         the "to" square are empty. 
     *      2) false - if at least one square between the "from" square and the
     *         "to" square is occupied.
     *         
     *  @param board
     *  @return boolean is the move over empty squares?
     **************************************************************************/
    private boolean isMoveOverEmptySquares(ICheckersPiece[][] board) {
    	
    	boolean movePathClear = true;  	
    	Location currLocation = new Location(this.from);
    	
    		while (movePathClear && !currLocation.equals(this.to)) {
    			currLocation.plus(currLocation.unitDirection(this.to));
    			
    			if (this.squareIsOccupied(currLocation, board)) {
    				movePathClear = false;
    			}
    		}   	
    	return movePathClear;
    	}
    	

    /********************************************************************
     *  The isMoveToEmptySquare method returns whether the move is to an
     *  empty square on the board. 
     *  
     *  @param board
     *  @return boolean is the move to an empty square on the board?
     ********************************************************************/
    private boolean isMoveToEmptySquare(ICheckersPiece[][] board)
    {   	
        if (this.isOnTheBoard(this.to) && board[to.row][to.column] == null ) {
        	return true;
        }
        return false;
    }

  
    
   
    
    /********************************************************************
     *  The fromLocationHasChessPiece method returns whether the from
     *  location square is occupied by a checkers piece.
     *  
     *  @param board
     *  @return boolean does the from location have a checkers piece?
     ********************************************************************/
    private boolean fromLocationHasCheckersPiece(ICheckersPiece[][] board )
    {
        if (board[from.row][from.column] != null) {
        	return true;
        }
        return false;
    }
    
    
    /**********************************************************************
     * This function is used to check whether a jump that a player makes
     * is backwards or not. Switches when a player reaches the end of the 
     * board
     *    * 
     * @param m
     * @return
     **********************************************************************/
    private boolean isBackwardsJump(ICheckersPiece[][] board) {
    	if(board[from.row][from.column].player() == Player.RED) {
    		if(to.row < 8 && (to.row > from.row)) {
    			return false;
    		}
    		
    	}
    	else if(board[from.row][from.column].player() == Player.BLACK){
    		if(to.row >= 0 && (to.row < from.row)) {
    			return false;
    		}    		
    	}
    	return true; 
    }

    /***********************************************************************
     * A valid move for any checker piece must satisfy the following general
     * requirements:
     *    1) Every move involves two distinctly different locations on the board;
     *    2) The "from" location must contain a checker piece;
     *    3) The "to" location is either exclusively empty or it contains a checker
     *       piece of the opposite color.
     **********************************************************************/
    /*********************************************************************
     * Checks if the move is basically valid.
     * 
     * @param n
     * @return boolean is the move basically valid?
     *********************************************************************/
    private boolean isBasicallyValid(ICheckersPiece[][] board)
    {        
    	if (isMoveToDifferentLocation() && (this.isOnTheBoard(this.from) && this.isOnTheBoard(this.to)) && 
    			fromLocationHasCheckersPiece(board) && (isMoveToEmptySquare(board))) {
    		return true;
    	}
    	return false;
    }
    
    
    /********************************************************************
     *  The isDiagonalMove method returns whether the move stays within
     *  the diagonal.The Diagonal Move should be all we need as checkers 
     *  only move diagonally.
     *  
     *  @return boolean is the move diagonal?
     ********************************************************************/
    private boolean isDiagonalMove() {
        Location distance = this.from.direction(this.to);
        
        if (Math.abs(distance.row) == Math.abs(distance.column)) {
        	return true;
        }
        return false;	// fix this
    }
    
    
    /************************************************************************
     * This function is used to determine when a piece reaches their 
     * respective end of the board. Then creates a king condition.
     * 
     * 
     * @param board
     * @return
     ************************************************************************/
    public boolean kingPiece(ICheckersPiece[][] board) {
    	if(to.row == 7 && to.column < 8 && (board[to.row][to.column].player() == Player.RED)) {    		
    		return true;
    	}
    	if(to.row == 0 && to.column < 8 && (board[to.row][to.column].player() == Player.BLACK)) {    		
    		return true;
    	}
    	return false;
    }
    

    /********************************************************************
     *  The isDiagonalMove method returns whether the move stays within
     *  the diagonal.The Diagonal Move should be all we need as checkers 
     *  only move diagonally. This is for Red Piece. Can move 1 to 2 
     *  spaces currently.
     *  
     *  @return boolean is this a diagonal move for a red piece?
     ********************************************************************/
    private boolean isDiagonalMoveForRedPiece() {
    	if (this.to.row - this.from.row == 1 && Math.abs(this.to.column - this.from.column) == 1)
    	{
    		return true;
    	}
   	return false;
    }
    
    /************************************************************************
     * Prevents the player from jumping the same player. 
     * 
     * @param board
     * @param m
     * @return
     ************************************************************************/
    private boolean jumpAlly(ICheckersPiece[][] board, Move m) {
    	String player1 = board[m.from.row][m.from.column].player().toString();
    	
    	String player2 = board[m.mid.row][m.mid.column].player().toString();
    	
    		if(player1 == player2)
    		{
    			return true;
    		}
    	return false;
    	}

    /********************************************************************
     *  The isDiagonalMoveForBlackPiece method returns whether the move 
     *  stays within the diagonal.The Diagonal Move should be all we need 
     *  as checkers only move diagonally. This is for the Black Piece.
     *  Can move 1 to 2 spaces currently 
     *  
     *  @return boolean is this a diagonal move for a black piece?
     ********************************************************************/
    private boolean isDiagonalMoveForGreenPiece() {
    	if ((this.to.row - this.from.row) == -1 && Math.abs(this.to.column - this.from.column) == 1)
    	{
    		return true;
    	}
    	return false;
    }
    
	private boolean isDiagonalMoveForKing() {
		if (((this.to.row - this.from.row) == -1 && Math.abs(this.to.column - this.from.column) == 1) 
				||( this.to.row - this.from.row == 1 && Math.abs(this.to.column - this.from.column) == 1 ))
    	{
    		return true;
    	}
    	return false;
	}
    /******************************************************************************
     * This function is in charge of checking whether or not the user is performing
     * a jump over another player. Checks all possible movements
     * 
     * @param m
     * @return
     *
     ******************************************************************************/
    
    protected boolean isJump(Move m) {
    	
    	if((m.from.row - m.to.row == 2) && (m.from.column - m.to.column == 2))  {
    		mid = new Location((from.row - 1),(from.column - 1));  
    		return true;
    	}
    	if((m.from.row - m.to.row == -2) && (m.from.column - m.to.column == -2)){
    		mid = new Location((from.row + 1),(from.column +1 ));
    		return true;	   		  		
    	}
    	if((m.from.row - m.to.row == 2) && (m.from.column - m.to.column == -2)) {
    		mid = new Location((from.row - 1),(from.column +1));
    		return true;
    	}
    	if((m.from.row - m.to.row == -2) && (m.from.column - m.to.column == 2)) {
    		mid = new Location((from.row + 1),(from.column -1));
    		return true;
    	}
    	return false;
    }
    
    /*******************************************************************************
     * The isValidMoveForBlackPawn method returns whether the move is a valid
     * diagonal move for a black checker piece.
     * 
     * @param board
     * @return boolean is this a valid move for a red piece?
     ******************************************************************************/
    protected boolean isValidMoveForRedPiece(ICheckersPiece[][] board, Move m)
    {   
    	if (isBasicallyValid(board)) {
            if (isMoveToEmptySquare(board) && isDiagonalMoveForRedPiece()) {
                return true;
            }
            if (isMoveToEmptySquare(board) && isMoveOverEmptySquares(board)) {
                if (isDiagonalMoveForRedPiece()) {
                    return true;
                }
            }
            if(isMoveToEmptySquare(board) && isJump(m) && !isBackwardsJump(board)) {
            	if(!jumpAlly(board,m)) {
            		return true;
            	}
            }
        }
        return false;
    }
    

    /*******************************************************************************
     * The isValidMoveForRedPawn method returns whether the move is a valid
     * diagonal move or a valid forward move for a Red checker piece.
     * 
     * @param board
     * @return boolean is this a valid move for a green piece?
     ******************************************************************************/
    protected boolean isValidMoveForGreenPiece(ICheckersPiece[][] board, Move m )
    {
    	if (isBasicallyValid(board)){
            if (isMoveToEmptySquare(board) && isDiagonalMoveForGreenPiece()) {
                return true;
            }
            if (isMoveToEmptySquare(board) && isMoveOverEmptySquares(board) && isDiagonalMoveForGreenPiece()) {                                
                return true;         
            }
            if(isMoveToEmptySquare(board) && isJump(m) && !isBackwardsJump(board)) {
            	if(!jumpAlly(board,m)) {
            		return true;
            	}
            }          
        }
        return false;
    }

	public boolean isValidMoveForBlackKing(ICheckersPiece[][] board, Move m) {
		if(isBasicallyValid(board) && isDiagonalMoveForKing()) {
			return true;
		}
		if(isBasicallyValid(board) && isMoveToEmptySquare(board) && isJump(m)) {
			if(!jumpAlly(board,m)){
				return true;
			}
		}
		return false;
	}
				
			
		
		

		public boolean isValidMoveForRedKing(ICheckersPiece[][] board, Move m) {		
			if(isBasicallyValid(board) && isDiagonalMoveForKing()) {
				return true;
			}
			if(isBasicallyValid(board) && isMoveToEmptySquare(board) && isJump(m)) {
				if(!jumpAlly(board,m)){
					return true;
				}
			}
			return false;		
	}
}
