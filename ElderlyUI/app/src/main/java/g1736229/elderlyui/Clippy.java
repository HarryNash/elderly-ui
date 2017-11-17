package g1736229.elderlyui;

import android.util.Log;
import android.widget.TextView;
import java.util.List;
import java.util.Random;

public class Clippy {
    private String overridingMessage;
    private List<String> advice;
    private TextView adviceBox;

    public Clippy(String overridingMessage, List<String> advice, TextView adviceBox) {
        this.overridingMessage = overridingMessage;
        this.advice = advice;
        this.adviceBox = adviceBox;
    }

    public void displayAdvice() {

        if (!(overridingMessage.equals(""))) {
            adviceBox.setText(overridingMessage);
        } else if (advice.size() == 1) {
            adviceBox.setText(advice.get(0));
        } else if (advice.size() > 1) {
            Random rand = new Random();
            int n = rand.nextInt(advice.size() - 1);
            adviceBox.setText(advice.get(n));
        }
    }
}
