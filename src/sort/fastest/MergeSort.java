package sort.fastest;

import java.util.Comparator;

import sort.Sort;

@SuppressWarnings("rawtypes")
public class MergeSort extends Sort {

	@Override
	public void sort(Comparable[] array) {
		
		Comparable[] aux = new Comparable[array.length];
		this.sort(array, aux, 0, array.length - 1);

	}

	private void sort(Comparable[] array, Comparable[] aux, int lo, int hi) {
		
		if (hi <= lo) return;
		int mid = (lo + hi) / 2;
		this.sort(array, aux, lo, mid);
		this.sort(array, aux, mid + 1, hi);
		this.merge(array, aux, lo, mid, hi);
			
	}

	@Override
	public void sort(Object[] array, Comparator comp) {
		
		Object[] aux = new Object[array.length];
		this.sort(array, aux, 0, array.length - 1, comp);

	}
	
	private void sort(Object[] array, Object[] aux, int hi, int lo, Comparator comp) {
		
		if (hi <= lo) return;
		int mid = (lo + hi) / 2;
		this.sort(array, aux, lo, mid, comp);
		this.sort(array, aux, mid + 1, hi, comp);
		this.merge(array, aux, lo, mid, hi, comp);
		
	}

	public void merge(Object[] array, Object[] aux, int lo, int mid, int hi, Comparator comp) {
		
		for (int i = lo; i <= hi; i++) aux[i] = array[i];
		
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			
			if (i > mid) array[k] = aux[j++];
			else if (j > hi) array[k] = aux[i++];
			else if (super.less(comp, aux[j], aux[i])) array[k] = aux[j++];
			else array[k] = aux[i++];
			
		}
		
	}

	public void merge(Comparable[] array, Comparable[] aux, int lo, int mid, int hi) {
		
		for (int i = lo; i <= hi; i++) aux[i] = array[i];
		
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			
			if (i > mid) array[k] = aux[j++];
			else if (j > hi) array[k] = aux[i++];
			else if (super.less(aux[j], aux[i])) array[k] = aux[j++];
			else array[k] = aux[i++];
			
		}
		
	}

}
