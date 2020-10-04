import java.util.*;
import java.util.Random;
import java.lang.Math;
import java.text.DecimalFormat;
/**
 * Write a description of class AreaofTriangle here.
 *
 * @author Shane Stacy and Brenden Streelman
 * @version 9/21/2017
 */
public class AreaofTriangle
{

   public static void main (String [] args){
       
       double sideA = 0;
       double sideB = 0;
       double sideC = 0;
       double triangleArea = 0;
       double trianglePerimeter = 0;
       double s = 0;
       
       Scanner keyboard = new Scanner(System.in);

       
       
       DecimalFormat decFor = new DecimalFormat("0.000");
       
       System.out.print("Enter Side A: ");
       sideA = keyboard.nextDouble();
       
       System.out.print("Enter Side B: ");
       sideB = keyboard.nextDouble();
       
       System.out.print("Enter Side C: ");
       sideC = keyboard.nextDouble();
       
       trianglePerimeter = sideA + sideB + sideC;
       s = 0.5 * trianglePerimeter;
       triangleArea = Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
       
       System.out.println("The area of the triangle is: " + decFor.format(triangleArea));
   
       
    }
}