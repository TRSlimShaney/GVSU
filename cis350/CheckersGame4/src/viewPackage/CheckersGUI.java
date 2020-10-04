package viewPackage;

import java.awt.Dimension;
import javax.swing.JFrame;

public class CheckersGUI {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Game of Checkers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CheckersPanel panel = new CheckersPanel( "gameMoves.txt" );
		frame.getContentPane().add(panel);
		
		frame.setResizable(true);
		frame.setPreferredSize(new Dimension(800, 900));
		frame.pack();
		frame.setVisible(true);
	}
}