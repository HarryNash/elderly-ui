package g1736229.elderlyui;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_COMPONENT_SIZE;
import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_MESSAGE;
import static java.lang.Thread.sleep;

public class DisplayContactInfoActivity extends AppCompatActivity {
    public static final String CLIPPY_MESSAGE_OVERRIDE = "g17361229.elderlyui.CLIPPY_MESSAGE_OVERRIDE";
    private String componentSize;
    private ContactInfo contactInfo;

    private String number;
    private String name;
    private long whatsAppID;

    private List<String> msgs = new ArrayList<>();

    private int VIDEO_CALL_CODE = 55;
    private int PHONE_CALL_CODE = 83;
    private long startCallTime;

    Handler reminderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact_info);

        Intent intent = getIntent();
        contactInfo = (ContactInfo) intent.getSerializableExtra(EXTRA_MESSAGE);

        componentSize = intent.getStringExtra(EXTRA_COMPONENT_SIZE);
        int textSize = ComponentResizing.adjectiveToNumber(componentSize);

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

        whatsAppID = getContactWhatsAppID();
        if (whatsAppID == -1) {
            removeVideoCallButton(); // Could grey out instead
        } else {
            //animateVideoCallButton();
        }

        initMsgs();
        callClippy(intent.getStringExtra(CLIPPY_MESSAGE_OVERRIDE));
    }

    private long getContactWhatsAppID() {
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Data.CONTENT_URI, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME);

        long contactID = -1;
        String videoCallMimeType = "vnd.android.cursor.item/vnd.com.whatsapp.video.call";
        while (cursor.moveToNext()) {
            long _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
            if (displayName.equals(name) && mimeType.equals(videoCallMimeType)) {
                contactID = _id;
                break;
            }
        }
        return contactID;
    }

    private void removeVideoCallButton() {
        Button button = (Button) findViewById(R.id.button7);
        ViewGroup layout = (ViewGroup) button.getParent();
        if (null != layout) {
            layout.removeView(button);
        }
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
                    txt.setText(videoCall); // Dies the second/third time this is called
                    try {
                        sleep(1000);
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
    public void callClippy(String overridingMessage) {
        String msg;
        if (overridingMessage == null) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, msgs.size());
            msg = msgs.get(randomNum);
        } else {
            msg = overridingMessage;
        }

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
            Intent intent = new Intent(getApplicationContext(), DisplayContactInfoActivity.class);
            intent.putExtra(EXTRA_MESSAGE, contactInfo);
            intent.putExtra(EXTRA_COMPONENT_SIZE, componentSize);
            intent.putExtra(CLIPPY_MESSAGE_OVERRIDE, "You set a reminder to call " + name + " at this time!");
            startActivity(intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        float callLength = TimeUnit.SECONDS.convert(System.nanoTime() - startCallTime, TimeUnit.NANOSECONDS);

        if (requestCode == VIDEO_CALL_CODE) {
            if (callLength < 10.0) { //if (resultCode == RESULT_OK) { }
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
            if (callLength < 10.0) { //if (resultCode == RESULT_OK) { }
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                reminderHandler.postDelayed(callReminder, 10000);
                                break;

                            case DialogInterface.BUTTON_NEUTRAL:
                                reminderHandler.postDelayed(callReminder, 100000);
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
        startCallTime = System.nanoTime();

        startActivityForResult(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null)), PHONE_CALL_CODE);
        //Intent cameraIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //startActivityForResult(cameraIntent, PHONE_CALL_CODE);
    }

    public void makeVideoCall(View view) {
        startCallTime = System.nanoTime();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("content://com.android.contacts/data/" + whatsAppID),
                "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
        intent.setPackage("com.whatsapp");

        startActivityForResult(intent, VIDEO_CALL_CODE);
        //Intent cameraIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //startActivityForResult(cameraIntent, VIDEO_CALL_CODE);
    }

    private String convertNull(String string) {
        if (string == null) {
            return "<Blank>";
        }
        return string;
    }
}
