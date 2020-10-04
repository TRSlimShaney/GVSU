package modelPackage;

import javax.swing.ImageIcon;

public class RedKing extends NormalPiece {
	
	public RedKing(Player player) {
		super(player);
		
		if (player == Player.RED)
		{
			icon = new ImageIcon("rKing.png");
		}
		else if (player == Player.BLACK)
		{
			icon = new ImageIcon("bKing.png");
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
		
		return move.isValidMoveForRedKing(board,move);
	}

}
