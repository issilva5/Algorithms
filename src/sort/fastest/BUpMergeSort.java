package sort.fastest;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class BUpMergeSort extends MergeSort {

	@Override
	public void sort(Comparable[] array) {
		
		int N = array.length;
		Comparable[] aux = new Comparable[N];
		
		for (int sz = 1; sz < N; sz += sz)
			for (int lo = 0; lo < N - sz; lo += sz+sz)
				this.merge(array, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
			

	}

	@Override
	public void sort(Object[] array, Comparator comp) {
		
		int N = array.length;
		Object[] aux = new Object[N];
		
		for (int sz = 1; sz < N; sz += sz)
			for (int lo = 0; lo < N - sz; lo += sz+sz)
				this.merge(array, aux, lo, lo + sz, Math.min(lo + sz + sz - 1, N - 1), comp);

	}

}
