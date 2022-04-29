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
    
    public final JFrame root = new JFrame("Game");
    public JButton[][] boardTiles;
    public JPanel boardPanel;

    /**
     * Base consctructor.
     */
    public GameGUI(Board board) {
        BOARD = board;
    }

    public void setBoard(Board board) {
        BOARD = board;
    }

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
     * Initializes the basic properties for the GUI.
     */
    public void init() {
        initRoot();
        initBoard();

        root.pack();
        root.setMinimumSize(root.getSize());
        root.setVisible(true);
    }

}
