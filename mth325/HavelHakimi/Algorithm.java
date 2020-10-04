import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {
	
	ArrayList<Integer> list;
	
	
	Algorithm(ArrayList<Integer> stuff){
		list = new ArrayList<Integer>();
		list = stuff;
	}
	
	 void doIt() {
		 if (list.size() == 0) {  //  if the list is empty, stop
			 return;
		 }
		 		 
		 Collections.sort(list); // sort the list in ascending order
		 Collections.reverse(list);  //  then reverse it
		 
		 int N = list.get(0);
		 
		 if (list.size() - 1 < N) {  //  if the first integer in the sequence is greater than the size minus one, stop
			 System.out.println("This sequence is not graphic. other");
			 return;
		 }
		 
		 list.remove(0);
		 
		for (int c = 0; c < N; c++) {  //  subtract 1 from the appropriate elements
		    list.set(c, list.get(c) - 1);
			
			if (list.get(c) == -1) {
				System.out.println("This sequence is not graphic. = -1");
				return;
			}
		}
		
		
		
		System.out.println(list);  //  print the list
		doIt();  //  recursion
	}
	 

}
