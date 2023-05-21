import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class book_details implements Serializable {
    ArrayList<book> books;
    static String file_path = "./Data/books.ser";

    book_details() {
        books = new ArrayList<>();
    }

    private void add_book_list(book bk) {
        books.add(bk);
    }

    private void delete_book_list(int index) {
        books.remove(index);
    }

    private void edit_book_list(int index, book bk) {
        books.set(index, bk);
    }

    static void add_book(book bk) {
        book_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((book_details) input.readObject());
            input.close();

            details.add_book_list(bk);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static book get_book(int index) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            return ((book_details) input.readObject()).books.get(index);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    static void delete_book(int index) {
        book_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((book_details) input.readObject());
            input.close();

            details.delete_book_list(index);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void edit_book(int index, book bk) {
        book_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((book_details) input.readObject());
            input.close();

            details.edit_book_list(index, bk);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String[][] get_books() {
        book_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((book_details) input.readObject());
            input.close();
        } catch (Exception e) {
        }

        String[][] data = new String[details.books.size()][3];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = details.books.get(i).getName();
            data[i][1] = details.books.get(i).getSerialNumber() + "";
            data[i][2] = details.books.get(i).getIssued() + "";
        }
        return data;
    }

    static void test() {
        for (int i = 0; i < 10; i++) {
            boolean issued;
            issued = Math.random() < 0.5;
            book bk = new book("Book - " + i, i * i, issued);
            add_book(bk);
        }
    }

    static void create_empty_file() {
        book_details details = new book_details();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        create_empty_file();
        test();
    }
}
