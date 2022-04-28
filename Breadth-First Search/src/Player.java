public class Player {
    protected int[] coordinate;
    protected char symbol;
    protected int hp;
    protected int movePoints;

    public Player() {
        this.coordinate = new int[]{0, 0};
        this.initializeDefaults();
    }

    public Player(int [] coordinate) {
        this.coordinate = coordinate;
        this.initializeDefaults();
    }

    /**
     * Sets the dafult properties for the class
     */
    protected void initializeDefaults() {
        this.symbol = 'X';
        this.hp = 10;
        this.movePoints = 1;
    }

    public int[] getCoordinate() {
        return this.coordinate;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public int getMovePoints() {
        return this.movePoints;
    }

    private int[] add(int[] v1, int[] v2) {
        return new int[]{v1[0]+v2[0], v1[1]+v2[0]};
    }

    public void move(int[] vector) {
        this.coordinate = this.add(this.coordinate, vector);
    }

    @Override
    public String toString() {
        return "{x: " + this.coordinate[0] + ", y: " + this.coordinate[1] + "}";
    }
}
