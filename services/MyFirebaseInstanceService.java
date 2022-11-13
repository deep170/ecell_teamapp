package ecell.app.ecellteam.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import ecell.app.ecellteam.C0250R;
import ecell.app.ecellteam.SplashActivity;
import java.util.Map;
import java.util.Random;

public class MyFirebaseInstanceService extends FirebaseMessagingService {
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().isEmpty()) {
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } else {
            showNotification(remoteMessage.getData());
        }
    }

    private void showNotification(Map<String, String> data) {
        String title = data.get("title").toString();
        String body = data.get("body").toString();
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("ecell.app.ecellteam.services.test", "Notification", 3);
            notificationChannel.setDescription("Test Check by Yash");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "ecell.app.ecellteam.services.test");
        notificationBuilder.setAutoCancel(true).setDefaults(-1).setWhen(System.currentTimeMillis()).setSmallIcon(C0250R.C0252drawable.common_google_signin_btn_icon_dark_focused).setContentTitle(title).setContentText(body).setContentInfo("Info");
        notificationBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 134217728));
        NotificationManager notificationManager2 = (NotificationManager) getSystemService("notification");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("ecell.app.ecellteam.services.test", "Notification", 3);
            notificationChannel.setDescription("Test Check by Yash");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "ecell.app.ecellteam.services.test");
        notificationBuilder.setAutoCancel(true).setDefaults(-1).setWhen(System.currentTimeMillis()).setSmallIcon(C0250R.C0252drawable.common_google_signin_btn_icon_dark_focused).setContentTitle(title).setContentText(body).setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        notificationBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 134217728));
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TOKEN", s);
    }
}
