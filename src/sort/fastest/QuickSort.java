package sort.fastest;

import java.util.Comparator;

import sort.Sort;
import sort.applications.Shuffle;

@SuppressWarnings("rawtypes")
public class QuickSort extends Sort {

	@Override
	public void sort(Comparable[] array) {
		
		Shuffle.shuffle(array);
		this.sort(array, 0, array.length-1);

	}

	private void sort(Comparable[] array, int lo, int hi) {
		
		if (hi <= lo)
			return;
		
		int j = this.partition(array, lo, hi);
		
		this.sort(array, lo, j-1);
		this.sort(array, j+1, hi);

	}

	private int partition(Comparable[] array, int lo, int hi) {
		
		int i = lo;
		int j = hi+1;
		
		while (true) {
			
			while (super.less(array[++i], array[lo]))
				if (i == hi) break;
			
			while (super.less(array[lo], array[--j]))
				if (j == lo) break;
			
			if (i >= j) break;
			
			super.swap(array, i, j);
			
		}
		
		super.swap(array, lo, j);
		
		return j;
		
		
	}

	@Override
	public void sort(Object[] array, Comparator comp) {
		
		Shuffle.shuffle(array);
		this.sort(array, 0, array.length-1, comp);

	}

	private void sort(Object[] array, int lo, int hi, Comparator comp) {
		
		if (hi <= lo)
			return;
		
		int j = this.partition(array, lo, hi, comp);
		
		this.sort(array, lo, j-1, comp);
		this.sort(array, j+1, hi, comp);
		
	}

	private int partition(Object[] array, int lo, int hi, Comparator comp) {
		
		int i = lo;
		int j = hi+1;
		
		while (true) {
			
			while (super.less(comp, array[++i], array[lo]))
				if (i == hi) break;
			
			while (super.less(comp, array[lo], array[--j]))
				if (j == lo) break;
			
			if (i >= j) break;
			
			super.swap(array, i, j);
			
		}
		
		super.swap(array, lo, j);
		
		return j;
		
	}

}
