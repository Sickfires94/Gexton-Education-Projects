
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class snake_menu {
    int snake_start_x = 100, snake_start_y = 400;
    int WINDOW_HEIGHT = 800,
            WINDOW_WIDTH = 800;
    int FOOD_WIDTH = 10,
            FOOD_HEIGHT = 10;
    String snake_image_path = "./assets/splash_screen2.png";
    String play_button_path = "./assets/play.png";
    String body_image_path = "./assets/red.png";
    boolean game_end = false,
            game_paused = false;

    JFrame window;
    boolean leftDirection = false,
            rightDirection = true,
            upDirection = false,
            downDirection = false;
    JTextArea food;
    ArrayList<JTextArea> snake = new ArrayList<JTextArea>();

    snake_menu() {
        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLayout(null);
        window.setResizable(false);
        window.setVisible(true);

        splash_screen();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menu();
    }

    void splash_screen() {
        window.getContentPane().setBackground(Color.black);

        ImageIcon imageIcon = new ImageIcon(snake_image_path);
        Image newimg = imageIcon.getImage().getScaledInstance(WINDOW_WIDTH - 100, WINDOW_HEIGHT - 100,
                java.awt.Image.SCALE_SMOOTH);
        JLabel splash_image = new JLabel(new ImageIcon(newimg));
        splash_image.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        window.add(splash_image);

        window.repaint();
    }

    void menu() {
        window.getContentPane().removeAll();

        window.getContentPane().setBackground(Color.black);

        ImageIcon snake_imageIcon = new ImageIcon(snake_image_path);
        Image snake_newimg = snake_imageIcon.getImage().getScaledInstance(500, 500,
                java.awt.Image.SCALE_SMOOTH);
        JLabel snake_image = new JLabel(new ImageIcon(snake_newimg), SwingConstants.CENTER);
        snake_image.setBounds(0, 30, 800, 500);
        window.add(snake_image);

        ImageIcon imageIcon = new ImageIcon(play_button_path);
        Image newimg = imageIcon.getImage().getScaledInstance(200, 200,
                java.awt.Image.SCALE_SMOOTH);
        JLabel splash_image = new JLabel(new ImageIcon(newimg), SwingConstants.CENTER);
        splash_image.setBounds(0, 500, 800, 300);
        window.add(splash_image);

        splash_image.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                start_game();
            }
        });

        JLabel hint = new JLabel("Remember:  You can press P or esc to pause the game");
        hint.setBounds(10, 740, WINDOW_WIDTH, 20);
        hint.setForeground(Color.WHITE);
        window.add(hint);

        window.repaint();
    }

    void start_game() {
        window.getContentPane().removeAll();
        window.setLayout(null);
        snake_game game = new snake_game();
        JFrame game_window = new JFrame();
        game_window.setSize(800, 800);
        game_window.add(game);

        game_window.setVisible(true);
        window.setVisible(false);

    }

}
