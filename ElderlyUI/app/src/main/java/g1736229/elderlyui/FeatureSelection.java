package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FeatureSelection extends AppCompatActivity {
    String componentSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_selection);

        Intent intent = getIntent();
        componentSize = intent.getStringExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE);

        ComponentResizing.resizeButton(componentSize, findViewById(R.id.gallery), getResources());
        ComponentResizing.resizeButton(componentSize, findViewById(R.id.contacts), getResources());
    }

    public void openGalleryActivity(View v) {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivity(cameraIntent);
    }

    public void openContactsActivity(View v) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE, componentSize);
        startActivity(intent);
    }
}
