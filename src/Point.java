import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private class SlpComp implements Comparator<Point> {
        private final Point invoking;

        public SlpComp(Point invoking) {
            this.invoking = invoking;
        }

        @Override
        public int compare(Point o1, Point o2) {
            if (invoking.slopeTo(o1) < invoking.slopeTo(o2)) {
                return -1;
            } else if (invoking.slopeTo(o1) > invoking.slopeTo(o2)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        } else {
            if (this.x < that.x) {
                return -1;
            } else if (this.x > that.x) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) {
            return 0.0;
        }
        return  ((double) that.y - this.y) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlpComp(this);
    }

    private static void mergeSort(Point[] arr) {
        Point[] temp = new Point[arr.length];
        sort(arr, temp, 0, arr.length - 1);
    }

    private static void sort(Point[] arr, Point[] temp, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(arr, temp, lo, mid);
        sort(arr, temp, mid + 1, hi);
        if (arr[mid].compareTo(arr[mid+1]) < 0) {
            return;
        }
        merge(arr, temp, lo, mid, hi);
    }

    private static void merge(Point[] arr, Point[] temp, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            temp[k] = arr[k];
        }

        int k = lo, i = lo, j = mid + 1;
        while (i <= mid && j <= hi) {
            arr[k++] = temp[i].compareTo(temp[j]) <= 0 ? temp[i++] : temp[j++];
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

        mergeSort(points);

        for (Point p : points) {
            System.out.println(p);
        }
    }
}