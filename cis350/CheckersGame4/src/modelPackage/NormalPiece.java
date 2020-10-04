package modelPackage;

/******************************************************
 * This class extends CheckersPiece into a Normal Piece.
 * 
 * @author
 ******************************************************/
abstract public class NormalPiece extends CheckersPiece {

	/******************************************************
	   * This method constructs a normal piece for a given player.
	   * 
	   * @param player the player
	   ******************************************************/
	public NormalPiece(Player player)
	{
		super(player);
	}

	/******************************************************
	   * This method returns the piece type.
	   * 
	   * @return String the piece type.
	   ******************************************************/
	public String type()
	{
		return "Checker";
	}
	
}
