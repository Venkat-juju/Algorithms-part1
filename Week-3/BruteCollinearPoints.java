import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	
	private final LineSegment[] lineSegment;
	
	public BruteCollinearPoints(Point[] point) {	// finds all line segments containing 4 points
		 
		// check for null arguments
		if (isNullPoints(point)) {
			throw new IllegalArgumentException("contructor contains null arguments");
		}
		
		Point[] sortedPoint = point;
		int sortedlen = sortedPoint.length;
		
		Arrays.sort(sortedPoint);
		if (isRepeatedElements(sortedPoint)) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<LineSegment> lineSegments = new ArrayList<>();
		
		for (int i = 0; i < sortedlen-3; i++) {
			for (int j = i+1; j < sortedlen-2; j++){
				for (int k = j+1; k < sortedlen - 1; k++) {
					if (sortedPoint[i].slopeTo(sortedPoint[j]) != sortedPoint[i].slopeTo(sortedPoint[k])) {
						continue;
					}
					
					for (int l = k+1; l < sortedlen; l++) {
						if (sortedPoint[i].slopeTo(sortedPoint[j]) == sortedPoint[i].slopeTo(sortedPoint[l])) {
							lineSegments.add(new LineSegment(sortedPoint[i],sortedPoint[l]));
						}
					}
				}
			}
		}
		
		lineSegment = lineSegments.toArray(new LineSegment[lineSegments.size()]);
	}
	
	public int numberOfSegments() {					// the number of line segments
		
		return lineSegment.length;
	}
	
	public LineSegment[] segments() {				// the line segments

		return Arrays.copyOf(lineSegment, lineSegment.length);
	}
	
	// checks whether the argument are any point in the argument array is null
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
	
	// check for repeated points in the given array points.
	private boolean isRepeatedElements(Point[] point) {
		for(int i = 0; i < point.length-1; i++) {
			if(point[i].compareTo(point[i+1]) == 0) {
				return true;
			}
		}
		
		return false;
	}
}
