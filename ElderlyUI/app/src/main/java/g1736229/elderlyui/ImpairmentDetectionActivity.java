package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ImpairmentDetectionActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT_SIZE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impairment_detection);
    }

    public void smallFont(View view) {
        Intent intent = new Intent(this, FeatureSelection.class);
        intent.putExtra(EXTRA_TEXT_SIZE, "14");
        startActivity(intent);
    }

    public void mediumFont(View view) {
        Intent intent = new Intent(this, FeatureSelection.class);
        intent.putExtra(EXTRA_TEXT_SIZE, "20");
        startActivity(intent);
    }

    public void largeFont(View view) {
        Intent intent = new Intent(this, FeatureSelection.class);
        intent.putExtra(EXTRA_TEXT_SIZE, "30");
        startActivity(intent);
    }

}
