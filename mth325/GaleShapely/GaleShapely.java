import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
	
	Map<String, ArrayList<String>> map1 = new HashMap<String, ArrayList<String>>();
	Map<String, ArrayList<String>> map2 = new HashMap<String, ArrayList<String>>();
	ArrayList<String> list1 = new ArrayList<String>();
	ArrayList<String> list2 = new ArrayList<String>();
	ArrayList<String> list3 = new ArrayList<String>();
	ArrayList<String> list4 = new ArrayList<String>();
	ArrayList<String> list5 = new ArrayList<String>();
	ArrayList<String> list6 = new ArrayList<String>();
	ArrayList<String> list7 = new ArrayList<String>();
	ArrayList<String> list8 = new ArrayList<String>();
	
	list1.add("Z");  //  put the preferences in the list for that item, 1st priority first, for map1
	list1.add("X");
	list1.add("Y");
	list1.add("W");
	
	list2.add("Y");
	list2.add("W");
	list2.add("X");
	list2.add("Z");
	
	list3.add("W");
	list3.add("X");
	list3.add("Y");
	list3.add("Z");
	
	list4.add("X");
	list4.add("Y");
	list4.add("Z");
	list4.add("W");
	//////////////////////////////////
	list5.add("A");  //  now, for map2
	list5.add("B");
	list5.add("C");
	list5.add("D");
	
	list6.add("A");
	list6.add("C");
	list6.add("B");
	list6.add("D");
	
	list7.add("C");
	list7.add("D");
	list7.add("A");
	list7.add("B");
	
	list8.add("C");
	list8.add("B");
	list8.add("A");
	list8.add("D");
	//////////////////////////////////
	
	map1.put("A", list1);  //  now, key the preference lists to their appropriate elements
	map1.put("B", list2);
	map1.put("C", list3);
	map1.put("D", list4);
	
	map2.put("X", list5);
	map2.put("Y", list6);
	map2.put("Z", list7);
	map2.put("W", list8);
	
	Algorithm a = new Algorithm(map1, map2);
	a.doit();
		
	}

}
