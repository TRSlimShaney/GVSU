package modelPackage;


/******************************************************
 * This class defines the interface for a checkers model.
 * 
 * @author
 ******************************************************/
public interface ICheckersModel {

	/******************************************************
	   * This method returns if the move is valid.
	   * 
	   * @param move the move
	   ******************************************************/
	boolean isValidMove(Move move);

	/******************************************************
	   * This method moves the piece
	   * 
	   * @param move the move
	   ******************************************************/
	void move(Move move);

	/******************************************************
	   * This method returns the current player.
	   * 
	   * @return Player the current player.
	   ******************************************************/
	Player currentPlayer();

}