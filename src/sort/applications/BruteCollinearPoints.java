package sort.applications;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

	private final List<LineSegment> segments;
	
	public BruteCollinearPoints(Point[] points) {
		
		if (points == null)
			throw new IllegalArgumentException();
		
		int SIZE = points.length;
		
		for (int i = 0; i < SIZE; i++) {
			
			if (points[i] == null)
				throw new IllegalArgumentException();
			
			for (int j = i+1; j < SIZE; j++)
				if (points[j] == null || points[i].compareTo(points[j]) == 0)
					throw new IllegalArgumentException();
				
		}
		
		Point[] copy = Arrays.copyOf(points, SIZE);
		Arrays.sort(copy);
		this.segments = new ArrayList<>();
		
		// check whether the three slopes between p and q, between p and r, and between p and s are all equal
		
		for (int i = 0; i < SIZE; i++) {
			
			for (int j = i+1; j < SIZE; j++) {
				
				for (int k = j+1; k < SIZE; k++) {
					
					for (int m = k+1; m < SIZE; m++) {
						
						Point p = copy[i];
						Point q = copy[j];
						Point r = copy[k];
						Point s = copy[m];
						
						double slopePQ = p.slopeTo(q);
						double slopePR = p.slopeTo(r);
						double slopePS = p.slopeTo(s);
						
						if (slopePQ == slopePR && slopePQ == slopePS) {
							
							this.segments.add(new LineSegment(p, s));
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public int numberOfSegments() {
		return this.segments.size();
	}
	
	public LineSegment[] segments() {
		Object[] aux =  this.segments.toArray();
		LineSegment[] segs = new LineSegment[this.numberOfSegments()];
		for (int i = 0; i < segs.length; i++)
			segs[i] = (LineSegment) aux[i];
		
		return segs;
		   
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
	
}
