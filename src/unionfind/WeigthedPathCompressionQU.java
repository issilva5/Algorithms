package unionfind;

public class WeigthedPathCompressionQU extends WeightedQuickUnion {

	public WeigthedPathCompressionQU(int N) {
		super(N);
	}
	
	@Override
	protected int root(int i) {
		
		while(i != this.id[i]) {
			
			this.id[i] = this.id[this.id[i]];
			i = this.id[i];
			
		}
		
		return i;
		
	}

}
