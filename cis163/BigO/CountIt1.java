
public class CountIt1 {
	public long SnippetNestedLoop(long n) {
		long i, j, x = 0;
		i = 0;	x++;
		while (i < n) {x++;			// i < n
			x++;				// SomeStatement
			j = 0; 	x++;		// j = 0;
			while (j < n) { x++; 		// j < n
				x++;		// SomeStatement
				j++; x++;		// j++;
			}
			x++; 				// Can you explain why is this here?
			i++;  x++;			// i++;
		}
		x++; 					// Can you explain why is this here? Ans: i < n
		return x;
	    }
	public static void main(String[] args) {
		CountIt1 r = new CountIt1();
		Long t = System.currentTimeMillis();
		System.out.println(r.SnippetNestedLoop(50000));
		System.out.println ("Time:" + (System.currentTimeMillis() - t));
	}
}
