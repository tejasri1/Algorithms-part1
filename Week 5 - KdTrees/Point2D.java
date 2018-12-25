import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point2D implements Comparable<Point2D> {

   private final double x;
   private final double y;

   // construct the point (x, y)
   public Point2D(double x, double y) {
   		this.x = x;
   		this.y = y;
   }              

   // x-coordinate 
   public double x() {
   		return x;
   }               

   // y-coordinate 
   public double y() {
   		return y;
   }

   // Euclidean distance between two points                               
   public double distanceTo(Point2D that) {
      if (that == null) throw new java.lang.IllegalArgumentException();
   	return Math.sqrt(distanceSquaredTo(that));
   }

   // square of Euclidean distance between two points         
   public double distanceSquaredTo(Point2D that) {
      if (that == null) throw new java.lang.IllegalArgumentException();
   	double dx = that.x() - this.x();
   	double dy = that.y() - this.y();
   	return ((dx*dx) + (dy*dy));
   }

   // for use in an ordered symbol table   
   public int compareTo(Point2D that) {
      if (that == null) throw new java.lang.IllegalArgumentException();
		if (this.y == that.y && this.x == that.x) {
         return 0;

      } else if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
         return -1;

      } else {
         return 1;
      }
   }         

   // does this point equal that object? 
   public boolean equals(Object that) {
   		if (that == this) return true;
   		if(that == null) return false;
   		if (that.getClass() != this.getClass()) return false;
   		Point2D p = (Point2D) that;
   		if (p.x() != this.x() || p.y() != this.y()) return false;
   		return true;
   }

   // draw to standard draw               
   public void draw() {
   		StdDraw.point(this.x, this.y);
   }                          

   // string representation
   public  String toString() {
   		return "(" + x + ", " + y + ")";
   }      

   public static void main(String[] args) {
   		Point2D p = new Point2D(0.5, 0.5);
   		p.draw();
   }                  
}