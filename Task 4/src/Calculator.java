import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Calculator {

    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 400;

    JFrame window;
    JTextArea input_screen;
    JTextArea output_screen;
    JTextArea show_angle_unit;
    Font default_screen_font = new Font("Ariel", Font.BOLD, 15);
    boolean degree = true;
    double prev_ans;

    Calculator() {
        window = new JFrame();
        window.setTitle("Calculator");

        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        input_screen = new JTextArea();
        input_screen.setBounds(10, 40, 760, 40);
        input_screen.setEditable(false);
        input_screen.setBackground(Color.LIGHT_GRAY);
        input_screen.setFont(default_screen_font);
        window.add(input_screen);

        output_screen = new JTextArea();
        output_screen.setBounds(10, 80, 760, 30);
        output_screen.setEditable(false);
        output_screen.setBackground(Color.lightGray);
        output_screen.setFont(default_screen_font);
        window.add(output_screen);

        show_angle_unit = new JTextArea();
        show_angle_unit.setBounds(10, 10, 760, 30);
        if (degree)
            show_angle_unit.setText("Degrees");
        else
            show_angle_unit.setText("Radians");
        show_angle_unit.setBackground(Color.lightGray);
        show_angle_unit.setFont(default_screen_font);
        window.add(show_angle_unit);

        initialize_buttons();

        window.setLayout(null);
        window.setVisible(true);

    }

    void initialize_buttons() {
        String[] buttons = { "!", "(", ")", "%", "Inv", "^", " / ",
                "sqrt", "ln", "log", "1", "2", "3", " + ",
                "e", "pi", "EXP", "4", "5", "6", " - ",
                "sin", "cos", "tan", "7", "8", "9", " x " };
        int STARTING_X = 10;
        int x_pos = 10, y_pos = 120;
        int x_gap = 100, y_gap = 40;

        for (String func : buttons) {
            JButton button = new JButton(func);
            button.setBounds(x_pos, y_pos, 90, 30);
            x_pos += x_gap;
            if (x_pos + 90 >= WINDOW_WIDTH) {
                y_pos += y_gap;
                x_pos = STARTING_X;
            }
            window.add(button);

            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    input_screen.append(func);
                }

            });
        }

        JButton angle_unit = new JButton("Radian      |      Degree");
        angle_unit.setBounds(x_pos, y_pos, 190, 30);
        x_pos += 200;
        angle_unit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (degree) {
                    degree = false;
                    show_angle_unit.setText("Radians");
                } else {
                    show_angle_unit.setText("Degrees");
                    degree = true;
                }
            }
        });
        window.add(angle_unit);

        JButton ans = new JButton("ANS");
        ans.setBounds(x_pos, y_pos, 90, 30);
        x_pos += x_gap;
        ans.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input_screen.append("ANS");
            }
        });
        window.add(ans);

        JButton zero = new JButton("0");
        zero.setBounds(x_pos, y_pos, 90, 30);
        x_pos += x_gap;
        zero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input_screen.append("0");
            }
        });
        window.add(zero);

        JButton Decimal = new JButton(".");
        Decimal.setBounds(x_pos, y_pos, 90, 30);
        x_pos += x_gap;
        Decimal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input_screen.append(".");
            }
        });
        window.add(Decimal);

        JButton equal = new JButton("=");
        equal.setBounds(x_pos, y_pos, 90, 30);
        x_pos += x_gap;
        equal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                output_screen.setText("" + compute(input_screen.getText().split("[()]")));
                input_screen.setText("");
            }
        });
        window.add(equal);

        JButton AC = new JButton("AC");
        AC.setBounds(x_pos, y_pos, 90, 30);
        x_pos += x_gap;
        AC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input_screen.setText("");
            }
        });
        window.add(AC);

        JButton back = new JButton("<==");
        back.setBounds(710, 120, 60, 30);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = input_screen.getText();
                input_screen.setText(text.substring(0, text.length() - 2));
            }
        });
        window.add(back);

    }

    double compute(String[] expression) {
        for (int i = 0; i < expression.length; i++) {
            if (!isConvertable(expression[i].split(" "))) {
                continue;
            }

            expression[i] = "" + apply_operators(expression[i].split(" "));

        }
        String final_expression = "";
        for (String line : expression) {
            final_expression += line;
        }

        prev_ans = apply_operators(final_expression.split(" "));
        return prev_ans;
    }

    boolean isConvertable(String[] s) {
        try {
            apply_functions(s[0]);
            apply_functions(s[s.length - 1]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    double apply_operators(String[] expression) {
        double answer = apply_functions(expression[0]);

        for (int i = 1; i < expression.length; i += 2) {
            double next = apply_functions(expression[i + 1]);

            switch (expression[i]) {
                case ("/"):
                    answer /= next;
                    break;
                case ("x"):
                    answer *= next;
                    break;
            }
        }
        for (int i = 1; i < expression.length; i += 2) {
            double next = apply_functions(expression[i + 1]);
            switch (expression[i]) {
                case ("+"):
                    answer += next;
                    break;
                case ("-"):
                    answer -= next;
                    break;
            }
        }
        return answer;
    }

    double apply_functions(String expression) {

        if (expression.endsWith("!"))
            return factorial(expression.substring(0, expression.length() - 1));
        if (expression.startsWith("sqrt"))
            return Math.sqrt(Double.parseDouble(expression.substring(4)));
        if (expression.endsWith("%"))
            return percent(expression.substring(0, expression.length() - 1));
        if (expression.startsWith("Inv"))
            return inverse(expression.substring(3));
        if (expression.startsWith("ln"))
            return Math.log(Double.parseDouble(expression.substring(2)));
        if (expression.startsWith("log"))
            return Math.log10(Double.parseDouble(expression.substring(3)));
        if (expression.contains("EXP"))
            return exp(expression);
        if (expression.startsWith("sin"))
            return sin(expression.substring(3));
        if (expression.startsWith("cos"))
            return cos(expression.substring(3));
        if (expression.startsWith("tan"))
            return tan(expression.substring(3));
        if (expression.contains("^"))
            return power(expression.split("\\^"));
        if (expression.equals("ANS"))
            return prev_ans;
        if (expression.equals("pi"))
            return Math.PI;
        if (expression.equals("e"))
            return Math.E;
        return Double.parseDouble(expression);
    }

    double factorial(String s) {
        double answer;
        answer = Double.parseDouble(s);
        if (answer <= 0)
            return 1;
        return answer * factorial((answer - 1) + "");
    }

    double percent(String s) {
        double answer = Double.parseDouble(s);
        answer /= 100;
        return answer;
    }

    double inverse(String s) {
        double answer = 1;
        answer /= Double.parseDouble(s);
        return answer;
    }

    double sin(String s) {
        double answer = Double.parseDouble(s);
        if (degree)
            answer = answer * Math.PI / 180;
        answer = Math.sin(answer);
        return answer;
    }

    double cos(String s) {
        double answer = Double.parseDouble(s);
        if (degree)
            answer = answer * Math.PI / 180;
        answer = Math.cos(answer);
        return answer;
    }

    double tan(String s) {
        double answer = Double.parseDouble(s);
        if (degree)
            answer = answer * Math.PI / 180;
        answer = Math.tan(answer);
        return answer;
    }

    double power(String[] s) {
        double answer = Math.pow(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
        return answer;
    }

    double exp(String s) {
        String[] nums = s.split("EXP");
        double answer = Double.parseDouble(nums[0]) * Math.pow(10, Double.parseDouble(nums[1]));
        return answer;
    }

}