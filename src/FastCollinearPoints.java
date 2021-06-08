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
            mergeSort(sortArr, cmp);
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

    private static void mergeSort(Point[] arr, Comparator<Point> cmp) {
        Point[] temp = new Point[arr.length];
        sort(arr, temp, cmp, 0, arr.length - 1);
    }

    private static void sort(Point[] arr, Point[] temp, Comparator<Point> cmp, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(arr, temp, cmp, lo, mid);
        sort(arr, temp, cmp, mid + 1, hi);
        if (cmp.compare(arr[mid], arr[mid+1]) < 0) {
            return;
        }
        merge(arr, temp, cmp, lo, mid, hi);
    }

    private static void merge(Point[] arr, Point[] temp, Comparator<Point> cmp, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            temp[k] = arr[k];
        }

        int k = lo, i = lo, j = mid + 1;
        while (i <= mid && j <= hi) {
            arr[k++] = cmp.compare(temp[i], temp[j]) <= 0 ? temp[i++] : temp[j++];
        }

        if (i > mid) {
            while (j <= hi) {
                arr[k++] = temp[j++];
            }
        } else {
            while (i <= mid) {
                arr[k++] = temp[i++];
            }
        }
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

//        Arrays.sort(points);
//        Arrays.sort(points, points[0].slopeOrder());
//
//        for (Point p : points) {
//            System.out.println(p);
//        }

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
        }
    }
}