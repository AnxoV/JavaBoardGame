package src.game;

import javax.management.InvalidAttributeValueException;

/**
 * The {@code Player} specifies a game Player object.
 * In which the Player data is stored.
 * 
 * <p>A Player is made out of a series of attributes, such as
 * health, damage, movePoints, range and a symbol to display
 * ont the board.
 * 
 * @since JDK 1.11
 * @version 1.0
 * 
 * @see src.game.Board Board
 */
public class Player {

    private int[] position;
    private int hp;
    private int damage;
    private int movePoints;
    private int range;
    private char symbol;


    /**
     * Constructs a Player object with the specified parameters.
     * @param hp - The health points
     * @param damage - The damage it can deal
     * @param movePoints - The number of maximum movements in a turn
     * @param range - The range of attack
     * @param symbol - The symbol of the Player in the board
     */
    public Player(int hp, int damage, int movePoints, int range, char symbol) {
        position = new int[]{0, 0};
        this.hp = hp;
        this.damage = damage;
        this.movePoints = movePoints;
        this.range = range;
        this.symbol = symbol;
    }

    /**
     * @return The position of the Player
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * @param position The position to set
     */
    public void setPosition(int[] position) {
        this.position = position;
    }

    /**
     * @return the health points of the Player
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp - The health points to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @return the damage of the Player
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @param damage - The damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * @return the move points of the Player
     */
    public int getMovePoints() {
        return movePoints;
    }

    /**
     * @param movePoints - The number of move points to set
     * @throws InvalidAttributeValueException If {@code movePoints < 0}
     */
    public void setMovePoints(int movePoints) throws InvalidAttributeValueException {
        if (movePoints < 0) {
            throw new InvalidAttributeValueException("The move points can't be lower than 0");
        }
        this.movePoints = movePoints;
    }

    /**
     * @return The range of the Player
     */
    public int getRange() {
        return range;
    }

    /**
     * @param range - The range to set
     * @throws InvalidAttributeValueException If {@code range <= 0}
     */
    public void setRange(int range) throws InvalidAttributeValueException {
        if (range <= 0) {
            throw new InvalidAttributeValueException("The range can't be negative nor 0");
        }
        this.range = range;
    }

    /**
     * @return the symbol of the Player
     
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of the Player.
     * @param symbol - The symbol to set
     * @throws InvalidAttributeValueException If the symbol is not an alphabetic character,
     * based on ASCII table values [65, 90] && [97, 122]
     */
    public void setSymbol(char symbol) throws InvalidAttributeValueException {
        if (symbol < 65 || (symbol > 90 && symbol < 97) || symbol > 122) {
            throw new InvalidAttributeValueException("The symbol of the player must be an alphabetic character");
        }
        this.symbol = symbol;
    }

    
    /**
     * Returns the information data about the Player.
     * @return a string with the data of the Player
     */
    @Override
    public String toString() {
        return "{"
                    + "position: (" + position[0] + ", " + position[1] + "), "
                    + "hp: " + hp + ", "
                    + "damage: " + damage + ", "
                    + "movePoints: " + movePoints + ", "
                    + "symbol: " + symbol
            + "}";
    }
}