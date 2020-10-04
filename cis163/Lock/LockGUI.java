package controlPackage;
 
import viewPackage.*;

import javax.swing.JFrame;

public class LockGUI
{
    public static void main (String[] args)
    {
        JFrame frame = new JFrame ("Lock and Digital Access Key example");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        mainPanel panel = new mainPanel();        
        frame.getContentPane().add(panel);
        frame.setSize (600,400);
        frame.setVisible(true);
    }
}
