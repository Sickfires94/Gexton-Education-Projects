import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class UserData implements Serializable {
    ArrayList<Object[]> data;

    UserData() {
        data = new ArrayList<>();
    }

    void add_user(Object[] user) {
        data.add(user);
    }

    void generate_file() throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"));
        oos.writeObject(this);
        oos.close();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        UserData a = new UserData();
        a.generate_file();
    }
}
