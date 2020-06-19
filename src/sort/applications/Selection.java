package sort.applications;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Selection {

	public static Comparable select(Comparable[] array, int k) {
		
		Shuffle.shuffle(array);
		int lo = 0, hi = array.length - 1;
		while (lo < hi) {
			
			int j = partition(array, lo, hi);
			if (j > k) hi = j-1;
			else if (j < k) lo = j+1;
			else return array[k];
			
		}
		
		return array[k];
		
	}
	
	private static int partition(Comparable[] array, int lo, int hi) {
		
		int i = lo;
		int j = hi+1;
		
		while (true) {
			
			while (less(array[++i], array[lo]))
				if (i == hi) break;
			
			while (less(array[lo], array[--j]))
				if (j == lo) break;
			
			if (i >= j) break;
			
			swap(array, i, j);
			
		}
		
		swap(array, lo, j);
		
		return j;
		
	}
	
	private static boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}
	
	private static void swap(Object[] array, int i, int j) {
		Object aux = array[i];
		array[i] = array[j];
		array[j] = aux;
	}
	
}
