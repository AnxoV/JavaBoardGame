package src.game;

/**
 * The {@code Character} specifies a game character object.
 * In which the character data is stored.
 * 
 * @since JDK 1.11
 * @version 1.0
 * 
 * @see src.game.Board Board
 */
public class Character {

    /**
     * The position of the character.
     */
    private int[] position;

    /**
     * The hp of the character.
     */
    private int hp;

    /**
     * The damage of the character.
     */
    private int damage;

    /**
     * The movePoints of the character.
     */
    private int movePoints;

    /**
     * The range of the character.
     */
    private int range;

    /**
     * The symbol of the character.
     */
    private char symbol;


    /**
     * Constructs a full custom character object.
     * @param hp - The hp of the character
     * @param damage - The damage of the character
     * @param movePoints - The movePoints of the character
     * @param range - The range of the character
     * @param symbol - The symbol of the character
     */
    public Character(int hp, int damage, int movePoints, int range, char symbol) {
        position = new int[]{0, 0};
        this.hp = hp;
        this.damage = damage;
        this.movePoints = movePoints;
        this.range = range;
        this.symbol = symbol;
    }

    /**
     * Returns the position of the character.
     * @return The position of the character
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Sets the position of the character.
     * @param position The position to set
     */
    public void setPosition(int[] position) {
        this.position = position;
    }

    /**
     * Returns the hp of the character.
     * @return the hp of the character
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the hp of the character.
     * @param hp - The hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Returns the damage of the character.
     * @return the damage of the character
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage of the character.
     * @param damage - The damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Returns the movePoints of the character.
     * @return the movePoints of the character
     */
    public int getMovePoints() {
        return movePoints;
    }

    /**
     * Sets the movePoints of the character.
     * @param movePoints - The movePoints to set
     */
    public void setMovePoints(int movePoints) {
        this.movePoints = movePoints;
    }

    /**
     * Returns the range of the character.
     * @return The range of the character
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the range of the character.
     * @param range - The range to set
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Returns the symbol of the character.
     * @return the symbol of the character
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of the character.
     * @param symbol - The symbol to set
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    
    /**
     * Returns the information data about the character.
     * @return a string with the data of the character
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
