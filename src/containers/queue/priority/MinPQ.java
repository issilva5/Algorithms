package containers.queue.priority;

public class MinPQ< Key extends Comparable<Key> > {

	private int length;
	private Key[] heap;
	
	@SuppressWarnings("unchecked")
	public MinPQ(int capacity) {
		
		this.heap = (Key[]) new Comparable[capacity+1];
		this.length = 0;
		
	}
	
	public MinPQ(Key[] array) {
		
		this(array.length);
		for (Key k : array)
			this.insert(k);
		
	}
	
	public void insert(Key element) {
		
		this.heap[++this.length] = element;
		this.swim(this.length);
		
	}
	
	private void swim(int i) {
		
		while (i > 1 && this.greater(i/2, i)) {
			
			this.swap(i, i/2);
			i /= 2;
			
		}
		
	}

	public Key deleteMin() {
		
		Key min = this.min();
		this.swap(1, this.length--);
		this.sink(1);
		this.heap[this.length+1] = null;
		return min;
		
	}
	
	private void sink(int k) {
		
		while (2*k <= this.length) {
			
			int i = 2*k;
			if (i < this.length && this.greater(i, i+1)) i++;
			if (!this.greater(k, i)) break;
			this.swap(i, k);
			k = i;
			
		}
		
	}

	public Key min() {
		
		if (this.isEmpty())
			throw new UnsupportedOperationException();
		
		return this.heap[1];
	}
	
	public boolean isEmpty() {
		return this.length == 0;
	}
	
	public int size() {
		return this.length;
	}
	
	private boolean greater(int i, int j) {
		
		return this.heap[i].compareTo(this.heap[j]) > 0;
		
	}
	
	private void swap(int i, int j) {
		
		Key aux = this.heap[i];
		this.heap[i] = this.heap[j];
		this.heap[j] = aux;
		
	}
	
}
