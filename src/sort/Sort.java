package sort;

import java.util.Comparator;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Sort {
	
	public abstract void sort(Comparable[] array);
	
	public abstract void sort(Object[] array, Comparator comp);
	
	protected boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}
	
	protected void swap(Object[] array, int i, int j) {
		Object aux = array[i];
		array[i] = array[j];
		array[j] = aux;
	}

}
