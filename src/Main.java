package src;

import javax.swing.SwingUtilities;

import src.game.*;
import src.pathfinder.A;

public class Main {
    public static void main(String[] args) throws Exception {

        Board board = new Board(9, 9);
        board.spawnPlayer(new Player(3, 1, 1, 2, 'k'));
        board.spawnEnemy(new Player(1, 1, 1, 1, 'm'));
        Gui gui = new Gui(board);
        A a = new A(board.getBoard(), ' ', '*');
        Runnable r = new Runnable() {
            @Override
            public void run() {
                gui.run();
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
