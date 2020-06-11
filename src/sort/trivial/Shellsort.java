package sort.trivial;

import java.util.Comparator;

import sort.Sort;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Shellsort extends Sort {

	@Override
	public void sort(Comparable[] array) {
		
		int h = 1;
		while (h < array.length/3) h = 3 * h + 1;
		
		while (h >= 1) {
			
			for (int i = h; i < array.length; i++) {
				
				for (int j = i; j >= h && super.less(array[j], array[j-h]); j -= h)
					 super.swap(array, j, j-h);
				
			}
			
			h /= 3;
			
		}

	}


	@Override
	public void sort(Object[] array, Comparator comp) {
		
		int h = 1;
		while (h < array.length/3) h = 3 * h + 1;
		
		while (h >= 1) {
			
			for (int i = h; i < array.length; i++) {
				
				for (int j = i; j >= h && comp.compare(array[j], array[j-h]) < 0; j -= h)
					 super.swap(array, j, j-h);
				
			}
			
			h /= 3;
			
		}
		
	}

}
