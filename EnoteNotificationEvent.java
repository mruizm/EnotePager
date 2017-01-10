package com.hpe.rumarco.enotepager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by rumarco on 15/12/2016.
 */
public class EnoteNotificationEvent {

    private Context pContext;
    public static final int NOTIFICATION_ID = 777;
    public static final int NOTIFICATION_ID_ACTIVE = 888;
    final static String GROUP_KEY_EMAILS = "group_key_emails";
    NotificationManager notificationManager;


    public EnoteNotificationEvent(Context context)
    {
        pContext = context;
    }

    public void showEnoteNotification(String titleNotif, String incSloType, String incSloPercent, String notifPrio, String notifId) {
        // Prepare intent which is triggered if the
        // Build notification
        // Actions are just fake
        /*Uri introURI = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);/**/
        //Random random = new Random();
        //int m = random.nextInt(99 - 50) + 100;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(pContext)
                .setSmallIcon(R.mipmap.ic_pager_2)
                .setContentTitle(titleNotif)
                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                //.setSound(introURI)
                .setContentText("-- Action Required --");

        Intent resultIntent = new Intent(pContext, NotificationListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(pContext, NOTIFICATION_ID, resultIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_INSISTENT | Notification.PRIORITY_MAX;

        notificationManager = (NotificationManager) pContext.getSystemService(pContext.NOTIFICATION_SERVICE);
        Log.d("NotifID", String.valueOf(NOTIFICATION_ID));
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void showEnoteNotificationActive(String titleNotif) {
        // Prepare intent which is triggered if the
        // Build notification
        // Actions are just fake
        Uri introURI = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(pContext)
                .setSmallIcon(R.drawable.ic_normal_notif)
                .setContentTitle("New SMS message!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(titleNotif))
                .setContentText(titleNotif);

        /*Intent resultIntent = new Intent(pContext, NotificationListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(pContext, NOTIFICATION_ID_ACTIVE, resultIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(pendingIntent);*/

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        notificationManager = (NotificationManager) pContext.getSystemService(pContext.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID_ACTIVE, notification);
    }
}
