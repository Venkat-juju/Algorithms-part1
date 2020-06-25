import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	
	private final ArrayList<LineSegment> lineSegments = new ArrayList<>();
	
	private final ArrayList<Point> startingPoints = new ArrayList<>();
	private final ArrayList<Point> endingPoints = new ArrayList<>();
	private int numberOfLineSegements = 0;
	
	public FastCollinearPoints(Point[] point) {	// finds all line segments containing 4 points
		 
		if (isNullPoints(point)) {
			throw new IllegalArgumentException();
		}
		
		int len = point.length;
		
		Point[] sortedPoints = Arrays.copyOf(point, len);
		
		Arrays.sort(sortedPoints);
		
		if (isReaptedPoints(sortedPoints, len)) {
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < sortedPoints.length; i++) {
			int count = 0;
			Point[] orderBySlope = Arrays.copyOf(sortedPoints, sortedPoints.length);
			
			// sorting the array with respect to the slope
			Arrays.sort(orderBySlope, sortedPoints[i].slopeOrder());
			
			//minimum and maximum points of the linesegment
			Point minPoint = orderBySlope[0];
			Point maxPoint = orderBySlope[0];
			int currentPosition;
			for (currentPosition = 1; currentPosition < sortedPoints.length - 1; currentPosition++) {
				if (orderBySlope[0].slopeTo(orderBySlope[currentPosition]) == orderBySlope[0].slopeTo(orderBySlope[currentPosition + 1])) {
					if (minPoint.compareTo(orderBySlope[currentPosition])  > 0) {
						minPoint  = orderBySlope[currentPosition];
					} else if (maxPoint.compareTo(orderBySlope[currentPosition+1]) < 0) {
						maxPoint = orderBySlope[currentPosition+1];
					}
					count++;
				} else {
					if (count >= 2) {
						addLineSegment(minPoint, maxPoint);
					}
					minPoint = orderBySlope[0];
					maxPoint = orderBySlope[0];
					count = 0;
				}
			}
			
			// checking for last point
			if (count >= 2)  {
			
				addLineSegment(minPoint, maxPoint);
			}
		}	
	}
	
	private void addLineSegment(Point minPoint, Point maxPoint) {
						
		for (int i = 0; i < numberOfLineSegements; i++) {
			if (startingPoints.get(i).compareTo(minPoint) == 0) {
				if (endingPoints.get(i).compareTo(maxPoint) == 0) {
					return;
				}
			}
		}
		
		LineSegment lineSegment = new LineSegment(minPoint, maxPoint);
		startingPoints.add(minPoint);
		endingPoints.add(maxPoint);
		lineSegments.add(lineSegment);
		numberOfLineSegements++;
	}

	private boolean isReaptedPoints(Point[] point, int length) {
		
		for (int i = 0; i < length -1; i++) {
			if (point[i] == point[i+1]) {
				return true;
			}
		}
		
		return false;
	}

	private boolean isNullPoints(Point[] point) {
		if (point == null) {
			return true;
		}
		
		for (Point points : point) {
			if (points == null) {
				return true;
			}
		}
		
		return false;
	}

	public int numberOfSegments() {					// the number of line segments
		
		return lineSegments.size();
	}
	
	public LineSegment[] segments() {				// the line segments
		
		return lineSegments.toArray(new LineSegment[lineSegments.size()]);
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