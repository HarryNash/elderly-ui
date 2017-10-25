package g1736229.elderlyui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "g17361229.elderlyui.MESSAGE";
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 5; // just a random number

    private final List<ContactInfo> contactInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.acquirePermissions();
    }

    private void displayContacts() {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, contactInfos));
        this.retrieveContactInfo();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position < contactInfos.size())
                    sendContactInfo(contactInfos.get(position));
                else
                    sendContactInfo(new ContactInfo());
            }
        });
    }

    public List<ContactInfo> getContactInfos() {
        return contactInfos;
    }

    // Acquire READ_CONTACTS Permissions
    private void acquirePermissions() {

        boolean granted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                          == PackageManager.PERMISSION_GRANTED;
        if (!granted) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            return;
        }

        displayContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   displayContacts();
                } else {
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

    // Get Contact Info from phone
    // TODO: put all of this into a cursor loader
    private List<ContactInfo> retrieveContactInfo() {
        contactInfos.clear();

        ContentResolver cr = this.getContentResolver();
        String OrderBy = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                                 null,
                                 null,
                                 null,
                                 OrderBy);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int hasPhoneNumberIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String phoneNumber = null;

                int hasPhoneNumber = cursor.getInt(hasPhoneNumberIndex);
                if (hasPhoneNumber > 0) {
                    Cursor pCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                              null,
                                              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                              null,
                                              null);

                    while (pCursor.moveToNext()) {
                        int phoneNumberIndex = pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNumber = pCursor.getString(phoneNumberIndex);
                    }

                    pCursor.close();
                }

                contactInfos.add(new ContactInfo(id, name, phoneNumber, null));
            }

            cursor.close();
        }

        return contactInfos;
    }

    public void sendContactInfo(ContactInfo contactInfo) {
        Intent intent = new Intent(this, DisplayContactInfo.class);
        intent.putExtra(EXTRA_MESSAGE, contactInfo);
        startActivity(intent);
    }
}
