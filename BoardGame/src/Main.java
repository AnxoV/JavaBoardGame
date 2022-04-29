package src;

import javax.swing.SwingUtilities;
import src.GUI.GameGUI;
import src.Game.Board;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board(10, 10);
        board.spawnEnemy(new int[]{0, 0});
        System.out.println(board.getBoard());

        Runnable r = new Runnable() {
            @Override
            public void run() {
                GameGUI gameGUI = new GameGUI(board);
                gameGUI.setBoard(board);
                gameGUI.init();
                
                
            }
        };
        SwingUtilities.invokeLater(r);
        /*Board board = new Board(9, 9);
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
        System.out.println("Game ended");*/
    }
}
