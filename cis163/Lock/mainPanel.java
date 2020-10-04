package viewPackage;
 

import javax.swing.*;

import modelPackage.*;

public class mainPanel extends JPanel {

	private LockGUIPanel1 p1;
	private LockGUIPanel1 p2;
	private LockGUIPanel1 p3;
	
	private JButton master;

	
	public mainPanel () {
		
		p1 = new LockGUIPanel1();
		p2 = new LockGUIPanel1();
		p3 = new LockGUIPanel1();
		master = new JButton("Master Not functioning yet!");
		
		add (p1);
		add (p2);
		add (p3);
		add (master);
	

		
	}
}
