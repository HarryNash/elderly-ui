package g1736229.elderlyui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;


public class ImpairmentDetectionActivity extends AppCompatActivity {

    public static final String EXTRA_COMPONENT_SIZE = "g17361229.elderlyui.TEXT_SIZE";
    public static final String HEADING_STYLE = "g17361229.elderlyui.HEADING_STYLE";
    public static final String PRACTICE_MODE = "g17361229.elderlyui.PRACTICE_MODE";
    public int numberOfTimesPressedPracticeModeButton = 0;
    public String practiceModeStatus = "off";
    //BootstrapLabel button = (BootstrapLabel)findViewById(R.id.practiceMode);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impairment_detection);

        ComponentResizing.resizeButton("h6", "small", findViewById(R.id.small), getResources());
        ComponentResizing.resizeButton("h4", "medium", findViewById(R.id.medium), getResources());
        ComponentResizing.resizeButton("h2","large", findViewById(R.id.large), getResources());
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

    public void selectPracticeMode(View v){
        numberOfTimesPressedPracticeModeButton++;
        BootstrapLabel button = (BootstrapLabel)findViewById(R.id.practiceMode);
        if((numberOfTimesPressedPracticeModeButton % 2) == 1){
            practiceModeStatus = "on";
            button.setText("Practice Mode On");
            button.setBootstrapBrand(DefaultBootstrapBrand.INFO);
        }else{
            practiceModeStatus = "off";
            button.setText("Practice Mode Off");
            button.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        }
    }

    public void selectCustomFont(String componentSize, String headingStyle) {
        Intent intent = new Intent(this, FeatureSelectionActivity.class);
        intent.putExtra(EXTRA_COMPONENT_SIZE, componentSize);
        intent.putExtra(HEADING_STYLE, headingStyle);
        intent.putExtra(PRACTICE_MODE, practiceModeStatus);
        startActivity(intent);
    }

}

