package src.game;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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

    // Application component tree
    private JFrame rootFrame;
    /**/private JPanel mainPanel;
    /****/private JLabel titleLabel;
    /****/private JPanel startPanel;
    /******/private JLabel startLabel;
    /******/private JLabel meritLabel;
    /****/private JLabel endLabel;
    /****/private JPanel gamePanel;
    /******/private JLabel statsLabel;
    /******/private JLabel boardLabel;

    // Used for managing the game audio
    private Clip music;
    private Clip effects;
    
    
    private boolean showView = false;
    private boolean iddle = false;
    private boolean run = false;
    private boolean ended = false;

    private HashMap<String, AudioInputStream> audioEffects = new HashMap<>();
    
    
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
            music = AudioSystem.getClip();
            audioEffects.put("decisive_battle",
                AudioSystem.getAudioInputStream(new File("src\\Audio\\bso\\Decisive Battle\\xDeviruchi - Decisive Battle (Loop).wav")));
            music.open(audioEffects.get("decisive_battle"));
            FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAudioEffects() {
        try {
            effects = AudioSystem.getClip();
            audioEffects.put("player_hit",
                AudioSystem.getAudioInputStream(new File("src\\Audio\\effects\\player_hit.wav")));
            effects.open(audioEffects.get("player_hit"));
            FloatControl gainControl = (FloatControl) effects.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
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
        mainPanel.setSize(rootFrame.getPreferredSize());
        mainPanel.setOpaque(false);
        rootFrame.add(mainPanel);
    }

    private void initTitle() {
        titleLabel = new JLabel("ASCIILVANIA");
        titleLabel.setFont(rootFrame.getFont().deriveFont(120f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
    }

    private void initStart() {
        startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        startLabel = new JLabel("<html>"
                                + "<style>"
                                    + "body {text-align: center;}"
                                + "</style>"
                                + "<body>"
                                    + "Presiona <b>Enter</b> para empezar o <b>Escape</b> para salir"
                                + "</body>"
                            + "</html>");
        meritLabel = new JLabel("<html>"
                                + "<style>"
                                    + "body {text-align: center; width: 525px; margin-top: 175px;}"
                                + "</style>"
                                + "<body>"
                                    + "Música por <b>xDeviruchi</b>"
                                + "</body>"
                            + "</html>");

        startLabel.setFont(rootFrame.getFont().deriveFont(40f));

        meritLabel.setFont(rootFrame.getFont().deriveFont(24f));

        startPanel.setPreferredSize(mainPanel.getSize());
        startPanel.setOpaque(false);

        startPanel.add(startLabel);
        startPanel.add(meritLabel);
        mainPanel.add(startPanel);
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
        initAudioEffects();
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
        Player player = BOARD.getPlayer();
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
                    music.stop();
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
                    startPanel.setVisible(false);
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
                        if (BOARD.attackInRange(keyCode)) {
                            try {
                                effects.setFramePosition(0);
                                effects.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
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
                    run = true;
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