
public class CountIt2 {
public long SnippetLog(long n) {
		long i, j, x = 0;

		i = 1;  x++;
		while (i < n) {	x++;
			x++;	// SomeStatement
			i = i * 2;  x++;
		              }
		x++; 				// Can you explain why is this here?
		return x;
	}
	
	public static void main(String[] args) {
		CountIt2 r = new CountIt2();
		Long t = System.currentTimeMillis();
		System.out.println(r.SnippetLog(1));
		System.out.println ("Time:" + (System.currentTimeMillis() - t));
	}
}
