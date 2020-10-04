
/*************************************************
 * This class tests MyPhone.
 *
 * @author Shane Stacy
 * @version 10/8/17
 ************************************************/
public class PhoneTest
{

    /*********************************
     * This main creates some phone objects and manipulates
     * some data types associated with a smartphone.
     ********************************/

    public static void main(String args[]){

        //  this object should represent normal operation
        MyPhone normal = new MyPhone("Shane Stacy", "6161111111");

        normal.setPhoneNumber("6161111111");
        normal.chargeBattery(120);  // fills battery 
        normal.streamAudio(360);  //  stream audio for 360 mins.
        normal.chargeBattery(30);  //  charge the battery for 30 mins.
        normal.streamAudio(530);  //  stream audio for 530 mins.
        normal.streamAudio(200);  //  stream audio for 200 mins.
        normal.sendText("message");  //  send a message
        normal.printStatement();  //  print this object's properties
        System.out.println("");

        //  this object tries to find faults in MyPhone code.
        MyPhone faults = new MyPhone("Lookingfor Faults", "6162222222");  //  should return an error, with number then set to 9999999999

        faults.setPhoneNumber("61622222222");
        faults.chargeBattery(-5);   //  try a negative number.  Should return an error.
        faults.streamAudio(-5);  //  try a negative number.  Should return an error.
        faults.chargeBattery(1000);   //  try a big number.  Should only fill battery to 100% or 1.0.
        faults.streamAudio(5000);  //  try a big number.  Should work and add appropriate charges.
        faults.sendText("ThisIsAreallyLongMessageThisIsAreallyLongMessageThisIsAreallyLongMessageThisIsAreallyLongMessage");  //  test string length capabilities
        faults.printStatement();  //  print this object's properties

    }  
}
