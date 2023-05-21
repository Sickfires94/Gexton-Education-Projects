import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Tour_Guide {

    private int WINDOW_WIDTH = 800,
            WINDOW_HEIGHT = 800;
    private String[] PLACES = { "Hospitals", "Schools", "Gas Stations", "Parks", "Offices" };

    private JFrame window;
    private ArrayList<country> countries;
    private JComboBox<city> Cities;
    private JComboBox<place> Places;

    public Tour_Guide() {
        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setResizable(false);
        window.setLayout(null);
        window.setVisible(true);

        splash_screen();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }

        fill_countries();
        menu();
    }

    private void splash_screen() {
        ImageIcon imageIcon = new ImageIcon("./assets/splash_img.png");
        Image newimg = imageIcon.getImage().getScaledInstance(800, 800,
                java.awt.Image.SCALE_SMOOTH);
        JLabel splash_img = new JLabel(new ImageIcon(newimg), SwingConstants.CENTER);
        splash_img.setBounds(0, 0, 800, 800);
        window.add(splash_img);
        window.repaint();

    }

    private void menu() {
        window.getContentPane().removeAll();

        JComboBox<country> Countries = new JComboBox<>();
        Countries.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                Cities.removeAllItems();
                for (city City : ((country) Countries.getSelectedItem()).getCities()) {
                    Cities.addItem(City);
                }
            }
        });

        Cities = new JComboBox<>();
        Cities.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                Places.removeAllItems();
                for (place Place : ((city) Cities.getSelectedItem()).getPlaces()) {
                    Places.addItem(Place);
                }
            }
        });
        Places = new JComboBox<>();
        Places.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                window.getContentPane().removeAll();
                window.add(Countries);
                window.add(Cities);
                window.add(Places);
                int start_x = 10;
                int current_x = 10, current_y = 100;
                int x_gap = 210, y_gap = 150;
                for (subplace Subplace : ((place) Places.getSelectedItem()).getsubplaces()) {
                    JLabel img = new JLabel(Subplace.getImageicon());
                    img.setBounds(current_x, current_y, 200, 100);
                    window.add(img);

                    JLabel name = new JLabel(Subplace.getName(), SwingConstants.CENTER);
                    name.setBounds(current_x, current_y + 110, 200, 30);
                    window.add(name);

                    current_x += x_gap;

                    if (current_x + x_gap >= WINDOW_WIDTH) {
                        current_x = start_x;
                        current_y += y_gap;
                    }

                }
                window.repaint();

            }
        });
        ;

        for (country Country : countries) {
            Countries.addItem(Country);
        }

        Countries.setBounds(10, 10, 200, 30);
        Cities.setBounds(220, 10, 200, 30);
        Places.setBounds(440, 10, 200, 30);

        window.add(Countries, BorderLayout.CENTER);
        window.add(Cities, BorderLayout.CENTER);
        window.add(Places, BorderLayout.CENTER);

        window.repaint();
    }

    private void fill_countries() {
        countries = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            country Country = new country("Country " + i);
            for (int j = 1; j <= 5; j++) {
                city City = new city("City " + i + j);
                for (String place : PLACES) {
                    place Place = new place(place);
                    for (int k = 1; k < 6; k++) {
                        subplace Subplace = new subplace(place + i + j + k, "./assets/" + place + ".jpg");
                        Place.add_subplace(Subplace);
                    }

                    City.add_place(Place);
                }
                Country.add_city(City);
            }
            countries.add(Country);
        }
    }
}
