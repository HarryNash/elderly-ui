package g1736229.elderlyui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ImpairmentDetectionActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impairment_detection);
    }

    public void smallFont(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "14");
        startActivity(intent);
    }

    public void mediumFont(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "20");
        startActivity(intent);
    }

    public void largeFont(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "30");
        startActivity(intent);
    }

}
