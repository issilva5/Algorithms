package sort;

import java.util.Comparator;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Sort {
	
	public abstract void sort(Comparable[] array);
	
	public abstract void sort(Object[] array, Comparator comp);
	
	protected boolean less(Comparator comp, Object a, Object b) {
		return comp.compare(a, b) < 0;
	}
	
	protected boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}
	
	protected void swap(Object[] array, int i, int j) {
		Object aux = array[i];
		array[i] = array[j];
		array[j] = aux;
	}
	
	public static boolean isSorted(Comparable[] array) {
		
		boolean issorted = true;
		
		for (int i = 1; i < array.length && issorted; i++)
			issorted = array[i-1].compareTo(array[i]) <= 0;
		
		return issorted;
		
	}
	
	public static boolean isSorted(Object[] array, Comparator comp) {
		
		boolean issorted = true;
		
		for (int i = 1; i < array.length && issorted; i++)
			issorted = comp.compare(array[i-1], array[i]) <= 0;
		
		return issorted;
		
	}

}
