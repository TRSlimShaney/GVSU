import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
	
	Map<String, String> edges;
	Map<String, Integer> degrees;
	edges = new HashMap<String, String>();
	degrees = new HashMap<String, Integer>();
	
	edges.put("A", "B");
	edges.put("B", "C");
	edges.put("C", "A");
	edges.put("A", "D");
	edges.put("D", "B");
	
	degrees.put("A", 3);
	degrees.put("B", 3);
	degrees.put("C", 2);
	degrees.put("D", 2);
	
	Algorithm a = new Algorithm(edges, degrees);
	a.doIt();
		
	}

}
