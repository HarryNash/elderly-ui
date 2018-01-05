package g1736229.elderlyui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class DisplayContactInfoActivityDevice extends DisplayContactInfoActivity{

    @Override
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
}
