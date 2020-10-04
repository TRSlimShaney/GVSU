package modelPackage;




/******************************************************
 * This class defines a enumerated Player type for two
 * constants RED and BLACK.
 * 
 * @param player the player
 ******************************************************/
public enum Player
{
	RED, BLACK;

	/******************************************************
	   * This method returns who the next player is.
	   * 
	   * @return Player the next player.
	   ******************************************************/
	public Player next()
	{
		return this == RED ? BLACK : RED;
	}
	
	/******************************************************
	   * This method returns a string of the player color.
	   * 
	   * @return String the string.
	   ******************************************************/
	public String toString( )
	{
	    return this == RED ? "R" : "B";
	}
}