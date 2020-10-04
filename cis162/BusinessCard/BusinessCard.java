
/**************************************************************
 * Draw a vector graphics business card using Java.
 *
 * @author Shane Stacy
 * @version V1.0 from 9/13/17 -> 9/20/17
 *************************************************************/
// import the required Java APIs
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class BusinessCard extends JPanel{

    int xLogo = 0, yLogo = 0;  //  emogee logo adjusts

    public static void main(String[] a) {

        //  initialize the Java graphical frame   
        JFrame f = new JFrame();  //  new graphical frame
        f.setContentPane(new BusinessCard());  // name it BusinessCard
        f.setSize(600, 400);  //  give it a resolution of 600 by 400 pixels
        f.setVisible(true);  // the window is not hidden

    }

    /***********************************************
     * Draw the vector graphics business card.
     **********************************************/
    public void paintComponent(Graphics g){

        // this statement required
        super.paintComponent(g);

        // optional: paint the background color (default is white)
        setBackground(Color.BLACK);

        // draw a face
        g.setColor(Color.YELLOW);
        g.fillOval(xLogo + 350, yLogo + 150, 100, 100);

        // draw the left lens
        g.setColor(Color.BLACK);
        g.fillOval(xLogo + 365, yLogo + 170, 34, 30);

        // draw the right lens
        g.setColor(Color.BLACK);
        g.fillOval(xLogo + 403, yLogo+ 170, 32, 30);

        // draw the glasses bridge
        g.setColor(Color.BLACK);
        g.fillRect(xLogo + 350, yLogo + 177, 100, 7);

        // draw red mouth
        g.setColor(Color.RED);
        g.drawLine(xLogo + 380, yLogo + 225, xLogo + 420, 225);

        // display shane stacy
        Font myFont1 = new Font("segoeui", Font.BOLD, 20);
        g.setFont(myFont1);
        g.setColor(Color.green);
        g.drawString("Shane Stacy", 300, 50);

        // display the real slim shaney
        Font myFont2 = new Font("cooperblack", Font.BOLD, 20);
        g.setFont(myFont2);
        g.setColor(Color.green);
        g.drawString("(The Real Slim Shaney)", 300, 75);

        // display fake phone number
        Font myFont3 = new Font("droidserif", Font.BOLD, 20);
        g.setFont(myFont3);
        g.setColor(Color.white);
        g.drawString("(616-DIS-FAKE)", 300, 110);        

        // draw a solid and empty rectangle
        g.setColor(Color.RED);  // set the color of the rectangle to RED
        g.drawRect(40, 20, 500, 300);  // draw the rectangle, (x-origin coordinate, y-origin coordinate, draw x for 70 pixels, draw y for 50 pixels)

        //  end drawing vector graphics
        /******************************************************************************/
        //  start drawing raster graphics

        //  draw the TRSS Productions raster image
        BufferedImage photo = null;
        try {
            File file = new File("MyPhoto.gif");  //  define the photo
            photo = ImageIO.read(file);  //  read the photo
        } catch (IOException e){
            g.drawString("Problem reading the file", 100, 100);  //  give an error if photo not found
        }
        g.drawImage(photo, 45, 240, 80, 80, null);  // finally draw the photo

        //  draw the IFP raster image
        BufferedImage photo2 = null;
        try {
            File file2 = new File("MyPhoto2.gif");  //  define the photo
            photo = ImageIO.read(file2);  //  read the photo
        } catch (IOException e){
            g.drawString("Problem reading the file", 100, 100);  //  give an error if photo not found
        }
        g.drawImage(photo, 120, 250, 425, 100, null);  // finally draw the photo

        //  draw the portrait raster image
        BufferedImage photo3 = null;
        try {
            File file3 = new File("MyPhoto3.jpg");  //  define the photo
            photo = ImageIO.read(file3);  //  read the photo
        } catch (IOException e){
            g.drawString("Problem reading the file", 100, 100);  //  give an error if photo not found
        }
        g.drawImage(photo, 50, 25, 150, 200, null);  // finally draw the photo

    }
}