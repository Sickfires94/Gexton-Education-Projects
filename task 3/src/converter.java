import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class converter {

    int WINDOW_WIDTH = 400;
    int WINDOW_HEIGHT = 400;

    HashMap<String, Double> toDollar_rate;
    JFrame window;
    JComboBox<String> primary_currency;
    JComboBox<String> secondary_currency;

    converter() {
        initialize_rate();
        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JLabel From = new JLabel("From:");
        From.setBounds(10, 10, 40, 20);
        primary_currency = new JComboBox<String>();
        primary_currency.setBounds(50, 10, 150, 20);
        window.add(From);
        window.add(primary_currency);

        JTextField input = new JTextField();
        input.setBounds(210, 10, 90, 20);
        input.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        window.add(input);

        JLabel To = new JLabel("To:");
        To.setBounds(10, 40, 40, 20);
        secondary_currency = new JComboBox<String>();
        secondary_currency.setBounds(50, 40, 150, 20);
        window.add(To);
        window.add(secondary_currency);

        JLabel output = new JLabel();
        output.setBounds(210, 40, 90, 20);
        output.setBackground(Color.WHITE);
        // output.setOpaque(true);
        output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        window.add(output);

        for (Object currency : toDollar_rate.keySet()) {
            primary_currency.addItem((String) currency);
            secondary_currency.addItem((String) currency);
        }

        JButton Convert_button = new JButton("Convert");
        Convert_button.setBounds(155, 70, 90, 20);
        window.add(Convert_button);

        Convert_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                double converted_amount = Double.parseDouble(input.getText());
                converted_amount /= toDollar_rate.get(primary_currency.getSelectedItem());
                converted_amount *= toDollar_rate.get(secondary_currency.getSelectedItem());
                output.setText(String.format("%.2f", converted_amount));
            }
        });

        window.setLayout(null);
        window.setVisible(true);
    }

    private void initialize_rate() {
        toDollar_rate = new HashMap<String, Double>(9);
        toDollar_rate.put("Pakistani Rupee", 209.76);
        toDollar_rate.put("Indian Rupee", 77.97);
        toDollar_rate.put("Euro", 0.95);
        toDollar_rate.put("Saudi Riyal", 3.75);
        toDollar_rate.put("Canadian Dollar", 1.30);
        toDollar_rate.put("Czech Koruna", 23.48);
        toDollar_rate.put("Chinese Yuan", 6.69);
        toDollar_rate.put("Japanese Yen", 134.88);
        toDollar_rate.put("USD", 1.0);
    }
}
