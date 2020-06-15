package sort.trivial;

import java.util.Comparator;

import sort.Sort;

@SuppressWarnings("rawtypes")
public class Insertion extends Sort {

	@Override
	public void sort(Comparable[] array) {
		
		for (int i = 0; i < array.length; i++)
			for (int j = i; j > 0; j--)
				if (super.less(array[j], array[j-1]))
					super.swap(array, j, j-1);
				else break;

	}

	@Override
	public void sort(Object[] array, Comparator comp) {
		
		for (int i = 0; i < array.length; i++)
			for (int j = i; j > 0; j--)
				if (super.less(comp, array[j], array[j-1]))
					super.swap(array, j, j-1);
				else break;
		
	}

}
