package sort.applications;

import java.util.Comparator;

public class Point2D {

	private final double x;
	private final double y;
	public final static Comparator<Point2D> Y_ORDER = new ByYOrder();
	public final Comparator<Point2D> POLAR_ORDER = new ByAngle();
	
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	private static class ByYOrder implements Comparator<Point2D> {

		@Override
		public int compare(Point2D p1, Point2D p2) {
			if (p1.y == p2.y)
				return Double.compare(p1.x, p2.x);
			return Double.compare(p1.y, p2.y);
		}
		
	}
	
	private class ByAngle implements Comparator<Point2D> {
		
		Point2D p0 = Point2D.this;
		
		@Override
		public int compare(Point2D p1, Point2D p2) {
			
			int orientation = Point2D.ccw(p0, p1, p2);
			
			if (orientation == 0)
				return Point2D.distSq(p0, p2) >= Point2D.distSq(p0, p1) ? -1 : 1;
			
			return orientation;
		}
		
	}
	
	public static double distSq(Point2D p0, Point2D p1) {
		
		return (p0.x - p1.x) * (p0.x - p1.x) + (p0.y - p1.y) * (p0.y - p1.y);
	}

	public static int ccw(Point2D a, Point2D b, Point2D c) {
		
		double area2 =  (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
		if (area2 < 0) return 1; // clockwise
		else if (area2 > 0) return -1; // counter-clockwise
		else return 0; // collinear
		
	}

}
