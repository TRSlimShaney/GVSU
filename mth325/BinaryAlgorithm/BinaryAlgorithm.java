import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BinaryAlgorithm {
	String theString;
	
	BinaryAlgorithm() {
		theString = "";
	}
	
	void convertBaseTenToBinary(int num) {
		int num2 = num;
		int remainder = 0;
		
		if (num <= 0) {  //  if the current number is 0 or less, print the string and return.
			System.out.println("The binary number is: " + theString);
			return;
		}
		
		remainder = num2 % 2;  //  the remainder (bit) is equal to the number modulo 2.
		num2 = num2 / 2;  //  the next number is equal to the number divided by 2.
		theString = remainder + theString;  //  the new string is the new bit plus the bits already in it.
		convertBaseTenToBinary(num2);  // recursion until return
	}
}

