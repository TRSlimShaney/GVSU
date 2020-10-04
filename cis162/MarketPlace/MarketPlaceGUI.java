import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

/*************************************************************
 * GUI for a book store.
 * 
 * @author Shane Stacy
 * @version 12/6/2017
 ************************************************************/
public class MarketPlaceGUI extends JFrame implements ActionListener{

    MarketPlace store;

    JButton simulate;

    JTextField cashiersTextField, avgArrivalTextField, avgServiceTextField;

    JCheckBox displayCheckBox;

    /** Results text area */
    JTextArea resultsArea;

    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem clearItem;

    /*****************************************************************
     * Main Method
     ****************************************************************/ 
    public static void main(String args[]){
        MarketPlaceGUI gui = new MarketPlaceGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("MarketPlace");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/    
    public MarketPlaceGUI(){

        store = new MarketPlace();

        // set the layout to GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();

        // create results area to span one column and 10 rows
        resultsArea = new JTextArea(20,20);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        loc.gridx = 0;
        loc.gridy = 1;
        loc.gridheight = 10;  
        loc.insets.left = 20;
        loc.insets.right = 20;
        loc.insets.bottom = 20;
        add(scrollPane, loc);

        // create Results label
        loc = new GridBagConstraints();
        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.bottom = 20;
        loc.insets.top = 20;
        add(new JLabel("Results"), loc);

        // create Searches label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 0;
        loc.gridwidth = 2;
        add(new JLabel("Parameters"), loc);

        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 1;
        //loc.gridwidth = 2;
        add(new JLabel("Cashiers"), loc);

        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 3;
        //loc.gridwidth = 2;
        add(new JLabel("Avg Arrival Time"), loc);

        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 5;
        //loc.gridwidth = 2;
        add(new JLabel("Avg Service Time"), loc);

        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 7;
        //loc.gridwidth = 2;
        add(new JLabel("Display"), loc);

        cashiersTextField = new JTextField("3", 5);
        loc.gridx = 2;
        loc.gridy = 1;
        loc.gridwidth = 2;
        add(cashiersTextField, loc);

        avgArrivalTextField = new JTextField("2.5", 5);
        loc.gridx = 2;
        loc.gridy = 3;
        loc.gridwidth = 2;
        add(avgArrivalTextField, loc);

        avgServiceTextField = new JTextField("6.6", 5);
        loc.gridx = 2;
        loc.gridy = 5;
        loc.gridwidth = 2;
        add(avgServiceTextField, loc);

        displayCheckBox = new JCheckBox();
        loc.gridx = 2;
        loc.gridy = 7;
        loc.gridwidth = 2;
        add(displayCheckBox, loc);

        simulate = new JButton("Simulate");
        loc.gridx = 2;
        loc.gridy = 9;
        loc.gridwidth = 2;
        add(simulate, loc);

        // hide details of creating menus
        setupMenus();

    }

    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     * 
     * @param e the event that was fired
     ****************************************************************/       
    public void actionPerformed(ActionEvent e){

        // extract the button that was clicked
        JComponent buttonPressed = (JComponent) e.getSource();

        // Allow user to load baby names from a file    
        int num = 0;
        double s = 0;
        double a = 0;
        boolean ck = true;
        if (buttonPressed == simulate) {
            MarketPlace bookstore = new MarketPlace();
            if (cashiersTextField.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Provide the number of cashiers.");  //  check for no answer
            }
            else if (Integer.parseInt(cashiersTextField.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Negative number of cashiers not allowed.");  // check for negative answer
            }
            else {
                num = Integer.parseInt(cashiersTextField.getText());
            }
            if (avgServiceTextField.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Provide the average service time in minutes.");  // check for no answer
            }
            else if (Double.parseDouble(avgServiceTextField.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Negative number minutes for service time is not allowed.");  // check for negative answer
            }
            else {
             s = Double.parseDouble(avgServiceTextField.getText());   
            }
            if (avgArrivalTextField.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Provide the average arrival time in minutes.");  // check for no answer
                
            }
            else if (Double.parseDouble(avgArrivalTextField.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Negative number minutes for arrival time is not allowed.");  // check for a negative answer
                
            }
            else {
                
                a = Double.parseDouble(avgArrivalTextField.getText());
            }
            
            // if checkbox is checked display the checkoutarea
            if (displayCheckBox.isSelected()) {
                ck = true;
            }
            else {
                ck = false;
            }
            bookstore.setParameters(num, s, a, ck);
            bookstore.startSimulation();
            resultsArea.append(bookstore.results);

        }

    }
    /*******************************************************
    Creates the menu items
     *******************************************************/    
    private void setupMenus(){
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        clearItem = new JMenuItem("Clear");
        fileMenu.add(clearItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        quitItem.addActionListener(this);
        clearItem.addActionListener(this);
        simulate.addActionListener(this);
        displayCheckBox.addActionListener(this);

    }

}