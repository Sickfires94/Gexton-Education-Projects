import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

public class Timer extends JLabel implements Runnable {
    Integer minutes_left;

    Timer(int minutes_left) {
        this.minutes_left = minutes_left;
        this.setText(minutes_left + " minutes left");
    }

    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            this.setText(minutes_left + " minutes left");
            if (minutes_left > 0)
                minutes_left--;

        }
    }
}