import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Primm {
	Map<String, Integer> map01;
	Map<String, Integer> map02;
	ArrayList<String> tree;
	String start = "A";
	int count = 1;
	
	Primm(Map<String, Integer> map1, Map<String, Integer> map2) {
		map01 = new HashMap<String, Integer>();
		map02 = new HashMap<String, Integer>();
		tree = new ArrayList<String>();
		map01 = map1;
		map02 = map2;
	}
	
	void primm(String edge) {
		
		if (edge == null) {
			for (String b : map01.keySet()) {
				if (b.contains(start)) {
					primm(b);
					return;
				}
			}
		}
		
		String edge1 = edge.substring(0, 2);
		String edge2 = edge.substring(1, -1);
		String vertex1 = edge.substring(0, 1);
		String vertex2 = edge.substring(1, 2);
		boolean couldConnect = false;
		
		for (String c : tree) {
			if (c.contains(vertex1) || c.contains(vertex2)) {
				couldConnect = true;
				break;
			}
		}
		
		if (map01.get(edge1) == count || map01.get(edge2) == count) {
			if (tree.contains(edge) == false && couldConnect) {
				tree.add(edge);
			}
			return;
		}
		else {
			count++;
			primm(edge);
		}
	}
	
	void dijkstra(String edge) {
		
	}
	
	public static void main(String[] args) {
		Map<String, Integer> map001;
		Map<String, Integer> map002;
		map001 = new HashMap<String, Integer>();
		map002 = new HashMap<String, Integer>();
		
		map001.put("AB", 1);
		//map002.put("BA", 1);
		map001.put("AD", 1);
		//map002.put("DA", 1);
		map001.put("AG", 1);
		//map002.put("GA", 1);
		map001.put("AH", 3);
		//map002.put("HA", 3);
		map001.put("BD", 3);
		//map002.put("DB", 3);
		map001.put("AE", 3);
		//map002.put("EA", 3);
		map001.put("GF", 3);
		//map002.put("FG", 3);
		
		Primm a = new Primm(map001, map002);
		a.primm(null);
		System.out.println(a.tree);
	}

}