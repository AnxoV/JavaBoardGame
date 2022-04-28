import java.util.ArrayList;

public class BFS {
    private char[][] board;
    private int boardWidth;
    private int boardHeight;

    private int[] position;
    private int[] target;

    private int[][] weights;

    public BFS(char[][] board) {
        this.board = board;
    }

    public char[][] getBoard() {
        return this.board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public int[] getPosition() {
        return this.position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getTarget() {
        return this.target;
    }

    public void setTarget(int[] target) {
        this.target = target;
    }



    /**
     * Sets the values for the variables position and target,
     * based on the characters of the board
     */
    private void initializeValues() {
        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[y].length; x++) {
                char tile = this.board[y][x];
                switch (tile) {
                    case '0':
                        this.position = new int[]{x, y};
                        break;
                    case 'X':
                        this.target = new int[]{x, y};
                        break;
                    default:
                        break;
                }
            }
        }

        this.boardWidth = this.board[0].length;
        this.boardHeight = this.board.length;
    }

    /**
     * Returns the sum between two int arrays
     * @param pointA
     * @param pointB
     * @return The sum between points
     */
    private int[] add(int[] pointA, int[] pointB) {
        return new int[]{(pointA[0]+pointB[0]), (pointA[1]+pointB[1])};
    }

    /**
     * Checks wether a certain coordinate is inside the bounds of the board
     * @param coordinate
     * @throws IndexOutOfBoundsException
     */
    private boolean validateCoordinate(int[] coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];
        if (x < 0 || x >= this.boardWidth || y < 0 || y >= this.boardHeight) {
            return false;
        }
        return true;
    }

    /**
     * Returns an array consisting of the coordinates of the adjacent tiles to the point
     * @param coordinate
     * @return Array of coordinates
     */
    private int[][] getAdjacents(int[] coordinate) {
        ArrayList<int[]> adjacentTiles = new ArrayList<>(4);
        for (Adjacent adjacentTile : Adjacent.values()) {
            int[] tile = this.add(coordinate, adjacentTile.getCoordinate());
            if (this.validateCoordinate(tile)) {
                adjacentTiles.add(tile);
            }
        }

        return adjacentTiles.toArray(int[][]::new);
    }

    /**
     * Represents a vector of the direction of the adjacent tile
     */
    enum Adjacent {
        UP(new int[]{0, -1}),
        DOWN(new int[]{0, 1}),
        LEFT(new int[]{-1, 0}),
        RIGHT(new int[]{1, 0});

        private int[] coordinate;

        private Adjacent(int[] coordinate) {
            this.coordinate = coordinate;
        }

        public int[] getCoordinate() {
            return this.coordinate;
        }
    }

    /**
     * Sets the values for the weights bidimensional array
     */
    /**
     * 1- Set a tile
     * 2- Get all non visited adjacent tiles
     * 3- Asign the weight to the tile
     * 4- Set the first tile of the visited to the tile
     * 5- Repeat
     */
    private void initializeWeights() {
        weights = new int[this.boardHeight][this.boardWidth];
        ArrayList<int[]> visited = new ArrayList<>();
        ArrayList<int[]> discovered = new ArrayList<>();
        int[] currentTile = this.position;
        int depth = 0;
        discovered.add(currentTile);
        boolean done = false;
        while (!done) {
            /** Over the discovered coordinates */
            int[] tile = discovered.get(0);
            /** Iterates over the adjacents coordinates to the discovered */
            int[][] adjacents = this.getAdjacents(tile);
            for (int j = 0; j < adjacents.length && !done; j++) {
                int[] adjacent = adjacents[j];
                /** If it's a valid coordinate and hasn't been visisted yet */
                if (this.validateCoordinate(adjacent) && !visited.contains(adjacent)) {
                    discovered.add(adjacent);
                    /** If the coordinates corresponds to the target */
                    if (adjacent[0] == this.target[0] && adjacent[1] == this.target[1]) {
                        done = true;
                    }
                }

                /** Move the coordinate from discovered to visited */
                discovered.remove(tile);
                visited.add(tile);
                weights[tile[1]][tile[0]] = 1;
            }
        }
    }

    /**
     * Returns the path consisting of the minimum tiles needed to cross to arrive at the target
     * @return Array of coordinates leading to the target
     */
    public int[][] path() {
        this.initializeValues();
        this.initializeWeights();

        for (int[] row : this.weights) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        return null;
    }


}
