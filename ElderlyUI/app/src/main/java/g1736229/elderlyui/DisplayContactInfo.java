package g1736229.elderlyui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapLabel;

public class DisplayContactInfo extends AppCompatActivity {

    String theNumber = "";

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
        theNumber = convertNull(contactInfo.getPhoneNumber());
        phoneText.setText(convertNull(contactInfo.getPhoneNumber()));
        phoneText.setTextSize(font);

        TextView emailText = (TextView) findViewById(R.id.textView3);
        emailText.setText(convertNull(contactInfo.getEmail()));
        emailText.setTextSize(font);

        int widthSize = font * 8;
        int heightSize = font * 6;

        final float scale = getResources().getDisplayMetrics().density;
        int heightDp = (int) (heightSize * scale + 0.5f);
        int widthDp = (int) (widthSize * scale + 0.5f);

        String headingStyle = intent.getStringExtra(ImpairmentDetectionActivity.HEADING_STYLE);
        Headings headingsObj = new Headings();

        BootstrapLabel b = (BootstrapLabel)findViewById(R.id.button6);
        b.setBootstrapHeading(headingsObj.getCorrespondingHeadingClass(headingStyle));
        ViewGroup.LayoutParams params = b.getLayoutParams();//b========>ur button
        params.height = heightDp;
        params.width = widthDp;
        b.setLayoutParams(params);
        b.requestLayout();
    }

    public void makeCall(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", theNumber, null)));
    }

    private String convertNull(String string) {
        if (string == null) {
            return "<Blank>";
        }

        return string;
    }
}
