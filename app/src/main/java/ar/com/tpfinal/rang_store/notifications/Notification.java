package ar.com.tpfinal.rang_store.notifications;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.Instant;

import ar.com.tpfinal.rang_store.R;

public class Notification {

    public static void  createNotificationChannel(Activity activity, String CHANNEL_ID, String name){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public static void createNotification(Activity activity, String CHANNEL_ID){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.baseline_shopping_cart_24);
        builder.setContentTitle("La orden de compra ha sido realizada!");
        builder.setContentText("Se enviara una notificacion cuando la misma sea aprobada");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(android.app.Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(activity.getApplicationContext());
        notificationManagerCompat.notify((int)Instant.now().toEpochMilli(), builder.build());
    }

}
