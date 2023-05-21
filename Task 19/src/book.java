import java.io.Serializable;

public class book implements Serializable {
    private String name;
    private int serial_number;
    private boolean issued;

    book(String name, int serial_number, boolean issued) {
        this.name = name;
        this.serial_number = serial_number;
        this.issued = issued;
    }

    String getName() {
        return name;
    }

    int getSerialNumber() {
        return serial_number;
    }

    boolean getIssued() {
        return issued;
    }
}
