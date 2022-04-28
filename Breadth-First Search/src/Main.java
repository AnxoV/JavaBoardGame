public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board(9, 9);
        board.spawnPlayer(new int[]{4, 4});
        board.spawnEnemy(new int[]{8, 2});
        board.spawnEnemy(new int[]{2, 8});
        int turns = 5;
        while (turns > 0) {
            System.out.print(board.getBoard());
            board.moveEnemies();
            Thread.sleep(1000);
            turns--;
        }

    }
}
