public class Enemy extends Player {
    public Enemy() {
        super();
        this.symbol = '0';
    }

    public Enemy(int[] coordinate) {
        super(coordinate);
        this.symbol = '0';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeDefaults() {
        this.symbol = '0';
        this.hp = 1;
        this.movePoints = 1;
    }
}
