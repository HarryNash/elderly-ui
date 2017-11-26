package g1736229.elderlyui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Startup extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent incomingIntent) {
        if (incomingIntent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent outgoingIntent = new Intent(context, ImpairmentDetectionActivity.class);
            outgoingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(outgoingIntent);
        }
    }

}
