import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class selection_screen {
    private int WINDOW_HEIGHT = 800,
            WINDOW_WIDTH = 800;

    private JFrame window;
    private String name;

    public selection_screen() {
        init_screen();
    }

    private void init_screen() {
        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setVisible(true);
        window.setResizable(false);

        login_screen();
    }

    private void login_screen() {
        JTextField name_field = new JTextField();
        name_field.setBounds(300, 150, 200, 50);
        window.add(name_field);
        JLabel name_prompt = new JLabel("Name: ");
        name_prompt.setBounds(200, 150, 100, 50);
        window.add(name_prompt);

        JPasswordField password_field = new JPasswordField();
        password_field.setBounds(300, 200, 200, 50);
        window.add(password_field);
        JLabel password_prompt = new JLabel("Password: ");
        password_prompt.setBounds(200, 200, 100, 50);
        window.add(password_prompt);

        JButton login = new JButton("Login");
        login.setBounds(300, 300, 200, 50);
        window.add(login);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                name = name_field.getText();
                if (librarian_details.verify_login(name_field.getText(), password_field.getText()))
                    login_librarian();
                else if (admin_details.verify_login(name_field.getText(), password_field.getText()))
                    login_admin();
                else {
                    JLabel login_failed = new JLabel("Wrong Username/Password Entered", SwingConstants.CENTER);
                    login_failed.setBounds(300, 400, 200, 50);
                    window.add(login_failed);
                }
            }
        });
        window.repaint();
    }

    void login_librarian() {
        window.getContentPane().removeAll();
        window.repaint();
        add_header();

        librarian_view panel = new librarian_view();
        panel.setBounds(0, 30, 800, 800);
        window.add(panel);
    }

    void login_admin() {
        window.getContentPane().removeAll();
        window.repaint();

        add_header();

        admin_view panel = new admin_view();
        panel.setBounds(0, 30, 800, 800);
        window.add(panel);
    }

    void add_header() {
        JLabel username = new JLabel("Welcome, " + name);
        username.setBounds(200, 10, 400, 30);
        window.add(username);

        JButton logout = new JButton("Log out");
        logout.setBounds(600, 10, 100, 30);
        window.add(logout);

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                login_screen();
            }
        });
    }
}