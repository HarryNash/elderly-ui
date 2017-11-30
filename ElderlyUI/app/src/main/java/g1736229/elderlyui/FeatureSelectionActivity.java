package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FeatureSelectionActivity extends AppCompatActivity {
    String componentSize;
    final int GALLERY_CODE = 67;
    String headingStyle = null;
    String practiceMode = "off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_selection);

        Intent intent = getIntent();
        componentSize = intent.getStringExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE);
        headingStyle = intent.getStringExtra(ImpairmentDetectionActivity.HEADING_STYLE);
        practiceMode = intent.getStringExtra(ImpairmentDetectionActivity.PRACTICE_MODE);
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.gallery), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.contacts), getResources());
    }

    public void openGalleryActivity(View v) {
        Intent cameraIntent = new Intent(Intent.ACTION_VIEW,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(cameraIntent, GALLERY_CODE);
    }

    public void openContactsActivity(View v) {
        Intent intent = new Intent(this, ContactSelectionActivity.class);
        intent.putExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE, componentSize);
        intent.putExtra(ImpairmentDetectionActivity.HEADING_STYLE, headingStyle);
        intent.putExtra(ImpairmentDetectionActivity.PRACTICE_MODE, practiceMode);
        startActivity(intent);
    }
}
