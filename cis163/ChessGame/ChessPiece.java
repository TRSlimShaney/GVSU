package modelPackage;


import javax.swing.*;
/******************************************************
* Chess piece and icon are inseparable.
* 
* 
* @author 
*
******************************************************/
public abstract class ChessPiece implements IChessPiece {
	
   private Player   owner;	    /** owner of the chess piece */
   public Location  location;	/** location location on the board */
   public ImageIcon icon;	    /** identifying icon */
   
   
   protected ChessPiece(Player player)
   {
       this.owner  = player;
       this.location = new Location( -1, -1 );
   }

   protected ChessPiece(Player player, Location location )
   {
       this.owner  = player;
       this.location = location;
   }

//   public abstract String type();  /** Not implemented by this abstract class */

   
   public Player player()
   {
       return owner;
   }
   
   public ImageIcon imageIcon()
   {
   	return icon;
   }
   
   /**
    * setLocation gives this chess piece its new location on the board.
    */
   public void setLocation( int row, int column )
   {
       location.set(row, column);
   }	
 
	public String toString( )
	{
		return player( ).toString() + " " + type() + " " + location.toString( );
	}
	
	public Player getOwner() {
		return this.owner;
	}

    //public boolean isValidMove(Move move, IChessPiece[][] board) 


}