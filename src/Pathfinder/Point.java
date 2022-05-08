package src.pathfinder;

import src.logic.*;

/**
 * The {@code Point} class encapsulates an array
 * representing a specified coordinate.
 * 
 * <p>Providing useful information like the previous point object.
 * 
 * @since JDK 11.0
 * @version 1.0
 * 
 * @see src.pathfinder.A A
 */
public class Point {
    public int[] coordinate;
    public Point previous;

    public Point(int[] coordinate, Point previous) {
        this.coordinate = coordinate;
        this.previous = previous;
    }

    @Override
    public boolean equals(Object o) {
        Point point = (Point) o;
        return Operator.equals(coordinate, point.coordinate);
    }

    @Override
    public String toString() {
        return "(" + coordinate[0] + ", " + coordinate[1] + ")";
    }
}