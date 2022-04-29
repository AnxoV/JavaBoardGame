package src;

import src.Exceptions.InvalidPositionError;
import src.Game.Board;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board(9, 9);
        board.spawnPlayer(new int[]{4, 4});
        board.spawnEnemy();
        board.spawnEnemy();
        while (board.getRun()) {
            System.out.print(board.getBoard());
            try {
                board.movePlayer(Board.Direction.UP.getVector());
            } catch (InvalidPositionError err) {}
            board.moveEnemies();
            Thread.sleep(500);
        }
        System.out.println(board.getBoard());
        System.out.println("Game ended");
    }
}
