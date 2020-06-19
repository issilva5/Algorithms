package sort.fastest;

import java.util.Comparator;

import sort.Sort;
import sort.applications.Shuffle;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ThreeWayQuickSort extends Sort {

	@Override
	public void sort(Comparable[] array) {
		
		Shuffle.shuffle(array);
		this.sort(array, 0, array.length - 1);

	}

	private void sort(Comparable[] array, int lo, int hi) {
		
		if (hi <= lo)
			return;
		
		int i = lo, lt = lo, gt = hi;
		Comparable v = array[lo];
		
		while (i <= gt) {
			
			int cmp = array[i].compareTo(v);
			if (cmp < 0) super.swap(array, lt++, i++);
			else if (cmp > 0) super.swap(array, i, gt--);
			else i++;
			
		}
		
		this.sort(array, lo, lt - 1);
		this.sort(array, gt + 1, hi);
		
		
	}

	@Override
	public void sort(Object[] array, Comparator comp) {
		
		Shuffle.shuffle(array);
		this.sort(array, 0, array.length - 1, comp);

	}

	private void sort(Object[] array, int lo, int hi, Comparator comp) {
		
		if (hi <= lo)
			return;
		
		int i = lo, lt = lo, gt = hi;
		Object v = array[lo];
		
		while (i <= gt) {
			
			int cmp = comp.compare(array[i], v);
			if (cmp < 0) super.swap(array, lt++, i++);
			else if (cmp > 0) super.swap(array, i, gt--);
			else i++;
			
		}
		
		this.sort(array, lo, lt - 1, comp);
		this.sort(array, gt + 1, hi, comp);
		
	}

}
