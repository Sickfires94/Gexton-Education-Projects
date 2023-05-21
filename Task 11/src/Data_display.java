
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Data_display extends JPanel {
    JFrame window;
    JTable table;
    int WINDOW_WIDTH = 1000;
    int WINDOW_HEIGHT = 400;
    Object[][] processed_details;
    Object[] data_format = { "Name", "Gender", "Date of Birth", "Email", "Password", "Picture" };

    public Data_display(ArrayList<Object[]> details) {
        processed_details = new Object[details.size()][data_format.length];

        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setTitle("Details");

        process_details(details);
        DefaultTableModel model = new DefaultTableModel(processed_details, data_format);

        JTable table = new JTable(model) {
            public Class getColumnClass(int column) {
                return (column == 5) ? Icon.class : Object.class;
            }
        };
        table.setRowHeight(100);
        // table.setDefaultEditor(Object.class, null);

        /**
         * DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
         * centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
         * 
         * table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
         * table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
         * table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
         * table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
         * table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
         * table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
         * 
         * table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         * 
         * table.getColumnModel().getColumn(0).setPreferredWidth(200);
         * table.getColumnModel().getColumn(1).setPreferredWidth(100);
         * table.getColumnModel().getColumn(2).setPreferredWidth(100);
         * table.getColumnModel().getColumn(3).setPreferredWidth(200);
         * table.getColumnModel().getColumn(4).setPreferredWidth(200);
         * table.getColumnModel().getColumn(5).setPreferredWidth(200);
         */

        window.add(table.getTableHeader(), BorderLayout.PAGE_START);

        JScrollPane scrollPane = new JScrollPane(table);
        window.getContentPane().add(scrollPane);
        // window.add(table);
        // window.pack();
        window.setVisible(true);
    }

    void process_details(ArrayList<Object[]> details) {
        for (int i = 0; i < details.size(); i++) {
            Object[] user = (Object[]) details.get(i);
            for (int j = 0; j < user.length; j++) {
                processed_details[i][j] = user[j];
            }
        }
    }
}