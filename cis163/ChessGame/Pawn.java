package modelPackage;

/**
* Pawn is a class definition for
* 
* @author 
*
*/
abstract public class Pawn extends ChessPiece {

	public Pawn(Player player)
	{
		super(player);
	}

	public String type()
	{
		return "Pawn";
	}
	
}
