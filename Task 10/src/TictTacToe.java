import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class TicTacToe {
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 800;
    Font splash_font = new Font("ariel", Font.BOLD, 60);
    Font title_font = new Font("ariel", Font.BOLD, 100);
    Font warning_font = new Font("ariel", Font.BOLD, 20);
    Font gameboard_font = new Font("ariel", Font.BOLD, 150);
    String image_path = "image.png";

    JFrame window;
    boolean x_turn = true;
    JTextField[] game_board;
    int p1_score_int, p2_score_int;
    String winner;
    String p1_name, p2_name;

    public TicTacToe() throws InterruptedException {
        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        window.setResizable(false);
        window.getContentPane().setBackground(Color.white);
        window.setLayout(null);
        window.setVisible(true);

        SplashScreen();
        Thread.sleep(3000);

        menu();

    }

    void SplashScreen() {
        ImageIcon imageIcon = new ImageIcon(image_path);
        Image newimg = imageIcon.getImage().getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        JLabel splash_image = new JLabel(new ImageIcon(newimg));
        splash_image.setBounds(0, 0, WINDOW_WIDTH, 600);
        window.add(splash_image);

        JLabel splash_text = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        splash_text.setFont(splash_font);
        splash_text.setBounds(0, 600, WINDOW_WIDTH, 100);

        window.add(splash_text, BorderLayout.SOUTH);
        window.repaint();

    }

    void menu() {
        window.getContentPane().removeAll();

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setBounds(0, 0, WINDOW_WIDTH, 200);
        title.setFont(title_font);
        window.add(title);

        ImageIcon imageIcon = new ImageIcon(image_path);
        Image newimg = imageIcon.getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        JLabel menu_image = new JLabel(new ImageIcon(newimg));
        menu_image.setBounds(0, 200, WINDOW_WIDTH, 300);
        window.add(menu_image);

        JLabel p1 = new JLabel("Player 1: ");
        p1.setBounds(40, 600, 90, 20);
        window.add(p1);
        JTextField player1 = new JTextField();
        player1.setBounds(100, 600, 200, 20);
        window.add(player1);

        JLabel p2 = new JLabel("Player 2: ");
        p2.setBounds(440, 600, 90, 20);
        window.add(p2);
        JTextField player2 = new JTextField();
        player2.setBounds(500, 600, 200, 20);
        window.add(player2);

        JLabel warning = new JLabel("");
        warning.setBounds(300, 700, 200, 30);
        warning.setForeground(Color.red);
        warning.setFont(warning_font);
        window.add(warning);

        JButton pvp = new JButton("Single Player");
        pvp.setBounds(100, 650, 150, 20);

        pvp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!player1.getText().equals("")) {
                    game(player1.getText(), null);
                } else
                    warning.setText("Please enter a name");
            }
        });
        window.add(pvp);

        JButton pve = new JButton("Multiplayer");
        pve.setBounds(500, 650, 150, 20);

        pve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!player1.getText().equals("") && !player2.getText().equals("")) {
                    game(player1.getText(), player2.getText());
                } else
                    warning.setText("Please enter names");
            }
        });
        window.add(pve);

        window.repaint();
    }

    void game(String p1, String p2) {
        window.getContentPane().removeAll();

        p1_name = p1;
        p2_name = p2;
        if (p2 == null)
            p2_name = "Computer";
        p1_score_int = 0;
        p2_score_int = 0;

        JButton reset_game = new JButton("Reset Scores");
        reset_game.setBounds(310, 100, 180, 20);
        reset_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset_game();
            }

        });
        window.add(reset_game);

        JLabel winner_text = new JLabel();
        winner_text.setBounds(355, 45, 90, 20);
        window.add(winner_text);

        JLabel p1_score = new JLabel(p1_name + " : " + this.p1_score_int);
        p1_score.setBounds(10, 45, 90, 20);
        window.add(p1_score);

        JLabel p2_score = new JLabel(p2_name + " : " + this.p2_score_int);
        p2_score.setBounds(650, 45, 90, 20);
        window.add(p2_score);

        int Start_X = 90;
        int CURRENT_X = 90, CURRENT_Y = 150;
        int X_GAP = 200, Y_GAP = 200;
        game_board = new JTextField[9];
        for (int i = 0; i < 9; i++) {
            JTextField box = new JTextField();
            box.setHorizontalAlignment(SwingConstants.CENTER);
            box.setEditable(false);
            box.setBounds(CURRENT_X, CURRENT_Y, 200, 200);
            box.setFont(gameboard_font);
            box.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {
                    if (!box.getText().equals(""))
                        return;

                    if (x_turn) {
                        box.setText("X");
                        x_turn = false;

                    } else {
                        box.setText("O");
                        x_turn = true;
                    }
                    if (check_winners()) {
                        winner_text.setText(winner + " Won!!!");
                        /**
                         * try {
                         * TimeUnit.SECONDS.sleep(5);
                         * } catch (InterruptedException e1) {
                         * e1.printStackTrace();
                         * }
                         */
                        reset_board();
                        p1_score.setText(p1_name + " : " + p1_score_int);
                        p2_score.setText(p2_name + " : " + p2_score_int);
                    }
                    if (!x_turn && p2_name.equals("Computer")) {
                        if (check_draw()) {
                            winner_text.setText("Draw!!!");
                            reset_board();
                        }
                        while (true) {
                            int temp = (int) (Math.random() * 9);
                            if (game_board[temp].getText().equals("")) {
                                game_board[temp].setText("O");
                                x_turn = true;
                                break;
                            }
                        }
                        if (check_draw()) {
                            winner_text.setText("Draw!!!");
                            reset_board();
                        }
                    }
                    if (check_winners()) {
                        winner_text.setText(winner + " Won!!!");
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        reset_board();
                        p1_score.setText(p1_name + " : " + p1_score_int);
                        p2_score.setText(p2_name + " : " + p2_score_int);
                        winner_text.setText(winner);
                    }

                }

                public void focusLost(FocusEvent e) {
                }
            });
            game_board[i] = box;
            window.add(box);

            CURRENT_X += X_GAP;
            if (CURRENT_X + X_GAP >= 800) {
                CURRENT_X = Start_X;
                CURRENT_Y += Y_GAP;
            }
        }
        window.repaint();
    }

    boolean check_winners() {

        if (!game_board[0].getText().equals("")) {
            if (game_board[0].getText().equals(game_board[1].getText())
                    && game_board[0].getText().equals(game_board[2].getText())) {
                if (game_board[0].getText().equals("X")) {
                    winner = p1_name;
                    p1_score_int++;
                } else {
                    winner = p2_name;
                    p2_score_int++;
                }
                return true;
            }

            if (game_board[0].getText().equals(game_board[3].getText())
                    && game_board[0].getText().equals(game_board[6].getText())) {
                if (game_board[0].getText().equals("X")) {
                    winner = p1_name;
                    p1_score_int++;
                } else {
                    winner = p2_name;
                    p2_score_int++;
                }
                return true;
            }

            if (game_board[0].getText().equals(game_board[4].getText())
                    && game_board[0].getText().equals(game_board[8].getText())) {
                if (game_board[0].getText().equals("X")) {
                    winner = p1_name;
                    p1_score_int++;
                } else {
                    winner = p2_name;
                    p2_score_int++;
                }
                return true;
            }
        }
        if (!game_board[1].getText().equals("")) {
            if (game_board[1].getText().equals(game_board[4].getText())
                    && game_board[1].getText().equals(game_board[7].getText())) {
                if (game_board[1].getText().equals("X")) {
                    winner = p1_name;
                    p1_score_int++;
                } else {
                    winner = p2_name;
                    p2_score_int++;
                }
                return true;
            }
        }
        if (!game_board[2].getText().equals("")) {
            if (game_board[2].getText().equals(game_board[5].getText())
                    && game_board[2].getText().equals(game_board[8].getText())) {
                if (game_board[2].getText().equals("X")) {
                    winner = p1_name;
                    p1_score_int++;
                } else {
                    winner = p2_name;
                    p2_score_int++;
                }
                return true;
            }
        }

        if (game_board[3].getText().equals(game_board[4].getText())
                && game_board[3].getText().equals(game_board[5].getText()) && !game_board[3].getText().equals("")) {
            if (game_board[3].getText().equals("X")) {
                winner = p1_name;
                p1_score_int++;
            } else {
                winner = p2_name;
                p2_score_int++;
            }
            return true;
        }
        if (!game_board[6].getText().equals("")) {
            if (game_board[6].getText().equals(game_board[7].getText())
                    && game_board[6].getText().equals(game_board[8].getText())) {
                if (game_board[6].getText().equals("X")) {
                    winner = p1_name;
                    p1_score_int++;
                } else {
                    winner = p2_name;
                    p2_score_int++;
                }
                return true;
            }

            if (game_board[6].getText().equals(game_board[4].getText())
                    && game_board[6].getText().equals(game_board[2].getText())) {
                if (game_board[6].getText().equals("X")) {
                    winner = p1_name;
                    p1_score_int++;
                } else {
                    winner = p2_name;
                    p2_score_int++;
                }
                return true;
            }
        }

        if (check_draw()) {
            winner = "";
        }

        return false;

    }

    void reset_board() {
        for (int i = 0; i < 9; i++)
            game_board[i].setText("");
        winner = "";
    }

    void reset_game() {
        game(p1_name, p2_name);
    }

    boolean check_draw() {
        boolean draw = true;
        for (JTextField box : game_board) {
            if (box.getText().equals(""))
                draw = false;
        }
        return draw;
    }
}