package g1736229.elderlyui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;

public class FeatureSelectionActivity extends AppCompatActivity {
    String componentSize;
    final int GALLERY_CODE = 67;
    String headingStyle = null;
    String practiceMode = "off";
    boolean isPracticeModeOn = false;
    MediaPlayer tutorial01;
    MediaPlayer tutorial02;
    MediaPlayer tutorial03;
    MediaPlayer tutorial04;
    MediaPlayer tutorial05;
    MediaPlayer tutorial06;
    MediaPlayer tutorial07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_selection);

        Intent intent = getIntent();
        componentSize = intent.getStringExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE);
        headingStyle = intent.getStringExtra(ImpairmentDetectionActivity.HEADING_STYLE);
        practiceMode = intent.getStringExtra(ImpairmentDetectionActivity.PRACTICE_MODE);
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.phone), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.contacts), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.camera), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.gallery), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.practice), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.tutorial), getResources());
        //ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.addcontacts), getResources());

        // Set up Media Players so tutorial can be played
        tutorial01 = MediaPlayer.create(this, R.raw.tutorial_01);
        tutorial02 = MediaPlayer.create(this, R.raw.tutorial_02);
        tutorial03 = MediaPlayer.create(this, R.raw.tutorial_03);
        tutorial04 = MediaPlayer.create(this, R.raw.tutorial_04);
        tutorial05 = MediaPlayer.create(this, R.raw.tutorial_05);
        tutorial06 = MediaPlayer.create(this, R.raw.tutorial_06);
        tutorial07 = MediaPlayer.create(this, R.raw.tutorial_07);
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
//    moved to ContactOptionsActivity class
//    public void openContactsActivity(View v) {
//        Intent intent = new Intent(this, ContactSelectionActivity.class);
//        intent.putExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE, componentSize);
//        intent.putExtra(ImpairmentDetectionActivity.HEADING_STYLE, headingStyle);
//        startActivity(intent);
//    }


    public void openContactOptionsActivity(View view) {
        Intent intent = new Intent(this, ContactOptionsActivity.class);
        intent.putExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE, componentSize);
        intent.putExtra(ImpairmentDetectionActivity.HEADING_STYLE, headingStyle);
        intent.putExtra(ImpairmentDetectionActivity.PRACTICE_MODE, practiceMode);
        startActivity(intent);
    }

    public void selectPracticeMode(View view){
        BootstrapLabel button = (BootstrapLabel) findViewById(R.id.practice);

        isPracticeModeOn = !isPracticeModeOn;

        if (isPracticeModeOn) {
            button.setText("Practice Mode On");
            practiceMode = "on";
            button.setBootstrapBrand(DefaultBootstrapBrand.INFO);
        } else {
            button.setText("Practice Mode Off");
            practiceMode = "off";
            button.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
        }
    }

    public void playTutorial(final View view) {
        stopAllMediaPlayers();

        tutorial01.start();
        tutorial01.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                               @Override
                                               public void onCompletion(MediaPlayer mp) {
                                                   BootstrapLabel selectedButton = (BootstrapLabel) findViewById(R.id.phone);
                                                   selectedButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                                                   playTutorial2(view);
                                               }
                                           });

        //selectedButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
    }

    public void playTutorial2(final View view) {
        tutorial02.start();
        tutorial02.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                BootstrapLabel selectedButton = (BootstrapLabel) findViewById(R.id.phone);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                selectedButton = (BootstrapLabel) findViewById(R.id.contacts);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                playTutorial3(view);
            }
        });
    }

    public void playTutorial3(final View view) {
        tutorial03.start();
        tutorial03.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                BootstrapLabel selectedButton = (BootstrapLabel) findViewById(R.id.contacts);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                selectedButton = (BootstrapLabel) findViewById(R.id.camera);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                playTutorial4(view);
            }
        });
    }

    public void playTutorial4(final View view) {
        tutorial04.start();
        tutorial04.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                BootstrapLabel selectedButton = (BootstrapLabel) findViewById(R.id.camera);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                selectedButton = (BootstrapLabel) findViewById(R.id.gallery);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                playTutorial5(view);
            }
        });
    }

    public void playTutorial5(final View view) {
        tutorial05.start();
        tutorial05.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                BootstrapLabel selectedButton = (BootstrapLabel) findViewById(R.id.gallery);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                selectedButton = (BootstrapLabel) findViewById(R.id.practice);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                playTutorial6(view);
            }
        });
    }

    public void playTutorial6(final View view) {
        tutorial06.start();
        tutorial06.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                BootstrapLabel selectedButton = (BootstrapLabel) findViewById(R.id.practice);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                selectedButton = (BootstrapLabel) findViewById(R.id.tutorial);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                playTutorial7(view);
            }
        });
    }

    public void playTutorial7(final View view) {
        tutorial07.start();
        tutorial07.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                BootstrapLabel selectedButton = (BootstrapLabel) findViewById(R.id.tutorial);
                selectedButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAllMediaPlayers();
    }

    private void stopAllMediaPlayers() {
        MediaPlayer[] tutorialPlayers = {tutorial01, tutorial02, tutorial03, tutorial04, tutorial05, tutorial06, tutorial07};
        for (MediaPlayer tutorial : tutorialPlayers) {
            if (tutorial.isPlaying()) {
                tutorial.stop();
            }
        }
    }
}
