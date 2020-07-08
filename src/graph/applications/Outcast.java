package graph.applications;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

	private final WordNet wn;
	
	public Outcast(WordNet wordnet) {
		
		if (wordnet == null)
			throw new IllegalArgumentException();
		
		this.wn = wordnet;
		
	}

	public String outcast(String[] nouns) {
		
		String noun = null;
		int d = -1;
		
		for (int i = 0; i < nouns.length; i++) {
			
			int di = 0;
			
			for (int j = 0; j < nouns.length; j++) {
				
				if (!nouns[i].equals(nouns[j]))
					di += this.wn.distance(nouns[i], nouns[j]);
				
			}
			
			if (di > d) {
				
				d = di;
				noun = nouns[i];
				
			}
			
		}
		
		
		return noun;
	}

	public static void main(String[] args) {
		
		WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
		
	}

}
