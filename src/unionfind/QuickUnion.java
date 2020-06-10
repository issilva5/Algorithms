package unionfind;

public class QuickUnion implements UF {

	protected int[] id;
	
	public QuickUnion(int N) {
		
		this.id = new int[N];
		for(int i = 0; i < N; i++)
			this.id[i] = i;
		
	}
	
	protected int root(int i) {
		
		while(i != this.id[i])
			i = this.id[i];
		
		return i;
		
	}
	
	@Override
	public void union(int p, int q) {

		int rp = this.root(p);
		int rq = this.root(q);
		if(rp == rq) return;
		
		this.id[rp] = rq;

	}

	@Override
	public boolean connected(int p, int q) {
		return this.root(p) == this.root(q);
	}

}
