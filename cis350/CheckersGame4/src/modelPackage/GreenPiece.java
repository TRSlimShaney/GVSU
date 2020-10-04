package modelPackage;

import javax.swing.ImageIcon;

/******************************************************
 * This class is an extension of a Normal Piece into a
 * black piece
 * 
 * @param player the player
 ******************************************************/
public class GreenPiece extends NormalPiece {

	/******************************************************
	   * Set the icon of the piece.
	   * 
	   * @param player the player
	   ******************************************************/
	public GreenPiece(Player player) {
		super(player);
		
		if (player == Player.RED)
		{
			icon = new ImageIcon("bPawn.png");
		}
		else if (player == Player.BLACK)
		{
			icon = new ImageIcon("wPawn.png");
		}
	}

	/******************************************************
	   * This method checks if the move is valid for a green
	   * piece.
	   * 
	   * @param move the move
	   * @param board the board
	   ******************************************************/
	public boolean isValidMove(Move move, ICheckersPiece[][] board) {	
		
		return move.isValidMoveForGreenPiece(board,move);
	}

}
