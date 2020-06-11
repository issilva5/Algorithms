package sort.trivial;

import java.util.Comparator;

import sort.Sort;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Selection extends Sort {

	@Override
	public void sort(Comparable[] array) {
		
		for (int i = 0; i < array.length; i++) {
			
			int min = i;
			for (int j = i + 1; j < array.length; j++) {
				if (super.less(array[j], array[min]))
					min = j;
			}
			super.swap(array, i, min);
			
		}
		
	}

	@Override
	public void sort(Object[] array, Comparator comp) {
		
			for (int i = 0; i < array.length; i++) {
			
			int min = i;
			for (int j = i + 1; j < array.length; j++) {
				if (comp.compare(array[j], array[min]) < 0)
					min = j;
			}
			super.swap(array, i, min);
			
		}
		
	}

}
