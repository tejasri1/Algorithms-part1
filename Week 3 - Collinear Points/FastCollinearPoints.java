import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class FastCollinearPoints {

	private int count;
	private LineSegment[] segments;


   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
   		int len = points.length;
   		int index = 0;
   		List<LineSegment> list = new ArrayList<LineSegment>();

   		while (index < len) {
   			Point[] copy = Arrays.copyOf(points, points.length);
   			Arrays.sort(copy, points[index].slopeOrder());

   			Integer low = 1;
   			Integer high = 1;

   			while (high < len) {

   				if (copy[0].slopeTo(copy[low]) == copy[0].slopeTo(copy[high])) {
   					high++;
   				
   				} else {
   					if (high - low >= 3) {	
   						addLine(low, high, points[index], copy, list);
   					}
   					low = high;
   				}
   			}

   			if (high - low >= 3) {
   				addLine(low, high, points[index], copy, list);
   			}	

   			index++;
   		}

   		int i = 0;
   		count = list.size();
   		segments = new LineSegment[count];
   		for (LineSegment l : list) {
   			segments[i++] = l;
   		}
   }     
   
   private void addLine(int low, int high, Point origin, Point[] copy, List<LineSegment> list) {
   		int newLen = high - low + 1;
		Point[] copy2 = new Point[newLen];
		copy2[0] = origin;
		System.arraycopy(copy, low, copy2, 1, high - low); 
		Arrays.sort(copy2);

		LineSegment l = new LineSegment(copy2[0], copy2[newLen - 1]);
        boolean found = false;
        for (LineSegment line : list) {
          if (line.toString().equals(l.toString())) found = true;
        }
		if (!found) {
			list.add(l);
		}
   }

   // the number of line segments
   public int numberOfSegments() {
   		return count;
   } 
   

    // the line segments
   public LineSegment[] segments() {
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
    	FastCollinearPoints collinear = new FastCollinearPoints(points);
    	for (LineSegment segment : collinear.segments()) {
    		StdOut.println(segment);
    	    segment.draw();
    	}
    	StdDraw.show();
	}
}