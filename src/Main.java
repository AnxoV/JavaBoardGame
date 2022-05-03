package src;

import javax.swing.SwingUtilities;

import src.GUI.*;
import src.Game.*;
import src.Game.Character;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board(9, 9);
        board.spawnPlayer(Character.Knight);
        board.spawnEnemy(Character.Bat);
        GameGUI gameGUI = new GameGUI(board);
        
        Runnable r = new Runnable() {
            @Override
            public void run() {
                gameGUI.run();
            }
        };
        SwingUtilities.invokeLater(r);

        /*
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 1000);
        */
    }
}
