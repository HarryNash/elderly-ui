package g1736229.elderlyui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "g17361229.elderlyui.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<ContactInfo> contactInfos = retrieveContactInfo();
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                sendContactInfo(contactInfos.get(position));
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

    private List<ContactInfo> retrieveContactInfo() {
        List<ContactInfo> contactInfos = new ArrayList<>();

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String phoneNumber =  cursor.getString(phoneNumberIndex);

                contactInfos.add(new ContactInfo(id, name, phoneNumber, null));
            }
        }

        return contactInfos;
    }

    public void sendContactInfo(ContactInfo contactInfo) {
        Intent intent = new Intent(this, DisplayContactInfo.class);
        intent.putExtra(EXTRA_MESSAGE, contactInfo);
        startActivity(intent);
    }
}
