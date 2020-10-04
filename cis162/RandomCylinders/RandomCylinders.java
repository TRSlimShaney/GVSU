import java.util.*;
import java.util.Random;
import java.lang.Math;
import java.text.DecimalFormat;
/*****************************************************************
 * Generate a random cylinder with its volume and surface area.
 *
 * @author Shane Stacy and Brenden Streelman
 * @version 9/21/2017
 ****************************************************************/
public class RandomCylinders
{

    
    
   public static void main (String [] args){
       
       double cylinderVolume = 0.0;
       double cylinderSurfaceArea = 0.0;
       int radius;
       int height;
       
       Scanner keyboard = new Scanner(System.in);
       String firstName = "";
       String lastName = "";
       Random randGenRadius = new Random();
       Random randGenHeight = new Random();
       
       
       radius = randGenRadius.nextInt(10)+1;
       height = randGenHeight.nextInt(10)+1;
       
       
       cylinderVolume = Math.PI * Math.pow(radius, 2.0) * height;
       cylinderSurfaceArea = 2 * Math.PI * radius * height;
       
       DecimalFormat decFor = new DecimalFormat("0.0");
       
       System.out.println("Cylinder Information:");
       
       System.out.println("Radius: " + radius);
       
       System.out.println("Height: " + height);
       
       System.out.println("Volume: " + decFor.format(cylinderVolume) + " cubic inches.");
       
       System.out.println("Surface Area: " + decFor.format(cylinderSurfaceArea) + " square inches.");
   
       
    }
}