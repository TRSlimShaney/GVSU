package viewPackage;

import modelPackage.ConnectFourGame3D;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.ColorChooserComponentFactory;

/********************************************************************************
 * ThreeDimPanel defines a class that displays a game of the superior Connect Four 3D experience.
 * NOTE:  THE 3D GAME DOES NOT IMPLEMENT ANY AI.  THIS GAME RUNS IN MANUAL MODE, ONLY.  TWO PLAYERS ARE REQUIRED.
 * 
 * 
 * @author Shane Stacy, Jacob Cramer
 * @version 2/7/2018
 *********************************************************************************/
public class ThreeDimPanel extends JPanel {

	/*  the size of the board  */
	private int boardSize = 10;
	
	/*  the array of buttons  */
	private JButton[][] btnBoard;

	/*  this item starts a new game  */
	private JMenuItem gameItem;
	
	/*  this item quits the game and closes the window  */
	private JMenuItem quitItem;

	/*  this buttons quits and closes the window  */
	private JButton exit;
	
	/*  this button resets the game  */
	private JButton reset;

	
	/*  the game of 3d connect four  */
	private ConnectFourGame3D game3D;

	
	/*  an icon for an unowned space  */
	private ImageIcon iconBlank;
	
	/*  an icon for a player-owned space  */
	private ImageIcon iconPlayer1;
	
	/*  an icon for a second player's owned space  */
	private ImageIcon iconPlayer2;
	

	/****************************************
     * creating the starting board
     ****************************************/
	public ThreeDimPanel(JMenuItem pquitItem, JMenuItem pgameItem, int size) {

		boardSize = size;

		game3D = new ConnectFourGame3D(boardSize);

		setLayout(new BorderLayout());

		JPanel top = new JPanel();
		add(BorderLayout.NORTH, top);

		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(size, size, 1, 1));
		add(BorderLayout.CENTER, bottom);

		ActionListener itemButtonListener = new ItemButtonListener();

		gameItem = pgameItem;
		gameItem.addActionListener(itemButtonListener);

		quitItem = pquitItem;
		quitItem.addActionListener(itemButtonListener);

		reset = new JButton("Reset");
		reset.addActionListener(itemButtonListener);
		top.add(reset);

		exit = new JButton("Exit");
		exit.addActionListener(itemButtonListener);
		top.add(exit);


		ActionListener buttonListener = new ButtonListener();

		Font font = new Font("Segoe UI", Font.BOLD, 16);
		
		btnBoard = new JButton[boardSize][boardSize];
		
		//create the array of buttons

        for (int r = 0; r < boardSize; r++) {
    		for (int c = 0; c < boardSize; c++) {
    			btnBoard[r][c] = new JButton();
    			btnBoard[r][c].setHorizontalAlignment(JLabel.CENTER);
    		    btnBoard[r][c].setVerticalAlignment(JLabel.CENTER);
    			btnBoard[r][c].setText("");
    			btnBoard[r][c].setFont(font);
    			btnBoard[r][c].setBorder(new LineBorder(Color.BLACK));
    			btnBoard[r][c].setOpaque(true);
    			btnBoard[r][c].addActionListener(buttonListener);
    			bottom.add(btnBoard[r][c]);
    			}
    	}
        resetBoard();
	}

	/* *****************************************
	 * resets the board
	 *******************************************/
    private void resetBoard() {
    	
    	for (int r = 0; r < boardSize; r++) {
    		for (int c = 0; c < boardSize; c++) {
    			btnBoard[r][c].setText("");
    			btnBoard[r][c].setForeground(Color.black);
    			}
    	}
    	game3D.reset();  
    	
    }
	/****************************************************
	 * update the button captions of the 2-d btnBoard array
	 ****************************************************/
	private void display3D() { 
	    	for (int c = 0; c < boardSize; c++) {
	    		for (int i = 0; i < boardSize; i++) {
	    			String some = "";
	    			for(int x = boardSize - 1; x >= 0; x--) {
	    			if(btnBoard[c][i].getText().length() < boardSize) {	
	    			if (game3D.getGameBoardValue(c, i, x) != -1) {
	    				some = some + "" + game3D.getGameBoardValue(c, i, x);
	    				btnBoard[c][i].setText(some);
	    				}
	    			}
	    		}
	    	}
		}
	}

	
	private class ItemButtonListener implements ActionListener {
		// --------------------------------------------------------------
		// Handles the Menu items and special buttons
		// --------------------------------------------------------------
		public void actionPerformed(ActionEvent event) {
			JComponent source = (JComponent) event.getSource();
			
			if(source == exit || source == quitItem) {
				System.exit(0);
			}
			
			if(source == reset || source == gameItem) {
				resetBoard();
			}	    
		    
		}
	}

	private class ButtonListener implements ActionListener {
		// --------------------------------------------------------------
		// Handles the game board buttons
		// --------------------------------------------------------------
			public void actionPerformed(ActionEvent event) {
				JComponent source = (JComponent) event.getSource();

				boolean successfulMove = false;
				
			    for(int x = 0; x < boardSize; x++) {
			    	for(int y = 0; y < boardSize; y++) {
			    		if(source == btnBoard[x][y]) {
			    			successfulMove = move(x, y);
			    			
			    		}
			    	}
			    }
			    if (successfulMove) {
			    	display3D();
	            	game3D.isWinner(0);
	            	game3D.isWinner(1);
			    
            	if (game3D.isWinner(game3D.getCurrentPlayer()) == true) {
                  	 JOptionPane.showMessageDialog(null, game3D.getWinner() + " has won the game!!!", "A WINNER IS DECLARED!!!", JOptionPane.INFORMATION_MESSAGE);
                  	 resetBoard();
            	}
                else if (!game3D.moveRemaining()) {
                    JOptionPane.showMessageDialog(null, "No moves left", "Game over!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                	game3D.switchplayer();
                }
			    }
			    
			    
			}
			
			public boolean move(int col, int row) {
	            int er = game3D.selectCol(col, row);
	            if (er == -1) {
	                JOptionPane.showMessageDialog(null, "Column is full!  Pick a different column");
	                return false;
	            } else {
	                return true;
	            }
	        }
		}
}