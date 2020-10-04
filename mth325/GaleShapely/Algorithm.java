import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Algorithm {
	
	Map<String, ArrayList<String>> map01;
	Map<String, ArrayList<String>> map02;
	Map<String, String> stables;
	
	Algorithm(Map<String, ArrayList<String>> map1, Map<String, ArrayList<String>> map2){
		map01 = new HashMap<String, ArrayList<String>>();
		map02 = new HashMap<String, ArrayList<String>>();
		stables = new HashMap<String, String>();
		map01 = map1;
		map02 = map2;
	}
	
	void doit() {
	
	for (String key2 : map02.keySet()) {  //  first, iterate through the dogs
		for (String key : map01.keySet()) {  //  second, iterate through the owners
			int count = 0;
			int count2 = 0;
			for (int c = 0; c < map01.get(key).size(); c++) {  // third, iterate through the lists of preferences
				if (key2 == map01.get(key).get(c) && count <= c) {  // if the current dog is the highest preference in the owner's list so far, set the pairing
					stables.put(key2, key);
					count = c;
				}
			}
		}
	}
	
	System.out.println("The optimal pairing is:");  //  print the pairings
	System.out.println(stables);
}
	
	
	
}

