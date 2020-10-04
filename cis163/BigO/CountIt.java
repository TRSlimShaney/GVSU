
public class CountIt {
	public int linearLoop(int N) {

		int x = 0;
		int count = 0;
		x++;
		while (count < N) {
			x++;
			System.out.println(".");
			x++;
			count++;
		}
		return x;
	}

	public static void main(String[] args) {
		CountIt ex = new CountIt();
		int N = 50000;
		long start = System.currentTimeMillis();
		ex.linearLoop(N);
		Long endTime = System.currentTimeMillis();
		System.out.println(
				"\nN = " + N + ". Loops ran in: " + (endTime - start));
	}
}