import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class librarian_view extends JPanel {
    int WINDOW_HEIGHT = 800,
            WINDOW_WIDTH = 800;
    String[] headings = { "Book", "Serial Number", "Issued" };
    String file_path = "./data/books.ser";

    JPanel panel;
    JTable table;
    JScrollPane scroll;

    librarian_view() {
        super();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(null);

        show_details();
        add_buttons();
    }

    void show_details() {
        String[][] processed_data = null;

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file_path));
            processed_data = book_details.get_books();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable(processed_data, headings);
        table.setBounds(50, 50, 800, 800);
        table.setRowHeight(50);
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);

        scroll = new JScrollPane(table);
        scroll.setBounds(100, 400, 620, 350);
        add(scroll);

    }

    void add_buttons() {
        JTextField name_field = new JTextField();
        name_field.setBounds(300, 150, 200, 50);
        add(name_field);
        JLabel name_prompt = new JLabel("Name: ");
        name_prompt.setBounds(200, 150, 100, 50);
        add(name_prompt);

        JTextField serial_field = new JTextField();
        serial_field.setBounds(300, 200, 200, 50);
        add(serial_field);
        JLabel serial_prompt = new JLabel("Serial Number: ");
        serial_prompt.setBounds(200, 200, 100, 50);
        add(serial_prompt);

        JLabel issued_prompt = new JLabel("Issued? ");
        issued_prompt.setBounds(350, 250, 50, 50);
        add(issued_prompt);
        JCheckBox issued_box = new JCheckBox();
        issued_box.setBounds(400, 250, 100, 50);
        add(issued_box);

        JButton delete_book = new JButton("Delete Selected Book");
        delete_book.setBounds(50, 300, 200, 50);
        add(delete_book);

        delete_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                book_details.delete_book(table.getSelectedRow());
                remove(scroll);
                show_details();
            }
        });

        JButton add_book = new JButton("Add book");
        add_book.setBounds(300, 300, 200, 50);
        add(add_book);

        add_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                book bk = new book(name_field.getText(), Integer.parseInt(serial_field.getText()),
                        issued_box.isSelected());
                book_details.add_book(bk);
                remove(scroll);
                show_details();

            }
        });

        JButton edit_book = new JButton("Edit Selected book");
        edit_book.setBounds(550, 300, 200, 50);
        add(edit_book);

        edit_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                book temp_bk = book_details.get_book(table.getSelectedRow());
                String name = temp_bk.getName();
                String serial = "" + temp_bk.getSerialNumber();
                if (!name_field.getText().equals(""))
                    name = name_field.getText();
                if (!serial_field.getText().equals(""))
                    serial = serial_field.getText();
                book bk = new book(name, Integer.parseInt(serial),
                        issued_box.isSelected());
                book_details.edit_book(table.getSelectedRow(), bk);
                remove(scroll);
                show_details();

            }
        });

    }

    static void test() {
        JFrame window = new JFrame();
        window.setSize(800, 800);
        window.add(new librarian_view());
        window.setVisible(true);
    }

    public static void main(String[] args) {
        test();
    }

}