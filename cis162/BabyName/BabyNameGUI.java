import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

/*************************************************************
 * GUI for a Baby Name Database
 * 
 * @author Shane Stacy
 * @version 11/9/2017
 ************************************************************/
public class BabyNameGUI extends JFrame implements ActionListener{

    BabyNamesDatabase database;

    JButton byYear, mostPopular, topTen, byName;

    JTextField yearTextField, nameTextField;

    /** Results text area */
    JTextArea resultsArea;

    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem openItem;
    JMenuItem countItem;

    /*****************************************************************
     * Main Method
     ****************************************************************/ 
    public static void main(String args[]){
        BabyNameGUI gui = new BabyNameGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Baby Names");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/    
    public BabyNameGUI(){

        database = new BabyNamesDatabase();

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
        add(new JLabel("Searches"), loc);

        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 1;
        //loc.gridwidth = 2;
        add(new JLabel("Year"), loc);

        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 7;
        loc.gridwidth = 2;
        add(new JLabel("Name"), loc);

        byYear = new JButton("By Year");
        mostPopular = new JButton("Most Popular");
        topTen = new JButton("Top Ten");
        byName = new JButton("By Name");

        byYear.addActionListener(this);
        mostPopular.addActionListener(this);
        topTen.addActionListener(this);
        byName.addActionListener(this);

        loc.gridx = 2;
        loc.gridy = 2;
        loc.gridwidth = 2;
        add(byYear,loc);

        loc.gridx = 2;
        loc.gridy = 3;
        loc.gridwidth = 2;
        add(mostPopular, loc);

        loc.gridx = 2;
        loc.gridy = 4;
        loc.gridwidth = 2;
        add(topTen, loc);

        loc.gridx = 2;
        loc.gridy = 8;
        loc.gridwidth = 2;
        add(byName, loc);

        yearTextField = new JTextField("", 5);
        loc.gridx = 2;
        loc.gridy = 1;
        loc.gridwidth = 2;
        add(yearTextField, loc);

        nameTextField = new JTextField("", 5);
        loc.gridx = 2;
        loc.gridy = 7;
        loc.gridwidth = 2;
        add(nameTextField, loc);
        // hide details of creating menus
        setupMenus();
        database.readBabyNameData("BabyNames.csv");
        JOptionPane.showMessageDialog(this, "BabyNames.csv was loaded by default." + "\n" + "Use the Open menu item to load a different database.");
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
        if (buttonPressed == openItem){
            openFile();
        }

        if (buttonPressed == countItem) {
            displayCounts();   
        }

        if (buttonPressed == byYear) {

            displayByYear();
        }

        if (buttonPressed == mostPopular) {
            displayMostPopular();
        }

        if (buttonPressed == topTen) {
            displayTopTen();
        }

        if (buttonPressed == byName) {

            displayByName();
        }

    }

    /*****************************************************************
     * open a data file with the name selected by the user
     ****************************************************************/ 
    private void openFile(){

        // create File Chooser so that it starts at the current directory
        String userDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(userDir);

        // show File Chooser and wait for user selection
        int returnVal = fc.showOpenDialog(this);

        // did the user select a file?
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fc.getSelectedFile().getName();

            // FIX ME: change the following line as needed
            // to use your instance variable name
            database.readBabyNameData(filename);          
        }
    }

    /*******************************************************
    Creates the menu items
     *******************************************************/    
    private void setupMenus(){
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        countItem = new JMenuItem("Counts");
        openItem = new JMenuItem("Open...");
        fileMenu.add(countItem);
        fileMenu.add(openItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        // FIX ME: register the menu items with the action listener
        openItem.addActionListener(this);
        countItem.addActionListener(this);

    }

    private void displayNames (ArrayList <BabyName> list) {
        resultsArea.setText("");
        for (BabyName b : list) {
            resultsArea.append("\n" + b.toString());    
        }
    }

    /************************************************************
     * Helper to display the most popular boy/girl names of year.
     ***********************************************************/
    private void displayMostPopular() {
        resultsArea.setText("");

        if (yearTextField.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Provide a Year.");
        }
        else if ((Integer.parseInt(yearTextField.getText()) > 2016) || ((Integer.parseInt(yearTextField.getText()) < 1880))) {
            JOptionPane.showMessageDialog(this, "This database tracks records from 1880 to 2016." + "\n" + "Please enter a year within this range.");
        }
        else {
            resultsArea.append("" + database.mostPopularBoy(Integer.parseInt(yearTextField.getText())));
            resultsArea.append("" + database.mostPopularGirl(Integer.parseInt(yearTextField.getText())));   
        }

    }

    /************************************************************
     * Helper to display the names of a requested year.
     ***********************************************************/
    private void displayByYear() {
        if (yearTextField.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Provide a Year.");
        }
        else if ((Integer.parseInt(yearTextField.getText()) > 2016) || ((Integer.parseInt(yearTextField.getText()) < 1880))) {
            JOptionPane.showMessageDialog(this, "This database tracks records from 1880 to 2016." + "\n" + "Please enter a year within this range.");
        }
        else {
            resultsArea.setText("");
            resultsArea.append("" + database.searchForName(Integer.parseInt(yearTextField.getText())));
        }

    }

    /************************************************************
     * Helper to display the top ten names of a year.
     ***********************************************************/
    private void displayTopTen() {
        resultsArea.setText("");
        if (yearTextField.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Provide a Year.");
        }
        else if ((Integer.parseInt(yearTextField.getText()) > 2016) || ((Integer.parseInt(yearTextField.getText()) < 1880))) {
            JOptionPane.showMessageDialog(this, "This database tracks records from 1880 to 2016." + "\n" + "Please enter a year within this range.");
        }
        else {
            resultsArea.setText("");
            resultsArea.append("Top Ten Baby Names in " + Integer.parseInt(yearTextField.getText()) + "\n" + database.topTenNames(Integer.parseInt(yearTextField.getText())));
        }
    }

    /************************************************************
     * Helper to display the names of a requested name.
     ***********************************************************/
    private void displayByName() {
        if ((nameTextField.getText().equals("")) || (database.searchForName(nameTextField.getText()).size()) == 0) {
            JOptionPane.showMessageDialog(this, "That name provided came back with no results." + "\n" + "Provide a name with only the first letter capitalized.");
        }
        else {
            resultsArea.setText("");
            resultsArea.append("" + database.searchForName(nameTextField.getText()));
        }

        resultsArea.append("\n" + "Total: " + database.searchForName(nameTextField.getText()).size() + "\n" + "All years with " + nameTextField.getText());
    }

    /************************************************************
     * Helper to display the count for total records.
     ***********************************************************/
    private void displayCounts() {
        resultsArea.setText("");
        resultsArea.append("Total Counts" + "\n");
        resultsArea.append("\n");       
        resultsArea.append("Total Girls: " + database.countAllGirls() + "\n");
        resultsArea.append("Total Boys: " + database.countAllBoys() + "\n");
        resultsArea.append("Total Names: " + database.countAllNames() + "\n");

    }

}