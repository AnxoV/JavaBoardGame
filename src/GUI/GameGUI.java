package src.GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.File;

import src.Exceptions.*;
import src.Game.*;

/**
 * To-Do:
 *  - Try to make the fonts work with the board
 */

/**
 * The {@code GameGUI} class creates a window to graphicly display the board
 * and control the player movements.
 * 
 * @since JDK 1.11
 * @version 1.0
 * 
 * @see src.Game.Board Board
 */
public class GameGUI {

    /**
     * The board to be displayed in the GUI.
     */
    private Board BOARD;

    /**
     * The root window for the application.
     */
    private final JFrame root = new JFrame("Game");

    /**
     * The panel to embed the board.
     */
    public JLabel boardPanel;

    /**
     * The key listener for keyboard events.
     */
    private GameKeyListener listener = new GameKeyListener();

    /**
     * Constructs the GameGUI from the specified board.
     * @param board - The specified board
     */
    public GameGUI(Board board) {
        BOARD = board;
    }

    /**
     * Sets the board of the GUI to the specified board.
     * @param board - The board to set
     */
    public void setBoard(Board board) {
        BOARD = board;
    }

    /**
     * Returns the board of the GUI.
     * @return The board of the GUI
     */
    public Board getBoard() {
        return BOARD;
    }

    /**
     * Initializes basic properties for the root window.
     */
    private void initRoot() {
        root.setLayout(new FlowLayout());
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initializes basic properties for the board panel.
     */
    private void initBoard() {
        boardPanel = new JLabel(BOARD.toHTMLString());
        root.add(boardPanel);
    }

    /**
     * Initiliazes the listeners for the window.
     */
    private void initListeners() {
        root.addKeyListener(listener);
        root.setFocusable(true);
    }

    /**
     * Initializes the fonts for the window.
     */
    private void initFont() {
        String path = "F:\\Proyectos\\Java\\BoardGame\\src\\Fonts\\Roboto.ttf";
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(10f);
            ge.registerFont(font);
            boardPanel.setFont(font);
            root.setFont(font);
        } catch(Exception err) {
            err.printStackTrace();
        }
    }


    /**
     * Runs the main window application.
     */
    public void run() {
        initRoot();
        initBoard();
        //initFont();
        initListeners();

        root.pack();
        root.setMinimumSize(root.getSize());
        root.setVisible(true);
    }

    /**
     * Updates the JPanel board array.
     */
    private void updateBoard() {
        boardPanel.setText(BOARD.toHTMLString());
    }

    /**
     * Implements the interface {@code KeyAdapater} for the keyboard events.
     */
    private class GameKeyListener extends KeyAdapter {
        private final static int LEFT = 37;
        private final static int UP = 38;
        private final static int RIGHT = 39;
        private final static int DOWN = 40;
    
        @Override
        public void keyTyped(KeyEvent e) {
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case LEFT:
                    BOARD.movePlayer(new int[] {-1, 0});
                    break;
                case UP:
                    BOARD.movePlayer(new int[] {0, -1});
                    break;
                case RIGHT:
                    BOARD.movePlayer(new int[] {1, 0});
                    break;
                case DOWN:
                    BOARD.movePlayer(new int[] {0, 1});
                    break;
            }
            
            BOARD.moveEnemies();
            updateBoard();
            BOARD.nextTurn();
        }
    
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
