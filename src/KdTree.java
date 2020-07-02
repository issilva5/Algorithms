import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

	private static class Node {
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private int cont;
	}

	private Node root;
	
	public KdTree() {
		
		this.root = null;
		
	}
	
	public boolean isEmpty() {

		return this.root == null;

	}

	public int size() {

		return this.size(this.root);

	}

	public void insert(Point2D p) {

		if (p == null)
			throw new IllegalArgumentException();
		
		if (!this.contains(p))
			this.root = this.insert(this.root, p, true, 0, 1);

	}

	private int size(Node x) {
		
		if (x == null)
			return 0;
		
		return x.cont;
		
	}
	
	private Node insert(Node x, Point2D p, boolean xcomp, double lblim, double rtlim) {
		
		if (x == null) {
			Node s = new Node();
			s.p = p;
			s.cont = 1 + this.size(s.lb) + this.size(s.rt);
			s.rect = xcomp ? new RectHV(p.x(), lblim, p.x(), rtlim) : new RectHV(lblim, p.y(), rtlim, p.y());
			return s;
		}
		
		
		if (xcomp) {
			
			int cmp = Double.compare(p.x(), x.p.x());
			
			if (cmp < 0)
				x.lb = this.insert(x.lb, p, !xcomp, 0, x.p.x());
			else
				x.rt = this.insert(x.rt, p, !xcomp, x.p.x(), 1);
			
		} else {
			
			int cmp = Double.compare(p.y(), x.p.y());
			
			if (cmp < 0)
				x.lb = this.insert(x.lb, p, !xcomp, 0, x.p.y());
			else 
				x.rt = this.insert(x.rt, p, !xcomp, x.p.y(), 1);
			
		}
		
		x.cont = 1 + this.size(x.lb) + this.size(x.rt);
		
		return x;
	}

	public boolean contains(Point2D p) {

		if (p == null)
			throw new IllegalArgumentException();

		return this.contains(this.root, p, true);

	}

	private boolean contains(Node x, Point2D p, boolean xcomp) {
		
		if (x == null)
			return false;
		
		if (p.equals(x.p))
			return true;
		
		if (xcomp) {
			
			int cmp = Double.compare(p.x(), x.p.x());
			
			if (cmp < 0)
				return this.contains(x.lb, p, !xcomp);
			
			return this.contains(x.rt, p, !xcomp);
			
		} else {
			
			int cmp = Double.compare(p.y(), x.p.y());
			
			if (cmp < 0)
				return this.contains(x.lb, p, !xcomp);
			
			return this.contains(x.rt, p, !xcomp);
			
		}

	}

	public void draw() {
		
		this.draw(this.root, true);

	}

	private void draw(Node x, boolean xcomp) {
		
		if (x == null)
			return;
		
		StdDraw.setPenRadius();
		
		if (xcomp)
			StdDraw.setPenColor(StdDraw.RED);
		else StdDraw.setPenColor(StdDraw.BLUE);
		
		x.rect.draw();
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		x.p.draw();
		
		this.draw(x.lb, !xcomp);
		this.draw(x.rt, !xcomp);
		
	}

	public Iterable<Point2D> range(RectHV rect) {

		if (rect == null)
			throw new IllegalArgumentException();
		
		Queue<Point2D> q = new Queue<>();
		
		this.range(rect, this.root, q, true);

		return q;

	}

	private void range(RectHV rect, Node x, Queue<Point2D> q, boolean xcomp) {

		if (x == null)
			return;
		
		if (rect.contains(x.p)) {
			
			q.enqueue(x.p);
			this.range(rect, x.lb, q, !xcomp);
			this.range(rect, x.rt, q, !xcomp);
			
		} else if (xcomp) {
			
			int cmp = Double.compare(rect.xmax(), x.p.x());
			if (cmp <= 0) this.range(rect, x.lb, q, !xcomp);
			else {
				this.range(rect, x.rt, q, !xcomp);
				
				cmp = Double.compare(rect.xmin(), x.p.x());
				if (cmp <= 0) this.range(rect, x.lb, q, !xcomp);
				
			}
		
		} else {
			
			int cmp = Double.compare(rect.ymax(), x.p.y());
			if (cmp <= 0) this.range(rect, x.lb, q, !xcomp);
			else {
				this.range(rect, x.rt, q, !xcomp);
				
				cmp = Double.compare(rect.ymin(), x.p.y());
				if (cmp <= 0) this.range(rect, x.lb, q, !xcomp);
			}
			
		}
		
	}

	public Point2D nearest(Point2D p) {

		if (p == null)
			throw new IllegalArgumentException();

		Point2D near = null;
		
		return this.nearest(p, this.root, near, true);

	}

	private Point2D nearest(Point2D p, Node x, Point2D near, boolean xcomp) {
		
		if (near == null)
			near = x.p;
		
		double act = near.distanceSquaredTo(p);
		double td = x.p.distanceSquaredTo(p);
		
		if (td < act) {
			near = x.p;
			act = td;
		}
		
		Double lbd = null;
		if (x.lb != null)
			lbd = x.lb.rect.distanceSquaredTo(p);
		
		Double rtd = null;
		if (x.rt != null)
			rtd = x.rt.rect.distanceSquaredTo(p);
		
		if (lbd != null && rtd != null) {
			
			if (lbd.compareTo(act) < 0 && rtd.compareTo(act) < 0) {
				
				if (xcomp) {
					
					int cmp = Double.compare(p.x(), x.p.x());
					
					if (cmp < 0) {
						near = this.nearest(p, x.lb, near, !xcomp);
						
						act = near.distanceSquaredTo(p);
						if (rtd.compareTo(act) < 0)
							near = this.nearest(p, x.rt, near, !xcomp);
						
					} else {
						
						near = this.nearest(p, x.rt, near, !xcomp);
						
						act = near.distanceSquaredTo(p);
						if (lbd.compareTo(act) < 0)
							near = this.nearest(p, x.lb, near, !xcomp);
					
						
					}
					
				} else {
					
					int cmp = Double.compare(p.y(), x.p.y());
					
					if (cmp < 0) {
						
						near = this.nearest(p, x.lb, near, !xcomp);
						
						act = near.distanceSquaredTo(p);
						if (rtd.compareTo(act) < 0)
							near = this.nearest(p, x.rt, near, !xcomp);
						
					} else {
						
						near = this.nearest(p, x.rt, near, !xcomp);
						
						act = near.distanceSquaredTo(p);
						if (lbd.compareTo(act) < 0)
							near = this.nearest(p, x.lb, near, !xcomp);
						
					}
					
				}
				
			} else if (lbd.compareTo(act) < 0) {
				
				near = this.nearest(p, x.lb, near, !xcomp);
				
			} else if (rtd.compareTo(act) < 0) {
				
				near = this.nearest(p, x.rt, near, !xcomp);
				
			}
			
		} else if (lbd != null && lbd.compareTo(act) < 0)
			near = this.nearest(p, x.lb, near, !xcomp);
		else if (rtd != null && rtd.compareTo(act) < 0)
			near = this.nearest(p, x.rt, near, !xcomp);
		
		return near;
		
	}

	public static void main(String[] args) {

		KdTree kd = new KdTree();
		StdOut.println(kd.size() + " " + kd.isEmpty());
//		kd.insert(new Point2D(0.7, 0.2));
//		StdOut.println(kd.size() + " " + kd.isEmpty());
//		kd.insert(new Point2D(0.5, 0.4));
//		StdOut.println(kd.size() + " " + kd.isEmpty());
//		kd.insert(new Point2D(0.2, 0.3));
//		StdOut.println(kd.size() + " " + kd.isEmpty());
//		kd.insert(new Point2D(0.4, 0.7));
//		StdOut.println(kd.size() + " " + kd.isEmpty());
//		kd.insert(new Point2D(0.9, 0.6));
//		StdOut.println(kd.size() + " " + kd.isEmpty());
//		kd.insert(new Point2D(0.5, 0.5));
//		StdOut.println(kd.size() + " " + kd.isEmpty());
//		RectHV r = new RectHV(0.75, 0.1, 0.8, 0.5);
//		kd.draw();
//		r.draw();
//		Iterable<Point2D> inter = kd.range(r);
		
//		A  0.875 0.28125
		kd.insert(new Point2D(0.875, 0.28125));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      B  0.1875 0.15625
		kd.insert(new Point2D(0.1875, 0.15625));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      C  0.8125 0.875
		kd.insert(new Point2D(0.8125, 0.875));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      D  0.46875 0.375
		kd.insert(new Point2D(0.46875, 0.375));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      E  0.21875 0.0625
		kd.insert(new Point2D(0.21875, 0.0625));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      F  0.40625 0.0
		kd.insert(new Point2D(0.40625, 0.0));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      G  0.96875 0.46875
		kd.insert(new Point2D(0.96875, 0.46875));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      H  0.59375 1.0
		kd.insert(new Point2D(0.59375, 1.0));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      I  0.09375 0.21875
		kd.insert(new Point2D(0.09375, 0.21875));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      J  0.625 0.1875
		kd.insert(new Point2D(0.625, 0.1875));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      K  0.75 0.65625
		kd.insert(new Point2D(0.75, 0.65625));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      L  0.90625 0.40625
		kd.insert(new Point2D(0.90625, 0.40625));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      M  1.0 0.75
		kd.insert(new Point2D(1.0, 0.75));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      N  0.375 0.9375
		kd.insert(new Point2D(0.375, 0.9375));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      O  0.0 0.34375
		kd.insert(new Point2D(0.0, 0.34375));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      P  0.15625 0.96875
		kd.insert(new Point2D(0.15625, 0.96875));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      Q  0.25 0.78125
		kd.insert(new Point2D(0.25, 0.78125));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      R  0.53125 0.6875
		kd.insert(new Point2D(0.53125, 0.6875));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      S  0.9375 0.5625
		kd.insert(new Point2D(0.9375, 0.5625));
		StdOut.println(kd.size() + " " + kd.isEmpty());
//	      T  0.34375 0.5
		kd.insert(new Point2D(0.34375, 0.5));
		StdOut.println(kd.size() + " " + kd.isEmpty());

		
		 double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
	        double x1 = 0.0, y1 = 0.0;      // current location of mouse
	        boolean isDragging = false;     // is the user dragging a rectangle

	        // draw the points
	        StdDraw.clear();
	        StdDraw.setPenColor(StdDraw.BLACK);
	        StdDraw.setPenRadius(0.01);
	        kd.draw();
	        StdDraw.show();

	        // process range search queries
	        StdDraw.enableDoubleBuffering();
	        while (true) {

	            // user starts to drag a rectangle
	            if (StdDraw.isMousePressed() && !isDragging) {
	                x0 = x1 = StdDraw.mouseX();
	                y0 = y1 = StdDraw.mouseY();
	                isDragging = true;
	            }

	            // user is dragging a rectangle
	            else if (StdDraw.isMousePressed() && isDragging) {
	                x1 = StdDraw.mouseX();
	                y1 = StdDraw.mouseY();
	            }

	            // user stops dragging rectangle
	            else if (!StdDraw.isMousePressed() && isDragging) {
	                isDragging = false;
	            }
	            
	            // draw the points
	            StdDraw.clear();
	            StdDraw.setPenColor(StdDraw.BLACK);
	            StdDraw.setPenRadius(0.01);
	            kd.draw();

	            // draw the rectangle
	            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
	                                     Math.max(x0, x1), Math.max(y0, y1));
	            StdDraw.setPenColor(StdDraw.BLACK);
	            StdDraw.setPenRadius();
	            rect.draw();

	            // draw the range search results for kd-tree in blue
	            StdDraw.setPenRadius(0.02);
	            StdDraw.setPenColor(StdDraw.BLUE);
	            for (Point2D p : kd.range(rect)) {
	            	System.out.println(p);
	                p.draw();
	            }
	            System.out.println("boo");
	            StdDraw.show();
	            StdDraw.pause(20);
	        }
		
//		RectHV r = new RectHV(0.125, 0.28125, 0.90625, 0.78125);
//		Iterable<Point2D> inter = kd.range(r);
//		kd.draw();
//		r.draw();
//		
//		StdDraw.setPenColor(StdDraw.GREEN);
//		StdDraw.setPenRadius(0.01);
//		for (Point2D p : inter) {
//			p.draw();
//			StdOut.println(p);
//		}
//
//		Point2D p = kd.nearest(new Point2D(0, 0));
//		StdDraw.line(0, 0, p.x(), p.y());
//		StdOut.println(p);

	}

}
