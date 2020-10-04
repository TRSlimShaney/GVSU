package modelPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TestCheckersGame {

	
	@Test
	void testModelConstructor() {
		CheckersModel model = new CheckersModel("fileName");
		assertTrue(model.player == Player.BLACK);
		assertTrue(model.blackJumps == 0);
		assertTrue(model.redJumps == 0);
		
	}

	@Test
	void testMoveConstructor() {
		CheckersModel model = new CheckersModel("fileName");
		Location from = new Location(5, 2);
		Location to = new Location(4, 3);
		Move m = new Move(from, to);
		
		assertTrue(m.from == from);
		assertTrue(m.to == to);
	}
	
	@Test
	void testCheckersPieceConstructor() {
		CheckersModel model = new CheckersModel("fileName");
		GreenPiece piece = new GreenPiece(model.player);
		model.changePlayers();
		RedPiece piece2 = new RedPiece(model.player);
		
		assertTrue(piece.player() == Player.BLACK);
		assertTrue(piece.location.row == -1);
		assertTrue(piece.location.column == -1);
		
		assertTrue(piece2.player() == Player.RED);
		assertTrue(piece2.location.row == -1);
		assertTrue(piece2.location.column == -1);
	}
	
	@Test
	void testCheckersPiece() {
		CheckersModel model = new CheckersModel("fileName");
		GreenPiece piece = new GreenPiece(model.player);
		
		assertTrue(piece.getOwner() == model.player);
		
		model.changePlayers();
		RedPiece piece2 = new RedPiece(model.player);
		
		assertTrue(piece2.getOwner() == model.player);
		
		piece.setLocation(4, 4);
		assertTrue(piece.location.row == 4);
		assertTrue(piece.location.column == 4);
		
		Location testRedStart = new Location(2, 0);
		Location testRedEnd = new Location(3, 1);
		
		Move theMove = new Move(testRedStart, testRedEnd);
		
		assertTrue(model.pieceAt(testRedStart).isValidMove(theMove, model.getBoard()));
	}
	
	@Test
	void testSwitchPlayer() {
		CheckersModel model = new CheckersModel("fileName");
		assertTrue(model.player == Player.BLACK);
		
		model.changePlayers();
		assertTrue(model.player == Player.RED);
		
		model.changePlayers();
		assertTrue(model.player == Player.BLACK);
	}
	
	@Test
	void testMove() {
		CheckersModel model = new CheckersModel("fileName");
		Location from = new Location(5, 2);
		Location to = new Location(4, 3);
		
		Move m = new Move(from, to);
		
		try {
		     model.move(m);
		   }
		   catch (Exception e) {
		     Assert.fail("Exception " + e);
		   }
	}
	
	@Test
	void testLocation() {
		Location from = new Location(5, 2);
		Location to = new Location(4, 3);
		
		assertTrue(from.row == 5);
		assertTrue(from.column == 2);
		assertTrue(to.row == 4);
		assertTrue(to.column == 3);
	}
}
