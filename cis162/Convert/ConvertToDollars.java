import java.util.*;
/**
 * Enter four integers for quarters, dimes, and nickels and pennies.
 * @version   V1.0
 * @author   Brenden Streelman
 * @author   Shane Stacy
 * 
 * 9/7/17
 */
public class ConvertToDollars
{

   public static void main (String [] args){
       
       int quarters;
       int dimes;
       int nickels;
       int pennies;
       int totalCents;
       double dollarAmount; 
   
       
       Scanner keyboard = new Scanner(System.in);
       
       System.out.print("Enter quarters: ");
       quarters = keyboard.nextInt();
       
       System.out.print("Enter dimes: ");
       dimes = keyboard.nextInt();
       
       System.out.print("Enter nickels: ");
       nickels = keyboard.nextInt();
       
       System.out.print("Enter pennies: ");
       pennies = keyboard.nextInt();
      
       totalCents = (quarters * 25) + (dimes * 10) + (nickels * 5) + pennies;
       dollarAmount = totalCents / 100.00;
       
       System.out.println("This is equivalent to $" + dollarAmount + ".");
    }
}