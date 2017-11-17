package g1736229.elderlyui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_COMPONENT_SIZE;
import static g1736229.elderlyui.ContactSelectionActivity.EXTRA_MESSAGE;
import static java.lang.Thread.sleep;
import java.util.Locale;

public class DisplayContactInfoActivity extends AppCompatActivity {
    public static final String CLIPPY_MESSAGE_OVERRIDE = "g17361229.elderlyui.CLIPPY_MESSAGE_OVERRIDE";
    private String componentSize;
    private int textSize;
    private ContactInfo contactInfo;

    private String phoneNumber = "";
    private String name;
    private long whatsAppID;

    private int VIDEO_CALL_CODE = 55;
    private int PHONE_CALL_CODE = 83;
    private long startCallTime;

    private String headingStyle;

    // User option for voice to text messaging
    boolean voiceOnly = true;

    // Code for voice input
    final int REQ_CODE_SPEECH_INPUT = 10;

    Handler reminderHandler = new Handler();

    final int PERMISSIONS_READ_WRITE_CALL_LOG = 432;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact_info);

        Intent intent = getIntent();
        contactInfo = (ContactInfo) intent.getSerializableExtra(EXTRA_MESSAGE);

        componentSize = intent.getStringExtra(EXTRA_COMPONENT_SIZE);
        textSize = ComponentResizing.adjectiveToNumber(componentSize);

        TextView nameText = (TextView) findViewById(R.id.textView);
        name = convertNull(contactInfo.getName());
        nameText.setText(name);
        nameText.setTextSize(textSize);

        phoneNumber = convertNull(contactInfo.getPhoneNumber());

        TextView advice = (TextView) findViewById(R.id.advice);
        advice.setTextSize(textSize);

        Clippy clippy = new Clippy(intent.getStringExtra(CLIPPY_MESSAGE_OVERRIDE), generateSuggestions(), advice);
        clippy.displayAdvice();

        headingStyle = intent.getStringExtra(ImpairmentDetectionActivity.HEADING_STYLE);
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.callButton), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.messageButton), getResources());
        ComponentResizing.resizeButton(headingStyle, componentSize, findViewById(R.id.button7), getResources());

        whatsAppID = getContactWhatsAppID();
        if (whatsAppID == -1) {
            // grey out the video-call-button
        } else {
            //animate the video-call-button
        }
    }

    private List<String> generateSuggestions() {
        List<String> suggestions = new LinkedList<>();

        //whenUsuallyAvailable(suggestions);

        suggestions.add("International video calls are free!");

        suggestions.add("Make sure you are in a well lit area before starting a video call.");

        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if (batLevel < 10) {
            suggestions.add("Your device needs charge before you should start a video call.");
        }

        WifiManager wifi =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!(wifi.isWifiEnabled())) {
            suggestions.add("It is recommended that you enable WiFi for video calls.");
        }

        return suggestions;
    }

    /*
    private void whenUsuallyAvailable(List<String> suggestions) {
        //Fetches the complete call log in descending order. i.e recent calls appears first.
        String[] projection = new String[] {
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };
        acquirePermissions();
        Cursor c = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, CallLog.Calls.DATE + " DESC");
        long lastSuccessfulCall = -1;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                String callerNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                long callDateandTime = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
                int callType = c.getInt(c.getColumnIndex(CallLog.Calls.TYPE));
                Log.d("grande", "weak");
                if (callType == CallLog.Calls.INCOMING_TYPE && callerNumber.equals(phoneNumber)) {
                    // put it here really
                } else {
                    lastSuccessfulCall = callDateandTime;
                }
                //if(callType == CallLog.Calls.MISSED_TYPE) { }
            } while(c.moveToNext());

        }

        if (lastSuccessfulCall != -1) {
            Log.d("rope", Long.toString(lastSuccessfulCall));
        }

    }
    */

    private void acquirePermissions() {

        boolean granted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED;
        granted &= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED;

        String[] requestedPermissions = new String[]{Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG};

        if (!granted) {
            ActivityCompat.requestPermissions(this, requestedPermissions, PERMISSIONS_READ_WRITE_CALL_LOG);
            return;
        }
    }

    // check if permissions were granted by the user
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // get the status of our permission request
        switch (requestCode) {
            case PERMISSIONS_READ_WRITE_CALL_LOG: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // if permission granted, display contacts
                } else {
                    // otherwise display an error message
                    String msg = "Permissions required for application to function";
                    Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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

    private Runnable callReminder = new Runnable() {
        public void run() {
            Intent intent = new Intent(getApplicationContext(), DisplayContactInfoActivity.class);
            intent.putExtra(EXTRA_MESSAGE, contactInfo);
            intent.putExtra(EXTRA_COMPONENT_SIZE, componentSize);
            intent.putExtra(CLIPPY_MESSAGE_OVERRIDE, "You set a reminder to call " + name + " at this time!");
            intent.putExtra(ImpairmentDetectionActivity.HEADING_STYLE, headingStyle);
            startActivity(intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//TODO
        float callLength = TimeUnit.SECONDS.convert(System.nanoTime() - startCallTime, TimeUnit.NANOSECONDS);

        if (requestCode == VIDEO_CALL_CODE) {
            if (callLength < 10.0) { //if (resultCode == RESULT_OK) { }
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                makeCall(findViewById(R.id.callButton));
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

        if(requestCode == REQ_CODE_SPEECH_INPUT){
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Intent intent = createTextMessageIntent(phoneNumber);
                intent.putExtra("sms_body", result.get(0));
                startActivity(intent);
            }
        }
    }

    public void makeCall(View view) {
        startCallTime = System.nanoTime();

        startActivityForResult(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)), PHONE_CALL_CODE);
        //Intent cameraIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //startActivityForResult(cameraIntent, PHONE_CALL_CODE);
    }

    public void makeText(View view) {
        if (voiceOnly) {
            startVoiceInput();
        } else {
            Intent intent = createTextMessageIntent(phoneNumber);
            startActivity(intent);
        }
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

    private Intent createTextMessageIntent(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + phoneNumber));
        return intent;
    }

    private String convertNull(String string) {
        if (string == null) {
            return "<Blank>";
        }
        return string;
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say your message");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast toast = Toast.makeText(this, "Voice input not available", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

