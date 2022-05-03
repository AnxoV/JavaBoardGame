package src.Game;

import java.util.ArrayList;
import java.util.Random;

import src.Exceptions.InvalidPositionError;

/**
 * TO-DO:
 * - Implement another way of calling coordinate based function parameters with two parameters x and y
 */


 /**
  * Represents the {@code Board} used to manage the game.
  * 
  * <p> {@code Board} is an {@code char[][]} object that stores the different values of the game.
  * 
  * <p>A {@code Board} may have one {@code Player} and one or more {@code Enemy}s.
  * 
  * <p>The base {@link src.Game.Board#Board() constructor} creates a basic 5 x 5 grid with the default character values.
  * This class implements different methods to manage what happens on the board and how it is displayed.
  * 
  * <p>It depends on the {@link src.Logic.Array2 Array.java} class to perform basic operations within the grid.
  * 
  * @see {@link src.Game.Board#spawnPlayer() spawnPlayer()}
  * @see {@link src.Game.Board#spawnEnemy() spawnEnemy()}
  * @see {@link src.Game.Board#move(Player, int[]) move(Player, int[])}
  * @see {@link src.Game.Board#getBoard() getBoard()}
  * 
  * @version 1.0
  * @since JDK 1.11
  */
public class Board {
    /**
     * The value corresponding an empty tile on the {@code Board}.
     */
    static final char BLANK = ' ';

    /**
     * The value corresponding a {@code Board} border.
     */
    static final char BORDER = '#';

    /**
     * The value corresponding a dead {@code Player}.
     */
    static final char DEAD = '-';

    /**
     * The {@code char[][]} array which stores the values of the {@code Board}.
     */
    protected char[][] board;

    /**
     * The main {@code Player} object.
     * 
     * @see {@link src.Game.Player Player}
     */
    protected Player player;

    /**
     * An {@code Enemy[]} array, representing the {@code Enemy} objects on the {@code Board}.
     * 
     * @see {@link src.Game.Player Player}
     * @see {@link src.Game.Enemy Enemy}
     */
    protected Enemy[] enemies;

    /**
     * Indicates which turn currently is.
     */
    private int turn = 0;

    /**
     * Creates a 5 x 5 empty {@code Board} object.
     */
    public Board() {
        board = new char[5][5];
        fillBlank();
    }

    /**
     * Creates a {@code width} x {@code height} empty {@code Board} object.
     * @param width - The width of the {@code Board}
     * @param height - The height of the {@code Board}
     */
    public Board(int width, int height) {
        board = new char[height][width];
        fillBlank();
    }

    /**
     * Returns the width of the {@code Board}.
     * @return The width of the {@code Board}
     */
    public int getWidth() {
        return board[0].length;
    }

    /**
     * Returns the height of the {@code Board}.
     * @return The height of the {@code Board}
     */
    public int getHeight() {
        return board.length;
    }

    /**
     * Returns the {@code int[]} center of the {@code Board}.
     * 
     * <p>If any of {@code Board}'s width or height is even, the center prioritizes the most bottom-right tile.
     * @return The center of the {@code Board}
     */
    public int[] getCenter() {
        return new int[]{getWidth()/2, getHeight()/2};
    }

    /**
     * Returns the {@code Player}.
     * @return The {@code Player}
     * @see {@link src.Game.Player Player}
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the {@code Enemy[]} array of the {@code Board}.
     * @return The {@code Enemy[]} array of the {@code Board}
     * @see {@link src.Game.Enemy Enemy}
     */
    public Enemy[] getEnemies() {
        return enemies;
    }

    /**
     * Returns the {@code BLANK} tile {@code char}.
     * @return The {@code BLANK} tile {@code char}
     */
    public char getBlank() {
        return BLANK;
    }

    /**
     * Returns the {@code BORDER} tile {@code char}.
     * @return The {@code BORDER} tile {@code char}
     */
    public char getBorder() {
        return BORDER;
    }

    /**
     * Returns the {@code DEAD} tile {@code char}.
     * @return The {@code DEAD} tile {@code char}
     */
    public char getDead() {
        return DEAD;
    }

    /**
     * Returns the current turn of the {@code Board}.
     * @return The current turn of the {@code Board}
     */
    public int getTurn() {
        return turn;
    }


    /**
     * Replaces every character of the {@code Board}'s grid with the {@code char BLANK}.
     */
    public void fillBlank() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                board[y][x] = getBlank();
            }
        }
    }

    /**
     * Checks wether a tile is empty and inside the grid of the {@code Board}.
     * @param coordinate - The {@code int[]} coordinate to check
     * @return A boolean representing if the given coordinate is valid
     */
    public boolean validateCoordinate(int[] coordinate) {
        boolean valid = true;
        try {
            if (board[coordinate[1]][coordinate[0]] != getBlank()) {
                valid = false;
            }
        } catch (IndexOutOfBoundsException err) {
            valid = false;
        }
        return valid;
    }

    /**
     * Spawns the {@code Player} at the center of the {@code Board}.
     * @throws InvalidPositionError If the coordinate is not valid
     */
    public void spawnPlayer() throws InvalidPositionError {
        if (!validateCoordinate(getCenter())) {
            throw new InvalidPositionError("Can't spawn the player at that position. The position is not empty or is outside the boards boundaries");
        }
        player = new Player();
        board[0][0] = player.getSymbol();
    }

    /**
     * Spawns the {@code Player} at a given {@code int[]} {x, y} coordinate.
     * @param coordinate - The {@code int[]} coordinate to spawn the {@code Player} at
     * @throws InvalidPositionError If the coordinate is not valid
     */
    public void spawnPlayer(int[] coordinate) throws InvalidPositionError {
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the player at that position. The position is not empty or is outside the boards boundaries");
        }
        player = new Player(coordinate);
        board[coordinate[1]][coordinate[0]] = player.getSymbol();
    }

    /**
     * Generates a random {@code int[]} {x, y} coordinate at the side of the {@code Board}.
     * @return An {@code int[]} {x, y} coordinate
     */
    private int[] randomBorderCoordinate() {
        Random rand = new Random();
        int width = getWidth();
        int height = getHeight();
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
     * @throws InvalidPositionError If the coordinate is not valid
     *//*
    public void spawnEnemy() throws InvalidPositionError {
        int[] coordinate = randomBorderCoordinate();
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the enemy at that position. The position is not empty or is outside the boards boundaries");
        }
        Enemy enemy = new Enemy(coordinate);
        board[coordinate[1]][coordinate[0]] = Enemy.symbol;
        enemies = Array2.add(enemies, enemy);
    }

    /**
     * Spawns an {@code Enemy} at a given {@code int[]} {x, y} coordinate. 
     * @param coordinate - The {@code int[]</code} {x, y} coordinate to spawn the {@code Enemy} at
     * @throws InvalidSpawnPosition If the coordinate to spawn the {@code Enemy} at, is not valid
     *//*
    public void spawnEnemy(int[] coordinate) throws InvalidPositionError {
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the enemy at that position. The position is not empty or is outside the boards boundaries");
        }
        Enemy enemy = new Enemy(coordinate);
        board[coordinate[1]][coordinate[0]] = Enemy.symbol;
        enemies.add(enemy);
    }

    /**
     * Moves any {@code Player} object with the force of an {@code int[]} {x, y} vector
     * @param player - A {@code Player} object to move
     * @param vector - An {@code int[]} {x, y} vector representing the force
     * @return Wether the move executed correctly
     *//*
    private boolean move(Player player, int[] vector) {
        boolean moved = true;
        int[] playerCoords = player.coordinate;
        int[] nextPosition = Array2.sum(playerCoords, vector);
        if (validateCoordinate(nextPosition)) {
            board[playerCoords[1]][playerCoords[0]] = blank;
            player.coordinate = nextPosition;
            board[nextPosition[1]][nextPosition[0]] = player.getSymbol();
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
     *//*
    private boolean attack(Player player, Player target) {
        boolean attacked = false;
        if (Array2.contains(target.coordinate, getAdjacentCoords(player))) {
            target.hp -= player.damage;

            if (isDead(target)) {
                if (Enemy.class.isAssignableFrom(target.getClass())) {
                    enemies.remove(target);
                    board[target.coordinate[1]][target.coordinate[0]] = blank;
                } else {
                    board[target.coordinate[1]][target.coordinate[0]] = death;
                    alive = false;
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
     *//*
    public void movePlayer(int[] vector) throws InvalidPositionError {
        if(!move(player, vector)) {
            throw new InvalidPositionError("Can't move the player to that position. The position is not empty or is outside the boards boundaries");
        }
    }

    /**
     * Iterates through the {@code Enemy[]} array moving them towards the {@code Player}.
     * <p>
     * If an {@code Enemy} is within reach of the {@code Player}, it attacks him.
     * <p>
     * An {@code Enemy} takes two turns to move one tile.
     *//*
    public void moveEnemies() {
        if (turn % 2 == 0) {
            Player target = player;
            for (Enemy enemy : enemies) {
                if (Array2.contains(target.coordinate, getAdjacentCoords(enemy))) {
                    attack(enemy, target);
                } else {
                    int[] distanceFromTarget = Array2.substract(enemy.coordinate, target.coordinate);
                    distanceFromTarget = Array2.unsign(distanceFromTarget);
        
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
    }

    /**
     * Advances to the next turn.
     */
    public void nextTurn() {
        turn++;
    }

    /**
     * Builds a string representing the {@code Board} and {@code Players}.
     * @return The stylished board string
     *//*
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
     * Builds an HTML encoded string representing the {@code Board} and {@code Players}.
     * @return The stylished board string
     *//*
    public String getHTMLBoard() {
        String string = "";
        string += "";
        for (int i = 0; i < width+2; i++) {
            string += border + " ";
        }
        string += "\n";
        for (char[] row : board) {
            string += border + " ";
            for (char c : row) {
                switch (c) {
                    case '<':
                        string += "&lt; ";
                        break;
                    case '>':                        
                        string += "&gt; ";
                        break;
                    default:
                        string += c + " ";
                        break;
                }
            }
            string += border + "\n";
        }
        for (int i = 0; i < width+2; i++) {
            string += border + " ";
        }
        string += "</p></html>";
        String html = "<html><p style=\"white-space: pre-wrap\">" + string + "</p></html>";
        return html;
    }*/

    /**
     * Returns the {@code char[][]} array of the {@code Board} object.
     * @return A {@code char[][]} array of the {@code Board} tiles
     */
    public char[][] getCharsBoard() {
        return board;
    }

}
