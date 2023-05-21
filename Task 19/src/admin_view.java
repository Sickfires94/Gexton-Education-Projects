import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class admin_view extends JPanel {
    int WINDOW_HEIGHT = 800,
            WINDOW_WIDTH = 800;
    String[] headings = { "Username", "Password" };
    String file_path = "./data/librarians.ser";

    JPanel panel;
    JTable table;
    JScrollPane scroll;

    admin_view() {
        super();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(null);

        show_details();
        add_buttons();
    }

    void show_details() {
        String[][] processed_data = null;
        processed_data = librarian_details.get_librarians();

        table = new JTable(processed_data, headings);
        table.setBounds(50, 50, 800, 800);
        table.setRowHeight(50);
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        scroll = new JScrollPane(table);
        scroll.setBounds(100, 350, 620, 350);
        add(scroll);

    }

    void add_buttons() {
        JTextField name_field = new JTextField();
        name_field.setBounds(300, 150, 200, 50);
        add(name_field);
        JLabel name_prompt = new JLabel("Name: ");
        name_prompt.setBounds(200, 150, 100, 50);
        add(name_prompt);

        JTextField password_field = new JTextField();
        password_field.setBounds(300, 200, 200, 50);
        add(password_field);
        JLabel password_prompt = new JLabel("Password: ");
        password_prompt.setBounds(200, 200, 100, 50);
        add(password_prompt);

        JButton delete_lib = new JButton("Delete Selected Librarian");
        delete_lib.setBounds(50, 270, 200, 50);
        add(delete_lib);

        delete_lib.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                librarian_details.delete_librarian(table.getSelectedRow());
                remove(scroll);
                show_details();
            }
        });

        JButton add_lib = new JButton("Add Librarian");
        add_lib.setBounds(300, 270, 200, 50);
        add(add_lib);

        add_lib.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                librarian lib = new librarian(name_field.getText(), password_field.getText());
                librarian_details.add_librarian(lib);
                remove(scroll);
                show_details();
                repaint();

            }
        });

        JButton edit_lib = new JButton("Edit Selected Librarian");
        edit_lib.setBounds(550, 270, 200, 50);
        add(edit_lib);

        edit_lib.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                librarian temp_lib = librarian_details.get_librarian(table.getSelectedRow());
                String name = temp_lib.getUsername();
                String password = "" + temp_lib.getPassword();
                if (!name_field.getText().equals(""))
                    name = name_field.getText();
                if (!password_field.getText().equals(""))
                    password = password_field.getText();
                librarian lib = new librarian(name, password);
                librarian_details.edit_librarian(table.getSelectedRow(), lib);
                remove(scroll);
                show_details();

            }
        });

    }

    static void test() {
        JFrame window = new JFrame();
        window.setSize(800, 800);
        window.add(new admin_view());
        window.setVisible(true);
    }

    public static void main(String[] args) {
        test();
    }

}