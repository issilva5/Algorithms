package union.find;

import java.util.Scanner;

public class Exercise03 {

	/*
	 * Successor with delete.
	 * Given a set of n integers S={0,1,...,n−1} and
	 * a sequence of requests of the following form:
	 *  - Remove x from S
	 *  - Find the successor of x: the smallest y in S such that y ≥ x.
	 * 
	 * design a data type so that all operations (except construction)
	 * take logarithmic time or better in the worst case.
	 */
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n, m, p;
		n = Integer.parseInt(sc.nextLine());
		m = Integer.parseInt(sc.nextLine());
		
		MaximumUF unionF = new MaximumUF(n);
		
		for(int i = 0; i < m; i++) {
			String l = sc.nextLine();
			String[] s = l.split(" ");
			
			String op = s[0];
			p = Integer.parseInt(s[1]);

			if(op.compareTo("r") == 0) {
				if(p+1 < n)
					unionF.union(p, p+1);
			} else {
				if(p+1 < n)
					System.out.println(unionF.find(p) + "");
				else
					System.out.println("-1");
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
