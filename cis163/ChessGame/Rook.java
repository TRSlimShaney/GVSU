package modelPackage;

import javax.swing.ImageIcon;

/**
 * Rook is a class definition for
 * 
 * @author 
 *
 */
public class Rook extends ChessPiece {

	public Rook(Player player)
	{
		super(player);
		if (player ==Player.BLACK)
		{
			icon = new ImageIcon("bRook.png");
		}
		else if (player == Player.WHITE)
		{
			icon = new ImageIcon("wRook.png");
		}
	}

	public Rook(Player player, Location location )
	{
		super( player, location );
		if (player ==Player.BLACK)
		{
			icon = new ImageIcon("bRook.png");
		}
		else if (player == Player.WHITE)
		{
			icon = new ImageIcon("wRook.png");
		}
	}

	public String type() {

		return "Rook";	// fix this
	}

	public boolean isValidMove(Move move, IChessPiece[][] board){		
		return move.isValidMoveForRook(board);	
	}
	
}
