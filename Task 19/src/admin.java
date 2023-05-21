import java.io.Serializable;

public class admin implements Serializable {
    private String username;
    private String password;

    admin(String username, String password) {
        this.username = username.toLowerCase().trim();
        this.password = password.toLowerCase().trim();
    }

    boolean login_success(String username, String password) {
        return this.username.equals(username.toLowerCase().trim())
                && this.password.equals(password.toLowerCase().trim());
    }
}
