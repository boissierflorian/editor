package org.ulco;

public class MathUtils {

    public static boolean distanceIsLowerThan(Point p1, Point p2, double distance) {
        double a = p1.getX() - p2.getX();
        double b = p1.getY() - p2.getY();

        return Math.sqrt(a * a + b * b) <= distance;
    }
}
