import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
   private final int count;
   private final LineSegment[] segments;

   public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
   		if (points == null) {
   			throw new IllegalArgumentException();
   		}
   		Arrays.sort(points);

   		int len = points.length;
   		List<LineSegment> list = new ArrayList<LineSegment>();

   		for (int i = 0; i < len; i++) {
   			Point p = points[i];	
			for (int j = i + 1; j < len; j++) {
   				double pq = p.slopeTo(points[j]);

   				for (int k = j + 1; k < len; k++) {
   					double pr = p.slopeTo(points[k]);
   					if(pq != pr) continue;

   					for (int l = k + 1; l < len; l++) {
   						if(points[i] == null || points[j] == null ||
   							points[k] == null || points[l] == null) {
							throw new java.lang.IllegalArgumentException();
						}


   						double ps = p.slopeTo(points[l]);
   						if(pq != ps || pq != pr) continue;

   						if(pq ==  Double.NEGATIVE_INFINITY 
   							|| pr ==  Double.NEGATIVE_INFINITY 
   							|| ps ==  Double.NEGATIVE_INFINITY) {
   							throw new java.lang.IllegalArgumentException();
   						}
   						
   						
   						if (pq == pr && pq == ps) {
   							LineSegment newLine = new LineSegment(p, points[l]);
	        				boolean found = false;
					        for (LineSegment line : list) {
					          if (line.toString().equals(newLine.toString())) found = true;
					        }
							if (!found) {
								list.add(newLine);
							}
   						}
   					}
   				}
   			}
   			
   		}	
   		int it = 0;
		count = list.size();
   		segments = new LineSegment[count];
   		for (LineSegment l : list) {
   			segments[it++] = l;
   		}	
		
   }

   public int numberOfSegments() {        // the number of line segments
   		return count;
   }

   public LineSegment[] segments() {               // the line segments
   		return segments;
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
    	    segment.draw();
    	}
    	StdDraw.show();
}

}

