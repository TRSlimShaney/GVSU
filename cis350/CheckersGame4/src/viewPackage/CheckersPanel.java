package viewPackage;

import modelPackage.*;
import java.awt.*;
import java.awt.event.*;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;

import javax.swing.*;

/*****************************************************************************
 * CheckersPanel is the GUI for the Checker game which has all buttons and GUI 
 * elements.
 * 
 * @author
 *
 *****************************************************************************/
public class CheckersPanel extends JPanel {

    private JButton[][] board;
    private CheckersModel model;
    
    ImageIcon bKing = new ImageIcon("bKing.png");
    ImageIcon rKing = new ImageIcon("rKing.png");
    

    private JButton reset;
    private JButton undo;
    private JButton automatic;
    private JButton btnTrace;   
    
    private JLabel rScore; //*Used to store the value of red player score
    private JLabel bScore; //*Used to store the value of black player score
   
    private boolean selfPlay;
    private int messageCode;
    private int turn;
    private String blackJumps;
    private String redJumps;
    private boolean trace;

    private ArrayList<Move> moveHistory;

    private String gameMovesInputFile;

    /******************************************************************
     * documentation
     * 
     * 
     ******************************************************************/
    public CheckersPanel(String gameMovesInputFile) {

        this.gameMovesInputFile = gameMovesInputFile;

        GeneralListener generalListener = new GeneralListener();
        GameMoveListener gameMoveListener = new GameMoveListener();

        JPanel boardpanel = new JPanel();
        boardpanel.setPreferredSize(new Dimension(600, 600));
        boardpanel.setLayout(new GridLayout(9, 9, 1, 1));
        createBoard(boardpanel, gameMoveListener);
        createScoreBoard();
        add(boardpanel, BorderLayout.WEST);

        JPanel buttonpanel = new JPanel();
        add(buttonpanel);

        reset = new JButton("Reset Game");
        reset.addActionListener(generalListener);
        buttonpanel.add(reset);

        //undo = new JButton("Undo");
        //undo.addActionListener(generalListener);
        //buttonpanel.add(undo);

        automatic = new JButton("Auto play");
        automatic.addActionListener(generalListener);
        buttonpanel.add(automatic);

        //btnTrace = new JButton("Trace");
        //btnTrace.addActionListener(generalListener);
        //buttonpanel.add(btnTrace);

        selfPlay = false;
        trace = false;
        turn = 0;

        reset(gameMovesInputFile);
    }

    /*********************************************************
     * documentation
     * 
     * 
     *********************************************************/
    private void createBoard(JPanel panel, GameMoveListener listener) {
        Color[] colors = { Color.RED, Color.BLACK };
        JButton b;
        JLabel label;

        this.board = new JButton[9][9];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c <= 8; c++) {
                if (c < 8) {
                    b = new JButton();
                    b.setBackground(colors[(r + c) % 2]);
                    b.addActionListener(listener);
                    panel.add(b);
                    this.board[r][c] = b;
                } else {
                    label = new JLabel("  " + r);
                    label.setForeground(Color.red);
                    panel.add(label);
                }
            }
        }

        int aa = (int) 'a';
        for (int c = 0; c < 8; c++) {
            label = new JLabel("         " + (char) (aa + c));
            label.setForeground(Color.red);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        }
    }

    /*****************************************************
     * documentation
     * 
     * 
     ****************************************************/
    public JButton buttonAt(Location s) {
    	System.out.println(s.row + "," + s.column);
        return board[s.row][s.column];
    }
    
    
    /****************************************************************
     * This is the function that creates the score board for the game
     * 
     ****************************************************************/
    private void createScoreBoard() {
    	  	
    	JPanel sPanel =  new JPanel();
    	sPanel.setPreferredSize(new Dimension(200, 100));
        sPanel.setLayout(new GridLayout(3, 2, 1, 1));
        add(sPanel);
        
        JLabel redText = new JLabel();
        JLabel blackText = new JLabel();
        
        redText.setFont(new Font("Verdana",1,15));
        blackText.setFont(new Font("Verdana",1,15));
        
        redText.setBackground(Color.RED);
        
        redText.setText("Red Score");
        blackText.setText("Black Score");
        
        rScore = new JLabel();
        bScore = new JLabel();
        
       
        sPanel.add(redText);
        sPanel.add(rScore);
        sPanel.add(blackText);
        sPanel.add(bScore);    	
    }

    /****************************************************
     * documentation
     * 
     * 
     ****************************************************/
    private void reset(String gameMovesInputFile) {
    	bScore.setText("0");
    	rScore.setText("0");
    			
    	
    	
        model = new CheckersModel(gameMovesInputFile);
        moveHistory = new ArrayList<Move>();

        turn = 0;
        model.trace = true;

            this.board[0][0].setIcon(model.pieceAt(0, 0).imageIcon());
            this.board[0][2].setIcon(model.pieceAt(0, 2).imageIcon());
            this.board[0][4].setIcon(model.pieceAt(0, 4).imageIcon());
            this.board[0][6].setIcon(model.pieceAt(0, 6).imageIcon());
            this.board[1][1].setIcon(model.pieceAt(1, 1).imageIcon());
            this.board[1][3].setIcon(model.pieceAt(1, 3).imageIcon());
            this.board[1][5].setIcon(model.pieceAt(1, 5).imageIcon());
            this.board[1][7].setIcon(model.pieceAt(1, 7).imageIcon());
            this.board[2][0].setIcon(model.pieceAt(2, 0).imageIcon());
            this.board[2][2].setIcon(model.pieceAt(2, 2).imageIcon());
            this.board[2][4].setIcon(model.pieceAt(2, 4).imageIcon());
            this.board[2][6].setIcon(model.pieceAt(2, 6).imageIcon());
            
            this.board[5][1].setIcon(model.pieceAt(5, 1).imageIcon());
            this.board[5][3].setIcon(model.pieceAt(5, 3).imageIcon());
            this.board[5][5].setIcon(model.pieceAt(5, 5).imageIcon());
            this.board[5][7].setIcon(model.pieceAt(5, 7).imageIcon());
            this.board[6][0].setIcon(model.pieceAt(6, 0).imageIcon());
            this.board[6][2].setIcon(model.pieceAt(6, 2).imageIcon());
            this.board[6][4].setIcon(model.pieceAt(6, 4).imageIcon());
            this.board[6][6].setIcon(model.pieceAt(6, 6).imageIcon());
            this.board[7][1].setIcon(model.pieceAt(7, 1).imageIcon());
            this.board[7][3].setIcon(model.pieceAt(7, 3).imageIcon());
            this.board[7][5].setIcon(model.pieceAt(7, 5).imageIcon());
            this.board[7][7].setIcon(model.pieceAt(7, 7).imageIcon());
            
            
        for (int r = 3; r < 5; r++) {
            for (int c = 0; c < 8; c++) {
                this.board[r][c].setIcon(null);
            }
        }
    }

    /*********************************************
     * documentation
     *********************************************/
    private void promote(Location s) {

        buttonAt(s).setIcon(model.pieceAt(s).imageIcon());

        if (model.pieceAt(s).player().equals(Player.BLACK)) {
            if (trace) {
                System.out.print(", Black pawn promoted");
            }
        } else if (model.pieceAt(s).player().equals(Player.RED)) {
            if (trace) {
                System.out.print(", Red pawn promoted");
            }
        }
    }

    /*********************************************
     * documentation
     *********************************************/
    private void move(Move m) {
    	

        if (trace) {
            System.out.print(" " + turn + " " + model.pieceAt(m.from).toString() + m.to.toString());
            if (model.pieceAt(m.to) != null) {
                System.out.print(" take " + model.pieceAt(m.to).toString());
            }
        }

        model.move(m);
       
          

        buttonAt(m.to).setIcon(m.fromPiece.imageIcon());
        buttonAt(m.from).setIcon(null);
        
        if(model.isKing(m) && model.currentPlayer() == Player.BLACK) {
        	this.board[m.to.row][m.to.column].setIcon(bKing);
        }
        
        if(model.isKing(m) && model.currentPlayer() == Player.RED) {
        	this.board[m.to.row][m.to.column].setIcon(rKing);
        }
        
        if(model.isMoveOverOpponentPiece(m) && model.currentPlayer() == Player.BLACK) { 
        	this.blackJumps = Integer.toString(model.blackJumps);
        	bScore.setText(blackJumps);
        	buttonAt(m.mid).setIcon(null);     
        	blackWinner();
        }
        else if(model.isMoveOverOpponentPiece(m) && model.currentPlayer() == Player.RED) {
        	this.redJumps = Integer.toString(model.redJumps);
        	rScore.setText(redJumps);
        	buttonAt(m.mid).setIcon(null);   
        	redWinner();
        }       
        moveHistory.add(m);      
    }

    /*********************************************
     * documentation
     *********************************************/
    private void undoMove() {
        if (moveHistory.size() > 0) {

            int n = moveHistory.size() - 1;
            Move m = moveHistory.remove(n);
            model.undo(m);

            buttonAt(m.from).setIcon(m.fromPiece.imageIcon());
            
            if(m.midPiece != null) {
            	buttonAt(m.mid).setIcon(m.midPiece.imageIcon());
            }
            else {
            	buttonAt(m.mid).setIcon(null);
            }
            
            if (m.toPiece != null) {
                buttonAt(m.to).setIcon(m.toPiece.imageIcon());
            } else {
                buttonAt(m.to).setIcon(null);
            }

            turn--;
            model.changePlayers();
        }
    }

    /*********************************************
     * documentation
     *********************************************/
    private void displayMessage(ICheckersPiece piece) {

        if (messageCode == 0) {
            // JOptionPane.showMessageDialog(null, "Turn done. It is your turn.");
        } else if (messageCode == 1) {
            JOptionPane.showMessageDialog(null, "It is not your turn.");
        } else if (messageCode == 2) {
            if (model.currentPlayer() == Player.BLACK) {
                JOptionPane.showMessageDialog(null, "Green is in check.");
            } else if (model.currentPlayer() == Player.RED) {
                JOptionPane.showMessageDialog(null, "Red is in check.");
            }
        } else if (messageCode == 3) {
            if (model.currentPlayer() == Player.BLACK) {
                JOptionPane.showMessageDialog(null, "Checkmate;  Red wins.");

            } else if (model.currentPlayer() == Player.RED) {
                JOptionPane.showMessageDialog(null, "Checkmate;  Green wins.");
            }
            gameOverDialog();
        } else if (model.getMessage() == 1) {
            JOptionPane.showMessageDialog(null, "Invalid move; the King is placed in check.");
        } else if (model.getMessage() == 2) {
            JOptionPane.showMessageDialog(null, "Invalid move; the King remains in check.");
        } else if (model.getMessage() == 3) {
            if (piece != null) {

                JOptionPane.showMessageDialog(null, "" + piece.type() + ":  Invalid move.");
            }
        }
        messageCode = 0;
    }

	/*********************************************
	 * documentation
	 *********************************************/
    private void provideFeedback(Location s, String message){     
        displayMessage(model.pieceAt(s));
    }

	/*********************************************
	 * documentation
	 *********************************************/
    private void gameOverDialog() {   	    	    	    
        int confirm = JOptionPane.showOptionDialog(null, "Would you like to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (confirm == JOptionPane.YES_OPTION) {
            model = new CheckersModel("");
            moveHistory = new ArrayList<Move>();
            turn = 0;
            reset(gameMovesInputFile);
        }
        if (confirm == JOptionPane.NO_OPTION || confirm == JOptionPane.CLOSED_OPTION) {
            System.exit(1);
        }    
    }

	/*********************************************
	 * documentation
	 *********************************************/
    private Location getEventLocation(ActionEvent event) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c] == event.getSource()) {
                    return new Location(r, c);
                }
            }
        }
        return null;
    }
    
    /****************************************************
     * 
     * Called upon when the red player wins
     * 
     ****************************************************/
    private void redWinner() {
    	if(model.redJumps == 12) {
    		JOptionPane.showMessageDialog(null,"Red has won the game!");  
    		gameOverDialog();
    	}
    }
    
    
    /******************************************************
     * 
     * Called upon when the black player wins
     * 
     ******************************************************/
    private void blackWinner() {
    	if(model.blackJumps == 12) {
    		JOptionPane.showMessageDialog(null,"Black has won the game!");
    		gameOverDialog();
    	}
    }

	/*********************************************
	 * documentation
	 *********************************************/
    private class GeneralListener implements ActionListener {
        Location eventLocation;
        Color backgroundColor;
        boolean fromTo;

        public void actionPerformed(ActionEvent event) {
            if (reset == event.getSource()) {
                reset(gameMovesInputFile);
                
                
                
            } else if (undo == event.getSource()) {
                if (fromTo == true) {
                    fromTo = false;
                    buttonAt(eventLocation).setBackground(backgroundColor);
                }
                undoMove();
            } else if (automatic == event.getSource()) {

			    
                if (selfPlay == true) {
                	automatic.setText("Self Play");
                	selfPlay = false;
                }
                else if (selfPlay == false) {
                	automatic.setText("Auto Play");
                	selfPlay = true;
                }

			    
            } else if (btnTrace == event.getSource()) {

            	if (trace == true) {
                	trace = false;
                }
                else if (trace = false) {
                	trace = true;
                }		    
            }
        }
    }

	/*********************************************
	 * documentation
	 *********************************************/
    private class GameMoveListener implements ActionListener {
        Location eventLocation;
        Location fromLocation;
        Color backgroundColor;
        boolean fromTo;
        Color[] colors = { Color.RED, Color.BLACK };

        public GameMoveListener() {
            fromTo = false;
        }

        public void actionPerformed(ActionEvent event) {
            Move thisMove;
            Location toLocation;

            eventLocation = getEventLocation(event);
            if (eventLocation != null) {
                if (!fromTo) {
                    fromLocation = eventLocation;
                    
					fromTo = true;
					buttonAt(fromLocation).setBackground(backgroundColor);
	
                } else if (fromTo) {
                    
                    toLocation = eventLocation;

                    thisMove = new Move(fromLocation, toLocation);
					

                    if (model.isValidMove(thisMove) && model.currentPlayer() == Player.BLACK && ((CheckersPiece) model.pieceAt(fromLocation)).getOwner() == Player.BLACK) {
                    move(thisMove);
                    model.changePlayers();      
                    
                    }
                    else if (model.isValidMove(thisMove) && model.currentPlayer() == Player.RED && ((CheckersPiece) model.pieceAt(fromLocation)).getOwner() == Player.RED){
                    	move(thisMove);
                        model.changePlayers();
                        
                    }
                    for (int r = 0; r < 8; r++) {
			            for (int c = 0; c <= 8; c++) {
			                if (c < 8) {
			                    board[r][c].setBackground(colors[(r + c) % 2]);
			                }
			            }
					}
					
					//model.takeInventory();
                    fromTo = false;
                }
                
                if (selfPlay) {
                    if (model.currentPlayer() == Player.RED) {
                        Move m = model.AI();
                        if (m != null) {
                            fromLocation = m.from;
                            move(m);
                                if (trace) {
                                    System.out.println();
                                }                          
                        }
                        model.changePlayers();
                        provideFeedback(fromLocation, "White in check");
                        
                        for (int r = 0; r < 8; r++) {
    			            for (int c = 0; c <= 8; c++) {
    			                if (c < 8) {
    			                    board[r][c].setBackground(colors[(r + c) % 2]);
    			            }
    			        }
    					}
                    }
                    turn++;
                }
            }
        }
    }
}