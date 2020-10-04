import java.util.*;
/******************************************
 * Testbench of BabyNamesDatabase
 *
 * @author Shane Stacy
 * @version 11/9/2017
 *****************************************/
public class BabyTest
{
    public static void main(String args[]){
        BabyNamesDatabase db = new BabyNamesDatabase();

        // read small data file created just for testing
        db.readBabyNameData("BabyNames.txt");

        // check number of records
        if(db.countAllNames() != 10){
            System.out.println("Error: Number of names should be 6");
        }

        // check most popular girl
        BabyName popular = db.mostPopularGirl(9999);
        String name = popular.getName();
        if(name.equals("Rebecca") == false){
            System.out.println("Error: Popular girl in 9999 should be Rebecca");
        }

        // check number of records for one year
        ArrayList<BabyName> tempList = db.searchForName(1990);

        if(tempList.size() != 2){
            System.out.println("Error: Should be 2 records in 1999");
        }

        System.out.println("Scanning complete.");
    }

    
    
}
