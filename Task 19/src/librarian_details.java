import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class librarian_details implements Serializable {
    ArrayList<librarian> librarians;
    static String file_path = "./Data/librarians.ser";

    librarian_details() {
        librarians = new ArrayList<>();
    }

    private void add_librarian_list(librarian lib) {
        librarians.add(lib);
    }

    private void delete_librarian_list(int index) {
        librarians.remove(index);
    }

    private void edit_librarian_list(int index, librarian lib) {
        librarians.set(index, lib);
    }

    static void add_librarian(librarian lib) {
        librarian_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((librarian_details) input.readObject());
            input.close();

            details.add_librarian_list(lib);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void delete_librarian(int index) {
        librarian_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((librarian_details) input.readObject());
            input.close();

            details.delete_librarian_list(index);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void edit_librarian(int index, librarian lib) {
        librarian_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((librarian_details) input.readObject());
            input.close();

            details.edit_librarian_list(index, lib);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static librarian get_librarian(int index) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            return ((librarian_details) input.readObject()).librarians.get(index);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    static String[][] get_librarians() {
        librarian_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((librarian_details) input.readObject());
            input.close();
        } catch (Exception e) {
        }

        String[][] data = new String[details.librarians.size()][2];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = details.librarians.get(i).getUsername();
            data[i][1] = details.librarians.get(i).getPassword();
        }
        return data;
    }

    static boolean verify_login(String name, String password) {
        ArrayList<librarian> details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((librarian_details) input.readObject()).librarians;
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (librarian lib : details) {
            if (lib.login_success(name, password))
                return true;
        }
        return false;
    }

    static void test() {
        for (int i = 0; i < 10; i++) {
            librarian lib = new librarian("librarian - " + i, "password-" + i);
            add_librarian(lib);
        }
    }

    static void create_empty_file() {
        librarian_details details = new librarian_details();
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
