import java.util.ArrayList;

public class city {
    private ArrayList<place> places;
    private String name;

    public city(String name) {
        this.name = name;
        places = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    void add_place(place p) {
        places.add(p);
    }

    ArrayList<place> getPlaces() {
        return places;
    }

    place getPlace(int index) {
        return places.get(index);
    }

    void fill_places() {
    }

    public String toString() {
        return name;
    }
}
