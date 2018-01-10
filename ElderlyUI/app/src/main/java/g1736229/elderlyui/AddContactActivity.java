package g1736229.elderlyui;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapLabel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_COMPONENT_SIZE;

public class AddContactActivity extends AppCompatActivity {

    private String componentSize;
    private String headingStyle;
    private String practiceMode;
    private final int PICK_PHOTO = 1;
    Bitmap photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Intent intent = getIntent();
        componentSize = intent.getStringExtra(EXTRA_COMPONENT_SIZE);
        headingStyle = intent.getStringExtra(ImpairmentDetectionActivity.HEADING_STYLE);
        practiceMode = intent.getStringExtra(ImpairmentDetectionActivity.PRACTICE_MODE);

        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.confirmadd), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.addphoto), getResources());

        // Defining OnClick listener for the photo
        View.OnClickListener addPhotoListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddContactActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        };

        View.OnClickListener addContactListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText contactName = (EditText) findViewById(R.id.contactname);

                EditText contactPhone = (EditText) findViewById(R.id.contactnumber);

                EditText contactEmail = (EditText) findViewById(R.id.contactemail);


                ArrayList<ContentProviderOperation> ops =
                        new ArrayList<ContentProviderOperation>();

                int rawContactID = ops.size();


                if (practiceMode.equals("on")) {
                    Toast.makeText(getApplicationContext(), "This would have created a new contact if practice mode was on.", Toast.LENGTH_LONG).show();
                } else {

                    // insert a new raw contact in the table ContactsContract.RawContacts
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .build());


                    // insert display name in the table ContactsContract.Data
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactName.getText().toString())
                            .build());


                    //insert Mobile Number in the table ContactsContract.Data
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactPhone.getText().toString())
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());

                    // insert Email in the table ContactsContract.Data
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contactEmail.getText().toString())
                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .build());

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if (photo != null) {    // If an image is selected successfully
                        Log.d("ape", "here4");
                        photo.compress(Bitmap.CompressFormat.PNG, 75, stream);

                        // Adding insert operation to operations list
                        // to insert Photo in the table ContactsContract.Data
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                                .withValue(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, stream.toByteArray())
                                .build());

                        try {
                            Log.d("ape", "here3");
                            stream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Log.d("ape", "here2");
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                        Log.d("ape", "here1");
                        Toast.makeText(getBaseContext(), "Contact successfully added", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException | OperationApplicationException e) {
                        e.printStackTrace();
                    }

                }
                finish();

            }


        };

        BootstrapLabel addContactButton = (BootstrapLabel) findViewById(R.id.confirmadd);
        BootstrapLabel addPhotoButton = (BootstrapLabel) findViewById(R.id.addphoto);

        //when "practice mode" is ON, the actions of the user have no real world consequences
        //i.e. the user can explore and navigate through the app but he/she can't add/delete contacts or call people
        //that's why the confirmadd button is disabled when practice mode is on
        //PracticeMode practiceModeObj = new PracticeMode();
        //practiceModeObj.switchOnOff(practiceMode, findViewById(R.id.confirmadd));

        addContactButton.setOnClickListener(addContactListener);
        addPhotoButton.setOnClickListener(addPhotoListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_PHOTO:
                if(resultCode == RESULT_OK){
                    // Getting the uri of the picked photo
                    Uri selectedImage = data.getData();

                    InputStream imageStream = null;
                    try {
                        // Getting InputStream of the selected image
                        assert selectedImage != null;
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // Creating bitmap of the selected image from its inputstream
                    photo = BitmapFactory.decodeStream(imageStream);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_PHOTO);

                } else {

                    // permission denied
                    Toast.makeText(AddContactActivity.this, "Permission denied to access gallery", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
