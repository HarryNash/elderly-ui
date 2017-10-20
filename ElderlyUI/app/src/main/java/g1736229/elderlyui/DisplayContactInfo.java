package g1736229.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact_info);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        ContactInfo contactInfo = (ContactInfo) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);



        // Capture the layout's TextView and set the string as its text
        TextView nameText = (TextView) findViewById(R.id.textView);
        nameText.setText(contactInfo.getName());
        TextView phoneText = (TextView) findViewById(R.id.textView2);
        phoneText.setText(contactInfo.getPhoneNumber());
        TextView emailText = (TextView) findViewById(R.id.textView3);
        emailText.setText(contactInfo.getEmail());
    }
}
