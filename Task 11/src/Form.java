import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;
import org.jdatepicker.impl.UtilDateModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

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
    JTextField name_field, email_field;
    String image_path;
    ButtonGroup bg;
    String birth_date;
    JDatePickerImpl datePicker;

    Form() {
        window = new JFrame();
        window.setSize(WINDOW_WIDHT, WINDOW_HEIGHT);

        JLabel name = new JLabel("Name : ");
        window.add(name);
        name.setBounds(10, 10, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        name_field = new JTextField();
        name_field.setBounds(130, 10, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(name_field);

        JLabel gender = new JLabel("Gender:");
        gender.setBounds(10, 30, 90, 30);
        window.add(gender);
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        male.setBounds(150, 30, 100, 20);
        female.setBounds(250, 30, 100, 20);
        bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);
        window.add(male);
        window.add(female);

        JLabel date = new JLabel("Birth Date: ");
        date.setBounds(10, 55, 90, 30);
        window.add(date);
        UtilCalendarModel model = new UtilCalendarModel();
        model.setDate(2022, 06, 8);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new LabelDateFormatter());
        datePicker.setBounds(130, 55, 200, 30);
        window.add(datePicker);

        JLabel email = new JLabel("Email : ");
        window.add(email);
        email.setBounds(10, 85, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        email_field = new JTextField();
        email_field.setBounds(130, 85, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(email_field);

        JLabel password = new JLabel("Password : ");
        window.add(password);
        password.setBounds(10, 105, DEFAULT_LABEL_WIDTH, DEFAULT_LABEL_HEIGHT);
        password_field = new JPasswordField();
        password_field.setBounds(130, 105, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(password_field);

        JLabel confirm_password = new JLabel("Confirm password : ");
        window.add(confirm_password);
        confirm_password.setBounds(10, 125, DEFAULT_LABEL_WIDTH + 20, DEFAULT_LABEL_HEIGHT);
        confirm_password_field = new JPasswordField();
        confirm_password_field.setBounds(130, 125, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);
        window.add(confirm_password_field);

        JButton image = new JButton("Select Image");
        image.setBounds(10, 165, 150, 20);
        JLabel photo = new JLabel();
        photo.setBounds(250, 145, 100, 100);
        window.add(photo);
        window.add(image);

        image.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("C:\\Users"));
                fileChooser.setDialogTitle("Select Picture");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("photo", "jpeg", "png"));
                if (fileChooser.showOpenDialog(image) == JFileChooser.APPROVE_OPTION) {
                    image_path = fileChooser.getSelectedFile().getPath();
                    ImageIcon imageIcon = new ImageIcon(image_path);
                    Image newimg = imageIcon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    photo.setIcon(new ImageIcon(newimg));
                }
            }

        });

        JButton submit = new JButton("Submit");
        submit.setBounds(10, 210, 90, 25);
        window.add(submit);

        JButton show = new JButton("Show Data");
        show.setBounds(110, 210, 120, 25);
        window.add(show);

        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream input = new ObjectInputStream(new FileInputStream("data.ser"));
                    UserData data = (UserData) input.readObject();
                    new Data_display(data.data);
                    input.close();
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

            }

        });

        JTextArea errors = new JTextArea();
        errors.setBounds(10, 240, 380, 225);
        errors.setEditable(false);
        window.add(errors);

        submit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String error_lines = "";

                if (!name_field.getText().trim().matches("[a-zA-Z ]+"))
                    error_lines += "Invalid name entered \n";

                if (!email_field.getText().contains("@") || !email_field.getText().endsWith(".com"))
                    error_lines += "Invalid Email Address \n";

                if (!password_field.getText()
                        .matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[,.<>?!@#$%^&-+=()])(?=\\S+$).{6,}$"))
                    error_lines += "Password is too weak \n";

                if (!confirm_password_field.getText().equals(password_field.getText()))
                    error_lines += "Passwords do not match";
                errors.setForeground(Color.RED);
                if (error_lines.equals(""))
                    try {
                        add_data();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                errors.setText(error_lines);
            }
        });

        window.setLayout(null);
        window.setVisible(true);

    }

    void add_data() throws FileNotFoundException, IOException, ClassNotFoundException {
        birth_date = datePicker.getModel().getDay() + "-" + (datePicker.getModel().getMonth() + 1) + "-"
                + datePicker.getModel().getYear();
        String gender = "";
        for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                gender = button.getText();
            }
        }
        ImageIcon imageIcon = new ImageIcon(image_path);
        Image newimg = imageIcon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        Object[] user_data = { name_field.getText(), gender, birth_date,
                email_field.getText(), password_field.getText(), new ImageIcon(newimg) };

        ObjectInputStream input = new ObjectInputStream(new FileInputStream("data.ser"));
        UserData data = (UserData) input.readObject();
        data.add_user(user_data);
        input.close();
        data.generate_file();
    }
}

class LabelDateFormatter extends AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}