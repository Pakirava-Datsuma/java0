package home_3_expressions;

/**
 * Created by swanta on 11.06.16.
 */
public class TriangleChecker {
    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;

        }

        @Override
        public String toString() {
            return "Point{" + x + ", " + y + '}';
        }
    }
    static class Line {
        int k, b;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Line line = (Line) o;

            if (k != line.k) return false;
            return b == line.b;

        }


        public Line(int k, int b) {

            this.k = k;
            this.b = b;
        }

        public Line (Point a, Point b) {
            this.k = (a.x - b.x) / (a.y - b.y);
            this.b = a.y - this.k * a.x;
        }

    }

    static boolean CheckTriangle (Point a, Point b, Point c) {
        //check if two lines has the same equations
        // y = k*x + b
        return new Line(a,b).equals(new Line(a,c));
    }

    public static void main(String[] args) {

        Point a = new Point(1,1);
        Point b = new Point (2,2);
        Point c = new Point (5,5);
        Point d = new Point (1,3);

        System.out.println(a+"\n"+b+"\n"+c+"\nis triangle:"+CheckTriangle(a,b,c));
        System.out.println(a+"\n"+d+"\n"+c+"\nis triangle:"+CheckTriangle(a,d,c));

    }


}
