package g1736229.elderlyui;

import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapLabel;

/**
 * Created by Evche on 30/11/2017.
 */

public class PracticeMode {
    public void switchOnOff(String practiceMode, View view){
        BootstrapLabel button = (BootstrapLabel) view;
        button.setEnabled(practiceMode.equals("off"));
    }
}
