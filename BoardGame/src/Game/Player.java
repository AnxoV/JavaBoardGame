package src.Game;

public class Player {
    protected int[] coordinate;
    private char symbol = '^';
    protected int hp;
    protected int movePoints;
    protected int damage;

    public Player() {
        coordinate = new int[]{0, 0};
        initializeDefaults();
    }

    public Player(int [] coordinate) {
        this.coordinate = coordinate;
        initializeDefaults();
    }

    /**
     * Sets the dafult properties for the class
     */
    protected void initializeDefaults() {
        hp = 10;
        movePoints = 1;
        damage = 1;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getMovePoints() {
        return movePoints;
    }

    private int[] add(int[] v1, int[] v2) {
        return new int[]{v1[0]+v2[0], v1[1]+v2[0]};
    }

    public void move(int[] vector) {
        coordinate = add(coordinate, vector);
    }

    @Override
    public String toString() {
        return "{x: " + coordinate[0] + ", y: " + coordinate[1] + "}";
    }
}
