import java.text.*;
import javax.swing.JOptionPane;
import java.lang.Object;
import java.util.Random;
/**********************************************
 * MyPhone simulates the functionality of a smartphone.
 *
 * @author Shane Stacy
 * @version 10/8/2017
 *********************************************/
public class MyPhone
{

    /** integer for number of texts */
    private int numTexts;

    /** double for the amount of data consumed */
    private double dataConsumed;

    /** double for the remaining battery life */
    private double batteryLeft;

    /** string for the customer name */
    private String customerName;

    /** string for ten-digit phone number */
    private String phoneNumber;

    /** the audio usage per minute */
    private final double audioUsePerMinute = 65/60.0;

    /** the maximum minutes of audio usage for a full battery charge */
    private final double maxBatteryMinutes = 720.0;

    /***************************************
     * Constructor for phones
     * (make sure to use quotations in parameters!)
     * @param enterName the name of the customer
     * @param enterNumber the customer's phone number
     **************************************/
    public MyPhone (String enterName, String enterNumber) {

        //  initialize these variables for the object
        numTexts = 0;
        dataConsumed = 0.0;
        batteryLeft = 0.0;
        customerName = enterName;
        phoneNumber = enterNumber;

    }

    /***************************************
     * Get the number of texts
     **************************************/
    public int getNumTexts() {
        return numTexts;
    }

    /***************************************
     * Get the battery Life
     **************************************/
    public double getBatteryLife() {

        return batteryLeft;
    }

    /***************************************
     * Get the data usage in MB
     **************************************/
    public double getDataUsage() {

        return dataConsumed;
    }

    /***************************************
     * Set the customer name
     * @param n New name
     **************************************/
    public void setName (String n) {

        customerName = n;

        return;
    }

    /***************************************
     * Set the phone number
     * @param n new number
     **************************************/
    public void setPhoneNumber (String n) {

        phoneNumber = n;

        if (phoneNumber.length() > 10) {
            System.out.println("Error:  Phone Number cannot be greater than 10 digits. The Phone Number will now be 9999999999.");
            phoneNumber = "9999999999";
            return;
        }
        return;
    }

    /***************************************
     * Method to discharge the battery
     * @param mins minutes to charge the battery
     **************************************/
    public void chargeBattery (int mins) {

        int charge = mins;
        if (charge < 0) {
            System.out.println("Error:  Negative input not valid for charging the battery.  Aborting method.");
            return;
        }
        else if (batteryLeft < 1.0){
            batteryLeft = batteryLeft + (charge * (100.0/12000.0));
        }
        JOptionPane displayBattery = new JOptionPane();
        JOptionPane.showMessageDialog(displayBattery, "The battery is at " + (getBatteryLife() * 100.0) + "%.");
        return;
    }

    /***************************************
     * Method to stream audio, uses data
     * @param mins minutes of audio streaming
     **************************************/
    public void streamAudio (int mins) {

        double possibleMins = batteryLeft * maxBatteryMinutes;
        if (mins == 0) {
            return;
        }
        if (mins < 0) {
            System.out.println("Error:  Negative input not valid for streaming audio.  Aborting method.");
            return;
        }
        if (mins <= possibleMins) {
            batteryLeft = batteryLeft - (mins/maxBatteryMinutes);
            dataConsumed = dataConsumed + (mins * audioUsePerMinute);
        }
        else {
            dataConsumed = dataConsumed + (possibleMins * audioUsePerMinute);
            batteryLeft = 0.0;
            JOptionPane displayBatWarning = new JOptionPane();
            JOptionPane.showMessageDialog(displayBatWarning, "The battery is empty.  Charge it.");
        }
        return;
    }

    /***************************************
     * Sending a text adds to text counter
     * @param text text to send
     **************************************/
    public void sendText (String text) {

        String textDisplay = text;
        JOptionPane textDialog = new JOptionPane();
        JOptionPane.showInputDialog(textDialog, textDisplay);
        numTexts = numTexts + 1;

        return;
    }

    /***************************************
     * Read the text
     **************************************/
    public void readText () {
        Random rand = new Random();
        int choice = rand.nextInt(5);
        JOptionPane displayText = new JOptionPane();

        switch (choice) {
            case 1:  JOptionPane.showMessageDialog(displayText, "Spicy memes.");
            break;
            case 2:  JOptionPane.showMessageDialog(displayText, "Pineapple pizza is the best.");
            break;
            case 3:  JOptionPane.showMessageDialog(displayText, "Gotta go fast.");
            break;
            case 4:  JOptionPane.showMessageDialog(displayText, "Remove the headphone jack.  Think Different.");
            break;
            case 5:  JOptionPane.showMessageDialog(displayText, "Have you played your Atari, today?");
            break;
        }

        return;
    }

    /***************************************
     * Print a phone statement and start a new month
     **************************************/
    public void printStatement() {  // print the phone statement

        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        System.out.println("Customer :             " + customerName);
        System.out.println("Number:               " + fmtPhoneNumber());
        System.out.println("Texts:                " + numTexts + " Texts");
        System.out.println("Data usage:           " + dataConsumed + " MB");
        System.out.println("");
        System.out.println("2GB Plan:             " + fmt.format(50.00));
        System.out.println("Additional data fee:  " + fmt.format(calcAdditionalDataFee()));
        System.out.println("Universal Usage (3%): " + fmt.format(0.03 * 50.00));
        System.out.println("Administrative Fee:   " + fmt.format(0.61));
        System.out.println("Total Charges:        " + fmt.format(calcTotalFee()));
        startNewMonth();

        return;
    }

    /***************************************
     * Set variables to zero for new month
     **************************************/
    private void startNewMonth() {
        numTexts = 0;
        dataConsumed = 0;
        return;
    }

    /***************************************
     * Calculate the additional fee
     **************************************/
    private double calcAdditionalDataFee() {

        double fee = 0.0;
        if (dataConsumed > 2000) {
            int gigs = (((int)dataConsumed) / 1000) - 1;
            fee = gigs * 15;
            return fee;
        }
        return fee;
    }

    /***************************************
     * Get the usage fee
     **************************************/
    private double calcUsageCharge() {
        double usageCharge;
        usageCharge = 0.03 * 50;
        return usageCharge;
    }

    /***************************************
     * Get the total fee
     **************************************/
    private double calcTotalFee() {
        double adminFee = 0.61;
        return  calcAdditionalDataFee() + calcUsageCharge() + 50.0 + adminFee;
    }

    /***************************************
     * Reformat the phone number to (###)###-####
     **************************************/
    private String fmtPhoneNumber() {
        String str = "(" + phoneNumber.substring(0,3) + ")" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6,10);
        return str;
    }

}
