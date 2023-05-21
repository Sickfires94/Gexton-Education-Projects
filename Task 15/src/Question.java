import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Question implements Serializable {
    String question;
    String[] answers;
    int correct_answer_index;

    public Question(String question, int correct_answer_index, String... answers) {
        this.question = question;
        this.answers = answers;
        this.correct_answer_index = correct_answer_index;
    }

    private static void init_science_category() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./questions/science.ser"));
            for (int i = 1; i <= 15; i++) {
                output.writeObject(new Question("Science " + i, (int) Math.round((Math.random() * 3)), "A" + i, "B" + i,
                        "C" + i, "D" + i));
            }
            output.close();
        } catch (Exception e) {
        }
    }

    private static void init_english_category() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./questions/english.ser"));
            for (int i = 1; i <= 15; i++) {
                output.writeObject(new Question("English " + i, (int) Math.round((Math.random() * 3)), "A" + i, "B" + i,
                        "C" + i, "D" + i));
            }
            output.close();
        } catch (Exception e) {
        }
    }

    private static void init_math_category() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./questions/maths.ser"));
            for (int i = 1; i <= 15; i++) {
                output.writeObject(new Question("Maths " + i, (int) Math.round((Math.random() * 3)), "A" + i, "B" + i,
                        "C" + i, "D" + i));
            }
            output.close();
        } catch (Exception e) {
        }
    }

    private static void init_general_category() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./questions/general.ser"));
            for (int i = 1; i <= 15; i++) {
                output.writeObject(new Question("General Knowledge " + i, (int) Math.round((Math.random() * 3)),
                        "A" + i, "B" + i, "C" + i, "D" + i));
            }
            output.close();
        } catch (Exception e) {
        }
    }

    // initialise arbritrary questions
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        init_english_category();
        init_general_category();
        init_math_category();
        init_science_category();

        ObjectInputStream input = new ObjectInputStream(new FileInputStream("./questions/maths.ser"));
        Question q1 = (Question) input.readObject();
        System.out.println(q1.question);
    }
}
