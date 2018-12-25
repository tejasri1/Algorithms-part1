import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;



public class KdTree {

	private Node root;
	private int size;
	private boolean flag;

	// construct an empty set of points
   	public KdTree() {
   		flag = true;
   	}                            

   	// is the set empty?    
   	public boolean isEmpty() {
   		return (size == 0);
   	}          

   	// number of points in the set            
   	public int size() {
   		return size;
   	}                          
   
   	// add the point to the set (if it is not already in the set)
   	public void insert(Point2D p) {
   		if (p == null) throw new java.lang.IllegalArgumentException();
   		if (!contains(p)) {
   			root = insertHelper(flag, p, root, 0, 0, 1, 1);
   			size++;
   		}

   	}

   	private Node insertHelper(boolean flag, Point2D p, Node root, 
   		double xmin, double ymin, double xmax, double ymax) {
   		if (root == null) {
   			return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
   		}
   		
   		if (flag) {
   			 if (p.x() < root.p.x()) {
   			 	//go left
   			 	root.lb = insertHelper(!flag, p, root.lb, xmin, ymin, root.p.x(), ymax);
   			 } else {
   			 	//go right
   			 	root.rt = insertHelper(!flag, p, root.rt, root.p.x(), ymin, xmax, ymax);
   			 }
   		} else {
   			if (p.y() < root.p.y()) {
   				//go left (bottom)
   			 	root.lb = insertHelper(!flag, p, root.lb, xmin, ymin, xmax, root.p.y());
   			 } else {
   			 	//go right (up)
   			 	root.rt = insertHelper(!flag, p, root.rt, xmin, root.p.y(), xmax, ymax);
   			 }
   		}
   		return root;
   	}

   	// does the set contain point p?             
   	public boolean contains(Point2D p) {
   		if (p == null) throw new java.lang.IllegalArgumentException();
   		return searchHelper(flag, p, root);
   	}

   	private boolean searchHelper(boolean flag, Point2D p, Node root) {
   		if (root == null) {
   			return false;
   		}
   		if (p.equals(root.p)) {
   			return true;
   		}
   		boolean out = false;
   		if (flag) {
   			 if (p.x() < root.p.x()) {
   			 	//go left
   			 	out = searchHelper(!flag, p, root.lb);
   			 } else {
   			 	//go right
   			 	out = searchHelper(!flag, p, root.rt);
   			 }
   		} else {
   			if (p.y() < root.p.y()) {
   				//go left
   			 	out = searchHelper(!flag, p, root.lb);
   			 } else {
   			 	//go right
   			 	out = searchHelper(!flag, p, root.rt);
   			 }
   		}
   		return out;
   	}     

   	// // draw all points to standard draw         
   	public void draw() {
   		drawHelper(root, true);
   	} 

   	private void drawHelper(Node root, boolean orient) {
   		if (root == null) return;
   		StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
   		root.p.draw();
   		if (orient) {
   			//vertical red
   			StdDraw.setPenColor(StdDraw.RED);
        	StdDraw.setPenRadius(0.001);
        	StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
   		} else {
   			//horizantal blue
   			StdDraw.setPenColor(StdDraw.BLUE);
        	StdDraw.setPenRadius(0.001);
        	StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p.y());
   		}
   		drawHelper(root.lb, !orient);
   		drawHelper(root.rt, !orient);
   	}

   	// // all points that are inside the rectangle (or on the boundary)                       
   	public Iterable<Point2D> range(RectHV rect) {
   		if (rect == null) throw new java.lang.IllegalArgumentException();
   		List<Point2D> list = new ArrayList<Point2D>();
   		rangeHelper(rect, root, list);
   		return list;
   	}

   	private void rangeHelper(RectHV rect, Node root, List<Point2D> list) {
   		if (root == null) return;
   		if (rect.intersects(root.rect)) {
   			if (rect.contains(root.p)) list.add(root.p);
   			rangeHelper(rect, root.lb, list);
   			rangeHelper(rect, root.rt, list);
   		}
   	}

   	// // a nearest neighbor in the set to point p; null if the set is empty
   	public Point2D nearest(Point2D p) {
   		if (p == null) throw new java.lang.IllegalArgumentException();
   		if (root == null) return null;
   		return nearestHelper(root, p, root.p);
   	}              

   	private Point2D nearestHelper(Node node, Point2D query, Point2D result) {
   		
   		if (node == null) return result;   		
   		double dist = query.distanceSquaredTo(result);

   		if (dist < node.rect.distanceSquaredTo(query)) return result;

   		double newDist = query.distanceSquaredTo(node.p);

   		if (dist >= newDist) {
   			result = node.p;
   			dist = newDist;
   		}

   		if (node.lb != null && node.rt != null && (node.lb.rect.distanceSquaredTo(query) 
   			< node.rt.rect.distanceSquaredTo(query))) {
   			result = nearestHelper(node.lb, query, result);
   			result = nearestHelper(node.rt, query, result);
   		} else {
   			result = nearestHelper(node.rt, query, result);
   			result = nearestHelper(node.lb, query, result);
   		}
   			
   		return result;
   		
   	}

   	private static class Node {
   		private Point2D p;      // the point
   		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
   		private Node lb;        // the left/bottom subtree
   		private Node rt;        // the right/top subtree
		private Node(Point2D point, RectHV rect) {
			this.p = point;
			this.rect = rect;
		}

	}

	// unit testing of the methods (optional)
   	public static void main(String[] args) {
   		KdTree kdtree = new KdTree();
   		Point2D p = new Point2D(0.7, 0.2);
   		Point2D q = new Point2D(0.5, 0.4);
   		Point2D r = new Point2D(0.2, 0.3);
   		Point2D s = new Point2D(0.4, 0.7);
   		kdtree.insert(p);
   		kdtree.insert(q);
   		kdtree.insert(r);
   		kdtree.insert(s);
   		kdtree.draw();
   		// StdOut.print("check:" + kdtree.nearest(new Point2D(1,2)));
   	}                   
}