package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_COMPONENT_SIZE;
import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_MESSAGE;

public class ContactOptionsActivity extends AppCompatActivity {

    private String componentSize;
    private String headingStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_options);

        Intent intent = getIntent();
        componentSize = intent.getStringExtra(EXTRA_COMPONENT_SIZE);
        headingStyle = intent.getStringExtra(ImpairmentDetectionActivity.HEADING_STYLE);

        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.viewcontacts), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.addcontacts), getResources());
    }

    public void openContactsActivity(View v) {
        Intent intent = new Intent(this, ContactSelectionActivity.class);
        intent.putExtra(ImpairmentDetectionActivity.EXTRA_COMPONENT_SIZE, componentSize);
        intent.putExtra(ImpairmentDetectionActivity.HEADING_STYLE, headingStyle);
        startActivity(intent);
    }

    public void addNewContact(View view) {
        
    }
}
