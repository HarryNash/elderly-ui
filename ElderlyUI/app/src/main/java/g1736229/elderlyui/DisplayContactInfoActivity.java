package g1736229.elderlyui;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static android.R.attr.type;
import static java.lang.Thread.sleep;

public class DisplayContactInfoActivity extends AppCompatActivity {
    String componentSize;

    String number;
    String name;

    List<String> msgs = new ArrayList<>();

    int VIDEO_CALL_CODE = 55;
    int PHONE_CALL_CODE = 83;
    long startVoiceCallTime;
    long startPhoneCallTime;

    Handler mHandler = new Handler();

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
        name = convertNull(contactInfo.getName());
        nameText.setText(name);
        nameText.setTextSize(textSize);

        TextView phoneText = (TextView) findViewById(R.id.textView2);
        number = convertNull(contactInfo.getPhoneNumber());
        phoneText.setText(convertNull(contactInfo.getPhoneNumber()));
        phoneText.setTextSize(textSize);

        TextView emailText = (TextView) findViewById(R.id.textView3);
        emailText.setText(convertNull(contactInfo.getEmail()));
        emailText.setTextSize(textSize);

        ComponentResizing.resizeButton(componentSize, findViewById(R.id.button6), getResources());
        ComponentResizing.resizeButton(componentSize, findViewById(R.id.button7), getResources());

        initMsgs();
        callClippy();

        //animateVideoCallButton();
    }


    public void animateVideoCallButton() {
        new Thread( new Runnable(){
            @Override
            public void run(){
                Looper.prepare();
                int i = 0;
                while (true) {
                    i = (i + 1) % 9;
                    Button txt = (Button) findViewById(R.id.button7);
                    StringBuilder videoCall = new StringBuilder("VIDEO CALL");
                    videoCall.setCharAt(i, Character.toLowerCase(videoCall.charAt(i)));
                    txt.setText(videoCall);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {

                    }
                    if (i == 0) {
                        try {
                            txt.setText("VIDEO CALL");
                            sleep(2000);
                        } catch (InterruptedException e) {

                        }
                    }
                }
            }
        }).start();
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

    private Runnable callReminder = new Runnable() {
        public void run() {
            // Sending to the start is just placement, send them to this screen with an intent overriding clippy to say "you asked for a reminder 5hrs ago to call bob again..."
            Intent i = new Intent(getApplicationContext(),ImpairmentDetectionActivity.class);
            startActivity(i);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_CALL_CODE) {
            float secondsSpentInVideoCall = TimeUnit.SECONDS.convert(System.nanoTime() - startVoiceCallTime, TimeUnit.NANOSECONDS);
            if (secondsSpentInVideoCall < 10.0) { //if (resultCode == RESULT_OK) { }
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                makeCall(findViewById(R.id.button6));
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("That video call didn't go so well, perhaps one of you had a bad connection, would you like to try a phone call which is more reliable instead?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        }

        if (requestCode == PHONE_CALL_CODE) {
            float secondsSpentInPhoneCall = TimeUnit.SECONDS.convert(System.nanoTime() - startPhoneCallTime, TimeUnit.NANOSECONDS);
            if (secondsSpentInPhoneCall < 10.0) { //if (resultCode == RESULT_OK) { }
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                mHandler.postDelayed(callReminder, 10000);
                                break;

                            case DialogInterface.BUTTON_NEUTRAL:
                                mHandler.postDelayed(callReminder, 100000);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("That phone call didn't go through, perhaps " + name + " is away from their device, would you like to be reminded to call them?")
                        .setPositiveButton("Remind me in an hour", dialogClickListener)
                        .setNegativeButton("Don't remind me", dialogClickListener)
                        .setNeutralButton("Remind me tomorrow", dialogClickListener).show();
            }
        }
    }

    public void makeCall(View view) {
        startPhoneCallTime = System.nanoTime();
        startActivityForResult(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null)), PHONE_CALL_CODE);
    }

    public void makeVideoCall(View view) {
        Log.d("xyz", "here");
        startVoiceCallTime = System.nanoTime();


        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(
                ContactsContract.Data.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME);

        //Now read data from cursor like

        while (cursor.moveToNext()) {
            long _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));

            Log.d("Data", _id+ " "+ displayName + " " + mimeType );
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        // the _ids you save goes here at the end of /data/12562
        //intent.setDataAndType(Uri.parse("content://com.android.contacts/data/_id"),
        //        "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
        intent.setDataAndType(Uri.parse("content://com.android.contacts/data/21"),
                "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");

        intent.setPackage("com.whatsapp");

        startActivityForResult(intent, VIDEO_CALL_CODE);
    }

    private String convertNull(String string) {
        if (string == null) {
            return "<Blank>";
        }
        return string;
    }
}
