import java.util.ArrayList;

public class Task_5 {
    public static void main(String[] args) throws Exception {
        ArrayList<String[]> students = new ArrayList();
        String[] student1 = { "1", "ABC", "Physics, Chemistry, Maths", "65", "100" };
        String[] student2 = { "2", "DEF", "Economics, Accounts, Business Studies", "90", "100" };
        String[] student3 = { "3", "XYZ", "Sociology, Psychology, English", "45", "100" };
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student3);
        students.add(student3);
        students.add(student3);
        students.add(student3);
        students.add(student3);

        new Mark_Sheet_Generator(students);
    }
}
