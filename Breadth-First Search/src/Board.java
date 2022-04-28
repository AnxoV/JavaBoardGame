import java.util.ArrayList;
import java.util.Random;

/**
 * TO-DO:
 * - Redesign movePlayer function
 * - Redesign moveEnemies function
 * - Make generic move function with parameters Player player and int[] vector
 * - No need for another add function, search for built-in array add function
 * - No need for distance function
 * - Redesign get board function
 */

 /**
  * Represents the Board class used to manage the game
  * 
  * @author Anxo Vilas
  */
public class Board {
    private char[][] board;
    private int width;
    private int height;

    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private char blank = ' ';
    private char border = '#';

    /**
     * Default Board constructor
     * Creates an empty board with dimensions 5 x 5
     */
    public Board() {
        this.width = 5;
        this.height = 5;
        this.board = new char[this.height][this.width];
        this.fillBlank();
    }

    /**
     * Custom Board constructor
     * Creates an empty board with custom dimension Width x Height
     * @param width The board's width
     * @param height The board's height
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[this.height][this.width];
        this.fillBlank();
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }


    /**
     * Fills the board with blank characters
     */
    public void fillBlank() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.board[y][x] = this.blank;
            }
        }
    }

    /**
     * Checks wether a given coordinate is inside the Board's boundaries
     * and is empty
     * @param coordinate The coordinate to check
     * @return True if the coordinate is valid, false otherwise
     */
    public boolean validateCoordinate(int[] coordinate) {
        if (this.board[coordinate[1]][coordinate[0]] != this.blank) {
            return false;
        }
        if (coordinate[0] < 0
            || coordinate[1] < 0
            || coordinate[0] >= this.width
            || coordinate[1] >= this.height) {
            return false;
        }
        return true;
    }


    /**
     * Spawns the player character at (0, 0)
     * @throws InvalidPositionError If the coordinate is not empty our outside the board
     */
    public void spawnPlayer() throws InvalidPositionError {
        if (!this.validateCoordinate(new int[]{0, 0})) {
            throw new InvalidPositionError("Can't spawn the player at that position");
        }
        this.player = new Player();
        this.board[0][0] = this.player.symbol;
    }

    /**
     * Spawns the player character at a given coordinate
     * @param coordinate The location coordinate
     * @throws InvalidPositionError If the coordinate is not empty our outside the board
     */
    public void spawnPlayer(int[] coordinate) throws InvalidPositionError {
        if (!this.validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the player at that position");
        }
        this.player = new Player(coordinate);
        this.board[coordinate[1]][coordinate[0]] = this.player.symbol;
    }


    /**
     * Creates a new Enemy object with the specified position
     * @param coordinate The coordinate to create the enemy at
     * @throws InvalidPositionError If the coordinate is not empty our outside the board
     */
    private void createEnemy(int[] coordinate) {
        Enemy enemy = new Enemy(coordinate);
        this.board[coordinate[1]][coordinate[0]] = enemy.symbol;
        enemies.add(enemy);
    }

    /**
     * Spawn an enemy at a random border position on the board
     * @throws InvalidSpawnPosition If the position the enemy attempts to spawn at is not empty
     */
    public void spawnEnemy() throws InvalidPositionError {
        Random rand = new Random();
        int[] coordinate = new int[]{
            rand.nextInt(this.width),
            rand.nextInt(this.height)
        };
        /** 50% chance to negate the value of an axis and another 50% chance to stick it to any wall */
        int randNum = rand.nextInt(4);
        if (randNum == 0) {
            coordinate[0] = 0;
        } else if (randNum == 1) {
            coordinate[0] = this.width-1;
        } else if (randNum == 2) {
            coordinate[1] = 0;
        } else if (randNum == 3) {
            coordinate[1] = this.height-1;
        }
        /** Checks if the position to spawn the enemy at is empty,
         * if not, throws a custom InvalidSpawnPosition exception
         */
        if (!this.validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the enemy at that position");
        }
        this.createEnemy(coordinate);
    }

    /**
     * Spawns an enemy at a given coordinate on the board
     * @param coordinate The coordinate to spawn the enemy at
     * @throws InvalidSpawnPosition If the position the enemy attempts to spawn at is not empty
     */
    public void spawnEnemy(int[] coordinate) throws InvalidPositionError {
        /** Checks if the position to spawn the enemy at is empty,
         * if not, throws a custom InvalidSpawnPosition exception
         */
        if (!this.validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the enemy at that position");
        }
        this.createEnemy(coordinate);
    }



    /**
     * Moves any player object with the force of a vector
     * @param player The player object to move
     * @param vector The force or movement to apply to the player
     * @return True if the player completed the move, false otherwise
     */
    private boolean move(Player player, int[] vector) {
        boolean moved = true;
        try {
            int[] playerCoords = player.coordinate;
            int[] nextPosition = IntArray.add(playerCoords, vector);
            if (this.validateCoordinate(nextPosition)) {
                this.board[playerCoords[1]][playerCoords[0]] = this.blank;
                player.coordinate = nextPosition;
                this.board[playerCoords[1]][playerCoords[0]] = player.symbol;
            } else {
                moved = false;
            }
        } catch (InvalidArrayError err) {
            moved = false;
        }
        return moved;
    }

    /**
     * Represents the different vectors for each cardinal direction
     */
    enum Direction {
        UP(new int[]{0, -1}),
        DOWN(new int[]{0, 1}),
        LEFT(new int[]{-1, 0}),
        RIGHT(new int[]{1, 0});

        private int[] vector;

        private Direction(int[] vector) {
            this.vector = vector;
        }

        private int[] getVector() {
            return this.vector;
        }
    }

    /**
     * Returns the adjacent coordinate positions relative to the player
     * @param player The player position to get the adjacents from
     * @return The array containing the sequence of adjacent coordinates ordered by UP, DOWN, LEFT, RIGHT
     */
    private int[][] getAdjacentsCoords(Player player) {
        int[] playerCoords = player.coordinate;
        Direction[] directions = Direction.values();
        int[][] adjacentCoords = new int[4][2];

        for (int i = 0; i < directions.length; i++) {
            int[] directionVector = Direction.values()[i].getVector();
            int[] coordinate = new int[]{
                playerCoords[0]+directionVector[0],
                playerCoords[1]+directionVector[1]
            };
            try {
                if (this.board[coordinate[1]][coordinate[0]] != this.blank) {
                    adjacentCoords[i] = coordinate;
                } else {
                    adjacentCoords[i] = new int[]{};
                }
            } catch (IndexOutOfBoundsException err) {
                /** Executes when the adjacent coordinate is out of the board bounds */
            }
        }

        return adjacentCoords;
    }


    private boolean attack(Player player, Player target) {
        int[] playerCoords = player.coordinate;
        int[] targetCoords = target.coordinate;

        pla
    }


    /**
     * Moves the Player with the force of a vector
     * @param vector The force or movement to apply to the Player
     * @throws InvalidPositionError If the player tries to move to an occupied coordinate or outside the board
     */
    public void movePlayer(int[] vector) throws InvalidPositionError {
        if(!this.move(this.player, vector)) {
            throw new InvalidPositionError("Can't move the player to that position");
        }
    }

    /**
     * Iterates through the enemies array moving them towards the Player
     */
    public void moveEnemies() {
        Player target = this.player;
        int[] targetCoords = target.coordinate;
        for (Enemy enemy : enemies) {
            try {
                int[] enemyCoords = enemy.coordinate;
                int[] distanceFromTarget = IntArray.unsign(
                                                IntArray.substract(enemyCoords, targetCoords)
                                            );
                int[] vector = new int[]{0, 0};

                if (distanceFromTarget[0] >= distanceFromTarget[1]) {
                    vector[0] = enemy.movePoints;
                    if (enemyCoords[0] > targetCoords[0]) {
                        vector[0] *= -1;
                    }
                } else if (distanceFromTarget[0] < distanceFromTarget[1]) {
                    vector[1] = enemy.movePoints;
                    if (enemyCoords[1] > targetCoords[1]) {
                        vector[1] *= -1;
                    }
                }
                
                this.move(enemy, vector);

            } catch (InvalidArrayError err) {
                /** This part shouldn't execute */
            }
        }
    }


    /**
     * Returns a multi-line string of the array board
     * @return The stylished board
     */
    public String getBoard() {
        String board = "";

        for (int i = 0; i < this.width+2; i++) {
            board += this.border + " ";
        }
        board += "\n";

        for (char[] row : this.board) {
            board += this.border + " ";
            for (char c : row) {
                board += c + " ";
            }
            board += this.border + "\n";
        }

        for (int i = 0; i < this.width+2; i++) {
            board += this.border + " ";
        }
        board += "\n";

        return board;
    }

}
