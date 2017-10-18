package g1736229.elderlyui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by jaspreet on 18/10/17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactImageView contactImageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            contactImageView = new ContactImageView(mContext);
            contactImageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            // TODO: fix warping of images when transformed into squares
            contactImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            contactImageView.setPadding(20, 20, 20, 20);

        } else {
            contactImageView = (ContactImageView) convertView;
        }

        contactImageView.setImageResource(mThumbIds[position]);
        ContactInfo contactInfo = new ContactInfo("x", "" + position, "y");
        contactImageView.setContactInfo(contactInfo);
        return contactImageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };

}
