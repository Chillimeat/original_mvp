package indi.ayun.mingwork_all.notices;



import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;


import com.ayun.mingwork_all.R;

import java.util.Arrays;


public class NotificationChannels {
    public final static String CRITICAL = "critical";
    public final static String IMPORTANCE = "importance";
    public final static String DEFAULT = "default";
    public final static String LOW = "low";
    public final static String MEDIA = "media";
    public final static String PERMANENT="permanent";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createAllNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm == null) {
                return;
            }

            NotificationChannel mediaChannel = new NotificationChannel(MEDIA, context.getString(R.string.channel_media), NotificationManager.IMPORTANCE_DEFAULT);
            mediaChannel.setSound(null, null);
            mediaChannel.setVibrationPattern(null);

            NotificationChannel criticalChannel = new NotificationChannel(CRITICAL, context.getString(R.string.channel_critical), NotificationManager.IMPORTANCE_HIGH);

            @SuppressLint("WrongConstant")
            NotificationChannel importanceChannel = new NotificationChannel(IMPORTANCE, context.getString(R.string.channel_importance), NotificationManager.IMPORTANCE_MAX);

            NotificationChannel defaultChannel = new NotificationChannel(DEFAULT, context.getString(R.string.channel_default), NotificationManager.IMPORTANCE_LOW);
            defaultChannel.setSound(null, null);
            defaultChannel.setVibrationPattern(null);

            NotificationChannel lowChannel = new NotificationChannel(LOW, context.getString(R.string.channel_low), NotificationManager.IMPORTANCE_MIN);
            lowChannel.setSound(null, null);
            lowChannel.setVibrationPattern(null);

            NotificationChannel permanentChannel = new NotificationChannel(PERMANENT, context.getString(R.string.channel_permanent), NotificationManager.IMPORTANCE_HIGH);
            permanentChannel.setSound(null, null);
            permanentChannel.setVibrationPattern(null);


            nm.createNotificationChannels(Arrays.asList(
                    criticalChannel,
                    importanceChannel,
                    defaultChannel,
                    lowChannel,
                    mediaChannel,
                    permanentChannel
            ));
        }
    }
}
