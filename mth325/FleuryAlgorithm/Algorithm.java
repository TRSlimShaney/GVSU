import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Algorithm {
	Map<String, String> edges;
	Map<String, Integer> degrees;
	Map<String, String> path;
	boolean couldBeACircuit = false;
	boolean couldBeAPath = false;
	boolean connected;

	Map<ArrayList<Integer>, Map<String, String>> graph;  // Integer is the degree of the vertex, first of submap
	//  is the string for the vertex and so is second.
	
	Algorithm(Map<String, String> edge2, Map<String, Integer> degree2){
		edges = new HashMap<String, String>();
		degrees = new HashMap<String, Integer>();
		path = new HashMap<String, String>();
		edges = edge2;
		degrees = degree2;
	}
	
	void doIt() {
		int count = 0;
		
		for (String key : degrees.keySet()) {  //  the number of odd degrees must either be 2 or 0 for it to have a path.
			if (degrees.get(key) %2 == 1) {
				count++;
			}
		}
		if (count == 2 || count == 0) {
			couldBeAPath = true;
			System.out.println("This graph could have a path.");
		}
		else {
			System.out.println("This graph could not have a path.");
		}
		
		count = 0;
		if (couldBeAPath) {
			for (String key : degrees.keySet()) {  //  there can be no odd degrees if there is a circuit.
				if (degrees.get(key) %2 == 1) {
					count++;
				}
			}
			if (count > 0) {
				System.out.println("This graph could not have a circuit.");
			}
			else {
				couldBeACircuit = true;
				System.out.println("This graph could have a circuit.");
			}
		}
		
		
		if (couldBeACircuit || couldBeAPath) {
			
			recursivePath(edges);
			
			if (path.keySet().toArray()[0] == path.values().toArray()[path.size() - 1]) {  //  check to see if the path ended where it started
				if (couldBeACircuit) {
					System.out.println("This graph has a circuit and a path.");
					System.out.println(path);
				}
				
			}
			else if (couldBeAPath) {
				System.out.println("This graph has a path but not a circuit.");
				System.out.println(path);
			}
		}
		else {
			System.out.println("There is no path or circuit in this graph.");
		}
		
		
		
	}
	
	void recursivePath(Map<String, String> edges5) {
		Map<String, String> missedEdges = new HashMap<String, String>();
		String startPoint;
		String secondPoint;
		String nextPoint;
		
		for (String key : edges5.keySet()) {  //  find a path
			secondPoint = edges5.get(key);
			for (String key2 : edges5.keySet()) {
				if (key2 == secondPoint) {
					path.put(key, secondPoint);
				}
				else if (edges5.get(secondPoint) == key2){
					missedEdges.put(key, secondPoint);  //  if an edge is not used, store it for later
				}
			}
		}
		/*
		if (missedEdges.size() == 0) {
			return;
		}
		else {
			recursivePath(missedEdges);
		}
		*/
	}
}
