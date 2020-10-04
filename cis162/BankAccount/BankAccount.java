import java.text.NumberFormat;
/***************************************
 * Represents a customer's bank account.
 *
 * @author Scott Nguyen
 * @author Shane Stacy
 * @version 9/28/2017
 **************************************/
public class BankAccount
{

    private String customerName;
    private double checkingBalance;
    private double savingsBalance;

    /**************************************
     * BankAccount constructor.
     * @param name the name of the account
     * @return none
     **************************************/        
    public BankAccount(String name, double amt) {

        //  set instance variables
        customerName = name;
        checkingBalance = amt;
        savingsBalance = 1000.0;
    }

    /***************************************
     * Gets the checking balance for an account.
     **************************************/
    public double getChecking() {
        return checkingBalance;
    }

    /**************************************
     * Sets the checking account balance.
     * @param amt the amount in the checking balance
     **************************************/
    public void setChecking(double amt) {

        checkingBalance = amt;

    }

    /**************************************
     * Deposits money into the checking account.
     * @param amt the amount being deposited into the checking balance
     **************************************/
    public void depositChecking(double amt) {

        //  add amount to checking balance
        checkingBalance = checkingBalance + amt;

    }

    /**************************************
     * Withdraws money from the checking account.
     * @param amt the amount withdrawn from the checking balance
     **************************************/
    public void withdrawChecking(double amt) {

        checkingBalance = checkingBalance - amt;

    }

    /**************************************
     * Transfers an amount from the checking 
     * balance into the savings balance.
     * @param amt the amount to transfer from the checking into the savings
     **************************************/
    public void transferToSavings(double amt) {

        checkingBalance = checkingBalance - amt;
        savingsBalance = savingsBalance + amt;

    }

    /**************************************
     * Displays the account information.
     **************************************/
    public String toString() {

        String name = customerName;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String str = "Customer: " + name + "\n" + "Checking: " + fmt.format(checkingBalance) + "\n" + "Savings: " + fmt.format(savingsBalance);
        //  add more to the String before returning
        return str;
    }

}
