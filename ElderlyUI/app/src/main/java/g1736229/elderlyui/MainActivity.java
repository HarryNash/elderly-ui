package g1736229.elderlyui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "g17361229.elderlyui.MESSAGE";
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 5; // just a random number

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.acquirePermissions();

        //injects some arbitrary contacts
        initialiseSampleContactData();

        //final List<ContactInfo> contactInfos = generateRandomContactInfo(0);
        final List<ContactInfo> contactInfos = retrieveContactInfo();
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, contactInfos));

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

    private List<ContactInfo> generateRandomContactInfo(int n) {
        List<ContactInfo> contactInfos = new ArrayList<>();
        RandomInfoGenerator randomInfoGenerator = new RandomInfoGenerator();
        for (int i = 0; i < n; i++) {
            contactInfos.add(randomInfoGenerator.contactInfo());
        }

        return contactInfos;
    }

    // Acquire READ_CONTACTS Permissions
    private void acquirePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    // Get Contact Info from phone
    // TODO: put all of this into a cursor loader
    private List<ContactInfo> retrieveContactInfo() {
        List<ContactInfo> contactInfos = new ArrayList<>();

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

    //called when user tabs on the "call" button
    public void callPerson(View view){
        //redirect to a new page
    }


    //called when user tabs on the "email" button
    public void emailPerson(View view){
        //redirect to a new page
    }


    //called when user tabs on the "message" button
    public void messagePerson(View view){
        //redirect to a new page
    }


    //called when user tabs on the "video call" button
    public void videoCallPerson(View view){
        //redirect to a new page
    }


    private void initialiseSampleContactData() {
        ArrayList<ContactInfo> contactInfoArray = new ArrayList<ContactInfo>();

        contactInfoArray.add(new ContactInfo("001", "Aleksandar", "02079460498", "aleks@abc.com"));
        contactInfoArray.add(new ContactInfo("002", "Maria", "02079461235", "maria@abc.com"));
        contactInfoArray.add(new ContactInfo("003", "Tatiana", "02079423456", "tatiana@abc.com"));
        contactInfoArray.add(new ContactInfo("004", "Borislav", "02079456789", "borislav@abc.com"));
        contactInfoArray.add(new ContactInfo("005", "Silvana", "02079402364", "silvana@abc.com"));
        contactInfoArray.add(new ContactInfo("006", "Krasimira", "02079445623", "krasimira@abc.com"));
        contactInfoArray.add(new ContactInfo("007", "Richard", "02079411447", "richard@abc.com"));
        contactInfoArray.add(new ContactInfo("008", "Tom", "02079425896", "tom@abc.com"));

        InitialiseSampleContactData initialiseSampleContactData =
                new InitialiseSampleContactData();
        for (ContactInfo contact : contactInfoArray){
            initialiseSampleContactData.addContactData(this, contact);
        }
    }
}
