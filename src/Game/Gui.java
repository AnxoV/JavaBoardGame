package src.game;

import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The {@code GameGUI} class creates a window to graphicly display the board
 * and control the player movements.
 * 
 * @since JDK 1.11
 * @version 1.0
 * 
 * @see src.Game.Board Board
 */
public class Gui {

    private Board BOARD;
    private HashMap<String, Font> fonts = new HashMap<>();

    private JFrame rootFrame;
    /**/private JPanel mainPanel;
    /****/private JLabel titleLabel;
    /****/private JLabel startLabel;
    /****/private JLabel endLabel;
    /****/private JPanel gamePanel;
    /******/private JLabel statsLabel;
    /******/private JLabel boardLabel;

    private Clip clip;
    
    
    private boolean showView = false;
    private boolean iddle = false;
    private boolean run = false;
    private boolean ended = false;
    
    
    private GameKeyListener listener = new GameKeyListener();
    private Timer timer;


    public Gui(Board board) {
        BOARD = board;
    }

    public void setBoard(Board board) {
        BOARD = board;
    }




    private void initFonts() {
        String path = "src\\Fonts\\";
        String[] fontNames = {
            "alagard.ttf",
            "caladan.ttf"
        };
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (String fontName : fontNames) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path, fontName)).deriveFont(24f);
                ge.registerFont(font);
                fonts.put(fontName, font);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initMusic() {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src\\Audio\\bso\\xDeviruchi - Decisive Battle.wav"));
            clip.open(inputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRoot() {
        rootFrame = new JFrame("Game");
        rootFrame.setLayout(new BoxLayout(rootFrame.getContentPane(), BoxLayout.PAGE_AXIS));
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.setFont(fonts.get("alagard.ttf"));
        rootFrame.setUndecorated(true);
        rootFrame.setContentPane(new JLabel(new ImageIcon("src\\Images\\background.png")));
        
        rootFrame.pack();
        rootFrame.setLocationRelativeTo(null);
        rootFrame.setResizable(true);
        rootFrame.setVisible(true);
    }

    private void initMain() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setOpaque(false);
        mainPanel.setSize(rootFrame.getSize());
        rootFrame.add(mainPanel);
    }

    private void initTitle() {
        titleLabel = new JLabel("ASCIILVANIA");
        titleLabel.setFont(rootFrame.getFont().deriveFont(120f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
    }

    private void initStart() {
        startLabel = new JLabel("<html>"
                                + "<style>"
                                    + "body {text-align: center; margin-top: 60px;}"
                                + "</style>"
                                + "<body>"
                                    + "Presiona <b>Enter</b> para empezar o <b>Escape</b> para salir"
                                + "</body>"
                            + "</html>");
        startLabel.setFont(rootFrame.getFont().deriveFont(40f));
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(startLabel);
    }

    private void initEnd() {
        endLabel = new JLabel("<html>"
                                + "<style>"
                                    + "body {text-align: center; margin-top: 20px;}"
                                + "</style>"
                                + "<body>"
                                    + "<div>Has perdido.</div>"
                                    + "<br>"
                                    + "<div>Presiona <b>Enter</b> para empezar o <b>Escape<b> para salir</div>"
                                + "</body>"
                            + "</html>");
        endLabel.setFont(rootFrame.getFont().deriveFont(40f));
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        endLabel.setVisible(false);
        mainPanel.add(endLabel);
    }
    
    private void initGame() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
        gamePanel.setOpaque(false);
        gamePanel.setVisible(false);
        mainPanel.add(gamePanel);
    }

    private void initStats() {
        statsLabel = new JLabel(getPlayerStats());
        statsLabel.setFont(rootFrame.getFont().deriveFont(24f));
        gamePanel.add(statsLabel);
    }

    private void initBoard() {
        boardLabel = new JLabel(BOARD.toHTMLString());
        boardLabel.setFont(rootFrame.getFont().deriveFont(24f));
        gamePanel.add(boardLabel);

    }


    private void initListeners() {
        rootFrame.addKeyListener(listener);
        rootFrame.setFocusable(true);
    }

    public void run() {
        initFonts();
        initMusic();
        initRoot();
        initMain();
        initTitle();
        initStart();
        initEnd();
        initGame();
        initStats();
        initBoard();
        initListeners();
    }

    private void updateBoard(String boardString) {
        boardLabel.setText(boardString);
    }

    private void updateStats(String statsString) {
        statsLabel.setText(statsString);
    }

    private char[][] getViewAttackBoard() {
        char[][] viewAttackBoard = new char[BOARD.getHeight()][BOARD.getWidth()];
        char[][] board = BOARD.getBoard();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                viewAttackBoard[y][x] = board[y][x];
            }
        }
        int[] position = BOARD.getPlayer().getPosition();
        int range = BOARD.getPlayer().getRange();
        int[][] viewLineCoords = BOARD.getViewLineCoordinates(position, range);
        for (int i = 0; i < viewLineCoords.length; i++) {
            int[] coord = viewLineCoords[i];
            viewAttackBoard[coord[1]][coord[0]] = 'x';
        }
        return viewAttackBoard;
    }

    private String getPlayerStats() {
        Character player = BOARD.getPlayer();
        return "<html>"
                + "<style>"
                    + "body {border: 5px dashed #333; padding: 20px; margin-left: 20px;}"
                + "</style>"
                + "<body>"
                    + "Points: " + BOARD.getPoints() + "<br><br>"
                    + "Class (" + player.getSymbol() + ")" + "<br>"
                    + "Hp: " + player.getHp() + "<br>"
                    + "Damage: " + player.getDamage() + "<br>"
                    + "Move points: " + player.getMovePoints() + "<br>"
                    + "Range: " + player.getRange()
                + "</body>"   
             + "</html>";
    }

    private class GameKeyListener extends KeyAdapter {
        private final static int LEFT = 37;
        private final static int UP = 38;
        private final static int RIGHT = 39;
        private final static int DOWN = 40;
        private final static int ENTER = 10;
        private final static int ESC = 27;
    
        @Override
        public void keyTyped(KeyEvent e) {
        }

        private void windowControls(int keyCode) {
            switch (keyCode) {
                case ESC:
                    rootFrame.dispose();
                    break;
                default:
                    break;
            }
        }

        private void loadStartPanel(int keyCode) {
            switch (keyCode) {
                case ENTER:
                    run = true;
                    gamePanel.setVisible(true);
                    startLabel.setVisible(false);
            }
        }

        private void playerMoveTurn(int keyCode) {
            switch (keyCode) {
                case LEFT:
                    BOARD.movePlayer(new int[]{-1, 0});
                    break;
                case UP:
                    BOARD.movePlayer(new int[]{0, -1});
                    break;
                case RIGHT:
                    BOARD.movePlayer(new int[]{1, 0});
                    break;
                case DOWN:
                    BOARD.movePlayer(new int[]{0, 1});
                    break;
                case ENTER:
                    BOARD.movePlayer(new int[]{0, 0});
                default:
                    break;
            }
        }
    
        private void playerAttackTurn(int keyCode) {
            if (!iddle) {
                timer = new Timer();
                iddle = true;
                timer.scheduleAtFixedRate(new TimerTask () {
                    @Override
                    public void run() {
                        if (showView) {
                            updateBoard(BOARD.toHTMLString());
                        } else {
                            updateBoard(BOARD.toHTMLString(getViewAttackBoard()));
                        }
                        showView = !showView;
                    };
                }, 0, 500);
            } else {
                switch (keyCode) {
                    case LEFT:
                    case UP:
                    case RIGHT:
                    case DOWN:
                        BOARD.attackInRange(keyCode);
                    case ENTER:
                        iddle = false;
                        timer.cancel();
                        break;
                    default:
                        break;
                }
            }
        }

        private void loadDeadPanel() {
            run = false;
            ended = true;
            gamePanel.setVisible(false);
            endLabel.setVisible(true);
        }

        private void resetGame(int keyCode) {
            switch (keyCode) {
                case ENTER:
                    ended = false;
                    run = false;
                    gamePanel.setVisible(true);
                    endLabel.setVisible(false);
                    BOARD.reset();
                    break;
                default:
                    break;
            }
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            windowControls(keyCode);
            if (!ended) {
                if (!run) {
                    loadStartPanel(keyCode);
                } else {
                    if (BOARD.getplayerMove()) {
                        playerMoveTurn(keyCode);
                    }
                    if (!BOARD.getplayerMove()) {
                        playerAttackTurn(keyCode);
                    }
                    
                    if (!BOARD.getplayerMove() && !iddle) {
                        BOARD.moveEnemies();
                        BOARD.nextTurn();
                    }
    
                    if (BOARD.isDead(BOARD.getPlayer()) && run) {
                        loadDeadPanel();
                    }
                }
            } else {
                resetGame(keyCode);
            }
            updateBoard(BOARD.toHTMLString());
            updateStats(getPlayerStats());
        }
    
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}