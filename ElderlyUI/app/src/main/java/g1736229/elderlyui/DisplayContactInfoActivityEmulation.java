package g1736229.elderlyui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class DisplayContactInfoActivityEmulation extends DisplayContactInfoActivity{

    @Override
    public void makeVideoCall(View view) {
        startCallTime = System.nanoTime();
        try {
            startActivityForResult(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", "0", null)), PHONE_CALL_CODE);
            //Intent cameraIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            //startActivityForResult(cameraIntent, PHONE_CALL_CODE);
        } catch (SecurityException e) {

        }
    }
}
