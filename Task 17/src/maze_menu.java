import javax.swing.JFrame;

public class maze_menu {
    private int WINDOW_WIDTH = 810,
            WINDOW_HEIGHT = 840;

    private JFrame window;

    maze_menu() {
        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setResizable(false);
        window.setLayout(null);
        maze_game game_panel = new maze_game();
        game_panel.setBounds(0, 0, 850, 850);

        window.add(game_panel);
        window.setVisible(true);
    }
}