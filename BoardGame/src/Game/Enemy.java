package src.Game;

public class Enemy extends Player {
    public static char symbol = '0';
    public Enemy() {
        super();
    }

    public Enemy(int[] coordinate) {
        super(coordinate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeDefaults() {
        hp = 1;
        movePoints = 1;
        damage = 1;
    }
}
