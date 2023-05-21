
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class maze_game extends JPanel implements ActionListener {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 800;
    private final int DOT_SIZE = 50;
    private final int[][] walls = { { 0, 1, 2, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
            { 0, 3, 7, 11, 15 },
            { 0, 3, 4, 5, 7, 11, 15 },
            { 0, 3, 5, 7, 9, 11, 15 },
            { 0, 7, 9, 10, 11, 15 },
            { 0, 7, 15 },
            { 0, 2, 3, 4, 12, 13, 14, 15 },
            { 0, 1, 2, 4, 5, 6, 7, 8, 9, 12, 15 },
            { 0, 6, 15 },
            { 0, 4, 5, 6, 10, 11, 12, 15 },
            { 0, 4, 12, 13, 14, 15 },
            { 0, 4, 5, 6, 15 },
            { 0, 6, 8, 9, 10, 11, 15 },
            { 0, 1, 2, 8, 11, 12, 13, 15 },
            { 0, 6, 8, 13, 15 },
            { 0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
    };
    private final int delay = 100;

    private int player_x;
    private int player_y;

    private final int apple_x = 3;
    private final int apple_y = 15;

    private boolean inGame = true;
    private boolean won = false;

    private Timer timer;
    private Image wall;
    private Image grass;
    private Image coin;
    private Image player;
    private Timer_custom game_timer;

    public maze_game() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon imageIcon = new ImageIcon("./assets/block.png");
        Image newimg = imageIcon.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE,
                java.awt.Image.SCALE_SMOOTH);
        ImageIcon iid = new ImageIcon(newimg);
        wall = iid.getImage();

        ImageIcon imageIcon1 = new ImageIcon("./assets/coin.png");
        Image newimg1 = imageIcon1.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE,
                java.awt.Image.SCALE_SMOOTH);
        ImageIcon iia = new ImageIcon(newimg1);
        coin = iia.getImage();

        ImageIcon imageIcon2 = new ImageIcon("./assets/mario.png");
        Image newimg2 = imageIcon2.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE,
                java.awt.Image.SCALE_SMOOTH);
        ImageIcon iih = new ImageIcon(newimg2);
        player = iih.getImage();

        ImageIcon imageIcon3 = new ImageIcon("./assets/grass.png");
        Image newimg3 = imageIcon3.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE,
                java.awt.Image.SCALE_SMOOTH);
        ImageIcon iid3 = new ImageIcon(newimg3);
        grass = iid3.getImage();
    }

    private void initGame() {

        player_x = 1;
        player_y = 1;

        timer = new Timer(delay, this);
        game_timer = new Timer_custom(30);
        Thread gt = new Thread(game_timer);
        gt.start();
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame || won) {

            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    g.drawImage(grass, j * DOT_SIZE, i * DOT_SIZE, this);
                }
            }

            Font small = new Font("Helvetica", Font.BOLD, 20);
            g.setFont(small);
            if (game_timer.seconds_left <= 0)
                inGame = false;
            g.drawString("Time Remaining: " + game_timer.seconds_left + "s", 9 * DOT_SIZE, 15 * 49);
            g.drawImage(coin, apple_x * DOT_SIZE, apple_y * DOT_SIZE, this);
            g.drawImage(player, player_x * DOT_SIZE, player_y * DOT_SIZE, this);

            for (int i = 0; i < 16; i++) {
                for (int j : walls[i]) {
                    g.drawImage(wall, j * DOT_SIZE, i * DOT_SIZE, this);
                }
            }
            if (won)
                GameWon(g);

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }

    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        String msg2 = "You Ran Out of Time";
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg2)) / 2, B_HEIGHT / 2 + 40);
    }

    private void GameWon(Graphics g) {
        String msg = "Congratulations";
        String msg2 = "You WON!!!";
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg2)) / 2, B_HEIGHT / 2 + 40);
    }

    private void checkCoin() {

        if ((player_x == apple_x) && (player_y == apple_y)) {
            won = true;
        }
    }

    private void checkCollision() {

        if (player_y * DOT_SIZE >= B_HEIGHT) {
            player_y--;
        }

        if (player_y * DOT_SIZE < 0) {
            player_y++;
        }

        if (player_x * DOT_SIZE >= B_WIDTH) {
            player_x--;
        }

        if (player_x * DOT_SIZE < 0) {
            player_x++;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkCoin();
            checkCollision();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT)) {
                boolean canmove = true;
                for (int x : walls[player_y])
                    if (x == player_x - 1) {
                        canmove = false;
                        break;
                    }
                if (canmove)
                    player_x--;
            }

            if ((key == KeyEvent.VK_RIGHT)) {
                boolean canmove = true;
                for (int x : walls[player_y])
                    if (x == player_x + 1) {
                        canmove = false;
                        break;
                    }
                if (canmove)
                    player_x++;
            }

            if ((key == KeyEvent.VK_UP)) {
                boolean canmove = true;
                for (int x : walls[player_y - 1])
                    if (x == player_x) {
                        canmove = false;
                        break;
                    }
                if (canmove)
                    player_y--;
            }

            if ((key == KeyEvent.VK_DOWN)) {
                boolean canmove = true;
                for (int x : walls[player_y + 1]) {
                    if (x == player_x) {
                        canmove = false;
                        break;
                    }
                }
                if (canmove)
                    player_y++;
            }

            if (key == KeyEvent.VK_Q) {
                System.exit(0);
            }
        }
    }
}
