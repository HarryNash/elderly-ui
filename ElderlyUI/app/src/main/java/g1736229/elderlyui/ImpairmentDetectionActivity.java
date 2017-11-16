package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class ImpairmentDetectionActivity extends AppCompatActivity {

    public static final String EXTRA_COMPONENT_SIZE = "g17361229.elderlyui.TEXT_SIZE";
    public static final String HEADING_STYLE = "g17361229.elderlyui.HEADING_STYLE";


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

    public void selectCustomFont(String componentSize, String headingStyle) {
        Intent intent = new Intent(this, FeatureSelectionActivity.class);
        intent.putExtra(EXTRA_COMPONENT_SIZE, componentSize);
        intent.putExtra(HEADING_STYLE, headingStyle);
        startActivity(intent);
    }

}

