package containers.symboltable.applications;

import containers.Bag;
import containers.symboltable.BST;

public class LineSegment implements Comparable<LineSegment>{

	public final Point p;   // one endpoint of this line segment
    public final Point q;  // the other endpoint of this line segment
    
    public LineSegment(Point p, Point q) {
		this.p = p;
		this.q = q;
	}
    
    public boolean isVertical() {
    	
    	return p.x == q.x;
    	
    }
    
    //Check intersection for ortogonal line segments
    public static int intersections(Iterable<LineSegment> lines) {
    	
    	int cont = 0;
    	
    	BST<Integer, Integer> ycoords = new BST<>();
    	BST<Point, Boolean> xcoords = new BST<>();
    	for (LineSegment l : lines) {
    		xcoords.put(l.p, l.isVertical());
    		xcoords.put(l.q, l.isVertical());
    	}
    	
    	if (xcoords.size() < 2)
    		return 0;
    	
    	while(!xcoords.isEmpty()) {
    		
    		Point p = xcoords.min();
    		
    		if (xcoords.get(p)) {
    			
    			xcoords.deleteMin();
    			Point q = xcoords.min();
    			xcoords.deleteMin();
    			
    			cont += ycoords.size(p.y, q.y);
    			
    		} else {
    			
    			xcoords.deleteMin();
    			
    			if (ycoords.contains(p.y))
    				ycoords.delete(p.y);
    			else ycoords.put(p.y, 0);
    			
    		}
    		
    		
    	}
    	
    	return cont;
    }
    
	@Override
	public int compareTo(LineSegment that) {
		return this.p.compareTo(that.p);
	}
    
    public static void main(String[] args) {
		
    	Bag<LineSegment> bag = new Bag<>();
    	bag.add(new LineSegment(new Point(1, 2), new Point(1, 5)));
    	bag.add(new LineSegment(new Point(2, 4), new Point(6, 4)));
    	bag.add(new LineSegment(new Point(0, 3), new Point(4, 3)));
    	bag.add(new LineSegment(new Point(3, 1), new Point(3, 5)));
    	bag.add(new LineSegment(new Point(5, 0), new Point(5, 5)));
    	bag.add(new LineSegment(new Point(2, 2), new Point(6, 2)));
    	assert LineSegment.intersections(bag) == 6;
    	
    }
	
}
