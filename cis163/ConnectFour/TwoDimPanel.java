package viewPackage;

import modelPackage.ConnectFourGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;


/********************************************************************************
 * TwoDimPanel defines a class that displays a game of inferior 2D Connect Four.
 * 
 * 
 * @author Shane Stacy, Jacob Cramer
 * @version 2/7/2018
 *********************************************************************************/
public class TwoDimPanel extends JPanel {
	
	/*  board size   */
    private int boardSize = 10; // size of connect four game board
    
    /*   label for the pieces  */
    private JLabel[][] board;
    
    /*  buttons to place a piece   */
    private JButton[] selection;
    

    /*  menu items   */
    private JMenuItem gameItem;
    
    /*  quits the program from the menu  */
    private JMenuItem quitItem;
    

    /*  quits the program from the game screen   */
    private JButton exit;
    
    /*  button that resets the game board   */
    private JButton reset;
    
    /*   button chooses between cpu game and 2 player game  */
    private JButton autoPlay;
    
    private JPanel bottom;
    
    /*  object of connect four game   */
    private ConnectFourGame game;
    
    /*  ???   */
    private boolean computerPlays= false;
    
    private ItemButtonListener itemButtonListener;
    
    private ButtonListener buttonListener;
    
    
    /****************************************
     * creating the starting board
     ****************************************/
    public TwoDimPanel(JMenuItem pquitItem, JMenuItem pgameItem, int size) {

        boardSize = size;

        // Panel layout
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        add(BorderLayout.NORTH, top);

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(boardSize + 1, boardSize, 1, 1)); // buttons stored in the last row
        add(BorderLayout.CENTER, bottom);

        itemButtonListener = new ItemButtonListener();
        buttonListener = new ButtonListener();

        Font font = new Font("Segoe UI", Font.BOLD, 30);
        
        // Menu items
        gameItem = pgameItem;
        gameItem.addActionListener(itemButtonListener);

        quitItem = pquitItem;
        quitItem.addActionListener(itemButtonListener);

        // Buttons
        reset = new JButton("Reset");
        reset.addActionListener(itemButtonListener);
        reset.setFont(font);
        reset.setForeground(Color.red);
        top.add(reset);

        exit = new JButton("Exit");
        exit.addActionListener(itemButtonListener);
        exit.setFont(font);
        exit.setForeground(Color.red);
        top.add(exit);

        autoPlay = new JButton("Manual");
        autoPlay.addActionListener(itemButtonListener);
        autoPlay.setFont( font );
        autoPlay.setForeground(Color.red);
        top.add(autoPlay);
        

        // Game related fields

        game = new ConnectFourGame(boardSize);

        // game board display of labels
        
        Font font2 = new Font("Segoe UI", Font.BOLD, 20);
        JPanel b = new JPanel();
        this.add(b);
        b.setLayout(new GridLayout(boardSize + 1, boardSize + 1));
        b.setBorder(BorderFactory.createLineBorder(Color.black));
        board = new JLabel[boardSize][boardSize];
        for (int r = 0; r < boardSize; r++) {
    		for (int c = 0; c < boardSize; c++) {
    			board[r][c] = new JLabel();
    			board[r][c].setHorizontalAlignment(JLabel.CENTER);
    		    board[r][c].setVerticalAlignment(JLabel.CENTER);
    			board[r][c].setText("-");
    			board[r][c].setFont(font2);
    			board[r][c].setOpaque(true);
    			board[r][c].setBorder(new LineBorder(Color.BLACK));
    			b.add(board[r][c]);
    			}
    	}

        
       selection = new JButton[boardSize];
        for(int r = 0; r < boardSize; r++) {
        	selection[r] = new JButton();
        	selection[r].addActionListener(buttonListener);
        	selection[r].setText("Select");
        	selection[r].setFont(font2);
        	b.add(selection[r]);
        }
        
        resetBoard();
        setVisible(true);
    }
    /****************************************
     * reseting the starting board
     ****************************************/
    private void resetBoard() {
    	
    	for (int r = 0; r < boardSize; r++) {
    		for (int c = 0; c < boardSize; c++) {
    			board[r][c].setText("-");
    			board[r][c].setForeground(Color.black);
    			board[r][c].setBackground(Color.lightGray);
    			}
    	}
    	game.reset();
    }
        
    /******************************************
     * update the labels in the 2-D board array
     ******************************************/
    private void displayBoard() {
    
    	//  UPDATE THE JLABELS
    	for (int c = 0; c < boardSize; c++) {
    		for (int i = 0; i < boardSize; i++) {
    			if (game.getGameBoardValue(c, i) == -1) {
    				board[c][i].setText("-");
    			}
    			else {
    			board[c][i].setText("" + game.getGameBoardValue(c, i));
    			if(game.getGameBoardValue(c, i) == 0) {
    				board[c][i].setForeground(Color.red);
    				board[c][i].setBackground(Color.red);
    			}
    			if(game.getGameBoardValue(c, i) == 1) {
    				board[c][i].setForeground(Color.blue);
    				board[c][i].setBackground(Color.blue);
    			}
    		}
    		}
    	}
        
        setVisible(true);
    }

    private class ItemButtonListener implements ActionListener {

        // --------------------------------------------------------------
        // Handles menu items and auxiliary buttons
        // --------------------------------------------------------------
        public void actionPerformed(ActionEvent event) {
            JComponent source = (JComponent) event.getSource();
            
            if(source == quitItem || source == exit) {
            	System.exit(0);
            }
            if(source == reset || source == gameItem) {
            	resetBoard();
            }
            
            if (source == autoPlay) {
                computerPlays = !computerPlays;
                if (computerPlays == false) {
                    autoPlay.setText("Manual");
                } else {
                    autoPlay.setText("Automatic");
                }
        }
        }
    }

    // *****************************************************************
    // Represents    a listener for button push (action) events.
    // *****************************************************************
    private class ButtonListener implements ActionListener {
        // --------------------------------------------------------------
        // Updates the game board of labels whenever a column is selected.
        // --------------------------------------------------------------

        public void actionPerformed(ActionEvent event) {
            JComponent source = (JComponent) event.getSource();

            //identify which button is the event source
            
            
            
            
            boolean successfulMove = false;
            
            for(int x = 0; x < boardSize; x++) {
            	if(source == selection[x]) {
            		successfulMove = move(x);
                	displayBoard();
            	}
            
            }
            
            
            // handle the event for the player
            
            
            if (successfulMove) {
            	
            	game.isWinner(0);
            	game.isWinner(1);
            	
            	if (game.isWinner(game.getCurrentPlayer()) == true) {
               	 JOptionPane.showMessageDialog(null, game.getWinner() + " has won the game!!!", "A WINNER IS DECLARED!!!", JOptionPane.INFORMATION_MESSAGE);
               	 resetBoard();
               }
               else if (!game.moveRemaining()) {
                   JOptionPane.showMessageDialog(null, "No moves left", "Game over!", JOptionPane.INFORMATION_MESSAGE);
               }
               else {
               	game.switchPlayer();
               	
               	if (computerPlays && game.moveRemaining() && game.playerIsComputer() && (game.isWinner(0) == false)) {
                       
                       game.moveAI();
                       displayBoard();
                       game.isWinner(1);
                       
                       if (!game.moveRemaining()) {
                           JOptionPane.showMessageDialog(null, "No moves left", "Game over!", JOptionPane.INFORMATION_MESSAGE);
                       }
                       
                       if (game.isWinner(game.getCurrentPlayer()) == true) {
                       	 JOptionPane.showMessageDialog(null, game.getWinner() + " has won the game!!!", "A WINNER IS DECLARED!!!", JOptionPane.INFORMATION_MESSAGE);
                       	resetBoard();
                       }
                       else {
                       game.switchPlayer();
                       }
                   }
                   
                   
               }
            	
            }
            

            
        }

        public boolean move(int col) {
            int row = game.selectCol(col);
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Column is full!  Pick a different column");
                return false;
            } else {
                return true;
            }
        }

    }
}

