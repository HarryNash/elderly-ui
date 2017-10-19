package g1736229.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "g17361229.elderlyui.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<ContactInfo> contactInfos = Arrays.asList(generateRandomContactInfo(20));
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                sendContactInfo(contactInfos.get(position));
            }
        });
    }

    private ContactInfo[] generateRandomContactInfo(int n) {
        ContactInfo[] contactInfos = new ContactInfo[n];
        RandomInfoGenerator randomInfoGenerator = new RandomInfoGenerator();
        for (int i = 0; i < n; i++) {
           contactInfos[i] = randomInfoGenerator.contactInfo();
        }

        return contactInfos;
    }

    public void sendContactInfo(ContactInfo contactInfo) {
        Intent intent = new Intent(this, DisplayContactInfo.class);
        intent.putExtra(EXTRA_MESSAGE, contactInfo.toString());
        startActivity(intent);
    }
}
