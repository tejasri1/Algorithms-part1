import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class RectHV {

   private final double xmin;
   private final double ymin;
   private final double xmax;
   private final double ymax;

   // construct the rectangle [xmin, xmax] x [ymin, ymax]
   // throw a java.lang.IllegalArgumentException if (xmin > xmax) or (ymin > ymax)
   public RectHV(double xmin, double ymin,       
                    double xmax, double ymax) {
      if (xmin > xmax || ymin > ymax) {
         throw new java.lang.IllegalArgumentException();
      }
      this.xmin = xmin;
      this.ymin = ymin;
      this.xmax = xmax;
      this.ymax = ymax;
   
   }      
   public  double xmin() {
      return xmin;
   }                            
   public  double ymin() {
      return ymin;
   }                            
   public  double xmax() {
      return xmax;
   }                            
   public  double ymax() {
      return ymax;
   }                            

   // does this rectangle contain the point p (either inside or on boundary)?
   public boolean contains(Point2D p) {
      if (p == null) throw new java.lang.IllegalArgumentException();
      return (p.x() >= xmin && 
         p.x() <= xmax && 
         p.y() >= ymin && 
         p.y() <= ymax);
   }               

   // does this rectangle intersect that rectangle (at one or more points)?
   public boolean intersects(RectHV that) {
      if (that == null) throw new java.lang.IllegalArgumentException();
      return (intervalOverlap(this.xmin(), this.xmax(), that.xmin(), that.xmax()) &&
         intervalOverlap(this.ymin(), this.ymax(), that.ymin(), that.ymax()));

   }

   private boolean intervalOverlap(double s1, double e1, double s2, double e2) {
      return (s1 >= s2 && s1 < e2) || (s1 <= s2 && s2 < e1);
   }

   private double minDistFromSides(double p, double min, double max) {
      return Math.min(Math.abs(p - min), Math.abs(p - max));
   }

   // Euclidean distance from point p to closest point in rectangle           
   public double distanceTo(Point2D p) {
      if (p == null) throw new java.lang.IllegalArgumentException();

      double dist = 0.0;
      if (this.contains(p)) {
         dist =  Math.min(minDistFromSides(p.x(), xmin, xmax), 
            minDistFromSides(p.y(), ymin, ymax));
      
      } else if (p.x() >= xmin && p.x() <= xmax) {
         dist = minDistFromSides(p.x(), xmin, xmax);

      } else if (p.y() >= ymin && p.y() <= ymax) {
         dist = minDistFromSides(p.y(), ymin, ymax);
      
      } else {
         double c1 = distanceSquaredFromCorner(p, xmin, ymin);
         double c2 = distanceSquaredFromCorner(p, xmin, ymax);
         double c3 = distanceSquaredFromCorner(p, xmax, ymin);
         double c4 = distanceSquaredFromCorner(p, xmax, ymax);
         dist = Math.min(Math.min(c1, c2), Math.min(c3, c4));
         dist = Math.sqrt(dist);
      }
      return dist;
   }

   private double distanceSquaredFromCorner(Point2D p, double x, double y) {
      Point2D q = new Point2D(x, y);
      return p.distanceSquaredTo(q);
   }

   // square of Euclidean distance from point p to closest point in rectangle             
   public double distanceSquaredTo(Point2D p) {
      if (p == null) throw new java.lang.IllegalArgumentException();
      
      double d = distanceTo(p);
      return d * d;
   }      
   
   // does this rectangle equal that object? 
   public boolean equals(Object that) {
      if (this == that) return true;
      if (that == null) return false;
      if (this.getClass() != that.getClass()) return false;
      RectHV rect = (RectHV) that;
      if (rect.xmin() != xmin || rect.ymin() != ymin 
         || rect.xmax != xmax || rect.ymax != ymax) return false;
      return true;
   }

   // draw to standard draw              
   public void draw() {
      double xmid = (xmin + xmax) / 2;
      double ymid = (ymin + ymax) / 2;
      double halfWidth = (ymax - ymin) / 2;
      double halfHeight = (xmax - xmin) / 2;
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      StdDraw.rectangle(xmid, ymid, halfWidth, halfHeight);   
   }                           

   // // string representation 
   public String toString() {
      String out = new Point2D(xmin, ymax).toString() + " --> " + 
         new Point2D(xmax, ymax).toString() + new Point2D(xmin, ymin).toString() + " --> " + 
         new Point2D(xmax, ymin).toString();
         return out;
   }

   public static void main(String[] args) {
      RectHV one = new RectHV(0, 0, 1, 1);
      RectHV two = new RectHV(0.3, 0.3, 0.7, 0.7);
      Point2D p = new Point2D(5, 5);
      StdOut.print(two.intersects(one));
      one.draw();
   }                        
}



