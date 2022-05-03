package src;
/*
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import src.GUI.GameGUI;
import src.Game.Board;*/
import src.Logic.DynamicArray;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
        Board board = new Board(9, 9);
        board.spawnPlayer(board.getCenter());
        board.spawnEnemy();
        GameGUI gameGUI = new GameGUI(board);
        
        Runnable r = new Runnable() {
            @Override
            public void run() {
                gameGUI.setBoard(board);
                gameGUI.init();
            }
        };
        SwingUtilities.invokeLater(r);

        /**
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 1000);
        */
        DynamicArray<Integer> array = DynamicArray.of(new Integer[]{1, 2, 3, 4, 5, 5, 5});
        array.pop(new Integer(5), 3);
        for (int i : array) {
            System.out.println(i);
        }
    }
}
