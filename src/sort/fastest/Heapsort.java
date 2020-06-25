package sort.fastest;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Heapsort {
	
	public void sort(Comparable[] array) {
		
		int N = array.length-1;
		for (int i = N/2; i >= 0; i--) {
			this.sink(array, i, N);
		}
		
		while (N > 0) {
			this.swap(array, 0, N--);
			this.sink(array, 0, N);
		}
		
	}
	
	private void sink(Comparable[] array, int k, int N) {
		
		while (2*k + 1 <= N) {
			
			int i = 2*k + 1;
			if (i < N && this.less(array, i, i+1)) i++;
			if (!this.less(array, k, i)) break;
			this.swap(array, i, k);
			k = i;
			
		}
		
	}
	
	private boolean less(Comparable[] array, int i, int j) {
			
		return array[i].compareTo(array[j]) < 0;
			
	}
	
	private void swap(Comparable[] array, int i, int j) {
		
		Comparable aux = array[i];
		array[i] = array[j];
		array[j] = aux;
		
	}

}
