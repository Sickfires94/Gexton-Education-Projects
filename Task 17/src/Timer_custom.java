import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

public class Timer_custom extends JLabel implements Runnable {
    Integer seconds_left;

    Timer_custom(int seconds_left) {
        this.seconds_left = seconds_left;
        this.setText("" + seconds_left);
    }

    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            this.setText("Time Remaining: " + seconds_left + "s");
            if (seconds_left > 0)
                seconds_left--;

        }
    }
}