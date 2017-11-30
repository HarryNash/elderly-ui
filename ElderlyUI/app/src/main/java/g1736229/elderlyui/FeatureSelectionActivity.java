package g1736229.elderlyui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.camera), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.contacts), getResources());
    }

    public void openGalleryActivity(View v) {
        Intent cameraIntent = new Intent(Intent.ACTION_VIEW,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(cameraIntent, GALLERY_CODE);
    }

    public boolean checkPermissionForCamera(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionForCamera(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            Toast.makeText(this, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, 1234);
        }
    }

    public void openCameraActivity(View v) {
        if (!checkPermissionForCamera())
            requestPermissionForCamera();

        Intent cameraIntent =
                new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1234);
    }

    public void openContactsActivity(View v) {
        Intent intent = new Intent(this, ContactSelectionActivity.class);
        intent.putExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE, componentSize);
        intent.putExtra(ImpairmentDetectionActivity.HEADING_STYLE, headingStyle);
        intent.putExtra(ImpairmentDetectionActivity.PRACTICE_MODE, practiceMode);
        startActivity(intent);
    }
}
