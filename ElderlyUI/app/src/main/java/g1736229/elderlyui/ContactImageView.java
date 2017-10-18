package g1736229.elderlyui;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by jaspreet on 18/10/17.
 */

public class ContactImageView extends ImageView {
    private ContactInfo contactInfo;

    public ContactImageView(Context c) {
       super(c);
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
