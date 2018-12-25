import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;


public class PointSET {
   
   private TreeSet<Point2D> set;

   // construct an empty set of points
   public PointSET() {
		set = new TreeSet<Point2D>();
   }                   
   // is the set empty?             
   public boolean isEmpty() {
		return set.isEmpty();
   }                    
   // number of points in the set 
   public int size() {
		return set.size();
   }                       
     
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
      if (p == null) throw new java.lang.IllegalArgumentException();
      if (!contains(p)) set.add(p);
   }              
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
      if (p == null) throw new java.lang.IllegalArgumentException();
   	return set.contains(p);
   }            

   // draw all points to standard draw
   public void draw() {
		for (Point2D p: set) {
			p.draw();
		}
   }        

   // all points that are inside the rectangle (or on the boundary)
   public Iterable<Point2D> range(RectHV rect) {
      if (rect == null) throw new java.lang.IllegalArgumentException();
		
      List<Point2D> list = new ArrayList<Point2D>();
		for (Point2D p: set) {
			if (rect.contains(p)) list.add(p);
		}
		return list;
   }              

   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
      if (p == null) throw new java.lang.IllegalArgumentException();
		if (set.isEmpty()) return null; 
		
		Point2D out = set.first();
		double minDist = p.distanceSquaredTo(out);
		for (Point2D point : set) {
			double dist = p.distanceSquaredTo(point);
			if (minDist > dist) {
				minDist = dist;
				out = point;
			}
		}
		return out;

   }              
   // unit testing of the methods (optional)
   public static void main(String[] args) {

   }                   
}