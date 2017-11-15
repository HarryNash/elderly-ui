package g1736229.elderlyui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DisplayContactInfoActivity extends AppCompatActivity {
    String componentSize;
    String theNumber = "";
    private String msgBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact_info);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        ContactInfo contactInfo = (ContactInfo) intent.getSerializableExtra(ContactSelectionActivity.EXTRA_MESSAGE);

        componentSize = intent.getStringExtra(ContactSelectionActivity.EXTRA_COMPONENT_SIZE);
        int textSize = ComponentResizing.adjectiveToNumber(componentSize);

        // Capture the layout's TextView and set the string as its text
        TextView nameText = (TextView) findViewById(R.id.textView);
        nameText.setText(convertNull(contactInfo.getName()));
        nameText.setTextSize(textSize);

        TextView phoneText = (TextView) findViewById(R.id.textView2);
        theNumber = convertNull(contactInfo.getPhoneNumber());
        phoneText.setText(convertNull(contactInfo.getPhoneNumber()));
        phoneText.setTextSize(textSize);

        TextView emailText = (TextView) findViewById(R.id.textView3);
        emailText.setText(convertNull(contactInfo.getEmail()));
        emailText.setTextSize(textSize);

        ComponentResizing.resizeButton(componentSize, findViewById(R.id.button6), getResources());
    }

    public void makeCall(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", theNumber, null)));
    }



    public void textMessage (View view) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + theNumber));
        smsIntent.putExtra("sms_body", msgBody);
        startActivity(smsIntent);
    }

    private String convertNull(String string) {
        if (string == null) {
            return "<Blank>";
        }
        return string;
    }
}
