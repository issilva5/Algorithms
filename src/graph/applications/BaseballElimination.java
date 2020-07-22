package graph.applications;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

	private final int numberOfTeams;
	private final int[] wins;
	private final int[] losses;
	private final int[] remaining;
	private final int[][] schedule;
	private final Map<String, Integer> dict;
	private final BaseballVertex s = new BaseballVertex(-1, -1);
	private final BaseballVertex t = new BaseballVertex(-2, -2);

	public BaseballElimination(String filename) {
		// create a baseball division from given filename in format specified below

		In in = new In(filename);
		this.numberOfTeams = in.readInt();
		in.readLine();

		this.wins = new int[this.numberOfTeams];
		this.losses = new int[this.numberOfTeams];
		this.remaining = new int[this.numberOfTeams];
		this.schedule = new int[this.numberOfTeams][this.numberOfTeams];
		this.dict = new TreeMap<>();

		int counter = 0;

		while (in.hasNextLine()) {

			String team = in.readString();
			this.dict.put(team, counter);
			this.wins[counter] = in.readInt();
			this.losses[counter] = in.readInt();
			this.remaining[counter] = in.readInt();

			for (int i = 0; i < this.numberOfTeams; i++)
				this.schedule[counter][i] = in.readInt();

			counter++;

			in.readLine();

		}

	}

	public int numberOfTeams() {
		// number of teams
		return numberOfTeams;
	}

	public Iterable<String> teams() {
		// all teams
		return dict.keySet();
	}

	public int wins(String team) {
		// number of wins for given team
		
		if (team == null || !dict.containsKey(team))
			throw new IllegalArgumentException();
		
		return wins[dict.get(team)];
	}

	public int losses(String team) {
		// number of losses for given team
		
		if (team == null || !dict.containsKey(team))
			throw new IllegalArgumentException();
		
		return losses[dict.get(team)];
	}

	public int remaining(String team) {
		// number of remaining games for given team
		
		if (team == null || !dict.containsKey(team))
			throw new IllegalArgumentException();
		
		return remaining[dict.get(team)];
	}

	public int against(String team1, String team2) {
		// number of remaining games between team1 and team2
		
		if (team1 == null || !dict.containsKey(team1))
			throw new IllegalArgumentException();
		
		if (team2 == null || !dict.containsKey(team2))
			throw new IllegalArgumentException();
		
		return schedule[dict.get(team1)][dict.get(team2)];
	}

	private BaseballFlowNetwork createFlowNetwork(String team) {

		BaseballFlowNetwork nt = new BaseballFlowNetwork();

		int teamId = this.dict.get(team);

		for (int i = 0; i < this.numberOfTeams; i++) {

			if (teamId != i) {

				BaseballVertex ti = new BaseballVertex(i, i);

				for (int j = i + 1; j < this.numberOfTeams; j++) {

					BaseballVertex tj = new BaseballVertex(j, j);

					if (teamId != j) {

						BaseballVertex game = new BaseballVertex(i, j);
						nt.addEdge(new BaseballEdge(s, game, schedule[i][j]));
						nt.addEdge(new BaseballEdge(game, ti, Double.POSITIVE_INFINITY));
						nt.addEdge(new BaseballEdge(game, tj, Double.POSITIVE_INFINITY));

					}

				}

				nt.addEdge(new BaseballEdge(ti, t, wins[teamId] + remaining[teamId] - wins[i]));

			}

		}

		return nt;
	}

	public boolean isEliminated(String team) {
		
		if (team == null || !dict.containsKey(team))
			throw new IllegalArgumentException();

		// is given team eliminated?
		if (this.trivialEliminated(team))
			return true;

		return this.nontrivialEliminated(team);

	}

	private boolean nontrivialEliminated(String team) {
		
		return this.certificateOfElimination(team) != null;
	}

	private boolean trivialEliminated(String team) {

		int teamId = dict.get(team);

		for (int i = 0; i < this.numberOfTeams; i++) {

			if (i != teamId)
				if (wins[teamId] + remaining[teamId] < wins[i])
					return true;

		}

		return false;

	}

	public Iterable<String> certificateOfElimination(String team) {
		
		if (team == null || !dict.containsKey(team))
			throw new IllegalArgumentException();
		
		if (this.dict.keySet().size() == 1)
			return null;
		
		int teamId = dict.get(team);

		Queue<String> subset = new Queue<>();
		
		if(trivialEliminated(team)) {
			
			for (String s : dict.keySet()) {
				
				if (wins[dict.get(s)] > wins[teamId] + remaining[teamId]) {
					subset.enqueue(s);
					return subset;
				}
				
			}
			
		}

		BaseballFlowNetwork nt = createFlowNetwork(team);
		BaseballFF ff = new BaseballFF(nt, s, t);

		
		for (String s : dict.keySet()) {
			int tm = dict.get(s);
			if (ff.inCut(new BaseballVertex(tm, tm)))
				subset.enqueue(s);
		}

		if (subset.size() == 0)
			return null;
		
		assert validateSubset(subset, teamId);
		
		return subset;
	}

	/**
	 * a(R) = (w(R) + g(R)) / |R|, where w(R) is the total number of wins of teams in R, 
	 * g(R) is the total number of remaining games between teams in R, and |R| is the number of teams in R.
	 * Check that a(R) is greater than the maximum number of games the eliminated team can win.
	 * @param subset
	 * @return
	 */
	private boolean validateSubset(Iterable<String> subset, int teamId) {
		
		double sumSub = 0;
		int mod = 0;
		for (String s : subset) {
			int id = dict.get(s);
			sumSub += (wins[id] + remaining[id]);
			mod++;
		}
		
		sumSub /= mod;
		
		return sumSub > (wins[teamId] + remaining[teamId]);
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination("teams1.txt");
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

}
