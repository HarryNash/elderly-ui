package g1736229.elderlyui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;


/**
 * Created by jaspreet on 18/10/17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<ContactInfo> contactInfos;

    public ImageAdapter(Context mContext, List<ContactInfo> contactInfos) {
        this.mContext = mContext;
        this.contactInfos = contactInfos;
    }

    public int getCount() {
        return contactInfos.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View view, ViewGroup parent) {
        ImageView imageView;

        if (view == null) {
            view = new SquareImageView(mContext);
            imageView = (ImageView) view;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }

        setImageViewToContactImage(imageView, contactInfos.get(position).getPicture());

        // uncomment if you want the dogs back
        //imageView.setImageResource(mThumbIds[position]);

        // uncomment if you want random colours
        //RandomInfoGenerator randomInfoGenerator = new RandomInfoGenerator(200, 200);
        //squareImageView.setImageBitmap(randomInfoGenerator.solidColorBitmap());
        return imageView;
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

    private void setImageViewToContactImage(ImageView imageView, Bitmap contactImage) {
       if (contactImage == null) {
           imageView.setImageResource(R.drawable.blank_profile);
       } else {
           imageView.setImageBitmap(contactImage);
       }
    }

}
