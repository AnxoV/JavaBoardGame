package src.game;

import java.util.Random;

import src.exceptions.*;
import src.logic.*;
import src.pathfinder.*;

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
  * Both player and enemy objects are created from the {@link src.game.Character Character} enum.
  * 
  * <p>The most basic {@link src.game.Board#Board() constructor} creates a basic
  * 5 x 5 grid with the default character values. This class implements different
  * methods to manage what happens on the board and how it is displayed.
  * 
  * <p>It is dependant on the {@link src.logic.Operator Operator} class to perform
  * basic operations within the grid. And on the {@link src.logic.DynamicArray DynamicArray}
  * class to create the enemies array.
  * 
  * @since JDK 1.11
  * @version 1.0
  * 
  * @see src.game.Character Character
  * @see src.logic.DynamicArray DynamicArray
  * @see src.logic.Operator Operator
  */
public class Board {

    /**
     * Object of the {@link java.util.Random Random} class.
     */
    private Random rand = new Random();

    /**
     * The value corresponding an empty tile on the board.
     */
    private char BLANK = ' ';

    /**
     * The value corresponding a board border.
     */
    private char BORDER = '*';

    /**
     * The value corresponding a dead character.
     */
    private char DEAD = '-';

    /**
     * The values of the board.
     */
    private char[][] board;

    /**
     * The main player.
     * @see src.game.Character Character
     */
    private Character player;

    /**
     * Copies the player character.
     */
    private Character copyPlayer;

    /**
     * The array of enemies on the board.
     * @see src.game.Character Character
     */
    private DynamicArray<Character> enemies = new DynamicArray<>(0);

    /**
     * Indicates which turn currently is.
     */
    private int turn = 0;

    /**
     * Indicates the number of times the player has moved this turno.
     */
    private int numMove;

    /**
     * Indicates if the player can move.
     */
    private boolean playerMove = true;

    /**
     * Indicates the number of points the player has earned this game.
     */
    private int points;

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
     * @see src.game.Character Character
     */
    public Character getPlayer() {
        return player;
    }

    /**
     * Returns the array of enemies on the board.
     * @return A {@code DynamicArray<Character>} of the enemies on the board
     * @see src.game.Character Character
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
     * Returns the move of the player.
     * @return The move of the player
     */
    public boolean getplayerMove() {
        return playerMove;
    }

    /**
     * Sets the move of the player.
     * @param playerMove - The move to set
     */
    public void setplayerMove(boolean playerMove) {
        this.playerMove = playerMove;
    }

    /**
     * Returns the number of times the player has moved this turn.
     * @return The number of times the player has moved this turn
     */
    public int getnumMove() {
        return numMove;
    }

    /**
     * Sets the number of player moves.
     * @param numMove - The moves to set
     */
    public void setnumMove(int numMove) {
        this.numMove = numMove;
    }

    /**
     * Returns the number of points the player has earned this game.
     * @return The number of points the player has earned this game
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the number of points of the game.
     * @param points - The points to set
     */
    public void setPoints(int points) {
        this.points = points;
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
     * @see src.game.Character Character
     */
    public void spawnPlayer(int[] coordinate, Character typeClass) throws InvalidPositionError {
        if (!validateCoordinate(coordinate)) {
            throw new InvalidPositionError("Can't spawn the player at that position."
                                        + "The position is not empty or is outside the boards boundaries");
        }
        player = typeClass;
        player.setPosition(coordinate);
        copyPlayer = new Character(player.getHp(),
                                    player.getDamage(),
                                    player.getMovePoints(),
                                    player.getRange(),
                                    player.getSymbol());
        board[coordinate[1]][coordinate[0]] = player.getSymbol();
    }

    /**
     * Tries to spawn the main player at the center of the board.
     * @param typeClass - The class of the player
     * @throws InvalidPositionError If the coordinate is not valid
     * @see src.game.Character Character
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
    public boolean isDead(Character character) {
        return character.getHp() < 1;
    }

    /**
     * Returns the coordinates that are in line range from a given position.
     * @param coordinate - The specified position
     * @param range - The range of the line
     * @return The coordinates that are in range from the position
     */
    public int[][] getLineCoordinates(int[] coordinate, int range) {
        int[][] lineCoords = new int[range*4][2];
        int i = 0;
        for (int x = -range; x <= range; x++) {
            if (x != 0) {
                lineCoords[i] = Operator.sum(coordinate, new int[]{x, 0});
                i++;
            }
        }
        for (int y = -range; y <= range; y++) {
            if (y != 0) {
                lineCoords[i] = Operator.sum(coordinate, new int[]{0, y});
                i++;
            }
        }
        return lineCoords;
    }

    /**
     * Returns the line of vision on range from a given position.
     * @param coordinate - The specified position
     * @param range - The range of the view
     * @return The coordinates that are in view (not blocked by non-empty tiles)
     */
    public int[][] getViewLineCoordinates(int[] coordinate, int range) {
        DynamicArray<int[]> viewCoords = new DynamicArray<>();
        boolean blockedView = false;
        for (int x = -1; x >= -range && !blockedView; x--) {
            int[] viewCoord = Operator.sum(coordinate, new int[]{x, 0});
            if (!validateCoordinate(viewCoord)) {
                blockedView = true;
            } else {
                viewCoords.push(viewCoord);
            }
        }
        blockedView = false;
        for (int x = 1; x <= range && !blockedView; x++) {
            int[] viewCoord = Operator.sum(coordinate, new int[]{x, 0});
            if (!validateCoordinate(viewCoord)) {
                blockedView = true;
            } else {
                viewCoords.push(viewCoord);
            }
        }
        blockedView = false;
        for (int y = -1; y >= -range && !blockedView; y--) {
            int[] viewCoord = Operator.sum(coordinate, new int[]{0, y});
            if (!validateCoordinate(viewCoord)) {
                blockedView = true;
            } else {
                viewCoords.push(viewCoord);
            }
        }
        blockedView = false;
        for (int y = 1; y <= range && !blockedView; y++) {
            int[] viewCoord = Operator.sum(coordinate, new int[]{0, y});
            if (!validateCoordinate(viewCoord)) {
                blockedView = true;
            } else {
                viewCoords.push(viewCoord);
            }
        }
        int[][] viewCoordsArray = new int[viewCoords.size()][2];
        for (int i = 0; i < viewCoords.size(); i++) {
            viewCoordsArray[i] = viewCoords.get(i);
        }
        return viewCoordsArray;
    }

    /**
     * Returns wether a target is in range of a character.
     * @param character - The character
     * @param target - The target to check
     * @return {@code true} if the target is in range of the character, {@code false} otherwise
     */
    private boolean isInRange(Character character, Character target) {
        int range = character.getRange();
        int[][] lineCoords = getLineCoordinates(character.getPosition(), range);
        if (Operator.contains(target.getPosition(), lineCoords)) return true;
        return false;
    }

    /**
     * Attacks a character if it is within reach.
     * @param character - The character who attacks
     * @param target - The character to be attacked
     * @return Wether or not the attack took place
     */
    private void attack(Character character, Character target) {
        target.setHp(target.getHp()-character.getDamage());

        if (!target.equals(player)) {
            if (character.getDamage() <= target.getHp()) {
                points += character.getDamage();
            } else {
                points += character.getDamage()-target.getHp();
            }
        }

        if (isDead(target)) {
            int[] targetPosition = target.getPosition();
            board[targetPosition[1]][targetPosition[0]] = BLANK;
            if (!target.equals(player)) {
                enemies.pop(target);
            }
        }
    }

    /**
     * Returns the enemy from the given position on the board.
     * @param coord - The specified position
     * @return The enemy in that position
     */
    private Character getEnemy(int[] coord) {
        try {
            if (board[coord[1]][coord[0]] != BLANK && board[coord[1]][coord[0]] != BORDER) {
                for (Character enemy : enemies) {
                    if (Operator.equals(enemy.getPosition(), coord)) return enemy;
                }
            }
        } catch (IndexOutOfBoundsException e) {}
        return null;
    }

    /**
     * Returns the first enemy found on the given direction from the player in range
     * @param direction - The specified direction
     * @return The first enemy found
     */
    private Character getFirstEnemy(int direction) {
        final int LEFT = 37;
        final int UP = 38;
        final int RIGHT = 39;
        final int DOWN = 40;
        Character enemy = null;
        int range = player.getRange();
        switch (direction) {
            case LEFT:
                for (int x = -1; x >= -range && enemy == null; x--) {
                    int[] coord = Operator.sum(player.getPosition(), new int[]{x, 0});
                    enemy = getEnemy(coord);
                } 
                break;
            case RIGHT:
                for (int x = 1; x <= range && enemy == null; x++) {
                    int[] coord = Operator.sum(player.getPosition(), new int[]{x, 0});
                    enemy = getEnemy(coord);
                }
                break;
            case UP:
                for (int y = -1; y >= -range && enemy == null; y--) {
                    int[] coord = Operator.sum(player.getPosition(), new int[]{0, y});
                    enemy = getEnemy(coord);
                } 
                break;
            case DOWN:
                for (int y = 1; y <= range && enemy == null; y++) {
                    int[] coord = Operator.sum(player.getPosition(), new int[]{0, y});
                    enemy = getEnemy(coord);
                }
                break;
            default:
                break;
        }
        return enemy;
    }

    /**
     * Attacks the first enemy found in the line direction from the player.
     * @param direction - The keycode representing the arrow direction of the attack
     */
    public void attackInRange(int direction) {
        Character enemy = getFirstEnemy(direction);
        if (enemy != null) {
            attack(player, enemy);
        }
    }


    /**
     * Moves a character with the force of an {x, y} vector.
     * @param character - The character to move
     * @param vector - The force to move the character with
     * @return Wether the move executed correctly or not
     */
    public boolean move(Character character, int[] vector) {
        int[] currentPosition = character.getPosition();
        int[] nextPosition = Operator.sum(currentPosition, vector);
        if (validateCoordinate(nextPosition)) {
            board[currentPosition[1]][currentPosition[0]] = BLANK;
            character.setPosition(nextPosition);
            board[nextPosition[1]][nextPosition[0]] = character.getSymbol();
            return true;
        }
        return false;
    }

    /**
     * Moves the player with the force of an {x, y} vector.
     * @param vector - The force to move the player with
     * @return Wether the move executed correctly or not
     */
    public boolean movePlayer(int[] vector) {
        if (isDead(player)) return false;
        numMove++;
        if (numMove >= player.getMovePoints()) {
            playerMove = false;
        }
        return move(player, vector);
    }

    /**
     * Returns the array resulting of converting any non-empty
     * tiles apart from the character to BLANK.
     * @param character - The character to get the view of
     * @return The view of the character
     */
    private char[][] getCharacterBoardView(Character character) {
        char[][] boardView = new char[board.length][board[0].length];
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                int[] coord = new int[]{x, y};
                char tile = board[y][x];
                if (tile != BLANK
                    && tile != player.getSymbol()
                    && !Operator.equals(coord, character.getPosition())
                ) {
                    boardView[y][x] = BORDER;
                } else {
                    boardView[y][x] = BLANK;
                }
            }
        }
        return boardView;
    }

    /**
     * Iterates through the enemies array moving them towards the player.
     * If the player is within reach of the enemy, they attack him.
     * 
     * <p>An enemy may move in a turn or not, it is randomly calculated.
     */
    public void moveEnemies() {
        int[] playerPos = player.getPosition();
        for (Character enemy : enemies) {
            if (rand.nextInt(10) <= 4) {
                int[] enemyPos = enemy.getPosition();
                if (isInRange(enemy, player)) {
                    attack(enemy, player);
                } else {
                    char[][] enemyBoardView = getCharacterBoardView(enemy);
                    try {
                        A a = new A(enemyBoardView);
                        Point enemyPoint = new Point(enemyPos, null);
                        Point playerPoint = new Point(playerPos, null);
                        DynamicArray<Point> path = a.path(enemyPoint, playerPoint);
                        
                        int[] nextPosition = path.get(0).coordinate;
                        board[enemyPos[1]][enemyPos[0]] = BLANK;
                        enemy.setPosition(nextPosition);
                        enemyPos = enemy.getPosition();
                        board[enemyPos[1]][enemyPos[0]] = enemy.getSymbol();
                    } catch (Exception e) {}
                }
            }
        }
    }

    /**
     * Advances to the next turn.
     */
    public void nextTurn() {
        numMove = 0;
        playerMove = true;
        turn++;
        if (rand.nextInt(101) <= (Math.pow(points, 2)+20) || enemies.size() == 0) {
            try {
                spawnEnemy(new Character(1, 1, 1, 1, 'm'));
            } catch (Exception e) {}
        }
    }

    /**
     * Replaces all non border tiles with the blank tile.
     */
    public void clearBord() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] != BORDER) {
                    board[y][x] = BLANK;
                }
            }
        }
    }

    /**
     * Sets the board to the default settings.
     */
    public void reset() {
        turn = 0;
        numMove = 0;
        points = 0;
        playerMove = true;
        player = copyPlayer;
        enemies = new DynamicArray<>();
        clearBord();
        try {
            spawnPlayer(player);
            spawnEnemy(new Character(1, 1, 1, 1, 'm'));
        } catch(Exception e){}
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
     * Builds an HTML encoded string representing the board and characters from a given board array.
     * @param board - The specified board array
     * @return The stylished board string
     */
    public String toHTMLString(char[][] board) {
        String string = "<html>"
                            + "<style>"
                                + "@font-face {font-family: Caladan; src: url(\"src\\Fonts\\Caladan.ttf\");}"
                                + "pre {font-family: \"Caladan\"; margin-left: 20px;}"
                            + "</style>"
                            + "<pre>";
        for (int i = 0; i < board[0].length+2; i++) {
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

        for (int i = 0; i < board[0].length+2; i++) {
            string += BORDER + " ";
        }
        string += "</pre></html>";
        return string;
    }

    /**
     * Builds an HTML encoded string representing the board and characters.
     * @return The stylished board string
     */
    public String toHTMLString() {
        return toHTMLString(board);
    }

}
