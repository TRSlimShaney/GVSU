package modelPackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
/*********************************************************************
 * CheckersModel is the class to setup the CheckerBoard and 
 * initializes the board with the different checker pieces. 
 * 
 * @author 
 ***********************************************************************/
public class CheckersModel implements ICheckersModel {
    
    public Player player;                  /** current player */
    public int redJumps;
    public int blackJumps;
    
    private ICheckersPiece[][] boardPattern;   /** a startup board configuration */
    private ICheckersPiece[][] board;          /** the checkerboard in play */
    private ArrayList<ICheckersPiece> rPieces; /** all red pieces */
    private ArrayList<ICheckersPiece> gPieces; /** all green pieces */
    private ArrayList<Move> potentialMoves; /** stores Moves for AI play. */

    /**
     * messageCode is used in CheckersPanel for displaying the appropriate game
     * message; 
     * 0 = not in check, 
     * 1 = moving into check, 
     * 2 = currently in check, 
     * 3 = invalid regardless
     */
    private int messageCode;
    public boolean trace;   /** trace is used to switch whether the model will log game moves. */

    /*************************************************************
     * The CheckersModel constructor initializes the Checkers Game
     * location.
     * 
     * @param fileName the file to import game moves from (optional)
     *************************************************************/
    public CheckersModel(String fileName) {

        createChessPieces();
        
        redJumps = 0;
        blackJumps = 0;

        board = new ICheckersPiece[8][8];
        player = Player.BLACK;

        potentialMoves = new ArrayList<Move>();      
        //createGameMoves(fileName);
        
        reset();
    }

    /*************************************************************
     * The CheckersModel constructor initializes the Checkers Game
     * location.
     * 
     * @param
     * @return void
     *************************************************************/
    private void createChessPieces() {
        Player r = Player.RED;
        Player g = Player.BLACK;

        ICheckersPiece[][] boardPattern = {
                { new RedPiece(r), null, new RedPiece(r), null, new RedPiece(r), null, new RedPiece(r),
                    null },
                { null, new RedPiece(r), null, new RedPiece(r), null, new RedPiece(r), null,
                    new RedPiece(r) },
                { new RedPiece(r), null, new RedPiece(r), null, new RedPiece(r), null, new RedPiece(r), null }, { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null }, 
                { null, new GreenPiece(g), null, new GreenPiece(g), null, new GreenPiece(g), null, new GreenPiece(g) },
                { new GreenPiece(g), null, new GreenPiece(g), null, new GreenPiece(g), null, new GreenPiece(g), null },
                { null, new GreenPiece(g), null, new GreenPiece(g), null, new GreenPiece(g), null, new GreenPiece(g) }
        };
        this.boardPattern = boardPattern;

        rPieces = new ArrayList<ICheckersPiece>();
        for (int n = 2; n >= 0; n--) {
            for (int c = 0; c < 8; c++) {
            	if (boardPattern[n][c] != null) {
                boardPattern[n][c].setLocation(n, c);
                rPieces.add(boardPattern[n][c]);
            	}
            }
        }

        gPieces = new ArrayList<ICheckersPiece>();
        for (int n = 5; n <= 7; n++) {
            for (int c = 0; c < 8; c++) {
            	if (boardPattern[n][c] != null) {
            	boardPattern[n][c].setLocation(n, c);
            	gPieces.add(boardPattern[n][c]);
            	}
            }
        }
    }

    /*************************************************************
     * This method copies the boardPattern to the board,
     * one square at a time.
     * 
     * @param
     * @return void
     *************************************************************/
    private void reset() {
        for (int n = 0; n < 8; n++) {
            for (int c = 0; c < 8; c++) {
                board[n][c] = boardPattern[n][c];// fix me
            }
        }
        trace = true;
    }

    /*********************************************************************
     * Returns a Checkers Piece on the board.
     * 
     * @param s the location of the piece in question.
     * @return ICheckersPiece the piece in question.
     *********************************************************************/
    public ICheckersPiece pieceAt(Location s) {
        return board[s.row][s.column];
    }

    /*********************************************************************
     * Returns a Checkers Piece on the board.
     * 
     * @param row the row of the piece in question
     * @param col the column of the piece in question.
     * @return boolean is location within range of the board?
     *********************************************************************/
    public ICheckersPiece pieceAt(int row, int column ) {
        return board[row][column];
    }

    /*********************************************************************
     * Sets the piece on the board at a location and updates the board
     * to have the piece at that location.
     * 
     * @param s the location in question.
     * @param piece the piece in question.
     * @return boolean is location within range of the board?
     *********************************************************************/
    public void setPiece(Location s, ICheckersPiece piece) {  	
        if (0 <= s.row && s.row <= 7 && 0 <= s.column && s.column <= 7) {   
        	
        		if (piece != null) {
        		piece.setLocation(s.row, s.column);
        		}
        		board[s.row][s.column] = piece;
        }
    }
        
    
    /*********************************************************************
     * Return the board in play.
     * 
     * @return board the board
     *********************************************************************/
    protected ICheckersPiece[][] getBoard() {
    	return board;
    }

    /*********************************************************************
     * Return the current player.
     * 
     * @return Player the current player.
     *********************************************************************/
    public Player currentPlayer() {
        return player;
    }
    /***********************************************************************
     * Sets the next player in the game. 
     *     
     *     
     ***********************************************************************/
    public void changePlayers() {
    	if (this.player == Player.BLACK) {
    		this.player = Player.RED;
    	}
    	else if (this.player == Player.RED) {
    		this.player = Player.BLACK;
    	}
    }
    
    
    public int getMessage() {
        return messageCode;
    }

    /***********************************************************
     * This method moves the from piece to take the to piece.
     * 
     * @param m the move in question.
     * @return void
     ***********************************************************/
    public void move(Move m) {

        m.fromPiece = pieceAt(m.from); /** remembers the fromPiece */
        m.toPiece   = pieceAt(m.to);   /** remembers the toPiece */
        
        if(this.player == player.RED && isMoveOverOpponentPiece(m)){
   		 	redJumps++;
        }
   	 
   	    if(this.player == player.BLACK && isMoveOverOpponentPiece(m)) {
   	    	blackJumps++;
   	    }   	
   	         
        if (m.toPiece != null) {
            m.toPiece.setLocation(-1, -1); /** removes the m.toPiece from the board */
        }
                     
        setPiece(m.to, m.fromPiece);  /** moves the "m.fromPiece" to the "m.to" location */
        setPiece(m.from, null);       /** the board at the "m.from" location no longer contains a piece. */
    }

    /*****************************************************************
     * The undo method restores the chess board model to the previous
     * state, the state just prior to the move.
     * 
     * @param previousMove the previous move
     * @return void
     *****************************************************************/
    public void undo(Move previousMove) {
        setPiece(previousMove.from, previousMove.fromPiece);
        setPiece(previousMove.to,   previousMove.toPiece);
    }
    
    /********************************************************************
    * The isMoveToOpponentPiece method returns whether the move is to
    * remove an opposing piece
    *  
    * @param board
    * @return boolean is this a move over an opponent piece?
    ********************************************************************/
    public boolean isMoveOverOpponentPiece(Move m)
    {      
     if(m.isJump(m) && (this.player != player.next())) {   	 
    	 board[m.mid.row][m.mid.column] = null;
    	 return true; 
     }       		    		    	  	      		
      return false;
    }
        
    public boolean isKing(Move m) {    	
    	if(player == Player.BLACK && m.kingPiece(board)) {
    		board[m.to.row][m.to.column] = (CheckersPiece) new BlackKing(player.BLACK);
    		gPieces.add(board[m.to.row][m.to.column]);
    		System.out.println(m.fromPiece.getClass().toString());
    		return true;
    	}
    	if(player == Player.RED && m.kingPiece(board)) {
    		board[m.to.row][m.to.column] = (CheckersPiece) new RedKing(player.RED);
    		rPieces.add(board[m.to.row][m.to.column]);
    		System.out.println(m.fromPiece.getClass().toString());
    		return true;
    	}
    	return false;
    	
    }
    
    /********************************************************************
     * Is the move valid for the piece in question?
     * 
     * @param m the move in question
     * @return boolean is the move valid?
     ******************************************************************/ 
    public boolean isValidMove(Move m) {
      
        boolean valid = true;
        
        CheckersPiece fromPiece = (CheckersPiece) pieceAt(m.from);

        if (fromPiece == null) {
            return false;
        }
        
        if (!fromPiece.isValidMove(m, board)) {
        	return false;
        }
  
                return valid;
    }

    /*********************************************************************
     * Take inventory of the pieces and print them out.
     * 
     * @return void
     *********************************************************************/
    public void takeInventory() {
    	for (ICheckersPiece bcp : rPieces) {
    		System.out.println(bcp.toString());
    	}
    	
    	for (ICheckersPiece wcp : gPieces) {
    		System.out.println(wcp.toString());
    	}
    }

    /*********************************************************************
     * Checks if a move is viable for the AI to try to make.
     * 
     * @return Move the move the AI should take.
     *********************************************************************/
    private Move getGameMove(ArrayList<ICheckersPiece> fromPieces, ArrayList<ICheckersPiece> toPieces) {
        if (potentialMoves.size() > 0) {
            Move tentativeMove = potentialMoves.get(0);
            if (isValidMove(tentativeMove)) {
                move(tentativeMove);
                if (moveEffectiveness() == null) {
                    undo(tentativeMove);
                    tentativeMove = potentialMoves.remove(0);
                    return tentativeMove;
                } else {
                    undo(tentativeMove);
                }
            }
        }
        return attackPieces(fromPieces, toPieces);
    }

    /*********************************************************************
     * This method control the AI.
     * 
     * @return Move the move the AI should take.
     *********************************************************************/
    public Move AI() {
            return attack();       
    }

    private Move attack() {
        Move tentativeMove = null;

        tentativeMove = getGameMove(rPieces, gPieces);
            
        if (tentativeMove != null) {
            return tentativeMove;
        }

        tentativeMove = attackPieces(rPieces, gPieces);
        if (tentativeMove != null) {
            return tentativeMove;
        }

        tentativeMove = attackWithCaution();
        if (tentativeMove != null) {
            return tentativeMove;
        }
        return attackBoard();
    }

    /*********************************************************************
     * Determine the AI move effectiveness.
     * 
     * @return Move the move the AI should take.
     *********************************************************************/
    private Move moveEffectiveness() {
        Move nextMove;
        nextMove = attackPieces(gPieces, rPieces);
        if (nextMove != null) {
            return nextMove;
        }
        return null;
    }

    /*********************************************************************
     * This method controls the AI move when there is no attack to be made.
     * 
     * @return Move the move the AI should take.
     *********************************************************************/
    public Move attackWithCaution() {
        Location fromLocation = new Location(-1, -1);
        Location toLocation = new Location(-1, -1);
        Location offBoard = new Location(-1, -1);
        Move tentativeMove = new Move(fromLocation, toLocation);
        Random rand23 = new Random();

        ArrayList<ICheckersPiece> pieces;
        CheckersPiece piece;

        if (player == Player.RED) {
            pieces = rPieces;
        } else {
            pieces = gPieces;
        }
        
        piece = ((CheckersPiece) pieces.get(rand23.nextInt(16) ));
        while (piece.location.equals(offBoard)) {
        	piece = ((CheckersPiece) pieces.get(rand23.nextInt(16) ));
        }
        fromLocation = ((CheckersPiece) piece).location;
       	 
       	 for (int r = 0; r < 8; r++) {      		     		
       		 for (int c = 0; c < 8; c++) {
       			 
       			 toLocation.set(r, c);
       			 tentativeMove = new Move(fromLocation, toLocation);
       			 
       			 if (piece.isValidMove(tentativeMove, board)) {
       				 return tentativeMove;
       			 }
       		 }	 
       	 }
        return null;
    }

    /*********************************************************************
     * Not entirely sure what this method does.
     * 
     * @return Move
     *********************************************************************/
    public Move attackBoard() {
        Move tentativeMove;
        Location fromLocation = new Location(-1, -1);
        Location toLocation = new Location(-1, -1);

        ArrayList<ICheckersPiece> pieces;
        CheckersPiece piece;

        if (player == Player.RED){
            pieces = rPieces;     
        } 
        else{
            pieces = gPieces;
        }       
        return null;
    }

    /*********************************************************************
     * Responsible for the AI to determine its potential
     * attack moves.
     * 
     * @return Move the attack move.
     *********************************************************************/
    private Move attackPieces(ArrayList<ICheckersPiece> fromPieces, ArrayList<ICheckersPiece> toPieces) {
        Move tentativeMove;
        Location fromLocation = new Location(-1, -1);
        Location toLocation = new Location(-1, -1);

        	 for (ICheckersPiece icp : fromPieces) // An array list of the player's pieces
             {
             	fromLocation = ((CheckersPiece) icp).location;
             	
                  for (ICheckersPiece icp2 : toPieces) {
                 	toLocation = ((CheckersPiece) icp2).location;
                 	tentativeMove = new Move(fromLocation, toLocation);
                    if (icp.isValidMove(tentativeMove, board)) {
                   	 return tentativeMove;
                  }                 
                }
             }
       return null;
    }
}