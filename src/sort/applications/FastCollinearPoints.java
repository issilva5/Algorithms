package sort.applications;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private final List<LineSegment> segments;
	
	public FastCollinearPoints(Point[] points) {
		
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
		
		this.segments = new ArrayList<>();
		
		Point[] save = Arrays.copyOf(points, SIZE);
		Arrays.sort(save);
		
		for (int i = 0; i < SIZE; i++) {
			
			Point[] copy = Arrays.copyOf(save, SIZE);
			
			Point p = copy[i];
			
			Arrays.sort(copy, i+1, SIZE, p.slopeOrder());
			
			double slope = Double.NEGATIVE_INFINITY;
			int segsz = 0;
			
			for (int j = i+1; j < SIZE; j++) {
				
				double slopePQ = p.slopeTo(copy[j]);
				
				if (slopePQ == slope) segsz++;
				else {
					
					if (segsz >= 3)
						this.segments.add(new LineSegment(p, copy[j-1]));
					
					slope = slopePQ;
					segsz = 1;
					
				}
				
			}
			
			if (segsz >= 3)
				this.segments.add(new LineSegment(p, copy[SIZE-1]));
			
			
		}
			
		
	}
	
	public int numberOfSegments()    {
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);

	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
	
}
