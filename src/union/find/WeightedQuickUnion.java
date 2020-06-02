package union.find;

public class WeightedQuickUnion extends QuickUnion {

	protected int[] sz;
	
	public WeightedQuickUnion(int N) {
		super(N);
		this.sz = new int[N];
		
		for(int i = 0; i < N; i++)
			sz[i] = 1;
		
	}
	
	@Override
	public void union(int p, int q) {
		
		int rp = super.root(p);
		int rq = super.root(q);
		if(rp == rq) return;
		
		if(sz[rp] >= sz[rq]) {
			
			super.id[rq] = rp;
			this.sz[rp] += this.sz[rq];
			
		} else {
			
			super.id[rp] = rq;
			this.sz[rq] += this.sz[rq];
			
		}
		
	}

}
