
/***************************************
 * Represents a customer's bank account.
 *
 * @author Scott Nguyen
 * @author Shane Stacy
 * @version 9/28/2017
 **************************************/
public class BankTest
{

    /**************************************
     * Make some accounts and do some checking.
     **************************************/
    public static void main(String args[]){
        BankAccount scott = new BankAccount("Scott Nguyen", 100.45);
        BankAccount shane = new BankAccount("Shane Stacy", 40000);
        scott.depositChecking(45.67);
        shane.withdrawChecking(5678.90);
        shane.transferToSavings(17500);
        System.out.println(scott);
        System.out.println(shane);  
    }

    
}
