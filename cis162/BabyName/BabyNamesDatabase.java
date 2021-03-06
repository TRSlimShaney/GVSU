import java.util.*;
import java.io.IOException;
import java.io.FileInputStream;
/*******************************************
 * Interface with a database of baby names.
 *
 * @author Shane Stacy
 * @version 11/9/2017
 *******************************************/
public class BabyNamesDatabase
{
    ArrayList<BabyName> list;
    //FIXME •   Define an instance variable that holds an ArrayList of BabyName.  Information is read from a text file. 
    /*******************************************
     * Constructor for a new baby name database.
     ******************************************/
    public BabyNamesDatabase() {
        list = new ArrayList<BabyName>();
    }

    /*******************************************
     * Reads the data from the specified database.
     * @param String filename the name of the file
     ******************************************/
    public void readBabyNameData(String filename) {
        // Read the full set of data from a text file
        try{ 

            // open the text file and use a Scanner to read the text
            FileInputStream fileByteStream = new FileInputStream(filename);
            Scanner scnr = new Scanner(fileByteStream);
            scnr.useDelimiter("[,\r\n]+");

            // keep reading as long as there is more data
            while(scnr.hasNext()) {

                // FIX ME: read the name, gender, count and year
                String name3 = scnr.next();
                String gender3 = scnr.next();
                int count3 = scnr.nextInt();
                int year3 = scnr.nextInt();


                // FIX ME: assign true/false to boolean isFemale based on
                // the gender String
                boolean isFemale = true;
                if (gender3.contains("M")) {
                    isFemale = false;
                }
                else {
                    isFemale = true;
                }

                // FIX ME: instantiate a new Baby Name and add to ArrayList
                BabyName entry = new BabyName(name3, isFemale, count3, year3);
                list.add(entry);

            }
            fileByteStream.close();
        }
        catch(IOException e) {
            System.out.println("Failed to read the data file: " + filename + " @ BabyNamesDatabase.readBabyNameData");
        }
    }

    /*******************************************
     * Retrieves the number of names in the database.
     * @return int the number of names in the database
     ******************************************/
    public int countAllNames() {
        return list.size();
    }

    /*******************************************
     * Retrieves the number of girls in the database.
     * @return int the number of girls in the databse.
     ******************************************/
    public int countAllGirls() {
        int numGirls = 0;
        for (BabyName b : list) {

            if (b.isFemale() == true) {
                numGirls++;
            }
        }
        return numGirls;
    }

    /*******************************************
     * Retrieves the number of boys in the database.
     * @return int the number of boys in the database.
     ******************************************/
    public int countAllBoys() {
        int numBoys = 0;
        for (BabyName b : list) {

            if (b.isFemale() == false) {
                numBoys++;
            }
        }
        return numBoys;
    }

    /*******************************************
     * Retrieves the most popular girl name in a certain year.
     * @param int year the year specified
     ******************************************/
    public BabyName mostPopularGirl(int year) {
        ArrayList<BabyName> girlNameList = new ArrayList<BabyName>();
        BabyName biggestGirl = new BabyName("", true, 0, 0);
        for (BabyName b : list) {
            if ((b.getBirthYear() == year) && (b.isFemale() == true)) {
                girlNameList.add(b);
            }
        }
        for (BabyName b : girlNameList) {
            if (b.getNumBabies() > biggestGirl.getNumBabies()) {
                biggestGirl = b;
            }
        }
        return biggestGirl;
    }

    /*******************************************
     * Retrieves the most popular boy name in a certain year.
     * @param int year the year specified
     ******************************************/
    public BabyName mostPopularBoy(int year) {
        ArrayList<BabyName> boyNameList = new ArrayList<BabyName>();
        BabyName biggestBoy = new BabyName("", false, 0, 0);
        for (BabyName b : list) {
            if ((b.getBirthYear() == year) && (b.isFemale() == false)) {
                boyNameList.add(b);
            }
        }
        for (BabyName b : boyNameList) {
            if (b.getNumBabies() > biggestBoy.getNumBabies()) {
                biggestBoy = b;
            }
        }
        return biggestBoy;
    }

    /*******************************************
     * Searches for the name in the database.
     * @param String name the name being searched for
     ******************************************/
    public ArrayList <BabyName> searchForName(String name) {
        ArrayList<BabyName> searchNameListByName = new ArrayList<BabyName>();
        for (BabyName b : list) {
            if (b.getName().equals(name)) {
                searchNameListByName.add(b);
            }
        }
        return searchNameListByName;
    }

    /*******************************************
     * Searches for the name in the database.
     * @param int year the year being searched
     ******************************************/
    public ArrayList <BabyName> searchForName(int year) {
        ArrayList<BabyName> searchNameListByYear = new ArrayList<BabyName>();
        for (BabyName b : list) {
            if (b.getBirthYear() == year) {
                searchNameListByYear.add(b);
            }
        }
        return searchNameListByYear;
    }

    /*******************************************
     * Generates The Top Ten names from a given year.
     * @param int year the year
     * @return ArrayList the top ten list
     ******************************************/
    public ArrayList <BabyName> topTenNames(int year) {
        ArrayList<BabyName> topTen = new ArrayList<BabyName>();
        BabyName top = new BabyName("", false, 0, 0);
        for (BabyName b : list) {
            if ((b.compareTo(b) > top.getNumBabies()) && topTen.size() < 10) {
                top = b;
                topTen.add(b);
            }
        }
        return topTen;
    }

}
