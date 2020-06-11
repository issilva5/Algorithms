package sort.applications;

import edu.princeton.cs.algs4.StdRandom;
import sort.Sort;

public class Shuffle {

	public void shuffle(Object[] array) {
		
		for (int i = 0, r; i < array.length; i++) {
			
			r = StdRandom.uniform(i + 1);
			this.swap(array, i, r);
			
		}
		
	}
	
	public void shuffleSortBased(Sort sort, Object[] array) {
		
		Pair[] paired = new Pair[array.length];
		for (int i = 0; i < paired.length; i++)
			paired[i] = new Pair(array[i], StdRandom.uniform());
		
		sort.sort(paired);
		
		for (int i = 0; i < paired.length; i++)
			array[i] = paired[i].obj;
		
	}
	
	private class Pair implements Comparable<Pair> {
		
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
	
	private void swap(Object[] array, int i, int j) {
		Object aux = array[i];
		array[i] = array[j];
		array[j] = aux;
	}
	
}
