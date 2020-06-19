package sort.applications;

import edu.princeton.cs.algs4.StdRandom;
import sort.Sort;

public class Shuffle {

	public static void shuffle(Object[] array) {
		
		for (int i = 0, r; i < array.length; i++) {
			
			r = StdRandom.uniform(i + 1);
			swap(array, i, r);
			
		}
		
	}
	
	public static void shuffleSortBased(Sort sort, Object[] array) {
		
		Pair[] paired = new Pair[array.length];
		for (int i = 0; i < paired.length; i++)
			paired[i] = new Pair(array[i], StdRandom.uniform());
		
		sort.sort(paired);
		
		for (int i = 0; i < paired.length; i++)
			array[i] = paired[i].obj;
		
	}
	
	private static class Pair implements Comparable<Pair> {
		
		Object obj;
		double weigth;
		
		public Pair(Object object, double uniform) {
			this.obj = object;
			this.weigth = uniform;
		}

		@Override
		public int compareTo(Pair that) {
			return Double.compare(this.weigth, that.weigth);
		}
		
		
		
	}
	
	private static void swap(Object[] array, int i, int j) {
		Object aux = array[i];
		array[i] = array[j];
		array[j] = aux;
	}
	
}
