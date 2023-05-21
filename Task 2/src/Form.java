import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JPasswordField password_field = new JPasswordField();
        password_field.setBounds(130, 90, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(password_field);

        JLabel confirm_password = new JLabel("Confirm password : ");
        window.add(confirm_password);
        confirm_password.setBounds(10, 110, DEFAULT_LABEL_WIDTH + 20, DEFAULT_LABEL_HEIGHT);
        JTextField confirm_password_field = new JTextField();
        confirm_password_field.setBounds(130, 110, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(confirm_password_field);

        JButton submit = new JButton("Submit");
        submit.setBounds(155, 150, 90, 20);
        window.add(submit);

        JTextArea errors = new JTextArea();
        errors.setBounds(10, 180, 380, 220);
        errors.setEditable(false);
        errors.setForeground(Color.RED);
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

                errors.setText(error_lines);
            }
        });

        window.setLayout(null);
        window.setVisible(true);

    }
}