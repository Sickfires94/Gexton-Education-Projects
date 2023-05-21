import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Form {
    JFrame window;
    int WINDOW_HEIGHT = 400;
    int WINDOW_WIDHT = 400;
    int DEFAULT_TEXTFIELD_WIDTH = 200;
    int DEFAULT_TEXTFIELD_HEIGHT = 20;
    int DEFAULT_LABEL_HEIGHT = 20;
    int DEFAULT_LABEL_WIDTH = 100;
    JPasswordField password_field;
    JPasswordField confirm_password_field;
    ArrayList<JTextField> suggestions;

    Form() {
        window = new JFrame();
        window.setSize(WINDOW_WIDHT, WINDOW_HEIGHT);

        JLabel name = new JLabel("Name : ");
        window.add(name);
        name.setBounds(10, 10, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        JTextField name_field = new JTextField();
        name_field.setBounds(130, 10, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(name_field);

        JLabel father_name = new JLabel("Father's name : ");
        window.add(father_name);
        father_name.setBounds(10, 30, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        JTextField father_name_field = new JTextField();
        father_name_field.setBounds(130, 30, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(father_name_field);

        JLabel number = new JLabel("Phone number : ");
        window.add(number);
        number.setBounds(10, 50, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        JTextField number_field = new JTextField();
        number_field.setBounds(130, 50, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(number_field);

        JLabel email = new JLabel("Email : ");
        window.add(email);
        email.setBounds(10, 70, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        JTextField email_field = new JTextField();
        email_field.setBounds(130, 70, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(email_field);

        JLabel password = new JLabel("Password : ");
        window.add(password);
        password.setBounds(10, 90, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        password_field = new JPasswordField();
        password_field.setBounds(130, 90, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(password_field);

        JButton suggest_password = new JButton("Suggest Password");
        suggest_password.setBounds(110, 140, 180, 20);
        window.add(suggest_password);
        suggest_password.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fill_passwords();
            }
        });
        generate_suggestions();

        JLabel confirm_password = new JLabel("Confirm password : ");
        window.add(confirm_password);
        confirm_password.setBounds(10, 170, DEFAULT_LABEL_WIDTH + 20, DEFAULT_LABEL_HEIGHT);
        confirm_password_field = new JPasswordField();
        confirm_password_field.setBounds(130, 170, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(confirm_password_field);

        JButton submit = new JButton("Submit");
        submit.setBounds(155, 210, 90, 20);
        window.add(submit);

        JTextArea errors = new JTextArea();
        errors.setBounds(10, 240, 380, 220);
        errors.setEditable(false);
        window.add(errors);

        submit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String error_lines = "";

                if (!name_field.getText().trim().matches("[a-zA-Z ]+"))
                    error_lines += "Invalid name entered \n";

                if (!father_name_field.getText().trim().matches("[a-zA-Z ]+"))
                    error_lines += "Invalid father's name entered \n";

                if (!number_field.getText().matches("[0-9]+") || number_field.getText().length() != 11)
                    error_lines += "Invalid Phone Number \n";

                if (!email_field.getText().contains("@") || !email_field.getText().endsWith(".com"))
                    error_lines += "Invalid Email Address \n";

                if (!password_field.getText()
                        .matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[,.<>?!@#$%^&-+=()])(?=\\S+$).{6,}$"))
                    error_lines += "Password is too weak \n";

                if (!confirm_password_field.getText().equals(password_field.getText()))
                    error_lines += "Passwords do not match";
                if (error_lines.equals("")) {
                    error_lines += String.format(
                            " Name : %s \n Father Name : %s \n Phone Number : %s \n Email : %s \n Password : %s \n",
                            name_field.getText(), father_name_field.getText(), number_field.getText(),
                            email_field.getText(), password_field.getText());
                    errors.setForeground(Color.BLACK);
                } else
                    errors.setForeground(Color.RED);

                errors.setText(error_lines);
            }
        });

        window.setLayout(null);
        window.setVisible(true);

    }

    void generate_suggestions() {
        suggestions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            JTextField button = new JTextField();
            suggestions.add(button);
            window.add(button);
        }
    }

    void fill_passwords() {
        int x_pos = 5, y_pos = 115;
        int x_gap = 125;
        for (JTextField suggestion : suggestions) {
            suggestion.setText(generate_password());
            suggestion.setBounds(x_pos, y_pos, 120, 20);
            x_pos += x_gap;
            suggestion.setBackground(Color.lightGray);
            suggestion.setEditable(false);
            suggestion.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {
                    password_field.setText(suggestion.getText());
                    confirm_password_field.setText(suggestion.getText());
                }

                public void focusLost(FocusEvent e) {
                }

            });
            window.add(suggestion);
        }
        window.repaint();
    }

    String generate_password() {
        String symbols = "!@#$%^&*()<>?,.";
        String password = "";
        password += (char) ((int) (Math.random() * 26 + 65));
        password += (char) ((int) (Math.random() * 26 + 65));
        password += (char) ((int) Math.round(Math.random() * 26 + 97));
        password += (char) ((int) Math.round(Math.random() * 26 + 97));
        password += Math.round(Math.random() * 9);
        password += Math.round(Math.random() * 9);
        password += symbols.charAt((int) (Math.random() * symbols.length()));
        password += symbols.charAt((int) (Math.random() * symbols.length()));
        return password;
    }
}