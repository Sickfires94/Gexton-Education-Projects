import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class Quiz {
    final private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 800;
    final private Font HEADER_FONT = new Font("Arial", Font.BOLD, 40);
    final private Font CATEGORY_FONT = new Font("Arial", Font.BOLD, 20);
    final private Font QUESTION_FONT = new Font("Arial", Font.BOLD, 20);
    final private Font ANSWER_FONT = new Font("Arial", Font.BOLD, 20);

    private JFrame window;
    private Question[] questions;
    private boolean[] answers;
    private JLabel Q_no;
    private JLabel statement;
    private JRadioButton A;
    private JRadioButton B;
    private JRadioButton C;
    private JRadioButton D;
    private ButtonGroup bg;
    private JButton next;
    private JButton prev;
    Timer T;

    public Quiz() {

        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setResizable(false);
        window.setLayout(null);
        window.setVisible(true);

        /**
         * splash_screen();
         * 
         * try {
         * TimeUnit.SECONDS.sleep(3);
         * } catch (InterruptedException e) {
         * e.printStackTrace();
         * }
         */

        category_selector();

    }

    private void splash_screen() {

        window.repaint();
    }

    private void category_selector() {
        window.getContentPane().removeAll();

        JLabel header = new JLabel("Select Category", SwingConstants.CENTER);
        header.setBounds(0, 0, 800, 300);
        header.setFont(HEADER_FONT);
        window.add(header);

        JButton science = new JButton("Science");
        science.setBounds(0, 300, 400, 200);
        science.setFont(CATEGORY_FONT);
        window.add(science);

        science.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_questions(category_init(science.getText().toLowerCase()));
            }
        });

        JButton english = new JButton("English");
        english.setBounds(400, 300, 400, 200);
        english.setFont(CATEGORY_FONT);
        window.add(english);

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_questions(category_init(english.getText().toLowerCase()));
            }
        });

        JButton math = new JButton("Maths");
        math.setBounds(0, 500, 400, 200);
        math.setFont(CATEGORY_FONT);
        window.add(math);

        math.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_questions(category_init(math.getText().toLowerCase()));
            }

        });

        JButton general = new JButton("General");
        general.setBounds(400, 500, 400, 200);
        general.setFont(CATEGORY_FONT);
        window.add(general);

        general.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show_questions(category_init(general.getText().toLowerCase()));
            }
        });

        window.repaint();
    }

    private Question[] category_init(String category) {
        questions = new Question[15];
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("./questions/" + category + ".ser"));
            for (int i = 0; i < 15; i++) {
                questions[i] = (Question) input.readObject();
            }
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        shuffle_questions(questions);
        return questions;
    }

    private void shuffle_questions(Question[] questions) {
        for (int i = 0; i < 15; i++) {
            int rand = (int) (Math.random() * (15 - i) + i);
            Question temp = questions[i];
            questions[i] = questions[rand];
            questions[rand] = temp;
        }
    }

    private void show_questions(Question[] questions) {
        window.getContentPane().removeAll();

        int question_number = 0;
        answers = new boolean[15];

        T = new Timer(20);
        Thread Timer = new Thread(T);
        Timer.start();
        T.setBounds(300, 10, 200, 30);
        T.setHorizontalAlignment(SwingConstants.CENTER);

        window.add(T);

        Question current_question = questions[question_number];
        show_first_question(current_question, question_number + 1);
    }

    private void show_first_question(Question question, int question_number) {

        prev = new JButton("Previous Question");
        prev.setBounds(10, 10, 200, 40);
        window.add(prev);
        if (question_number == 1)
            prev.setVisible(false);
        else
            prev.setVisible(true);

        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.remove(next);
                window.remove(prev);
                check_answer(question, question_number);
                show_question(questions[question_number - 2], question_number - 1);
            }
        });

        next = new JButton("Next Question");
        next.setBounds(550, 10, 200, 40);
        window.add(next);
        if (question_number == 15)
            next.setVisible(false);
        else
            next.setVisible(true);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.remove(prev);
                window.remove(next);
                check_answer(question, question_number);
                show_question(questions[question_number], question_number + 1);
            }
        });

        Q_no = new JLabel("Question no. " + question_number + " /15", SwingConstants.CENTER);
        Q_no.setBounds(0, 150, WINDOW_WIDTH, 50);
        Q_no.setFont(QUESTION_FONT);
        window.add(Q_no);

        statement = new JLabel(question.question, SwingConstants.CENTER);
        statement.setBounds(0, 200, WINDOW_WIDTH, 200);
        statement.setFont(QUESTION_FONT);
        window.add(statement);

        A = new JRadioButton("A. " + question.answers[0]);
        A.setFont(ANSWER_FONT);
        A.setBounds(10, 450, WINDOW_WIDTH, 50);

        B = new JRadioButton("B. " + question.answers[1]);
        B.setFont(ANSWER_FONT);
        B.setBounds(10, 500, WINDOW_WIDTH, 50);

        C = new JRadioButton("C. " + question.answers[2]);
        C.setFont(ANSWER_FONT);
        C.setBounds(10, 550, WINDOW_WIDTH, 50);

        D = new JRadioButton("D. " + question.answers[3]);
        D.setFont(ANSWER_FONT);
        D.setBounds(10, 600, WINDOW_WIDTH, 50);

        bg = new ButtonGroup();
        bg.add(A);
        bg.add(B);
        bg.add(C);
        bg.add(D);

        window.add(A);
        window.add(B);
        window.add(C);
        window.add(D);

        window.repaint();
    }

    void show_question(Question question, int question_number) {

        prev = new JButton("Previous Question");
        prev.setBounds(10, 10, 200, 40);
        window.add(prev);
        if (question_number == 1)
            prev.setVisible(false);
        else
            prev.setVisible(true);

        if (question_number == 15) {
            JButton submit = new JButton("Submit");
            submit.setBounds(300, 100, 200, 50);
            window.add(submit);

            submit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    show_result();
                }
            });
        }

        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.remove(prev);
                window.remove(next);
                check_answer(question, question_number);
                show_question(questions[question_number - 2], question_number - 1);
            }
        });

        next = new JButton("Next Question");
        next.setBounds(550, 10, 200, 40);
        window.add(next);
        if (question_number == 15)
            next.setVisible(false);
        else
            next.setVisible(true);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.remove(prev);
                window.remove(next);
                check_answer(question, question_number);
                show_question(questions[question_number], question_number + 1);
            }
        });

        Q_no.setText("Question no. " + question_number + " /15");
        statement.setText(question.question);

        A.setText("A. " + question.answers[0]);
        B.setText("B. " + question.answers[1]);
        C.setText("C. " + question.answers[2]);
        D.setText("D. " + question.answers[3]);

        window.repaint();
    }

    void check_answer(Question question, int question_number) {
        if (Integer.parseInt(T.getText()) <= 0)
            show_result();
        int selected_answer = 5;
        String answer = "";
        for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                answer = button.getText();
            }
        }
        bg.clearSelection();
        if (answer.length() == 0) {
            System.out.println("Nothing selected");
            selected_answer = 5;
        } else {
            switch (answer.charAt(0)) {
                case ('A'):
                    selected_answer = 0;
                    break;
                case ('B'):
                    selected_answer = 1;
                    break;
                case ('C'):
                    selected_answer = 2;
                    break;
                case ('D'):
                    selected_answer = 3;
                    break;
                default:
            }
        }
        answers[question_number - 1] = question.correct_answer_index == selected_answer;
        System.out.println(answers[question_number - 1]);
    }

    void show_result() {
        window.getContentPane().removeAll();

        String[] headings = { "Question", "A", "B", "C", "D", "Correct Answer", "Selected Correct?" };
        String[][] processed_data = generate_data();

        for (String[] q : processed_data) {
            for (String s : q) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
        int correct_no = 0;
        for (boolean correct : answers)
            if (correct)
                correct_no++;

        JLabel score = new JLabel(correct_no + " correct out of 15", SwingConstants.CENTER);
        score.setBounds(10, 10, 800, 50);
        window.add(score);

        JTable table = new JTable(processed_data, headings);
        table.setBounds(50, 50, 800, 800);
        table.setRowHeight(50);
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 50, 750, 650);
        window.add(scroll);

        window.repaint();
    }

    private String[][] generate_data() {
        String[][] processed_data = new String[15][7];

        for (int i = 0; i < 15; i++) {
            String answer;

            switch (questions[i].correct_answer_index) {
                case (0):
                    answer = "A";
                    break;
                case (1):
                    answer = "B";
                    break;
                case (2):
                    answer = "C";
                    break;
                case (3):
                    answer = "D";
                    break;
                default:
                    answer = null;

            }
            String[] data = { questions[i].question, questions[i].answers[0], questions[i].answers[1],
                    questions[i].answers[2], questions[i].answers[3], answer, "" + answers[i] };
            processed_data[i] = data;
        }

        return processed_data;
    }
}
