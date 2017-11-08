package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ImpairmentDetectionActivity extends AppCompatActivity {
    public static final String EXTRA_COMPONENT_SIZE = "g17361229.elderlyui.TEXT_SIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impairment_detection);
    }

    public void selectSmallComponents(View view) {
        selectCustomFont("small");
    }

    public void selectMediumComponents(View view) {
        selectCustomFont("medium");
    }

    public void selectLargeComponents(View view) {
        selectCustomFont("large");
    }

    public void selectCustomFont(String componentSize) {
        Intent intent = new Intent(this, FeatureSelection.class);
        intent.putExtra(EXTRA_COMPONENT_SIZE, componentSize);
        startActivity(intent);
    }
}
