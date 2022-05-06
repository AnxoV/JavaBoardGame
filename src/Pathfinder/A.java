package src.Pathfinder;

import src.Logic.*;

/**
 * The {@code A} class provides an example of the A* pathfinder algorithm.
 * 
 * <p>The constructor takes a bidimensional array of characters and thorugh
 * the path method returns the shortest path to the target.
 * 
 * <p>The full-custom constructor defines what characters conform the BLANK
 * and OBSTACLE, providing a glimpse of customization.
 * 
 * @since JDK 11.0
 * @version 1.0
 * 
 * @see src.Pathfinder.Point Point
 */
public class A {

    /**
     * A tile that a player can pass through.
     */
    private char BLANK = ' ';

    /**
     * A tile that a player can't pass through.
     */
    private char OBSTACLE = '#';


    /**
     * The bidimensional array character data.
     */
    private char[][] board;

    /**
     * The avilable points on the board.
     */
    private DynamicArray<Point> openSet;

    /**
     * The visited points on the board.
     */
    private DynamicArray<Point> closedSet;

    
    /**
     * Returns the BLANK character.
     * @return The BLANK character.
     */
    public char getBlank() {
        return BLANK;
    }

    /**
     * Sets the BLANK character.
     * @param BLANK - The character to set
     */
    public void setBlank(char BLANK) {
        this.BLANK = BLANK;
    }

    /**
     * Returns the OBSTACLE character.
     * @return The OBSTACLE character
     */
    public char getObstacle() {
        return OBSTACLE;
    }

    /**
     * Sets the OBSTACLE character.
     * @param OBSTACLE - The character to set
     */
    public void setObstacle(char OBSTACLE) {
        this.OBSTACLE = OBSTACLE;
    }

    /**
     * Returns the board array.
     * @return The board array
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Sets the board array.
     * @param board - The array to set
     */
    public void setBoard(char[][] board) {
        this.board = board;
    }


    /**
     * Constructs a board with the specified array.
     * @param board - The board to construct
     */
    public A(char[][] board) {
        this.board = board;
    }

    /**
     * Constructs a board with the specified array, and the customizable parameters.
     * @param board - The board to construct
     * @param BLANK - A BLANK tile
     * @param OBSTACLE - An OBSTACLE tile
     */
    public A(char[][] board, char BLANK, char OBSTACLE) {
        this.board = board;
        this.BLANK = BLANK;
        this.OBSTACLE = OBSTACLE;
    }


    /**
     * Returns wether a coordinate is walkable or not.
     * @param point - The coordinate to check
     * @return True if the coordinate is walkable, false otherwise
     */
    private boolean isWalkable(Point point) {
        int[] coordinate = point.coordinate;
        if (coordinate[0] < 0 || coordinate[0] > board[0].length-1) return false;
        if (coordinate[1] < 0 || coordinate[1] > board.length-1) return false;
        if (board[coordinate[1]][coordinate[0]] != BLANK) return false;
        return true;
    }


    /**
     * Returns the neighbors of the specified coordinate.
     * @param point - The given coordinate
     * @return An array consisting of the neighbors of the coordinate
     */
    private DynamicArray<Point> findNeighbors(Point point) {
        DynamicArray<Point> neighbors = new DynamicArray<>();
        int[][] directions = {
            {0, -1},
            {0, 1},
            {-1, 0},
            {1, 0}
        };
        for (int[] vector : directions) {
            int[] adjacentCoord = Operator.sum(point.coordinate, vector);
            Point adjacent = new Point(adjacentCoord, point);
            if (isWalkable(adjacent)) {
                neighbors.push(adjacent);
            }
        }
        return neighbors;
    }


    /**
     * Generates the openSet array of the points on the board.
     * @return The openSet array of the board
     */
    private DynamicArray<Point> generateOpenSet() {
        DynamicArray<Point> openSet = new DynamicArray<>();
        for (int i = 0; i < closedSet.size(); ++i) {
            Point point = closedSet.get(i);
            for (Point neighbor : findNeighbors(point)) {
                if (!closedSet.contains(neighbor) && !openSet.contains(neighbor)) {
                    openSet.push(neighbor);
                }
            }
        }
        return openSet;
    }


    /**
     * Generates the array path from the point chain.
     * @return The generated path
     */
    private DynamicArray<Point> generatePath() {
        DynamicArray<Point> path = new DynamicArray<>();
        Point point = closedSet.get(closedSet.size()-1);
        while (point.previous != null) {
            path.push(0, point);
            point = point.previous;
        }
        return path;
    }
    

    /**
     * Returns the shortest path from the start point to the end point.
     * @param start - The start point
     * @param end - The end point
     * @return The shortest path from the start point to the end point
     */
    public DynamicArray<Point> path(Point start, Point end) {
        boolean finished = false;
        closedSet = new DynamicArray<>();
        closedSet.push(start);
        while(!finished) {
            openSet = generateOpenSet();

            for (int i = 0; i < openSet.size() && !finished; i++) {
                Point point = openSet.get(i);
                if (!closedSet.contains(point)) {
                    closedSet.push(point);
                }

                if (end.equals(point)) {
                    finished = true;
                }
            }

            if (!finished && openSet.size() == 0) {
                return null;
            }
        }

        
        return generatePath();
    }

}
