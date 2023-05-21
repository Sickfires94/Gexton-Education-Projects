import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class admin_details implements Serializable {
    ArrayList<admin> admins;
    static String file_path = "./data/admins.ser";

    admin_details() {
        admins = new ArrayList<>();
    }

    void add_admin_list(admin adm) {
        admins.add(adm);
    }

    static void add_admin(admin adm) {
        admin_details details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((admin_details) input.readObject());
            input.close();

            details.add_admin_list(adm);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_path));
            oos.writeObject(details);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean verify_login(String name, String password) {
        ArrayList<admin> details = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            details = ((admin_details) input.readObject()).admins;
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (admin adm : details) {
            if (adm.login_success(name, password))
                return true;
        }
        return false;
    }

    static void test() {
        admin adm = new admin("admin", "adminpassword");
        add_admin(adm);
    }

    static void create_empty_file() {
        admin_details details = new admin_details();
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
