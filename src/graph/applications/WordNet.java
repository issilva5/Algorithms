package graph.applications;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

	private final Digraph network;
	private final Set<String> words;
	private final Map<Integer, String> dict;
	private final SAP lcaf;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {

		if (synsets == null || hypernyms == null)
			throw new IllegalArgumentException();

		this.words = new TreeSet<>();
		this.dict = new TreeMap<>();

		In in = new In(synsets);
		int nLine = 0;

		while (in.hasNextLine()) {

			nLine++;

			String line = in.readLine();
			String[] fields = line.split(",");
			Integer id = Integer.parseInt(fields[0]);
			this.dict.put(id, fields[1]);
			String[] nouns = fields[1].split(" ");

			for (String n : nouns)
				this.words.add(n);

		}

		in.close();

		this.network = new Digraph(nLine);
		in = new In(hypernyms);

		while (in.hasNextLine()) {

			String line = in.readLine();
			String[] fields = line.split(",");
			Integer id = Integer.parseInt(fields[0]);

			for (int i = 1; i < fields.length; i++)
				this.network.addEdge(id, Integer.parseInt(fields[i]));

		}

		this.lcaf = new SAP(this.network);

	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return this.words;
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {

		if (word == null)
			throw new IllegalArgumentException();

		return this.words.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {

		if (nounA == null || nounB == null)
			throw new IllegalArgumentException();

		if (!this.isNoun(nounA) || !this.isNoun(nounB))
			throw new IllegalArgumentException();

		return this.lcaf.length(this.findSyns(nounA), this.findSyns(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA
	// and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {

		if (nounA == null || nounB == null)
			throw new IllegalArgumentException();

		if (!this.isNoun(nounA) || !this.isNoun(nounB))
			throw new IllegalArgumentException();

		return dict.get(this.lcaf.ancestor(this.findSyns(nounA), this.findSyns(nounB)));
	}

	private Iterable<Integer> findSyns(String noun) {

		Queue<Integer> q = new Queue<Integer>();

		for (Integer i : this.dict.keySet()) {
			String[] words = this.dict.get(i).split(" ");

			for (String w : words)
				if (w.equals(noun))
					q.enqueue(i);

		}

		return q;
	}

	// do unit testing of this class
	public static void main(String[] args) {

		WordNet wn = new WordNet(args[0], args[1]);
		StdOut.println(wn.sap("worm", "bird"));
		StdOut.println(wn.distance("worm", "bird"));
		StdOut.println();
		StdOut.println(wn.sap("municipality", "region"));
		StdOut.println(wn.distance("municipality", "region"));
		StdOut.println();
		StdOut.println(wn.sap("edible_fruit", "physical_entity"));
		StdOut.println(wn.distance("edible_fruit", "physical_entity"));
		StdOut.println();
		StdOut.println(wn.sap("edible_fruit", "individual"));
		StdOut.println(wn.distance("edible_fruit", "individual"));
		StdOut.println();
		StdOut.println(wn.sap("white_marlin", "mileage"));
		StdOut.println(wn.distance("white_marlin", "mileage"));
		StdOut.println();
		StdOut.println(wn.sap("black_marlin", "Black_Plague"));
		StdOut.println(wn.distance("black_marlin", "Black_Plague"));
		StdOut.println();
		StdOut.println(wn.sap("American_water_spaniel", "histology"));
		StdOut.println(wn.distance("American_water_spaniel", "histology"));
		StdOut.println();
		StdOut.println(wn.sap("Brown_Swiss", "barrel_roll"));
		StdOut.println(wn.distance("Brown_Swiss", "barrel_roll"));
		StdOut.println();
		StdOut.println(wn.sap("Ambrose", "Brown_Swiss"));
		StdOut.println(wn.distance("Ambrose", "Brown_Swiss"));

	}

}
