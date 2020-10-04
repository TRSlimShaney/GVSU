import java.util.*;
/**
 * This class converts from seconds to hours, minutes, and seconds.
 * @version   V1.0
 * @author   Brenden Streelman
 * @author   Shane Stacy
 * 
 * 9/7/17
 */
public class ConvertFromSeconds
{

   public static void main (String [] args){
       
       int seconds;
       int totalHours;
       int totalMinutes;
       int totalSeconds;
       
       Scanner keyboard = new Scanner(System.in);
       
       System.out.print("Enter seconds: ");
       seconds = keyboard.nextInt();
       
       totalHours = seconds / 3600;
       seconds = seconds % 3600;
       totalMinutes = seconds / 60;
       seconds = seconds % 60;
       
       System.out.println("This is " + totalHours + " hours, " + totalMinutes + " minutes and, " + seconds + " seconds.");
       
       
    
    
}
}