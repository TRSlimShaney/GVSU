import java.util.*;
/**
 * This class converts hours, minutes, and seconds to seconds.
 * @version   V1.0
 * @author   Brenden Streelman
 * Shane Stacy
 * 
 * 9/7/17
 */
public class ConvertToSeconds
{

   public static void main (String [] args){
       
       int totalSeconds;
       int hours;
       int minutes;
       int seconds;
       
       Scanner keyboard = new Scanner(System.in);
       
       System.out.print("Enter the number of hours: ");
       hours = keyboard.nextInt();
       
       System.out.print("Enter the number of minutes: ");
       minutes = keyboard.nextInt();
       
       System.out.print("Enter the number of seconds: ");
       seconds = keyboard.nextInt();
       
       totalSeconds = ((hours * 3600) + (minutes * 60) + seconds);
       
       System.out.println("The total number of seconds is " + totalSeconds + " seconds.");
    
}
}