package src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.event.KeyAdapter;

import src.Exceptions.InvalidPositionError;
import src.Game.*;

/**
 * The class for the game GUI.
 */
public class GameGUI {
    private final Color TILE_COLOR = new Color(220, 220, 220);
    private final Color PLAYER_COLOR = new Color(154, 216, 252);
    private final Color ENEMY_COLOR = new Color(247, 116, 106);

    private Board BOARD;

    private final JFrame root = new JFrame("Game");
    public JLabel boardPanel;
/*
    private GameKeyListener listener = new GameKeyListener();
*/
    /**
     * Base consctructor.
     */
    public GameGUI(Board board) {
        BOARD = board;
    }

    public void setBoard(Board board) {
        BOARD = board;
    }

    public Board getBoard() {
        return BOARD;
    }
/*
    public void setFont(String path) {
        try {
            font =  Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, 20f);
            ge.registerFont(font);
        } catch (Exception err) {
            System.out.println(err);
        }
    }*/
    /*
    public Font getFont(String name) {
        System.out.println(name);
        for (Font font : ge.getAllFonts()) {
            System.out.println(font);
            if (name.equals(font.getFontName())) {
                return font;
            }
        }
        return null;
    }*/

    /**
     * Initializes basic properties for the root {@code Frame} window application.
     * @param width - The width of the {@code Frame}
     * @param height - The height of the {@code Frame}
     */
    private void initRoot() {
        root.setLayout(new FlowLayout());
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initializes basic properties for the {@code Board} window panel.
     *//*
    private void initBoard() {
        boardPanel = new JLabel(BOARD.getHTMLBoard());
        root.add(boardPanel);
    }

    /**
     * Initiliazes the listeners for the {@code Frame} window application.
     *//*
    private void initListeners() {
        root.addKeyListener(listener);
        root.setFocusable(true);
    }

    /**
     * Initializes the fonts for the {@code Frame} window application
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
     * Initializes the basic properties for the GUI.
     *//*
    public void init() {
        initRoot();
        initBoard();
        initFont();
        initListeners();

        System.out.println(boardPanel.getFont());

        root.pack();
        root.setMinimumSize(root.getSize());
        root.setVisible(true);
    }

    /**
     * Updates the {@code char[][]} board array.
     *//*
    private void updateBoard() {
        boardPanel.setText(BOARD.getHTMLBoard());
    }

    /**
     * Implements the interface {@code KeyAdapater}.
     *//*
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
            try {
                switch (keyCode) {
                    case LEFT:
                        BOARD.getPlayer().setSymbol('<');
                        BOARD.movePlayer(new int[] {-1, 0});
                        break;
                    case UP:
                        BOARD.getPlayer().setSymbol('^');
                        BOARD.movePlayer(new int[] {0, -1});
                        break;
                    case RIGHT:
                        BOARD.getPlayer().setSymbol('>');
                        BOARD.movePlayer(new int[] {1, 0});
                        break;
                    case DOWN:
                        BOARD.getPlayer().setSymbol('v');
                        BOARD.movePlayer(new int[] {0, 1});
                        break;
                }
                
                BOARD.moveEnemies();
                updateBoard();
                BOARD.nextTurn();
            } catch (InvalidPositionError err) {
                /** Add an animation or effect if the player tries to move to an invalid position? */
            /*}
        }
    
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
    */
    

}
