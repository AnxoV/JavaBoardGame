package src.Game;

import java.util.Random;

import src.Logic.*;
import src.Exceptions.*;

/**
 * To-Do:
 *  - Change the amount of turns an enemy takes to move to happen more randomly
 */


 /**
  * Represents the board used to manage the game.
  * 
  * <p> A board is an object that stores the data of the game.
  * 
  * <p>It may have one player and one or more enemies.
  * Both player and enemy objects are created from the {@link src.Game.Character Character} enum.
  * 
  * <p>The most basic {@link src.Game.Board#Board() constructor} creates a basic
  * 5 x 5 grid with the default character values. This class implements different
  * methods to manage what happens on the board and how it is displayed.
  * 
  * <p>It is dependant on the {@link src.Logic.Operator Operator} class to perform
  * basic operations within the grid. And on the {@link src.Logic.DynamicArray DynamicArray}
  * class to create the enemies array.
  * 
  * @since JDK 1.11
  * @version 1.0
  * 
  * @see src.Game.Character Character
  * @see src.Logic.DynamicArray DynamicArray
  * @see src.Logic.Operator Operator
  */
public class Board {

    /**
     * The value corresponding an empty tile on the board.
     */
    protected char BLANK = ' ';

    /**
     * The value corresponding a board border.
     */
    protected char BORDER = '#';

    /**
     * The value corresponding a dead character.
     */
    protected char DEAD = '-';

    /**
     * The values of the board.
     */
    protected char[][] board;

    /**
     * The main player.
     * @see src.Game.Character Character
     */
    protected Character player;

    /**
     * The array of enemies on the board.
     * @see src.Game.Character Character
     */
    protected DynamicArray<Character> enemies = new DynamicArray<>(0);

    /**
     * Indicates which turn currently is.
     */
    private int turn = 0;

    /**
     * Constructs a 5 x 5 empty board.
     */
    public Board() {
        board = new char[5][5];
        fillBlank();
    }

    /**
     * Constructs a <b>width</b> x <b>height</b> empty board.
     * @param width - The width of the board
     * @param height - The height of the board
     */
    public Board(int width, int height) {
        board = new char[height][width];
        fillBlank();
    }

    /**
     * Constructs a full-custom board.
     * 
     * <p>Defines width and height of the board, as well as the different
     * chars used for filling the board.
     * 
     * @param width - The width of the board
     * @param height - The height of the board
     * @param BLANK - The BLANK tile value
     * @param BORDER - The BORDER tile value
     * @param DEAD - The DEAD tile value
     */
    public Board(int width, int height, char BLANK, char BORDER, char DEAD) {
        board = new char[height][width];
        fillBlank();
        this.BLANK = BLANK;
        this.BORDER = BORDER;
        this.DEAD = DEAD;
    }

    /**
     * Returns the board array of the board.
     * @return The board array of the board
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Returns the width of the board.
     * @return The width of the board
     */
    public int getWidth() {
        return board[0].length;
    }

    /**
     * Returns the height of the board.
     * @return The height of the board
     */
    public int getHeight() {
        return board.length;
    }

    /**
     * Returns the center of the board.
     * 
     * <p>If any of board's width or height is even, the center prioritizes the most bottom-right position.
     * @return A {@code int[]} coordinate representing the center of the board.
     */
    public int[] getCenter() {
        return new int[]{getWidth()/2, getHeight()/2};
    }

    /**
     * Returns the main player.
     * @return The main player
     * @see src.Game.Character Character
     */
    public Character getPlayer() {
        return player;
    }

    /**
     * Returns the array of enemies on the board.
     * @return A {@code DynamicArray<Character>} of the enemies on the board
     * @see src.Game.Character Character
     */
    public DynamicArray<Character> getEnemies() {
        return enemies;
    }

    /**
     * Returns the BLANK tile.
     * @return The BLANK tile
     */
    public char getBlank() {
        return BLANK;
    }

    /**
     * Returns the BORDER tile.
     * @return The BORDER tile
     */
    public char getBorder() {
        return BORDER;
    }

    /**
     * Returns the DEAD tile.
     * @return The DEAD tile
     */
    public char getDead() {
        return DEAD;
    }

    /**
     * Returns the current turn of the board.
     * @return The current turn of the board
     */
    public int getTurn() {
        return turn;
    }



    /**
     * Replaces every tile of the board with the BLANK tile.
     */
    public void fillBlank() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                board[y][x] = getBlank();
            }
        }
    }


    /**
     * Checks wether a tile is BLANK and inside of the board.
     * @param coordinate - The coordinate to check
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
     * Tries to spawn the player at the specified coordinate.
     * @param coordinate - The coordinate to spawn the player at
     * @param typeClass - The class of the player
     * @throws InvalidPositionError If the coordinate is not valid
     * @see src.Game.Character Character
     */
    public void spawnPlayer(int[] coordinate, Character typeClass) throws InvalidPositionError {
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the player at that position."
                                        + "The position is not empty or is outside the boards boundaries");
        }
        player = typeClass;
        player.setPosition(coordinate);
        board[coordinate[1]][coordinate[0]] = player.getSymbol();
    }

    /**
     * Tries to spawn the main player at the center of the board.
     * @param typeClass - The class of the player
     * @throws InvalidPositionError If the coordinate is not valid
     * @see src.Game.Character Character
     */
    public void spawnPlayer(Character typeClass) throws InvalidPositionError {
        spawnPlayer(getCenter(), typeClass);
    }


    /**
     * Generates a random coordinate at the side of the board.
     * @return A random {@code int[]} coordinate at the side of the board
     */
    private int[] getRandomBorderCoordinate() {
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
     * Tries to spawn an enemy at the specified coordinate. 
     * @param coordinate - The coordinate to spawn the enemy at
     * @throws InvalidSpawnPosition If the coordinate is not valid
     */
    public void spawnEnemy(int[] coordinate, Character typeClass) throws InvalidPositionError {
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the enemy at that position. The position is not empty or is outside the boards boundaries");
        }
        Character enemy = typeClass;
        enemy.setPosition(coordinate);
        board[coordinate[1]][coordinate[0]] = enemy.getSymbol();
        enemies.push(enemy);
    }

    /**
     * Tries to spawn an enemy at a random border coordinate.
     * @throws InvalidPositionError If the coordinate is not valid
     */
    public void spawnEnemy(Character typeClass) throws InvalidPositionError {
        spawnEnemy(getRandomBorderCoordinate(), typeClass);
    }


    /**
     * Represents the different {x, y} vectors for each cardinal direction
     */
    public enum Direction {
        UP(new int[]{0, -1}),
        DOWN(new int[]{0, 1}),
        LEFT(new int[]{-1, 0}),
        RIGHT(new int[]{1, 0});

        private int[] vector;

        private Direction(int[] vector) {
            this.vector = vector;
        }

        public int[] getVector() {
            return vector;
        }
    }

    /**
     * Returns all the adjacent coordinates relative to a character.
     * @param character - The character to get the adjacent coordinates from
     * @return An array of adjacent coordinates ordered by UP, DOWN, LEFT, RIGHT
     */
    public int[][] getAdjacentCoords(Character character) {
        int[] position = character.getPosition();
        int[][] adjacentCoords = new int[4][2];
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length; i++) {
            int[] direction = directions[i].getVector();
            int[] adjacent = new int[]{
                position[0]+direction[0],
                position[1]+direction[1]
            };
            adjacentCoords[i] = adjacent;
        }

        return adjacentCoords;
    }


    /**
     * Checks if the hp of a character is below or equal to 0.
     * @param character - The character to check
     * @return A boolean representing the lifeness of the character
     */
    private boolean isDead(Character character) {
        return player.getHp() < 1;
    }

    /**
     * Attacks a character if it is within reach.
     * @param character - The character who attacks
     * @param target - The character to be attacked
     * @return Wether or not the attack took place
     */
    private boolean attack(Character character, Character target) {
        boolean attacked = false;
        if (Operator.contains(target.getPosition(), getAdjacentCoords(player))) {
            target.setHp(target.getHp()-player.getDamage());

            if (isDead(target)) {
                int[] targetPosition = target.getPosition();
                board[targetPosition[1]][targetPosition[0]] = BLANK;
            }
            attacked = true;
        }
        return attacked;
    }


    /**
     * Moves a character with the force of an {x, y} vector.
     * @param character - The character to move
     * @param vector - The force to move the character with
     * @return Wether the move executed correctly or not
     */
    public boolean move(Character character, int[] vector) {
        boolean moved = true;
        int[] currentPosition = character.getPosition();
        int[] nextPosition = Operator.sum(currentPosition, vector);
        if (validateCoordinate(nextPosition)) {
            board[currentPosition[1]][currentPosition[0]] = BLANK;
            character.setPosition(nextPosition);
            board[nextPosition[1]][nextPosition[0]] = character.getSymbol();
        } else {
            moved = false;
        }
        return moved;
    }

    /**
     * Moves the player with the force of an {x, y} vector.
     * @param vector - The force to move the player with
     * @return Wether the move executed correctly or not
     */
    public boolean movePlayer(int[] vector) {
        return move(player, vector);
    }


    /**
     * Iterates through the enemies array moving them towards the player.
     * 
     * <p>If the player is within reach of the enemy, it attacks him.
     * 
     * <p>An enemy takes two turns to move one tile.
     */
    public void moveEnemies() {
        if (turn % 2 == 0) {
            int[] playerPos = player.getPosition();
            for (Character enemy : enemies) {
                int[] enemyPos = enemy.getPosition();
                if (Operator.contains(playerPos, getAdjacentCoords(enemy))) {
                    attack(enemy, player);
                } else {
                    int[] distanceFromTarget = Operator.substract(enemyPos, playerPos);
                    distanceFromTarget = Operator.unsign(distanceFromTarget);
        
                    int[] vector = new int[2];
        
                    if (distanceFromTarget[0] >= distanceFromTarget[1]) {
                        vector[0] = enemy.getMovePoints();
                        if (enemyPos[0] > playerPos[0]) {
                            vector[0] *= -1;
                        }
                    } else if (distanceFromTarget[0] < distanceFromTarget[1]) {
                        vector[1] = enemy.getMovePoints();
                        if (enemyPos[1] > playerPos[1]) {
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
     * Returns a string representing the board and characters.
     * @return The stylished board string
     */
    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < getWidth()+2; i++) {
            string += BORDER + " ";
        }
        string += "\n";
        for (char[] row : board) {
            string += BORDER + " ";
            for (char c : row) {
                string += c + " ";
            }
            string += BORDER + "\n";
        }

        for (int i = 0; i < getWidth()+2; i++) {
            string += BORDER + " ";
        }
        string += "\n";
        return string;
    }

    /**
     * Builds an HTML encoded string representing the board and characters.
     * @return The stylished board string
     */
    public String toHTMLString() {
        return "<html><pre>"
                + toString()
                + "</pre></html>";
    }

}
