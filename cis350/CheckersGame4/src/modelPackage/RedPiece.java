
package modelPackage;

import javax.swing.ImageIcon;

/******************************************************
 * This class extends a Normal Piece into a Red Piece.
 * 
 * @author
 ******************************************************/
public class RedPiece extends NormalPiece {
    
	/******************************************************
	   * This method constructs a Red Piece given the player.
	   * 
	   * @param player the player
	   ******************************************************/
    public RedPiece(Player player) {
        super(player);

        if (player == Player.RED) {
            icon = new ImageIcon("bPawn.png");
        } else if (player == Player.BLACK) {
            icon = new ImageIcon("wPawn.png");
        }
    }

    /******************************************************
     * This method returns whether or not the move is valid.
     * 
     * @param move the move
     * @param board the board
     * @return boolean is the move valid?
     ******************************************************/
    public boolean isValidMove(Move move, ICheckersPiece[][] board) {
        
		return move.isValidMoveForRedPiece(board,move);
    }
}
