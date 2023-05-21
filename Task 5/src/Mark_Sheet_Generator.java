import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Mark_Sheet_Generator extends JPanel {
    JFrame window;
    JTable table;
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 400;
    String[][] processed_details;
    String[] data_format = { "Roll no", "Name", "Subjects", "Marks Obtained", "Total Marks", "Percentage", "Grade",
            "Remarks" };

    public Mark_Sheet_Generator(ArrayList<String[]> details) {
        processed_details = new String[details.size()][8];

        window = new JFrame();
        window.setTitle("Mark Sheet");

        process_details(details);

        table = new JTable(processed_details, data_format);
        table.setRowHeight(50);
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);

        window.add(table.getTableHeader(), BorderLayout.PAGE_START);
        window.add(table);
        window.pack();
        window.setVisible(true);
    }

    void process_details(ArrayList<String[]> details) {
        for (int i = 0; i < details.size(); i++) {
            String[] student = (String[]) details.get(i);
            for (int j = 0; j < student.length; j++) {
                processed_details[i][j] = student[j];
            }
            double percent_achieved = Double.parseDouble(student[3]) / Double.parseDouble(student[4]) * 100;

            String grade;
            String remark = "Pass";
            if (percent_achieved >= 80)
                grade = "A+";
            else if (percent_achieved >= 70)
                grade = "A";
            else if (percent_achieved >= 60)
                grade = "B";
            else if (percent_achieved >= 50)
                grade = "C";
            else {
                grade = "F";
                remark = "Fail";
            }

            processed_details[i][5] = percent_achieved + "%";
            processed_details[i][6] = grade;
            processed_details[i][7] = remark;

        }
    }
}