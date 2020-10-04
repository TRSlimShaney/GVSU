package modelPackage;

import javax.swing.ImageIcon;
import java.util.Scanner;
/*************************************************************************************
 * Move is a class definition for a chess piece move.  Two board squares are involved
 * in a move:
 *      1) a from square
 *      2) a to square
 * 
 * Let the chess piece being moved be denoted as the fromPiece, and let the chess piece
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

    public IChessPiece fromPiece; // stores the "from" chess piece in the move
    public IChessPiece toPiece;   // stores the "to" chess piece, if one exists, in the move

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
     *  @return
     *********************************************************/
    public String toString() {
        return " " + from.toString() + " " + to.toString();
    }

    private boolean isWithinRange( int n )
    {
        return 0 <= n && n < 8;
    }

    private boolean isOnTheBoard( Location s )
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
     *  @return boolean
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
     *  @return boolean
     **************************************************************************/
    private boolean squareIsOccupied( Location square, IChessPiece[][] board ) {
        
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
     *  @return boolean
     **************************************************************************/
    private boolean isMoveOverEmptySquares(IChessPiece[][] board) {
    	boolean movePathClear = true;
    	
    	Location currLocation = new Location(this.from);
    	
    	if (isMoveToOpponentPiece(board)) {
    		while (movePathClear && !currLocation.equals(this.to)) {
    			currLocation.plus(currLocation.unitDirection(this.to));
    			
    			if (this.squareIsOccupied(currLocation, board) && !(currLocation.equals(this.to))) {
    				movePathClear = false;
    			}
    		}
    	}
    	else {
    		while (movePathClear && !currLocation.equals(this.to)) {
    			currLocation.plus(currLocation.unitDirection(this.to));
    			
    			if (this.squareIsOccupied(currLocation, board)) {
    				movePathClear = false;
    			}
    		}
    		
    	}
    	return movePathClear;
    	}
    	

    /********************************************************************
     *  The isMoveToEmptySquare method returns whether the move is to an
     *  empty square on the board. 
     *  
     *  @param board
     *  @return
     ********************************************************************/
    private boolean isMoveToEmptySquare(IChessPiece[][] board )
    {
        if (this.isOnTheBoard(this.to) && board[to.row][to.column] == null ) {
        	return true;
        }
        return false;
    }

    /********************************************************************
     * The isMoveToOpponentPiece method returns whether the move is to
     * remove an opposing piece
     *  
     * @param board
     * @return
     ********************************************************************/
    private boolean isMoveToOpponentPiece(IChessPiece[][] board )
    {
        if (board[to.row][to.column] != null && board[to.row][to.column].player() != board[from.row][from.column].player()) {
        	return true;
        }
        return false;
    }

    /********************************************************************
     *  The fromLocationHasChessPiece method returns whether the from
     *  location square is occupied by a chess piece.
     *  
     *  @param board
     *  @return
     ********************************************************************/
    private boolean fromLocationHasChessPiece(IChessPiece[][] board )
    {
        if (board[from.row][from.column] != null) {
        	return true;
        }
        return false;
    }

    /***********************************************************************
     * A valid move for any chess piece must satisfy the following general
     * requirements:
     *    1) Every move involves two distinctly different locations on the board;
     *    2) The "from" location must contain a chess piece;
     *    3) The "to" location is either exclusively empty or it contains a chess
     *       piece of the opposite color.
     **********************************************************************/    
    private boolean isBasicallyValid( IChessPiece[][] board )
    {
        
    	if (isMoveToDifferentLocation() && (this.isOnTheBoard(this.from) && this.isOnTheBoard(this.to)) && fromLocationHasChessPiece(board) && (isMoveToEmptySquare(board) || isMoveToOpponentPiece(board))) {
    		return true;
    	}
    	return false;
    }

    /********************************************************************
     * The isHorizontalMove method returns whether the move stays
     * within the row.
     *  
     * @return
     ********************************************************************/
    private boolean isHorizontalMove() {
	
    	if (to.row - from.row == 0) {
    		return true;
    	}
    	
        return false;	// fix this
    }

    /*********************************************************************
     *  This isVerticalMove method returns whether the move stays within
     *  the column.
     *  
     *  @return
     *********************************************************************/
    private boolean isVerticalMove() {
    	if (to.column - from.column == 0) {
    		return true;
    	}
        return false;	// fix this
    }

    /********************************************************************
     *  The isDiagonalMove method returns whether the move stays within
     *  the diagonal.
     *  
     *  @return
     ********************************************************************/
    private boolean isDiagonalMove() {
        Location distance = this.from.direction(this.to);
        
        if (Math.abs(distance.row) == Math.abs(distance.column)) {
        	return true;
        }

        return false;	// fix this
    }

    /**************************************************************************
     * The follow methods pertain to moves for Black and White pawns. 
     * 
     * The rows on a chess board are numbered from 0 to 7. A chess game starts
     * with the black pawns in row 1 and the white pawns in row 6.
     * 
     * In addition to satisfying the general requirements for moving a chess 
     * piece, a pawn can move forward one square, two squares for a white pawn
     * from row 6 and two squares for a black pawn from row 2, but only if no
     * other piece is in its way. A pawn can move diagonally one unit, but only
     * to take a piece.
     **************************************************************************/

    /********************************************************************
     *  This isVerticalMove method returns whether the move is forward
     *  n rows.
     *  
     *  @param n
     *  @return
     ********************************************************************/
    private boolean isVerticalMove(int n) {
    	Location direction = this.from.direction(this.to);
    	
//        if ((isVerticalMove() || isDiagonalMove()) && isMoveToDifferentLocation() && Math.abs(direction.row) == n) {
//        	return true;
//        }
    	
        return (n == (this.to.row - this.from.row));
    }

    /********************************************************************
     *  The isForwardMoveForBlackPawn method returns whether the move
     *  is forward over n rows.
     *  
     *  @param n
     *  @return
     ********************************************************************/
    private boolean isForwardMoveForBlackPawn(int n) {

    	if (n == 1 || n == 2 && from.row == 1) 
        {
            return this.isVerticalMove( n );
        }
        else
        {
            return false;
        }
    }

    /********************************************************************
     *  The isForwardMoveForWhitePawn method returns whether the move
     *  is forward over n rows.
     *  
     *  @param n
     *  @return
     ********************************************************************/
    private boolean isForwardMoveForWhitePawn(int n) {
    	
    	if (n == 1 || n == 2 && from.row == 6) 
        {
            return this.isVerticalMove( -n );
        }
        else
        {
            return false;
        }
    }

    /********************************************************************
     *  The isDiagonalMoveForBlackPawn method returns whether the move
     *  is forward one row and diagonal one column.
     *  
     *  @return
     ********************************************************************/
    private boolean isDiagonalMoveForBlackPawn() {
    	if (this.to.row - this.from.row == 1 && Math.abs(this.to.column - this.from.column) == 1){
    		return true;
    }
    return false;
    }

    /********************************************************************
     *  The isDiagonalMoveForWhitePawn method returns whether the move
     *  is forward one row and diagonal one column.
     *  
     *  @return
     ********************************************************************/
    private boolean isDiagonalMoveForWhitePawn() {
    	if (this.to.row - this.from.row == -1 && Math.abs(this.to.column - this.from.column) == 1){
    		return true;
    }
    return false;
}
    

    /*******************************************************************************
     * The isValidMoveForBlackPawn method returns whether the move is a valid
     * diagonal move or a valid forward move for a black pawn.
     * 
     * @param board
     * @return
     ******************************************************************************/
    protected boolean isValidMoveForBlackPawn(IChessPiece[][] board )
    {
    	if ( isBasicallyValid( board ) ) {

            if (isDiagonalMoveForBlackPawn( ) && isMoveToOpponentPiece(board)) {
                return true;
            }
            if (isMoveToEmptySquare(board) && isVerticalMove())
            {
                if (isForwardMoveForBlackPawn(1)) {
                    return true;
                }

                if (isForwardMoveForBlackPawn(2) && isMoveOverEmptySquares( board ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /*******************************************************************************
     * The isValidMoveForWhitePawn method returns whether the move is a valid
     * diagonal move or a valid forward move for a white pawn.
     * 
     * @param board
     * @return
     ******************************************************************************/
    protected boolean isValidMoveForWhitePawn(IChessPiece[][] board )
    {
    	if ( isBasicallyValid( board ) ) {

            if (isDiagonalMoveForWhitePawn( ) && isMoveToOpponentPiece(board)) {
                return true;
            }
            if (isMoveToEmptySquare(board) && isVerticalMove())
            {
                if (isForwardMoveForWhitePawn(1)) {
                    return true;
                }

                if (isForwardMoveForWhitePawn(2) && isMoveOverEmptySquares( board ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**************************************************************************
     * The follow methods pertain to moves for chess pieces, other than pawns. 
     **************************************************************************/

    /**********************************************************************
     * A valid move for a Knight must satisfy the following requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to either 
     *          A) two rows and one column, or
     *          B) one row and two columns.
     * 
     * @param board
     * @return
     **********************************************************************/    
    protected boolean isValidMoveForKnight(IChessPiece[][] board)
    {
        Location distance = from.direction( to );

        return (    
            isBasicallyValid( board ) &&
            (Math.abs(distance.row ) == 2 && Math.abs(distance.column) == 1 ||
            Math.abs(distance.row ) == 1 && Math.abs(distance.column) == 2)
        );
    }

    /*******************************************************************
     * A valid move for a King must satisfy the following requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to one square in
     *       any direction.
     * 
     * @param board
     * @return
     *****************************************************************/
    protected boolean isValidMoveForKing(IChessPiece[][] board)
    {
    	Location distance = from.direction( to );
    	
        return(
        		isBasicallyValid(board) && 
        		((Math.abs(distance.row) == 1 && Math.abs(distance.column) == 0) ||
        		(Math.abs(distance.row) == 0 && Math.abs(distance.column) == 1) ||
        		(Math.abs(distance.row) == 1 && Math.abs(distance.column) == 1))
        		);

    }

    /*********************************************************************
     * A valid move for a Bishop on the board must satisfy the following
     * requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to a diagonal;
     *    3) The locations strictly between the "from" location and the
     *       "to" location must be empty (unoccupied).
     * 
     * @param board
     * @return
     **********************************************************************/    
    protected boolean isValidMoveForBishop( IChessPiece[][] board )
    {
	
        return isBasicallyValid(board) && isDiagonalMove() && isMoveOverEmptySquares(board);

    }

    /***********************************************************************
     * A valid move for a Rook must satisfy the following requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to either a row or a
     *       column;
     *    3) The locations strictly between the "from" location and the
     *       "to" location must be empty (unoccupied).
     * 
     * @param board
     * @return
     ***********************************************************************/   
    protected boolean isValidMoveForRook( IChessPiece[][] board )
    {
        return (
            isBasicallyValid(board) && (isHorizontalMove() || isVerticalMove()) && isMoveOverEmptySquares(board));  
    }

}
