package g1736229.elderlyui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;
import java.util.UUID;

/**
 * Created by jaspreet on 19/10/17.
 */

public class RandomInfoGenerator {
    private int width;
    private int height;

    public RandomInfoGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public RandomInfoGenerator() {
        this.width = 200;
        this.height = 200;
    }

    public ContactInfo contactInfo()
    {
       ContactInfo contactInfo = new ContactInfo(UUID.randomUUID().toString(),
                                                 UUID.randomUUID().toString(),
                                                 UUID.randomUUID().toString(),
                                                 UUID.randomUUID().toString(),
                                                 null);

        return contactInfo;
    }

    public Bitmap solidColorBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Random rand = new Random();
        int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        paint.setColor(color);
        canvas.drawRect(0, 0, (float) width, (float) height, paint);
        return bitmap;
    }
}
