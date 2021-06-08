import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {

    private LinkedList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("No points.");
        }

        int n = points.length;
        segments = new LinkedList<>();

        Arrays.sort(points);

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (points[i] == null || points[j] == null
                         || points[k] == null || points[l] == null) {
                            throw new IllegalArgumentException("Null point.");
                        } else if (points[i].compareTo(points[j]) == 0
                                || points[j].compareTo(points[k]) == 0
                                || points[k].compareTo(points[l]) == 0
                                || points[i].compareTo(points[k]) == 0
                                || points[i].compareTo(points[l]) == 0
                                || points[j].compareTo(points[l]) == 0) {
                            throw new IllegalArgumentException("Repeated point.");
                        }

                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                        &&  points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            segments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[1]);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
        }
    }
}