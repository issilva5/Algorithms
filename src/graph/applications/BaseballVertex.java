package graph.applications;

public class BaseballVertex {

	public final int t1;
	public final int t2;
	
	public BaseballVertex(int team1, int team2) {
		
		t1 = team1;
		t2 = team2;
		
	}
	
	@Override
	public String toString() {
		
		if (t1 == t2)
			return "Team " + t1;
		
		return "Game (" + t1 + " x " + t2 + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + t1;
		result = prime * result + t2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseballVertex other = (BaseballVertex) obj;
		if (t1 != other.t1)
			return false;
		if (t2 != other.t2)
			return false;
		return true;
	}

}
