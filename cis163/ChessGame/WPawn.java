package modelPackage;

import javax.swing.ImageIcon;

/**
 * WPawn is a class definition for
 * 
 * @author 
 *
 */
public class WPawn extends Pawn {

	public WPawn(Player player) {
		super(player);
		
		if (player == Player.BLACK)
		{
			icon = new ImageIcon("bPawn.png");
		}
		else if (player == Player.WHITE)
		{
			icon = new ImageIcon("wPawn.png");
		}
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		
		return move.isValidMoveForWhitePawn(board);
	}

}
