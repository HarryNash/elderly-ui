package g1736229.elderlyui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;



public class ImpairmentDetectionActivity extends AppCompatActivity {

    public static final String EXTRA_COMPONENT_SIZE = "g17361229.elderlyui.TEXT_SIZE";
    public static final String HEADING_STYLE = "g17361229.elderlyui.HEADING_STYLE";
    public static final String PRACTICE_MODE = "g17361229.elderlyui.PRACTICE_MODE";
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impairment_detection);
        ComponentResizing.resizeButton("h6", "small", findViewById(R.id.small), getResources());
        ComponentResizing.resizeButton("h4", "medium", findViewById(R.id.medium), getResources());
        ComponentResizing.resizeButton("h2","large", findViewById(R.id.large), getResources());

        /*BootstrapLabel buttonSmall = (BootstrapLabel) findViewById(R.id.small);
        BootstrapLabel buttonMedium = (BootstrapLabel) findViewById(R.id.medium);
        BootstrapLabel buttonLarge = (BootstrapLabel) findViewById(R.id.large);

        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.impairment_popup,null);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.rl);


        popupWindow = new PopupWindow(container, 400, 400, true);
        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, 500, 500);

        buttonSmall.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
*/
    }

    public void selectSmallComponents(View view) {
        selectCustomFont("small", "h6");
    }

    public void selectMediumComponents(View view) {
        selectCustomFont("medium", "h4");
    }

    public void selectLargeComponents(View view) {
        selectCustomFont("large", "h2");
    }

    public void selectCustomFont(String componentSize, String headingStyle) {
        Intent intent = new Intent(this, FeatureSelectionActivity.class);
        intent.putExtra(EXTRA_COMPONENT_SIZE, componentSize);
        intent.putExtra(HEADING_STYLE, headingStyle);
        intent.putExtra(PRACTICE_MODE, "off"); // Practice mode off by default
        startActivity(intent);
    }

}

