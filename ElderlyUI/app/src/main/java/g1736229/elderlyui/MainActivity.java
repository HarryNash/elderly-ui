package g1736229.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "g17361229.elderlyui.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public void sendContactInfo(View view) {
        Intent intent = new Intent(this, DisplayContactInfo.class);
        ContactImageView contactImageView = (ContactImageView) view;
        ContactInfo contactInfo = contactImageView.getContactInfo();
        intent.putExtra(EXTRA_MESSAGE, contactInfo.toString());
        startActivity(intent);
    }
}
