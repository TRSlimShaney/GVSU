package modelPackage;


import javax.swing.ImageIcon;
import javax.swing.*;

/******************************************************
 * This class defines the interface for the pieces in
 * the game.
 * 
 * @author
 ******************************************************/
public interface ICheckersPiece {

	/******************************************************
	   * Return the player that owns this piece.
	   * 
	   * @return Player the owner.
	   ******************************************************/
	Player player();
	
	/******************************************************
	   * This method returns the icon for a piece.
	   * 
	   * @return ImageIcon the icon.
	   ******************************************************/
   ImageIcon imageIcon();

   /******************************************************
    * This method returns the type of piece.
    * 
    * @return String the type of piece.
    ******************************************************/
	String type();

	/******************************************************
	   * This method sets the location of a piece.
	   * 
	   * @param row the row
	   * @param col the column
	   ******************************************************/
	void setLocation(int row, int column);

	/******************************************************
	   * This method returns whether the move is valid or not.
	   * 
	   * @param move the move
	   * @param board the board
	   * @return boolean is the move valid or not?
	   ******************************************************/
	boolean isValidMove(Move move, ICheckersPiece[][] board);
}
