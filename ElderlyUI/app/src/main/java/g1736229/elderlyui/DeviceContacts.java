package g1736229.elderlyui;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.widget.Toast;
import android.app.Activity;

import static android.support.v4.content.ContextCompat.startActivity;
import static java.lang.System.*;

import android.support.v4.content.ContextCompat;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Evche on 19/10/2017.
 */

public class DeviceContacts {


    public static void initialiseSampleContactData(Context context) {
        addContactData(context, new ContactInfo("001", "Aleksandar", "02079460498", "aleks@abc.com", null));
        addContactData(context, new ContactInfo("002", "Maria", "02079461235", "maria@abc.com", null));
        addContactData(context, new ContactInfo("003", "Tatiana", "02079423456", "tatiana@abc.com", null));
        addContactData(context, new ContactInfo("004", "Borislav", "02079456789", "borislav@abc.com", null));
        addContactData(context, new ContactInfo("005", "Silvana", "02079402364", "silvana@abc.com", null));
        addContactData(context, new ContactInfo("006", "Krasimira", "02079445623", "krasimira@abc.com", null));
        addContactData(context, new ContactInfo("007", "Richard", "02079411447", "richard@abc.com", null));
        addContactData(context, new ContactInfo("008", "Tom", "02079425896", "tom@abc.com", null));
    }

    public static void addContactData(Context context, ContactInfo contactInfo) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        if (contactInfo.getName() != null) {
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            contactInfo.getName()).build());
        }

        // Mobile Number
        if (contactInfo.getPhoneNumber() != null) {
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactInfo.getPhoneNumber())
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
        }

        // Email
        if (contactInfo.getEmail() != null) {
            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, contactInfo.getEmail())
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                            ContactsContract.CommonDataKinds.Email.TYPE_WORK).build());
        }
        //TODO : initialise a picture too;

        // Asking the Contact provider to create a new contact
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
