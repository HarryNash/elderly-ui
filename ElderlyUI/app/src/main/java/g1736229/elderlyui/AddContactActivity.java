package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_COMPONENT_SIZE;

public class AddContactActivity extends AppCompatActivity {

    private String componentSize;
    private String headingStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Intent intent = getIntent();
        componentSize = intent.getStringExtra(EXTRA_COMPONENT_SIZE);
        headingStyle = intent.getStringExtra(ImpairmentDetectionActivity.HEADING_STYLE);

        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.confirmadd), getResources());

    }
}
