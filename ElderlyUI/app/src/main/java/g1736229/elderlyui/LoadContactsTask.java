package g1736229.elderlyui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.util.List;

/**
 * Created by jaspreet on 25/10/17.
 */

// Retrieve contacts in parallel as the UI updates
public class LoadContactsTask extends AsyncTask<Void, Void, Void> {

    ContactSelectionActivity activity;

    public LoadContactsTask(ContactSelectionActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        ProgressBar progressBar = activity.getProgressBar();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ProgressBar progressBar = activity.getProgressBar();
        progressBar.setVisibility(View.GONE);

        BaseAdapter adapter = activity.getImageAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected Void doInBackground(Void... params) {
        this.retrieveContactInfo();

        return null;
    }

    // Update Grid View via its adapter
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        BaseAdapter adapter = activity.getImageAdapter();
        adapter.notifyDataSetChanged();
    }

    // Gets contact info from user
    private List<ContactInfo> retrieveContactInfo() {

        List<ContactInfo> contactInfos = activity.getContactInfos();
        contactInfos.clear();

        ContentResolver cr = activity.getContentResolver();
        String OrderBy = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                                 null,
                                 null,
                                 null,
                                 OrderBy);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int hasPhoneNumberIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                Long id = cursor.getLong(idIndex);
                String name = cursor.getString(nameIndex);
                String phoneNumber = null;

                int hasPhoneNumber = cursor.getInt(hasPhoneNumberIndex);
                if (hasPhoneNumber > 0) {
                    Cursor pCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                              null,
                                              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                              null,
                                              null);

                    while (pCursor.moveToNext()) {
                        int phoneNumberIndex = pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNumber = pCursor.getString(phoneNumberIndex);
                    }

                    pCursor.close();
                }

                Bitmap picture = loadProfilePicture(cr, id);

                // add contact info to list of info in the activity
                contactInfos.add(new ContactInfo(id.toString(), name, phoneNumber, null, picture));

                // tell the UI that the list of contact info has been updated
                this.publishProgress();
            }

            cursor.close();
        }

        return contactInfos;
    }

    private Bitmap loadProfilePicture(ContentResolver cr, long id) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri, true);
        if (input == null) {
            return null;
        }
        return BitmapFactory.decodeStream(input);
    }
}
