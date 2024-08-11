package com.bytedesk.core.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

/**
 * TODO: 通知管理
 *
 * @author bytedesk.com on 2018/12/14
 */
public class BDNotificationUtils {


    /** Message ID Counter **/
    private static int MessageID = 0;

    /**
     * Displays a notification in the notification area of the UI
     * @param context Context from which to create the notification
     * @param messageString The string to display to the user as a message
     * @param intent The intent which will start the activity when the user clicks the notification
     * @param notificationTitle The resource reference to the notification title
     */
    static void notifcation(Context context, String messageString, Intent intent, int notificationTitle) {

        //Get the notification manage which we will use to display the notification
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

        long when = System.currentTimeMillis();

        //get the notification title from the application's strings.xml file
        CharSequence contentTitle = context.getString(notificationTitle);

        //the message that will be displayed as the ticker
        String ticker = contentTitle + " " + messageString;

        //build the pending intent that will start the appropriate activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, 0);

        //build the notification
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
        notificationCompat.setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentIntent(pendingIntent)
                .setContentText(messageString)
                .setTicker(ticker)
                .setWhen(when);
//                .setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = notificationCompat.build();
        //display the notification
        mNotificationManager.notify(MessageID, notification);
        MessageID++;

    }




}
