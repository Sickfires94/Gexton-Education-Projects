import java.util.ArrayList;

public class place {
    private ArrayList<subplace> subplaces;
    private String name;

    public place(String name) {
        this.name = name;
        subplaces = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    void add_subplace(subplace s) {
        subplaces.add(s);
    }

    ArrayList<subplace> getsubplaces() {
        return subplaces;
    }

    subplace getsubplace(int index) {
        return subplaces.get(index);
    }

    void fill_places() {
    }

    public String toString() {
        return name;
    }
}