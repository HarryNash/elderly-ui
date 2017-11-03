package g1736229.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DisplayContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact_info);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        ContactInfo contactInfo = (ContactInfo) intent.getSerializableExtra(ContactsActivity.EXTRA_MESSAGE);
        int font = Integer.parseInt(intent.getStringExtra(ContactsActivity.EXTRA_FONT));
        Log.d("GANG", Integer.toString(font));

        // Capture the layout's TextView and set the string as its text
        TextView nameText = (TextView) findViewById(R.id.textView);
        nameText.setText(convertNull(contactInfo.getName()));
        nameText.setTextSize(font);

        TextView phoneText = (TextView) findViewById(R.id.textView2);
        phoneText.setText(convertNull(contactInfo.getPhoneNumber()));
        phoneText.setTextSize(font);

        TextView emailText = (TextView) findViewById(R.id.textView3);
        emailText.setText(convertNull(contactInfo.getEmail()));
        emailText.setTextSize(font);
    }

    private String convertNull(String string) {
        if (string == null) {
            return "<Blank>";
        }

        return string;
    }
}
