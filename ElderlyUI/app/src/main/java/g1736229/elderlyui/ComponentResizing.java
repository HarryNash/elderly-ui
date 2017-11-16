package g1736229.elderlyui;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapLabel;

public class ComponentResizing {
    public static void resizeButton(String headingStyle, String componentSize, View view, Resources resources) {
        BootstrapLabel button = (BootstrapLabel) view;

        int factor = adjectiveToNumber(componentSize);
        Headings headingsObj = new Headings();

        int widthSize = factor * 8;
        int heightSize = factor * 6;

        final float scale = resources.getDisplayMetrics().density;
        int heightDp = (int) (heightSize * scale + 0.5f);
        int widthDp = (int) (widthSize * scale + 0.5f);

        button.setBootstrapHeading(headingsObj.getCorrespondingHeadingClass(headingStyle));
        ViewGroup.LayoutParams params = button.getLayoutParams();
        params.height = heightDp;
        params.width = widthDp;
        button.setLayoutParams(params);
        button.requestLayout();
    }

    public static int adjectiveToNumber(String adjective) {
        int number = 100;
        switch (adjective) {
            case "small":
                number = 10;
                break;
            case "medium":
                number = 20;
                break;
            case "large":
                number = 30;
                break;
        }
        return number;
    }
}
