package containers.symboltable.applications;

public class Point implements Comparable<Point> {

	public final int x;
	public final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int compareTo(Point that) {
		if (this.x < that.x) return -1;
		if (this.x > that.x) return 1;
		return this.y - that.y;
	}

}
