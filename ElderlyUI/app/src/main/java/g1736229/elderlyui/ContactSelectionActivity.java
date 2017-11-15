package g1736229.elderlyui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactSelectionActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "g17361229.elderlyui.MESSAGE";
    public static final String EXTRA_COMPONENT_SIZE = "g17361229.elderlyui.TEXT_SIZE";
    private final int PERMISSIONS_READ_WRITE_CONTACTS = 5; // Code for Contacts Permissions

    private final List<ContactInfo> contactInfos = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private GridView gridView;
    private ProgressBar progressBar;
    private String componentSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_selection);

        Intent intent = getIntent();
        componentSize = intent.getStringExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE);
        this.acquirePermissions();
    }

    // init the views and tell app to load contacts asynchronously
    private void displayContacts() {

        //injects some arbitrary contacts
        //DeviceContacts.initialiseSampleContactData(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        gridView = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this, contactInfos);
        gridView.setAdapter(imageAdapter);

        LoadContactsTask loadContactsTask = new LoadContactsTask(this);
        loadContactsTask.execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position < contactInfos.size())
                    // send contact info corresponding to the selected image to the next activity
                    sendContactInfo(contactInfos.get(position));
                else
                    sendContactInfo(new ContactInfo());
            }
        });
    }

    public List<ContactInfo> getContactInfos() {
        return contactInfos;
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    public GridView getGridView() {
        return gridView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    // TODO: add checks if either read_contacts or write_contacts permissions are granted but not both
    // check if the application has the valid permissions and if not then ask for the user for them
    private void acquirePermissions() {

        boolean granted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                          == PackageManager.PERMISSION_GRANTED;
        granted &= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                   == PackageManager.PERMISSION_GRANTED;

        String[] requestedPermissions = new String[]{Manifest.permission.READ_CONTACTS,
                                                     Manifest.permission.WRITE_CONTACTS};

        if (!granted) {
            ActivityCompat.requestPermissions(this, requestedPermissions, PERMISSIONS_READ_WRITE_CONTACTS);
            return;
        }

        // contacts are displayed if user has given permissions
        displayContacts();
    }

    // check if permissions were granted by the user
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // get the status of our permission request
        switch (requestCode) {
            case PERMISSIONS_READ_WRITE_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // if permission granted, display contacts
                    displayContacts();
                } else {
                    // otherwise display an error message
                    String msg = "Permissions required for application to function";
                    Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // Send selected contact info to next activity
    public void sendContactInfo(ContactInfo contactInfo) {
        Intent intent = new Intent(this, DisplayContactInfoActivity.class);
        intent.putExtra(EXTRA_MESSAGE, contactInfo.createSerialisbleCopy());
        intent.putExtra(EXTRA_COMPONENT_SIZE, componentSize);
        startActivity(intent);
    }


}