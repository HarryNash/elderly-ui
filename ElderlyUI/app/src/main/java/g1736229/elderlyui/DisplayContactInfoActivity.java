package g1736229.elderlyui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DisplayContactInfoActivity extends AppCompatActivity {
    String componentSize;
    String theNumber = "";
    List<String> msgs = new ArrayList<>();

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
        ComponentResizing.resizeButton(componentSize, findViewById(R.id.button7), getResources());

        initMsgs();
        callClippy();
    }

    /** This calls a random
     *
     */
    public void callClippy() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, msgs.size());
        String msg = msgs.get(randomNum);
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void initMsgs() {
        String msg;
        msg = "Permissions required for application to function";
        msgs.add(msg);
        msg = "Hello";
        msgs.add(msg);
        msg = "Fake";
        msgs.add(msg);
    }

    public void makeCall(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", theNumber, null)));
    }

    public void makeVideoCall(View view) {
        Intent videoCallIntent = new Intent("com.android.phone.videocall");
        videoCallIntent.putExtra("videocall", true);
        videoCallIntent.setData(Uri.parse("tel:" + theNumber));
        startActivity(videoCallIntent);
    }

    private String convertNull(String string) {
        if (string == null) {
            return "<Blank>";
        }
        return string;
    }
}
