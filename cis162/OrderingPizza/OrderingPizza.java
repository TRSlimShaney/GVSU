import java.util.*;
import java.util.Random;
import java.lang.Math;
import java.text.NumberFormat;
/**
 * Write a description of class AreaofTriangle here.
 *
 * @author Shane Stacy and Brenden Streelman
 * @version 9/21/2017
 */
public class OrderingPizza
{

   public static void main (String [] args){
       
       int numPizzas = 0;
       double subTotal = 0.0;
       double totalDue = 0.0;
       double price = 9.99;
       double tax = 1.06;
       
       Scanner keyboard = new Scanner(System.in);

       
       
       
       System.out.print("How many pizzas? ");
       numPizzas = keyboard.nextInt();
       
       subTotal = price * numPizzas;
       
       totalDue = subTotal * tax;
       
       NumberFormat defaultFormat = defaultFormat
       
       System.out.println("Sub Total: $" + decFor.format(subTotal));
       
       System.out.println("Total Due: $" + decFor.format(totalDue));
   
       
    }
}