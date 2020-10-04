package modelPackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
/*********************************************************************
 * ChessModel is the class for .... 
 * 
 *         <complete the documentation>
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author 
 *
 ***********************************************************************/
public class ChessModel implements IChessModel {
    private IChessPiece[][] board;          /** the chess board in play */
    public Player player;                  /** current player */
    private IChessPiece[][] boardPattern;   /** a startup board configuration */

    private IChessPiece bKing;              /** black king must be known */
    private IChessPiece wKing;              /** white king must be known */

    private ArrayList<IChessPiece> bPieces; /** all black pieces */
    private ArrayList<IChessPiece> wPieces; /** all white pieces */

    private ArrayList<Move> potentialMoves; /** stores Moves for AI play. */

    /**
     * messageCode is used in ChessPanel for displaying the appropriate game
     * message; 
     * 0 = not in check, 
     * 1 = moving into check, 
     * 2 = currently in check, 
     * 3 = invalid regardless
     */
    private int messageCode;
    public boolean trace;   /** trace is used to switch whether the model will log game moves. */

    public ChessModel(String fileName) {

        createChessPieces();

        board = new IChessPiece[8][8];
        player = Player.WHITE;

        potentialMoves = new ArrayList<Move>();      
        createGameMoves(fileName);

        reset();
    }

    private void createChessPieces() {
        Player b = Player.BLACK;
        Player w = Player.WHITE;

        IChessPiece[][] boardPattern = {
                { new Rook(b), new Knight(b), new Bishop(b), new Queen(b), new King(b), new Bishop(b), new Knight(b),
                    new Rook(b) },
                { new BPawn(b), new BPawn(b), new BPawn(b), new BPawn(b), new BPawn(b), new BPawn(b), new BPawn(b),
                    new BPawn(b) },
                { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
                { new WPawn(w), new WPawn(w), new WPawn(w), new WPawn(w), new WPawn(w), new WPawn(w), new WPawn(w),
                    new WPawn(w) },
                { new Rook(w), new Knight(w), new Bishop(w), new Queen(w), new King(w), new Bishop(w), new Knight(w),
                    new Rook(w) }, };
        this.boardPattern = boardPattern;

        bKing = boardPattern[0][4];
        bPieces = new ArrayList<IChessPiece>();
        for (int r = 1; r >= 0; r--) {
            for (int c = 0; c < 8; c++) {
                boardPattern[r][c].setLocation(r, c);
                bPieces.add(boardPattern[r][c]);
            }
        }

        wKing = boardPattern[7][4];
        wPieces = new ArrayList<IChessPiece>();
        for (int r = 6; r <= 7; r++) {
            for (int c = 0; c < 8; c++) {
            	boardPattern[r][c].setLocation(r, c);
            	wPieces.add(boardPattern[r][c]);
                
            }
        }
    }

    /**********************************************
     * The reset method copies the boardPattern to 
     * the chess board, one square at a time.
     **********************************************/
    private void reset() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c] = boardPattern[r][c];// fix me
            }
        }
        trace = true;
    }

    /*************************************************
     * This method promotes a pawn to a queen.
     *************************************************/
    public boolean promote(Location s) {
        if (pieceAt(s).type().equals("Pawn")) {
            if (pieceAt(s).player().equals(Player.WHITE) && s.row == 0) {
                setPiece(s, new Queen(Player.WHITE));

                wPieces.add(pieceAt(s));

                return true;
            } else if (pieceAt(s).player().equals(Player.BLACK) && s.row == 7) {
                setPiece(s, new Queen(Player.BLACK));

                bPieces.add(pieceAt(s));

                return true;
            }
        }
        return false;
    }

    public IChessPiece pieceAt(Location s) {
        return board[s.row][s.column];
    }

    public IChessPiece pieceAt(int row, int column ) {
        return board[row][column];
    }

    /**
     * This method not only sets the piece on the board as location s,
     * but it also sets the location of that piece to location s.
     * 
     */
    public void setPiece(Location s, IChessPiece piece) {
    	
        if (0 <= s.row && s.row <= 7 && 0 <= s.column && s.column <= 7) {
        		
        		if (piece != null) {
        			piece.setLocation(s.row, s.column);
        		}
        		board[s.row][s.column] = piece;
        }
    }

    public Player currentPlayer() {
        return player;
    }

    public void setNextPlayer() {
        player.next();
    }
    
    public void changePlayers() {
    	if (this.player == Player.WHITE) {
    		this.player = Player.BLACK;
    	}
    	else if (this.player == Player.BLACK) {
    		this.player = Player.WHITE;
    	}
    }

    public int getMessage() {
        return messageCode;
    }

    /***********************************************************
     * The move method first saves the state of the chess piece
     * involved in the move,
     * 
     *          move.fromPiece & move.toPiece
     * 
     * before it makes the move.
     ***********************************************************/
    public void move(Move m) {

        m.fromPiece = pieceAt(m.from); /** remembers the fromPiece */
        m.toPiece   = pieceAt(m.to);   /** remembers the toPiece */

        if (m.toPiece != null) {
            m.toPiece.setLocation(-1, -1); /** removes the m.toPiece from the board */
        }

        setPiece(m.to, m.fromPiece);  /** moves the "m.fromPiece" to the "m.to" location */
        setPiece(m.from, null);       /** the board at the "m.from" location no longer contains a piece. */
    }

    /*****************************************************************
     * The undo method restores the chess board model to the previous
     * state, the state just prior to the move.
     *****************************************************************/
    public void undo(Move previousMove) {
        setPiece(previousMove.from, previousMove.fromPiece);
        setPiece(previousMove.to,   previousMove.toPiece);
    }

    /********************************************************************
     * The isValidMove method identifies the chess piece and checks if
     * the move is valid for that particular piece. The move by a player
     * is valid means that its king, after the move, is not in check.
     * 
     * If the move is valid within the context of the particular chess
     * piece, the method tests whether or not the move is valid within
     * the context of the game overall. This is accomplished with the
     * following sequence:
     * 
     * 1) Locally save/remember whether the king is in check before the move.
     * 2) Make the move to change the model. 
     * 3) Locally save/remember whether the king is in check after the move. 
     * 4) Undo the move to restore the model to the previous state.
     * 
     * From the above, the method determines, as a method side effect,
     * the most appropriate message code and finally returns the result
     * for whether or not the move is valid, i.e. true or false.
     * 
     * messageCode index:
     * 
     * 0: no message, no error 
     * 1: "Invalid move; the King is placed in check."
     * 2: "Invalid move; the King remains in check." 
     * 3: "" + piece.type() + ":  Invalid move."
     * 
     ******************************************************************/
    public boolean isValidMove(Move m) {
        boolean inCheckBefore = false;
        boolean inCheckAfter = false;
        boolean valid = true;
        

        ChessPiece fromPiece = (ChessPiece) pieceAt(m.from);

        // valid = board[m.from.row][m.from.column].isValidMove(m, board);
        if (fromPiece == null) {
            return false;
        }
        
        if (!fromPiece.isValidMove(m, board)) {
        	return false;
        }
        
        inCheckBefore = inCheck(player);      

        if (inCheckBefore) {
        	//JOptionPane.showMessageDialog(null, "The king is now in check.", "King Check Status", JOptionPane.INFORMATION_MESSAGE);
            if(isCheckMate()) {
             	JOptionPane.showMessageDialog(null, "Check Mate", "King Check Status", JOptionPane.INFORMATION_MESSAGE);
             	
            }
        	move(m);
        	inCheckAfter = inCheck(player);
        	if (inCheckAfter == false) {
        		undo(m);
            	return true;
            	
            }
        	else {
        		JOptionPane.showMessageDialog(null, "The king is still in check.", "King Check Status", JOptionPane.INFORMATION_MESSAGE);
        		undo(m);
        		return false;
        	}
        }
        else {
        	move(m);
        	inCheckAfter = inCheck(player);
        	if (inCheckAfter == false) {
        		undo(m);
            	return true;
            	
            }
        	else {
        		JOptionPane.showMessageDialog(null, "The king is placed in check.", "King Check Status", JOptionPane.INFORMATION_MESSAGE);
        		undo(m);
        	}
        }
        return valid;
    }

    /***********************************************************
     * If the Rook move has happened, is it possible to castle?
     ***********************************************************/
    public Move castleMove(Move m) {

        Location whiteKing = new Location(7, 4);
        Location blackKing = new Location(0, 4);

        if (pieceAt(m.to).type().equals("Rook")) {
            Location unitLocation = m.from.unitDirection(m.to);
            if (unitLocation.row == 0) {
                Location kingLocation = new Location(m.to);
                kingLocation.plus(unitLocation);
                if (kingLocation.equals(whiteKing) || kingLocation.equals(blackKing)) {
                    if (pieceAt(kingLocation) != null && pieceAt(kingLocation).type().equals("King")) {
                        Location destination = new Location(m.to);
                        destination.minus(unitLocation);
                        Move castle = new Move(kingLocation, destination);
                        return castle;
                    }
                }
            }
        }
        return null;
    }

    /***************************************************************************
     * The king is in check if a valid move exists for at least one chess piece,
     * on the offense, from its current location on the board to the location
     * occupied by the king.
     * 
     * @param king   is of one color
     * @param pieces are of the other color
     * @return
     ***************************************************************************/
    private boolean inCheck(IChessPiece king, ArrayList<IChessPiece> pieces) {
        Move     pieceToKingMove;
        Location pieceLocation;
        Location kingLocation;
        Location offBoard = new Location(-1, -1);

        kingLocation = ((ChessPiece) king).location;
        for (IChessPiece cp : pieces) {
        	
            
            if (((ChessPiece) cp).location.equals(offBoard)) {
            	continue;
            }
            pieceLocation = ((ChessPiece) cp).location;
            
            pieceToKingMove = new Move(pieceLocation, kingLocation);
            
            if (cp.isValidMove(pieceToKingMove, board)) {
            	return true;
            }
        }
        return false;
    }

    public void takeInventory() {
    	for (IChessPiece bcp : bPieces) {
    		System.out.println(bcp.toString());
    	}
    	
    	for (IChessPiece wcp : wPieces) {
    		System.out.println(wcp.toString());
    	}
    }
    /************************************************************************
     * This inCheck method identifies the king in question, and the opponent
     * chess pieces, to determine whether or not it is in check.
     ************************************************************************/
    public boolean inCheck(Player p) {
        IChessPiece king;                       /** the king under attack */
        ArrayList<IChessPiece> opposingPieces;  /** Opposing color pieces */

        if (p == Player.BLACK) {
            king           = bKing;     /** the king in question  */
            opposingPieces = wPieces;   /** opponent chess pieces */
        } else {
            king           = wKing;     /** the king in question  */
            opposingPieces = bPieces;   /** opponent chess pieces */
        }

        return inCheck(king, opposingPieces);
    }

    /***************************************************************************
     * The isCheckMate method is a check for the defense.
     * 
     * The state of the king is check mate if no valid move on the chess board
     * model exists for any chess piece of the same color as the king in
     * question.
     * 
     * For each chess piece in the ArrayList of pieces for that color, that are
     * still on the chess board, this method does an exhaustive search to
     * find a single valid move from its current location, i.e. to any other
     * location on the board, so that the king of its color is not in check.
     * 
     * @return
     ****************************************************************************/
    public boolean isCheckMate() {
        Move     defensiveMove;
        Location fromLocation = new Location(-1, -1);
        Location toLocation = new Location(-1, -1);
        Location offBoard = new Location(-1, -1);
        int count5 = 0;

        ArrayList<IChessPiece> allPiecesOnDefense;
        ChessPiece pieceOnDefense;

        if (inCheck(player)) // if current player is in check
        {
            if (player == Player.BLACK) {
                allPiecesOnDefense = bPieces;
            } else {
                allPiecesOnDefense = wPieces;
            }

            for (IChessPiece icp : allPiecesOnDefense) // An array list of the player's pieces
            {
            	pieceOnDefense = (ChessPiece)icp;
                
                if (pieceOnDefense.location.equals(offBoard)) {
                	continue;
                }
                
                fromLocation = pieceOnDefense.location;
                
                for (int r = 0; r < 8; r++) {
                	for (int c = 0; c < 8; c++ ) {
                		toLocation.set(r, c);
                		defensiveMove = new Move(fromLocation, toLocation);
                		
                		if (pieceOnDefense.isValidMove(defensiveMove, board)) {
                			count5++;
                		}
                	}
                }      
            }
            if (count5 > 0) {
            return false;
            }
            else {
            	return true;
            }
        }
        else {
        	return false;
        }
    }

    //     private void addGameMoves() {
    //         Location fromLocation = new Location(0, 6);
    //         Location toLocation = new Location(2, 5);
    //         Move move1 = new Move(fromLocation, toLocation);
    //         potentialMoves.add(move1);
    // 
    //         fromLocation = new Location(1, 6);
    //         toLocation = new Location(2, 6);
    //         move1 = new Move(fromLocation, toLocation);
    //         potentialMoves.add(move1);
    // 
    //         fromLocation = new Location(0, 5);
    //         toLocation = new Location(1, 6);
    //         move1 = new Move(fromLocation, toLocation);
    //         potentialMoves.add(move1);
    // 
    //         fromLocation = new Location(0, 7);
    //         toLocation = new Location(0, 5);
    //         move1 = new Move(fromLocation, toLocation);
    //         potentialMoves.add(move1);
    // 
    //         fromLocation = new Location(1, 4);
    //         toLocation = new Location(3, 4);
    //         move1 = new Move(fromLocation, toLocation);
    //         potentialMoves.add(move1);
    // 
    //         /**
    //          * Need to add more game moves to make the game more interesting and
    //          * challenging!
    //          */
    //     }

    private void addGameMoves() {
        potentialMoves.add(new Move("0g2f"));
        potentialMoves.add(new Move("1g2g"));
        potentialMoves.add(new Move("0f1g"));
        potentialMoves.add(new Move("0h0f"));
        potentialMoves.add(new Move("1e3e"));
        potentialMoves.add(new Move("1c3c"));

        /**
         * Add more game moves for the AI to make the game more interesting and more
         * agressive!
         */
    }

    private void createGameMoves(String fileName) {

        if (fileName != null && fileName.length() > 0) {
            this.read(fileName);
        }
        else {
            addGameMoves();
        }
    }

    public void read(String filename) {
        try {
            FileInputStream fileByteStream = new FileInputStream(filename);
            Scanner scnr = new Scanner(fileByteStream);
            scnr.useDelimiter(" \r\n");

            while (scnr.hasNext()) {
                potentialMoves.add(new Move(scnr.nextLine()));
            }
            scnr.close();
            fileByteStream.close();
        } catch (IOException e) {
            System.out.println("Failed to read the data file: " + filename);

            addGameMoves();
        }
    }

    private Move getGameMove(ArrayList<IChessPiece> fromPieces, ArrayList<IChessPiece> toPieces) {
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

    /*******************************************************************************************
     * You are welcome, and even encouraged, to design your own AI from scratch.
     * 
     *******************************************************************************************/
    public Move AI() {

        /** Write a simple AI set of rules in the following order. a. Check to see if you
         * are in check. i. If so, get out of check by moving the king or placing a
         * piece to block the check
         * 
         * b. Attempt to put opponent into check (or checkmate). i. Attempt to put
         * opponent into check without losing your piece ii. Perhaps you have won the
         * game.
         *
         * c. Determine if any of your pieces are in danger, i. Move them if you can.
         * ii. Attempt to protect that piece.
         *
         * d. Move a piece (pawns first) forward toward opponent king i. check to see if
         * that piece is in danger of being removed, if so, move a different piece.
         */

        if (isCheckMate()) {
            System.out.println("Checkmate!!");
            return null;
        } else {
            return attack();
        }
    }

    private Move attack() {
        Move tentativeMove = null;

        if (!inCheck(Player.BLACK)) {
            tentativeMove = getGameMove(bPieces, wPieces);
        }
        if (tentativeMove != null) {
            return tentativeMove;
        }

        tentativeMove = attackKing(bPieces, Player.WHITE);
        if (tentativeMove != null) {
            return tentativeMove;
        }

        tentativeMove = attackPieces(bPieces, wPieces);
        if (tentativeMove != null) {
            return tentativeMove;

        }

        tentativeMove = attackWithCaution();
        if (tentativeMove != null) {
            return tentativeMove;

        }

        return attackBoard();
    }

    /*****************************************************
     * The moveThreat/moveEffectiveness method returns 
     *****************************************************/
    private Move moveEffectiveness() {
        Move nextMove;
        nextMove = attackPieces(wPieces, bPieces);
        if (nextMove != null) {
            return nextMove;
        }

        nextMove = attackKing(wPieces, Player.BLACK);
        if (nextMove != null) {
            return nextMove;
        }
        return null;
    }

    public Move attackWithCaution() {
        Location fromLocation = new Location(-1, -1);
        Location toLocation = new Location(-1, -1);
        Location offBoard = new Location(-1, -1);
        Move tentativeMove = new Move(fromLocation, toLocation);
        Random rand23 = new Random();

        ArrayList<IChessPiece> pieces;
        ChessPiece piece;

        if (player == Player.BLACK) {
            pieces = bPieces;
        } else {
            pieces = wPieces;
        }
        
        piece = ((ChessPiece) pieces.get(rand23.nextInt(16) + 0));
        while (piece.location.equals(offBoard)) {
        	piece = ((ChessPiece) pieces.get(rand23.nextInt(16) + 0));
        }
        fromLocation = ((ChessPiece) piece).location;
       	 
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

    public Move attackBoard() {
        Move tentativeMove;
        Location fromLocation = new Location(-1, -1);
        Location toLocation = new Location(-1, -1);

        ArrayList<IChessPiece> pieces;
        ChessPiece piece;

        if (player == Player.BLACK) {
            pieces = bPieces;
            
        } else {
            pieces = wPieces;
        }

       
        return null;
    }

    private Move attackPieces(ArrayList<IChessPiece> fromPieces, ArrayList<IChessPiece> toPieces) {
        Move tentativeMove;
        Location fromLocation = new Location(-1, -1);
        Location toLocation = new Location(-1, -1);

        	 for (IChessPiece icp : fromPieces) // An array list of the player's pieces
             {
             	fromLocation = ((ChessPiece) icp).location;
             	
                  for (IChessPiece icp2 : toPieces) {
                 	toLocation = ((ChessPiece) icp2).location;
                 	tentativeMove = new Move(fromLocation, toLocation);
                    if (icp.isValidMove(tentativeMove, board)) {
                   	 return tentativeMove;
                  }
                  
                  }
             }
        return null;
    }

    private Move attackKing(ArrayList<IChessPiece> fromPieces, Player opponent) {
        {
            Move tentativeMove;
            Location fromLocation;
            Location toLocation = new Location(-1, -1);

            for (IChessPiece bcp : fromPieces) {

                
                
                
                
                // fix me    
                
                
                
                
                
            }
            return null;
        }
    }
}