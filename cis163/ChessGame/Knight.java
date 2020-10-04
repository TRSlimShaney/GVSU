package modelPackage;
import javax.swing.ImageIcon;

/**
 * Knight is a class definition for
 * 
 * @author 
 *
 */
public class Knight extends ChessPiece {

    public Knight(Player player)
    {
        super(player);

        if (player == Player.BLACK)
        {
            icon = new ImageIcon("bKnight.png");
        }
        else if (player == Player.WHITE)
        {
            icon = new ImageIcon("wKnight.png");
        }
    }

    public String type()
    {
		return "Knight";	// fix this
    }

    public boolean isValidMove(Move move, IChessPiece[][] board)
    {
		return move.isValidMoveForKnight(board);	// fix this
    }

}
