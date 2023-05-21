import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class table extends JFrame {
    private int PANEL_WIDTH = 800,
            PANEL_HEIGHT = 800;

    private final static String ApiUrl = "http://newsapi.org/v2/top-headlines?country=au&apiKey=44259b51932446038c9a3a26075f20b0";

    table() {
        TableCellRenderer tableRenderer;
        JTable table = new JTable(new JTableButtonModel());
        tableRenderer = table.getDefaultRenderer(JButton.class);
        table.setDefaultRenderer(JButton.class, new JTableButtonRenderer(tableRenderer));
        table.setRowHeight(20);
        table.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if (table.getSelectedColumn() == 3) {

                }
            }

            public void focusLost(FocusEvent e) {
            }

        });
        table.addMouseListener(new JTableButtonMouseListener(table));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setSize(PANEL_HEIGHT, PANEL_WIDTH);
        setVisible(true);

    }

    static Object[][] get_data() {
        JSONObject json = null;
        try {
            json = JsonParser.readJsonFromUrl(ApiUrl);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        JSONArray data = json.getJSONArray("articles");
        Object[][] parsed_data = new Object[data.length()][4];
        for (int i = 0; i < data.length(); i++) {
            JSONObject current = data.getJSONObject(i);
            String author = current.optString("author");
            String title = current.optString("title").toString();
            String desc = current.optString("description").toString();
            parsed_data[i][0] = author;
            parsed_data[i][1] = title;
            parsed_data[i][2] = desc;

            JButton openLink = new JButton("Open Article");
            openLink.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        java.awt.Desktop.getDesktop().browse(new URI(current.optString("url")));
                    } catch (JSONException | IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            parsed_data[i][3] = openLink;
        }
        return parsed_data;
    }

    void show_table() {

    }

    public static void main(String[] args) {
        new table().get_data();
    }

    class JTableButtonRenderer implements TableCellRenderer {
        private TableCellRenderer defaultRenderer;

        public JTableButtonRenderer(TableCellRenderer renderer) {
            defaultRenderer = renderer;
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof Component)
                return (Component) value;
            return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    class JTableButtonModel extends AbstractTableModel {
        private final Object[][] rows = get_data();
        private final String[] columns = { "Author", "Title", "Description", "Link" };

        public String getColumnName(int column) {
            return columns[column];
        }

        public int getRowCount() {
            return rows.length;
        }

        public int getColumnCount() {
            return columns.length;
        }

        public Object getValueAt(int row, int column) {
            return rows[row][column];
        }

        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
        }
    }

}

class JTableButtonMouseListener extends MouseAdapter {
    private final JTable table;

    public JTableButtonMouseListener(JTable table) {
        this.table = table;
    }

    public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndexAtX(e.getX());
        int row = e.getY() / table.getRowHeight();

        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
            }
        }
    }
}
