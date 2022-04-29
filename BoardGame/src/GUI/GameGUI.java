package src.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import src.Game.*;

public class GameGUI {
    private final int TILE_GAP = 2;
    private final Color TILE_COLOR = new Color(220, 220, 220);
    private final Color PLAYER_COLOR = new Color(154, 216, 252);
    private final Color ENEMY_COLOR = new Color(247, 116, 106);
    private Board BOARD;
    
    private final JFrame root = new JFrame("Game");
    private JButton[][] boardTiles;
    private JPanel boardPanel;

    /**
     * Base consctructor.
     */
    public GameGUI(Board board) {
        BOARD = board;
        initRoot(300, 300);
        initBoard();
        root.pack();
        root.setMinimumSize(root.getSize());
        root.setVisible(true);
    }

    public void setBoard(Board board) {
        BOARD = board;
    }

    /**
     * Initializes basic properties for the root {@code Frame} window application.
     * @param width - The width of the {@code Frame}
     * @param height - The height of the {@code Frame}
     */
    private void initRoot(int width, int height) {
        root.setSize(width, height);
        root.setLayout(new FlowLayout());
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initializes basic properties for the {@code Board} window panel.
     */
    private void initBoard() {
        boardTiles = new JButton[BOARD.getHeight()][BOARD.getWidth()];
        boardPanel = new JPanel(new GridLayout(BOARD.getHeight(), BOARD.getWidth(), TILE_GAP, TILE_GAP));
        int tileSize = 50;
        for (int y = 0; y < boardTiles.length; y++) {
            for (int x = 0; x < boardTiles[y].length; x++) {
                JButton tile = new JButton();
                tile.setBackground(TILE_COLOR);
                tile.setMargin(new Insets(1, 1, 1, 1));
                tile.setBorder(null);
                ImageIcon icon = new ImageIcon(new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB));
                tile.setIcon(icon);

                boardTiles[y][x] = tile;
                boardPanel.add(boardTiles[y][x]);
            }
        }
        root.add(boardPanel);
    }

    /**
     * Updates the board values.
     * @param b - A {@code Board} object
     */
    public void updateBoard(char[][] board) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                char tile = board[y][x];
                if (tile == Player.symbol) {
                    boardPanel.getComponentAt(x+1, y+1).setBackground(PLAYER_COLOR);
                } else if (tile == Enemy.symbol) {
                    boardPanel.getComponentAt(x+1, y+1).setBackground(ENEMY_COLOR);
                }
            }
        }
    }
}
