import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BinaryMain {

	public static void main(String[] args) {
	
	int num = 20;  //  this is the base-ten number to be converted.
	
	System.out.println("The base-ten number is: " + num);
	BinaryAlgorithm a = new BinaryAlgorithm();
	a.convertBaseTenToBinary(num);
		
	}
}
