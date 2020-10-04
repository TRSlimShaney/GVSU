
/*******************************************
 * Shane Stacy's awesome super fun time Game of Pig!
 *
 * @author Shane Stacy
 * @version 10/26/17
 *******************************************/
public class PigGame
{

    GVdie die1, die2;
    int playerScore;
    int cpuScore;
    int currentRoundScore;
    int total;
    int roundNumber;
    final int holdScore = 20;
    final int winningScore = 100;
    boolean isPlayerTurn = true;
    /*******************************************
     * PigGame constructor for dice
     ******************************************/
    public PigGame() {

        die1 = new GVdie();
        die2 = new GVdie();
    }

    /*******************************************
     * Retrieves the round score.
     * @return int the round score
     ******************************************/
    public int getRoundScore() {
        return currentRoundScore;
    }

    /*******************************************
     * Retrieves the player score.
     * @return int the player score
     ******************************************/
    public int getPlayerScore() {
        return playerScore;
    }

    /*******************************************
     * Retrieves the computer score.
     * @return int the computer score
     ******************************************/
    public int getComputerScore() {
        return cpuScore;
    }

    /*******************************************
     * Determines whether player has won or not
     * @return boolean true if player won
     ******************************************/
    public boolean playerWon() {
        if (playerScore >= winningScore) {
            return true;
        }
        return false;
    }

    /*******************************************
     * Determines whether computer has won or not
     * @return boolean true if computer won
     ******************************************/
    public boolean computerWon() {
        if (cpuScore >= winningScore) {
            return true;
        }
        return false;
    }

    /*******************************************
     * Rolls the dice
     ******************************************/
    private void rollDice() {
        die1.roll();
        die2.roll();
        total = 0;

        if ((die1.getValue() == 1) || (die2.getValue() == 1)) {
            total = 0;
            currentRoundScore = 0;
            System.out.println("Die 1 Value: " + die1.getValue());
            System.out.println("Die 2 Value: " + die2.getValue());
            System.out.println("Round score: " + getRoundScore());
        }
        else {
            currentRoundScore = currentRoundScore + die1.getValue() + die2.getValue();
            total = die1.getValue() + die2.getValue();
            System.out.println("Die 1 Value: " + die1.getValue());
            System.out.println("Die 2 Value: " + die2.getValue());
            System.out.println("Round score: " + getRoundScore());
        }
    }

    /*******************************************
     * Part 1 of player's turn, playerRolls
     ******************************************/
    public void playerRolls() {
        rollDice();
        if (playerWon() == true) {
            System.out.println("Player has won.");
        }
        else {
            playerScore = playerScore + total;
            if (isPlayerTurn() == false) {

                System.out.println("---- Player score: " + getPlayerScore());
            }
        }

    }

    /*******************************************
     * Part 2 of player's turn, playerHolds
     ******************************************/
    public void playerHolds() {
        currentRoundScore = 0;
        System.out.println("---- Player score: " + getPlayerScore());
        System.out.println("The player's turn has ended.");
        System.out.println("");
        System.out.println("It's now the computer's turn.");
        isPlayerTurn = false;

    }

    /*******************************************
     * The computer's turn
     ******************************************/
    public void computerTurn() {
        int stop = 0;
        while (stop == 0) {
            rollDice();
            if ((die1.getValue() == 1) && (die2.getValue() == 1)) {
                total = 0;
                cpuScore = 0;
                break;
            }
            if ((die1.getValue() == 1) || (die2.getValue() == 1)) {
                total = 0;
                break;
            }

            if (currentRoundScore > 19) {
                cpuScore = cpuScore + total;
                stop = 1;
                break;
            }
            if (computerWon() == true) {
                System.out.println("Computer has won.");
                stop = 1;
                break;
            }
            cpuScore = cpuScore + total;

        }
        
        System.out.println("---- Computer score: " + getComputerScore());
        System.out.println("The computer's turn has ended.");
        System.out.println("");
        System.out.println("It's now the player's turn.");
        total = 0;
        currentRoundScore = 0;
        isPlayerTurn = true;
    }

    /*******************************************
     * Restarts the game
     ******************************************/
    public void restart() {
        playerScore = 0;
        cpuScore = 0;
        currentRoundScore = 0;
        roundNumber = 0;
    }

    /*******************************************
     * The player's turn
     ******************************************/
    private void playerTurn() {
        do {
            playerRolls();
            if ((die1.getValue() == 1) && (die2.getValue() == 1)) {
                playerScore = 0;
                playerHolds();
            }
            else if ((die1.getValue() == 1) || (die2.getValue() == 1)) {
                playerHolds();
            }
            if (currentRoundScore > holdScore) {
                playerHolds();
            }
        }while (isPlayerTurn);
    }

    /*******************************************
     * Runs an automatically run game without user input
     ******************************************/
    public void autoGame() {
        roundNumber = 0;
        while (computerWon() == false && playerWon() == false) {
            playerTurn();
            if (playerWon() == false) {
                computerTurn();
            }
            roundNumber = roundNumber +1;
        }
        System.out.println("Game won after " + roundNumber + " rounds.");
    }

    /*******************************************
     * Determines if it is the player's turn or not.
     * @return boolean true if it is the player's turn
     ******************************************/
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    /*******************************************
     * Retrieves the die given an integer
     * @param int num the die number
     * @return GVdie the die according to integer input.  Defaults to die1 if 2 is not given.
     ******************************************/
    public GVdie getDie (int num) {
        int n = num;

        if (n == 2) {
            return die2;
        }
        else {
            return die1;
        }
    }

}

