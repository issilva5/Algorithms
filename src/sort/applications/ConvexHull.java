package sort.applications;

import containers.stack.Stack;
import containers.stack.StackArray;
import sort.Sort;

public class ConvexHull {
	
	public static Stack<Point2D> getConvexHull(Point2D[] points, Sort sort) {
		
		Stack<Point2D> hull = new StackArray<>();
		sort.sort(points, Point2D.Y_ORDER);
		sort.sort(points, points[0].POLAR_ORDER);
		
		hull.push(points[0]);
		hull.push(points[1]);
		
		for (int i = 2; i < points.length; i++) {
			
			Point2D top = hull.pop();
			while (Point2D.ccw(hull.peek(), top, points[i]) >= 0) {
				top = hull.pop();
			}
			
			hull.push(top);
			hull.push(points[i]);
			
		}
		
		return hull;
		
	}
	
}
