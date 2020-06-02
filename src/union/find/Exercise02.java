package union.find;

import java.util.Scanner;

public class Exercise02 {

	/*
	 * Union-find with specific canonical element.
	 * Add a method find() to the union-find data type so that 
	 * find(i) returns the largest element in the connected component
	 * containing i. The operations, union(), connected(), and 
	 * find() should all take logarithmic time or better.
	 * For example, if one of the connected components is {1,2,6,9},
	 * then the find() method should return 9 for each of the 
	 * four elements in the connected components.
	 */
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n, m, p, q;
		n = Integer.parseInt(sc.nextLine());
		m = Integer.parseInt(sc.nextLine());
		
		MaximumUF unionF = new MaximumUF(n);
		
		for(int i = 0; i < m; i++) {
			String l = sc.nextLine();
			String[] s = l.split(" ");
			
			String op = s[0];
			p = Integer.parseInt(s[1]);

			if(op.compareTo("u") == 0) {
				q = Integer.parseInt(s[2]);
				unionF.union(p, q);
			} else {
				System.out.println(unionF.find(p) + "");
			}

		}
		
		sc.close();
		
		
	}
	
	private static class MaximumUF extends WeigthedPathCompressionQU {
		
		private int[] maximum;
		
		public MaximumUF(int N) {
			super(N);
			this.maximum = new int[N];
			
			for(int i = 0; i < N; i++) {
				this.maximum[i] = i;
			}
		}
		
		@Override
		public void union(int p, int q) {
			
			int rp = super.root(p);
			int rq = super.root(q);
			if(rp == rq) return;
			
			if(super.sz[rp] >= super.sz[rq]) {
				
				super.id[rq] = rp;
				super.sz[rp] += super.sz[rq];
				this.maximum[rp] = Integer.max(this.maximum[rp], this.maximum[rq]);
				
			} else {
				
				super.id[rp] = rq;
				super.sz[rq] += super.sz[rq];
				this.maximum[rq] = Integer.max(this.maximum[rp], this.maximum[rq]);
				
			}
			
		}
		
		public int find(int p) {
			return this.maximum[super.root(p)];
		}
		
	}
	
}
