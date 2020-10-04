
package modelPackage;

//import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.Timer;
/********************************************************************
 * Lock simulates an entrance device, lock and key, controlled with
 * digital access in place of keyless entry.
 * 
 * @author (Unknown) 
 * @version (6 January 2018)
 ********************************************************************/
public class Lock {

    /** stores the access code to gain entrance */
    private String accessCode;

    /** stores a user attempt to match the access code */
    public  String userEnteredCode;
    
    /** stores a master access code that unlocks all Locks */
    private static String masterCode = "999";

    /******************************************************
     * Constructor
     ******************************************************/
    public Lock () {
        accessCode = "123";
        userEnteredCode = "";
    }

    /*****************************************************
     * Constructor initializes the accessCode for a Lock
     *****************************************************/
    public Lock (String code) {
        if (code.length() == 0) {
            throw new IllegalArgumentException();
        }
        accessCode = code;
        userEnteredCode = "";
    }

    /**********************************************************
     * Sets the userEnteredCode, instance variable, to be used
     * to match the actual access code. 
     * 
     * @param  userEnterCode a digital sequence  
     ***********************************************************/
    public void enterCode (String userEnterCode) {
        this.userEnteredCode = userEnterCode;
    }

    /**************************************************************
     * Returns whether the userEnteredCode opens the lock. 
     * 
     * @return     boolean
     **************************************************************/
    public boolean isUnlock() {   
        if ((accessCode.equals (userEnteredCode))  ||
        (userEnteredCode.equals(masterCode)))
            return true;
        else
            return false;
    }

    /**********************************************************
     * Returns whether the accessCode for this Lock matches the
     * accessCode for some other Lock.
     * 
     * @param  other   
     * @return     boolean
     ***********************************************************/
     public boolean equals (Lock other) {
        if (other.accessCode.equals(this.accessCode))
            return true;
        else 
            return false;
    }

    /**********************************************************
     * Returns whether the accessCode for this Lock matches the
     * accessCode for some other Lock. 
     * 
     * Parameter other must be type cast to a Lock.
     * 
     * @param  other  a reference to some other Lock 
     * @return     boolean
     ***********************************************************/
    public boolean equals (Object other) {
        if (other != null) {
            if (other instanceof Lock) {
                Lock temp = (Lock) other;
                if (temp.accessCode.equals(this.accessCode))
                    return true;
                else 
                    return false;
            }
        }
        return false;
    }

    /*************************************************************
     * Returns whether two different Lock instances have the same
     * accessCode for some other Lock. 
     * 
     * Parameter other must be type cast to a Lock.
     * 
     * @param  s1  reference to a Lock 
     * @param  s2  reference to a Lock 
     * @return     boolean
     *************************************************************/
    public static boolean equals (Lock s1, Lock s2) {
        if (s1.accessCode.equals(s2.accessCode))
            return true;
        else 
            return false;

    }

    /*************************************************************
     * Reads from file the access code and a  user entered code.
     * 
     * @param  fileName file contains two numbers
     *************************************************************/   
    public void load(String fileName) {
        try{

            // open the data file
            Scanner fileReader = new Scanner(new File(fileName)); 

            // read one int
//             accessCode = fileReader.next();
//             userEnteredCode = fileReader.next();

            accessCode = fileReader.nextLine();
            userEnteredCode = fileReader.nextLine();



//             System.out.println (accessCode);
//             System.out.println (userEnteredCode);
        }

        // problem reading the file
        catch(Exception error){
            System.out.println("Oops!  Attempt to read file. Something went wrong.");
        }

    }

    /*************************************************************
     * Writes to file the access code and a used entered code.
     * 
     * @param  fileName file to contain two numbers
     *************************************************************/   
    public void save(String fileName) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        out.println(accessCode);
        out.println(userEnteredCode);
        out.close(); 
    }
}
