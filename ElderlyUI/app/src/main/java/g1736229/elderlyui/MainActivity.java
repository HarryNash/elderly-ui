package g1736229.elderlyui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "g17361229.elderlyui.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //private function - fakes the contacts
        initialiseSampleContactData();

        //private function - pulls the fake contacts


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                sendContactInfo(v);
            }
        });
    }

    private void initialiseSampleContactData() {
        ArrayList<ContactInfo> contactInfoArray = new ArrayList<ContactInfo>();

        contactInfoArray.add(new ContactInfo("Aleksandar", "02079460498", "aleks@abc.com"));
        contactInfoArray.add(new ContactInfo("Maria", "02079461235", "maria@abc.com"));
        contactInfoArray.add(new ContactInfo("Tatiana", "02079423456", "tatiana@abc.com"));
        contactInfoArray.add(new ContactInfo("Borislav", "02079456789", "borislav@abc.com"));
        contactInfoArray.add(new ContactInfo("Silvana", "02079402364", "silvana@abc.com"));
        contactInfoArray.add(new ContactInfo("Krasimira", "02079445623", "krasimira@abc.com"));
        contactInfoArray.add(new ContactInfo("Richard", "02079411447", "richard@abc.com"));
        contactInfoArray.add(new ContactInfo("Tom", "02079425896", "tom@abc.com"));

        InitialiseSampleContactData initialiseSampleContactData =
                new InitialiseSampleContactData();
        for (ContactInfo contact : contactInfoArray){
            initialiseSampleContactData.addContactData(this, contact);
        }
    }

    public void sendContactInfo(View view) {
        Intent intent = new Intent(this, DisplayContactInfo.class);
        ContactImageView contactImageView = (ContactImageView) view;
        ContactInfo contactInfo = contactImageView.getContactInfo();
        intent.putExtra(EXTRA_MESSAGE, contactInfo.toString());
        startActivity(intent);
    }
}
