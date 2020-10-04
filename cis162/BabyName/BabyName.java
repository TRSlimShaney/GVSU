
/********************************************************
 * Stores information obtained from the baby name database.
 *
 * @author Shane Stacy
 * @version 11/9/2017
 *******************************************************/
public class BabyName implements Comparable
{

    String name = "";
    boolean gender = false;
    int numBabies = 0;
    int birthYear = 0;

    /*******************************************
     * BabyName constructor for a baby
     ******************************************/
    public BabyName (String n, boolean g, int count, int yr) {

        name = n;
        gender = g;
        numBabies = count;
        birthYear = yr;

    }

    /*******************************************
     * Check if the baby is a girl or not.
     * @return boolean true if girl false if boy
     ******************************************/
    public boolean isFemale() {
        if (gender == true) {
            return true;
        }
        else {
            return false;
        }
    }

    /*******************************************
     * Retrieves the name of the baby.
     * @return String the name of the baby
     ******************************************/
    public String getName() {
        return name;
    }

    /*******************************************
     * Retrieves the number of babies.
     * @return int the number of babies
     ******************************************/   
    public int getNumBabies() {
        return numBabies;
    }

    /*******************************************
     * Retrieves the birth year.
     * @return int the birth year
     ******************************************/    
    public int getBirthYear() {
        return birthYear;
    }

    /*******************************************
     * Sets the gender of the baby.
     * @param boolean z true if girl false if boy
     ******************************************/
    public void setGender(boolean z) {
        gender = z;
    }

    /*******************************************
     * Sets the name of the baby.
     * @param String y the name of the baby
     ******************************************/
    public void setName(String y) {
        name = y;
    }

    /*******************************************
     * Sets the number of babies.
     * @param int x the number of babies
     ******************************************/
    public void setNumBabies(int x) {
        numBabies = x;
    }

    /*******************************************
     * Sets the birth year of the baby.
     * @param int w the birth year of the baby
     ******************************************/
    public void setBirthYear(int w) {
        birthYear = w;
    }

    /*******************************************
     * Retrieves a formatted string.
     * @return String a formatted string
     ******************************************/
    public String toString() {
        String gender4 = "";
        if (isFemale() == false) {
            gender4 = "boys";
        }
        else {
            gender4 = "girls";
        }

        return numBabies + " " + gender4 + " named " + name + " in " + birthYear + "\n";
    }

    /*******************************************
     * Tests the methods.
     ******************************************/
    public static void main (String [] args) {
        BabyName Shane = new BabyName("Shane", false, 1, 1998);
        if (Shane.isFemale() == true) {
            System.out.println(Shane.getName() + " is a girl.");
        }
        else {
            System.out.println(Shane.getName() + " is a boy.");
        }
        System.out.println("There are " + Shane.getNumBabies() + " babies with that name.");
        System.out.println("This baby was born in " + Shane.getBirthYear() + ".");
        System.out.println("Now, to switch up the data.");
        Shane.setName("Shannon");
        Shane.setGender(true);
        Shane.setNumBabies(25);
        Shane.setBirthYear(1999);
        System.out.println("Data switched.  Now, let's check the methods again.");
        if (Shane.isFemale() == true) {
            System.out.println(Shane.getName() + " is a girl.");
        }
        else {
            System.out.println(Shane.getName() + " is a boy.");
        }
        System.out.println("There are " + Shane.getNumBabies() + " babies with that name.");
        System.out.println("This baby was born in " + Shane.getBirthYear() + ".");

    }

    public int compareTo (Object other) {
        BabyName b = (BabyName) other;
        return (b.numBabies - numBabies);   
    }

}
