package src.Game;

import java.util.ArrayList;
import java.util.Random;

import src.Logic.Array;
import src.Exceptions.InvalidPositionError;

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
  * Represents the Board Class used to manage the game
  * 
  * @version 1.0
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
    private char death = '-';

    private boolean run = true;

    /**
     * Basic constructor.
     * <p>
     * Creates a 5 x 5 empty {@code Board} object.
     */
    public Board() {
        width = 5;
        height = 5;
        board = new char[height][width];
        fillBlank();
    }

    /**
     * Custom Board constructor.
     * <p>
     * Creates a {@code width} x {@code height} empty {@code Board} object
     * @param width - The width of the board
     * @param height - The height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new char[height][width];
        fillBlank();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public char getBlank() {
        return blank;
    }

    public char getBorder() {
        return border;
    }

    public char getDeath() {
        return death;
    }

    public boolean getRun() {
        return run;
    }


    /**
     * Fills the {@code Board} with the {@code blank} character value.
     */
    public void fillBlank() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = blank;
            }
        }
    }

    /**
     * Checks wether a given coordinate is empty and inside the {@code Board} boundaries.
     * @param coordinate - The {@code int[]} coordinate to check
     * @return A boolean representing if the given coordinate is valid
     */
    public boolean validateCoordinate(int[] coordinate) {
        boolean valid = true;
        try {
            if (board[coordinate[1]][coordinate[0]] != blank) {
                valid = false;
            }
        } catch (IndexOutOfBoundsException err) {
            valid = false;
        }
        return valid;
    }

    /**
     * Spawns the {@code Player} character at the {@code int[]} {0, 0} coordinate .
     * @throws InvalidPositionError If the coordinate to spawn the {@code Player} at, is not valid
     */
    public void spawnPlayer() throws InvalidPositionError {
        if (!validateCoordinate(new int[]{0, 0})) {
            throw new InvalidPositionError("Can't spawn the player at that position. The position is not empty or is outside the boards boundaries");
        }
        player = new Player();
        board[0][0] = player.symbol;
    }

    /**
     * Spawns the {@code Player} character at a given {@code int[]} {x, y} coordinate.
     * @param coordinate - The {@code int[]} coordinate to spawn the {@code Player}
     * @throws InvalidPositionError If the coordinate to spawn the {@code Player} at, is not valid
     */
    public void spawnPlayer(int[] coordinate) throws InvalidPositionError {
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the player at that position. The position is not empty or is outside the boards boundaries");
        }
        player = new Player(coordinate);
        board[coordinate[1]][coordinate[0]] = player.symbol;
    }

    /**
     * Generates a random {@code int[]} {x, y} coordinate at the side of a rectangle.
     * @return An {@code int[]} {x, y} coordinate
     */
    private int[] randomBorderCoordinate() {
        Random rand = new Random();
        int[] coordinate = new int[]{
            rand.nextInt(width),
            rand.nextInt(height)
        };
        int randNum = rand.nextInt(4);
        if (randNum == 0) {
            coordinate[0] = 0;
        } else if (randNum == 1) {
            coordinate[0] = width-1;
        } else if (randNum == 2) {
            coordinate[1] = 0;
        } else if (randNum == 3) {
            coordinate[1] = height-1;
        }
        return coordinate;
    }

    /**
     * Spawns an {@code Enemy} at a random border coordinate.
     * @throws InvalidPositionError If the coordinate the {@code Enemy} attempts to spawn at, is not valid
     */
    public void spawnEnemy() throws InvalidPositionError {
        int[] coordinate = randomBorderCoordinate();
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the enemy at that position. The position is not empty or is outside the boards boundaries");
        }
        Enemy enemy = new Enemy(coordinate);
        board[coordinate[1]][coordinate[0]] = enemy.symbol;
        enemies.add(enemy);
    }

    /**
     * Spawns an {@code Enemy} at a given {@code int[]} {x, y} coordinate. 
     * @param coordinate - The {@code int[]</code} {x, y} coordinate to spawn the {@code Enemy} at
     * @throws InvalidSpawnPosition If the coordinate to spawn the {@code Enemy} at, is not valid
     */
    public void spawnEnemy(int[] coordinate) throws InvalidPositionError {
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the enemy at that position. The position is not empty or is outside the boards boundaries");
        }
        Enemy enemy = new Enemy(coordinate);
        board[coordinate[1]][coordinate[0]] = enemy.symbol;
        enemies.add(enemy);
    }

    /**
     * Moves any {@code Player} object with the force of an {@code int[]} {x, y} vector
     * @param player - A {@code Player} object to move
     * @param vector - An {@code int[]} {x, y} vector representing the force
     * @return Wether the move executed correctly
     */
    private boolean move(Player player, int[] vector) {
        boolean moved = true;
        int[] playerCoords = player.coordinate;
        int[] nextPosition = Array.sum(playerCoords, vector);
        if (validateCoordinate(nextPosition)) {
            board[playerCoords[1]][playerCoords[0]] = blank;
            player.coordinate = nextPosition;
            board[nextPosition[1]][nextPosition[0]] = player.symbol;
        } else {
            moved = false;
        }
        return moved;
    }

    /**
     * Represents the different {@code int[]} {x, y} vectors for each cardinal direction
     */
    public enum Direction {
        UP(new int[]{0, -1}),
        DOWN(new int[]{0, 1}),
        LEFT(new int[]{-1, 0}),
        RIGHT(new int[]{1, 0});

        private int[] vector;

        /**
         * Basic constructor.
         * @param vector - An {@code int[]} {x, y} vector
         */
        private Direction(int[] vector) {
            this.vector = vector;
        }

        /**
         * Gets the {@code int[]} {x, y} vector of the direction
         * @return An {@code int[]} {x, y} vector
         */
        public int[] getVector() {
            return vector;
        }
    }

    /**
     * Gets all the {@code int[]} {x, y} coordinate positions relative to a {@code Player}
     * @param player The {@code Player} position
     * @return The array containing the sequence of adjacent coordinates ordered by UP, DOWN, LEFT, RIGHT
     */
    public int[][] getAdjacentCoords(Player player) {
        int[] playerCoords = player.coordinate;
        int i = 0;
        int[][] adjacentCoords = new int[4][2];
        for (Direction direction : Direction.values()) {
            int[] vector = direction.getVector();
            int[] coordinate = new int[]{
                playerCoords[0]+vector[0],
                playerCoords[1]+vector[1]
            };
            adjacentCoords[i] = coordinate;
            i++;
        }

        return adjacentCoords;
    }

    /**
     * Attacks a {@code Player} if it is within reach.
     * @param player - The {@code Player} who attacks
     * @param target - The {@code Player} to be attacked
     * @return Wether or not the attack took place
     */
    private boolean attack(Player player, Player target) {
        boolean attacked = false;
        if (Array.contains(target.coordinate, getAdjacentCoords(player))) {
            target.hp -= player.damage;

            if (isDead(target)) {
                if (Enemy.class.isAssignableFrom(target.getClass())) {
                    enemies.remove(target);
                    board[target.coordinate[1]][target.coordinate[0]] = blank;
                } else {
                    board[target.coordinate[1]][target.coordinate[0]] = death;
                    run = false;
                }
            }
            attacked = true;
        }
        return attacked;
    }

    /**
     * Checks if a {@code Player} hp is below or equal to 0.
     * @param player - A {@code Player} object
     * @return A boolean representing the lifeness of a {@code Player}
     */
    private boolean isDead(Player player) {
        return player.hp < 1;
    }


    /**
     * Moves the {@code Player} character.
     * @param vector - An {@code int[]} {x, y} vector to add to the {@code Player}
     * @throws InvalidPositionError If the coordinate the {@code Player} tries to move at, is not valid
     */
    public void movePlayer(int[] vector) throws InvalidPositionError {
        if(!move(player, vector)) {
            throw new InvalidPositionError("Can't move the player to that position. The position is not empty or is outside the boards boundaries");
        }
    }

    /**
     * Iterates through the {@code Enemy[]} array moving them towards the {@code Player}.
     * <p>
     * If an {@code Enemy} is within reach of the {@code Player}, it attacks him.
     */
    public void moveEnemies() {
        Player target = player;
        for (Enemy enemy : enemies) {
            if (Array.contains(target.coordinate, getAdjacentCoords(enemy))) {
                attack(enemy, target);
            } else {
                int[] distanceFromTarget = Array.substract(enemy.coordinate, target.coordinate);
                distanceFromTarget = Array.unsign(distanceFromTarget);
    
                int[] vector = new int[2];
    
                if (distanceFromTarget[0] >= distanceFromTarget[1]) {
                    vector[0] = enemy.movePoints;
                    if (enemy.coordinate[0] > target.coordinate[0]) {
                        vector[0] *= -1;
                    }
                } else if (distanceFromTarget[0] < distanceFromTarget[1]) {
                    vector[1] = enemy.movePoints;
                    if (enemy.coordinate[1] > target.coordinate[1]) {
                        vector[1] *= -1;
                    }
                }
                
                move(enemy, vector);
            }
        }
    }


    /**
     * Builds a string representing the {@code Board} and {@code Players}.
     * @return The stylished board
     */
    public String getBoard() {
        String string = "";

        for (int i = 0; i < width+2; i++) {
            string += border + " ";
        }
        string += "\n";

        for (char[] row : board) {
            string += border + " ";
            for (char c : row) {
                string += c + " ";
            }
            string += border + "\n";
        }

        for (int i = 0; i < width+2; i++) {
            string += border + " ";
        }
        string += "\n";

        return string;
    }

    /**
     * Returns the {@code char[][]} array of the {@code Board} object.
     * @return A {@code char[][]} array of the {@code Board} tiles
     */
    public char[][] getCharsBoard() {
        return board;
    }

}
