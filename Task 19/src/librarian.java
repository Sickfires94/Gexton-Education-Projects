import java.io.Serializable;

public class librarian implements Serializable {
    private String username;
    private String password;

    librarian(String username, String password) {
        this.username = username;
        this.password = password;
    }

    boolean login_success(String username, String password) {
        return this.username.equals(username.toLowerCase().trim())
                && this.password.equals(password.toLowerCase().trim());
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
