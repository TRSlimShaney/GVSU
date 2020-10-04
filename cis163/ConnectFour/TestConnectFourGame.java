package modelPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestConnectFourGame {

	
	@Test
	void testConstructor() {
		ConnectFourGame game5 = new ConnectFourGame(5);
		assertTrue(true);
	}

	@Test
	void testIsWinner() {
		
		
		
		
	}
	
	@Test
	void testSwitchPlayer() {
		ConnectFourGame game5 = new ConnectFourGame(5);
		assertTrue(game5.getCurrentPlayer() == 0);
		
		game5.switchPlayer();
		assertTrue(game5.getCurrentPlayer() == 1);
		
		
	}
	
	@Test
	void testPlayerIsComputer() {
		ConnectFourGame game5 = new ConnectFourGame(5);
		assertTrue(game5.playerIsComputer() == false);
		
		game5.switchPlayer();
		assertTrue(game5.playerIsComputer() == true);
	}
	
}
