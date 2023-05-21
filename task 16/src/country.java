import java.util.ArrayList;

public class country {
    private ArrayList<city> cities;
    private String name;

    public country(String name) {
        this.name = name;
        cities = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    void add_city(city c) {
        cities.add(c);
    }

    ArrayList<city> getCities() {
        return cities;
    }

    city getCity(int index) {
        return cities.get(index);
    }

    void fill_cities() {
    }

    public String toString() {
        return name;
    }
}
