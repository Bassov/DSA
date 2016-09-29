package algorithms.polygon;

import collection.list.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

public class Polygon {

    // Polygon class that uses Monte Carlo integration method for counting area

    private static final double INTEGRATION_ACCURACY = 1E-4;
    // provides correct accuracy for tests
    private static final int POINTS_PER_ITERATION = 1000000;
    private static final double EPSILON = 1E-6;

    private ArrayList<Point> polygon = new ArrayList<>();

    private double maxX;
    private double minX;

    private double maxY;
    private double minY;

    private int vertexNum;

    public Polygon() {
    }

    public Polygon(String points) {
        Point[] polygon = parseCoordinates(points);

        for (Point p : polygon) {
            addPoint(p);
        }
    }

    public void addPoint(Point point) {
        double x = point.getX();
        double y = point.getY();

        if (vertexNum == 0) {
            maxX = x;
            minX = x;
            maxY = y;
            minY = y;
        }

        if (x > maxX) maxX = x;
        if (x < minX) minX = x;

        if (y > maxY) maxY = y;
        if (y < minY) minY = y;

        polygon.add(point);
        vertexNum++;
    }

    public double countArea() {
        double thrown = 0;
        double hits = 0;

        double rectangleSide = maxX - minX;
        double rectangleArea = rectangleSide * rectangleSide;

        Double result = 0.0;
        Double diff;

        do {
            for (int i = 0; i <= POINTS_PER_ITERATION; i++) {
                double x = ThreadLocalRandom.current().nextDouble(minX, maxX);
                double y = ThreadLocalRandom.current().nextDouble(minY, minY + rectangleSide);

                if (isInside(new Point(x, y))) {
                    hits++;
                }
                thrown++;
            }
            diff = Math.abs(result - rectangleArea * hits / thrown);
            result = rectangleArea * hits / thrown;
        } while (diff > INTEGRATION_ACCURACY);

        return result;
    }

    private boolean isInside(Point point) {
        Point secondP = new Point(maxX * 1.1, maxY * 1.1);
        // Count of intersects
        int count = 0;

        for (int i = 0; i < polygon.size(); i++) {
            Point start = polygon.get(i);
            // to get side (last point - first point)
            Point end = polygon.get((i + 1) % polygon.size());

            if (intersects(start, end, point, secondP)) {
                count++;
            }
        }

        return !(count % 2 == 0);
    }

    private boolean intersects(Point a, Point b, Point c, Point d) {
        if (isOnSegment(a, b, c)) {
            return false;
        }
        // We describe the section AB as A+(B-A)*u and CD as C+(D-C)*v
        // then we solve A + (B-A)*u = C + (D-C)*v
        // let's use Kramer's rule to solve the task (Ax = B) were x = (u, v)^T
        // build a matrix for the equation
        double[][] A = new double[2][2];
        A[0][0] = b.getX() - a.getX();
        A[1][0] = b.getY() - a.getY();
        A[0][1] = c.getX() - d.getX();
        A[1][1] = c.getY() - d.getY();
        // calculate determinant
        double det0 = A[0][0] * A[1][1] - A[1][0] * A[0][1];
        // substitute columns and calculate determinants
        double detU = (c.getX() - a.getX()) * A[1][1] - (c.getY() - a.getY()) * A[0][1];
        double detV = A[0][0] * (c.getY() - a.getY()) - A[1][0] * (c.getX() - a.getX());
        // calculate the solution
        // even if det0 == 0 (they are parallel) this will return NaN and comparison will fail -> false
        double u = detU / det0;
        double v = detV / det0;
        return u > 0 && u < 1 && v > 0 && v < 1;
    }

    private boolean isOnSegment(Point start, Point end, Point point) {
        // start to end vector
        Point s_e = new Point(end.getX() - start.getX(), end.getY() - start.getY());
        // start to point vector
        Point s_p = new Point(point.getX() - start.getX(), point.getY() - start.getY());

        double cross1 = s_e.getX() * s_p.getY() - s_p.getX() * s_e.getY();

        // check if point belongs to line
        if (cross1 > EPSILON || cross1 < 0) {
            return false;
        }

        // point to start vector
        Point p_s = new Point(start.getX() - point.getX(), start.getY() - point.getY());
        // point to end vector
        Point p_e = new Point(end.getX() - end.getX(), end.getY() - end.getY());
        double cross2 = p_s.getX() * p_e.getY() - p_e.getX() * p_s.getY();
        return cross2 <= 0;
    }

    private Point[] parseCoordinates(String input) {
        String[] coordinates = input.split(" ");
        Point[] points = new Point[coordinates.length / 2];

        for (int i = 0; i < coordinates.length; i += 2) {
            double x = Double.parseDouble(coordinates[i]);
            double y = Double.parseDouble(coordinates[i + 1]);
            points[i / 2] = new Point(x, y);
        }

        return points;
    }

    class Point {

        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

    }

}
