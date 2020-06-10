package containers;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		
		RandomizedQueue<String> rq = new RandomizedQueue<>();
		int n = Integer.parseInt(args[0]);
		
		while(!StdIn.isEmpty()) {
			
			rq.enqueue(StdIn.readString());
			
		}
		
		for(int i = 0; i < n; i++) {
			StdOut.println(rq.dequeue());
		}

	}

}