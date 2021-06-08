import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class FastCollinearPoints {
    private LinkedList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("No points.");
        }

        int n = points.length;
        segments = new LinkedList<>();
        int p = 0, collinearPs = 1;
        Point[] sortArr = new Point[n];
        Arrays.sort(points);
        System.arraycopy(points, 0, sortArr, 0, n);

        for (int i = 0; i < n; i++) {
            Comparator<Point> cmp = points[i].slopeOrder();
            Arrays.sort(sortArr, cmp);
            for (int j = 1; j < n; j++) {
                if (sortArr[j].slopeTo(sortArr[0]) != sortArr[j-1].slopeTo(sortArr[0])) {
                    if (collinearPs > 2) {
                        segments.add(new LineSegment(sortArr[0], sortArr[j - 1]));
                    }
                    collinearPs = 1;
                } else {
                    collinearPs++;
                    if (collinearPs > 2 && j == n - 1) {
                        segments.add(new LineSegment(sortArr[0], sortArr[j]));
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
        }
    }
}