package viewPackage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import javax.swing.*;

/******************************
 * A Timer
 * 
 * @author Shane Stacy
 * @version 1/10/2017
 ******************************/


public class TimerPanel extends JPanel{

	private JLabel time;  //  Define a label
	private JButton toggle;  //  Define a button
	private javax.swing.Timer timer;  //  Define the timer
	
	final int DELAY = 50;  //  Delay argument
	
	
	public TimerPanel() {
		
		time = new JLabel("11:15 AM");  //  Create a label
		toggle = new JButton(" On");  //  Create a button
		listener listener = new listener();  //  Create a listener
		toggle.addActionListener(listener);  //  listener will listen as an ActionListener
		timer = new javax.swing.Timer(DELAY, new TimerListener());  //  Create the timer
				
	}
	
	private class listener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			time.setText("clicked");
			
			
		}
		
	}
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			time.setText("clicked");
			
			
		}
		
	}
		
	public static void main(String[] args) {
	
		
			
		
		
	}

}
