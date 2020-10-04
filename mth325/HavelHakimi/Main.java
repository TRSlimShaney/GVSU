import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
	
	ArrayList<Integer> theSequence = new ArrayList<Integer>();
	theSequence.add(5);
	theSequence.add(4);
	theSequence.add(4);
	theSequence.add(2);
	theSequence.add(2);
	theSequence.add(2);
	theSequence.add(1);
	Algorithm a = new Algorithm(theSequence);
	a.doIt();
		
	}

}
