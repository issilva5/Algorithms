import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {

	private TreeSet<Point2D> set; 
	
	public PointSET() {
		
		this.set = new TreeSet<>();
		
	}

	public boolean isEmpty() {
		
		return this.set.isEmpty();
		
	}

	public int size() {
		
		return this.set.size();
		
	}

	public void insert(Point2D p) {
		
		if (p == null)
			throw new IllegalArgumentException();
		
		if (!this.contains(p))
			this.set.add(p);
		
	}

	public boolean contains(Point2D p) {
		
		if (p == null)
			throw new IllegalArgumentException();
		
		return this.set.contains(p);
		
	}

	public void draw() {
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		
		for (Point2D p : this.set)
			p.draw();
		
	}

	public Iterable<Point2D> range(RectHV rect) {
		
		if (rect == null)
			throw new IllegalArgumentException();
		
		Queue<Point2D> q = new Queue<>();
		
		for (Point2D p : this.set)
			if (rect.contains(p))
				q.enqueue(p);
		
		return q;
		
	}

	public Point2D nearest(Point2D p) {
		
		if (p == null)
			throw new IllegalArgumentException();
		
		Point2D near = null;
		Double dist = null;
		
		for (Point2D q : this.set) {
			
			Double pqd = p.distanceSquaredTo(q);
			
			if (dist == null) {
				
				near = q;
				dist = pqd;
				
			} else if (pqd.compareTo(dist) < 0) {
				
				near = q;
				dist = pqd;
				
			}
				
		}
		
		return near;
		
	}

	public static void main(String[] args) {
		
		PointSET ps = new PointSET();
		ps.insert(new Point2D(0.1, 0.25));
		ps.insert(new Point2D(0.31, 0.67));
		ps.insert(new Point2D(0.3456, 0.574));
		RectHV r = new RectHV(0, 0.5, 0.32, 1);
		//ps.draw();
		Iterable<Point2D> inter = ps.range(r);
		
		for (Point2D p : inter)
			StdOut.println(p);
		
		Point2D p = ps.nearest(new Point2D(0, 0));
		StdOut.println(p);
		
	}

}
