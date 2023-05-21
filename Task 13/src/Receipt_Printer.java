import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Receipt_Printer {

    Font header_font = new Font("arial", Font.BOLD, 20);
    int WINDOW_HEIGHT = 400, WINDOW_WIDTH = 400;
    String[] item_list = { "Furniture", "Groceries", "Kitchen Appliances", "Computer Parts", "Car Parts",
            "Fragile Goods" };

    JFrame main_window;
    JFrame preview_window;
    int selected_trucks;
    String selected_type, selected_petrol_cost, selected_driver_cost, selected_distance;

    Receipt_Printer() {
        main_window = new JFrame();
        main_window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        main_window.setResizable(false);

        JLabel trucks_prompt = new JLabel("Number of Trucks used: ");
        trucks_prompt.setBounds(10, 10, 180, 20);
        main_window.add(trucks_prompt);
        JComboBox<Integer> trucks = new JComboBox<Integer>();
        for (int i = 1; i <= 20; i++)
            trucks.addItem(i);
        trucks.setBounds(200, 10, 180, 20);
        main_window.add(trucks);

        JLabel items_prompt = new JLabel("Type of Goods Transported: ");
        items_prompt.setBounds(10, 40, 180, 20);
        main_window.add(items_prompt);
        JComboBox<String> item = new JComboBox<String>(item_list);
        item.setBounds(200, 40, 180, 20);
        main_window.add(item);

        JLabel petrol_cost_prompt = new JLabel("Cost of Petrol per Kilometer: ");
        petrol_cost_prompt.setBounds(10, 70, 180, 20);
        main_window.add(petrol_cost_prompt);
        JTextField petrol_cost = new JTextField();
        petrol_cost.setBounds(200, 70, 180, 20);
        main_window.add(petrol_cost);

        JLabel distance_travelled_prompt = new JLabel("Total Distance Travelled: ");
        distance_travelled_prompt.setBounds(10, 100, 180, 20);
        main_window.add(distance_travelled_prompt);
        JTextField distance_travelled = new JTextField();
        distance_travelled.setBounds(200, 100, 180, 20);
        main_window.add(distance_travelled);

        JLabel driver_cost_prompt = new JLabel("Driver Charges: ");
        driver_cost_prompt.setBounds(10, 130, 180, 20);
        main_window.add(driver_cost_prompt);
        JTextField driver_cost = new JTextField();
        driver_cost.setBounds(200, 130, 180, 20);
        main_window.add(driver_cost);

        JLabel total_cost_prompt = new JLabel("Total Cost: ");
        total_cost_prompt.setBounds(10, 160, 180, 20);
        main_window.add(total_cost_prompt);
        JLabel total_cost = new JLabel();
        total_cost.setBounds(200, 160, 180, 20);
        main_window.add(total_cost);

        JButton calc_cost = new JButton("Calculate Cost");
        calc_cost.setBounds(100, 190, 180, 20);
        main_window.add(calc_cost);

        calc_cost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int cost = (int) trucks.getSelectedItem() * (Integer.parseInt(driver_cost.getText())
                        + Integer.parseInt(distance_travelled.getText()) * Integer.parseInt(petrol_cost.getText()));
                total_cost.setText("" + cost);
            }
        });

        JButton receipt = new JButton("Preview Receipt");
        receipt.setBounds(100, 230, 180, 40);
        main_window.add(receipt);
        receipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                selected_distance = distance_travelled.getText();
                selected_driver_cost = driver_cost.getText();
                selected_trucks = (int) trucks.getSelectedItem();
                selected_petrol_cost = petrol_cost.getText();
                selected_type = (String) item.getSelectedItem();
                preview_receipt(true);

            }
        });

        JButton print = new JButton("Print Receipt");
        print.setBounds(100, 280, 180, 40);
        main_window.add(print);
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected_distance = distance_travelled.getText();
                selected_driver_cost = driver_cost.getText();
                selected_trucks = (int) trucks.getSelectedItem();
                selected_petrol_cost = petrol_cost.getText();
                selected_type = (String) item.getSelectedItem();
                try {
                    print();
                } catch (PrinterException e1) {
                    e1.printStackTrace();
                }
            }
        });

        main_window.setLayout(null);
        main_window.setVisible(true);
    }

    void preview_receipt(boolean show) {
        if (preview_window == null)
            preview_window = new JFrame();
        preview_window.getContentPane().removeAll();
        preview_window.setVisible(show);
        preview_window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        preview_window.setResizable(false);
        preview_window.getContentPane().setBackground(Color.WHITE);

        JLabel header = new JLabel("Total Bill Reciept");
        header.setBounds(100, 10, 180, 30);
        header.setFont(header_font);
        preview_window.add(header);

        JLabel trucks_prompt = new JLabel("Number of Trucks used           :");
        trucks_prompt.setBounds(10, 60, 180, 20);
        preview_window.add(trucks_prompt);
        JLabel truck = new JLabel("" + selected_trucks);
        truck.setBounds(200, 60, 180, 20);
        preview_window.add(truck);

        JLabel items_prompt = new JLabel("Type of Goods Transported    : ");
        items_prompt.setBounds(10, 90, 180, 20);
        preview_window.add(items_prompt);
        JLabel item = new JLabel(selected_type);
        item.setBounds(200, 90, 180, 20);
        preview_window.add(item);

        JLabel petrol_cost_prompt = new JLabel("Cost of Petrol per Kilometer   : ");
        petrol_cost_prompt.setBounds(10, 120, 180, 20);
        preview_window.add(petrol_cost_prompt);
        JLabel petrol_cost = new JLabel(selected_petrol_cost);
        petrol_cost.setBounds(200, 120, 180, 20);
        preview_window.add(petrol_cost);

        JLabel distance_travelled_prompt = new JLabel("Total Distance Travelled          : ");
        distance_travelled_prompt.setBounds(10, 150, 180, 20);
        preview_window.add(distance_travelled_prompt);
        JLabel distance_travelled = new JLabel(selected_distance);
        distance_travelled.setBounds(200, 150, 180, 20);
        preview_window.add(distance_travelled);

        JLabel driver_cost_prompt = new JLabel("Driver Charges                           : ");
        driver_cost_prompt.setBounds(10, 180, 180, 20);
        preview_window.add(driver_cost_prompt);
        JLabel driver_cost = new JLabel("" + selected_driver_cost);
        driver_cost.setBounds(200, 180, 180, 20);
        preview_window.add(driver_cost);

        JLabel total_cost_prompt = new JLabel("Total Cost                                    : ");
        total_cost_prompt.setBounds(10, 210, 180, 20);
        preview_window.add(total_cost_prompt);
        int cost = selected_trucks * (Integer.parseInt(selected_driver_cost)
                + Integer.parseInt(selected_distance) * Integer.parseInt(selected_petrol_cost));
        JLabel total_cost = new JLabel("" + cost);
        total_cost.setBounds(200, 210, 180, 20);
        preview_window.add(total_cost);

        JLabel signature = new JLabel("Signature: ________________");
        signature.setBounds(180, 260, 180, 20);
        preview_window.add(signature);

        preview_window.setLayout(null);
        preview_window.repaint();
    }

    void print() throws PrinterException {
        preview_receipt(false);
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat preformat = pjob.defaultPage();
        preformat.setOrientation(PageFormat.LANDSCAPE);
        PageFormat postformat = pjob.pageDialog(preformat);
        // If user does not hit cancel then print.
        if (preformat != postformat) {
            // Set print component
            pjob.setPrintable(new printer(preview_window.getContentPane()), postformat);
            if (pjob.printDialog()) {
                pjob.print();
            }
        }
    }
}
