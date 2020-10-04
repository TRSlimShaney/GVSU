package modelPackage;


import javax.swing.*;
/******************************************************
* This class is an implementation of ICheckersPiece.
* 
* 
* @author 
*
******************************************************/
public abstract class CheckersPiece implements ICheckersPiece {
	
   private Player owner;	    /** owner of the checker piece */
   public Location location;	/** location location on the board */
   public ImageIcon icon;	    /** identifying icon */
   

   /******************************************************
   * This method constructs a checkers piece for a given player.
   * 
   * @param player the player
   ******************************************************/
   protected CheckersPiece(Player player)
   {
       this.owner  = player;
       this.location = new Location( -1, -1 );
   }

   /******************************************************
    * This method constructs a checkers piece for a given player and a location.
    * 
    * @param player the player
    * @param location the location
    ******************************************************/
   protected CheckersPiece(Player player, Location location )
   {
       this.owner  = player;
       this.location = location;
   }

   /******************************************************
    * This method returns the owner of a piece.
    * 
    * @return Player the owner
    ******************************************************/
   public Player player()
   {
       return owner;
   }
   
   /******************************************************
    * This method returns the icon for a piece.
    * 
    * @return ImageIcon the icon for the piece
    ******************************************************/
   public ImageIcon imageIcon()
   {
   	return icon;
   }
   
   /******************************************************
    * This method gives a piece a new location on the board.
    * 
    * @param row the row
    * @param col the column
    * @return void
    ******************************************************/
   public void setLocation( int row, int column )
   {
       location.set(row, column);
   }	
 
   /******************************************************
    * This method returns the player, piece type, and piece
    * location in a string.
    * 
    * @param player the player
    ******************************************************/
	public String toString( )
	{
		return player( ).toString() + " " + type() + " " + location.toString( );
	}
	
	/******************************************************
	   * This method returns the owner for a piece.
	   * 
	   * @return Player the owner
	   ******************************************************/
	public Player getOwner() {
		return this.owner;
	}


}