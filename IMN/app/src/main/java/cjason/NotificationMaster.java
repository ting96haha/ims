package cjason;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.List;

import altf4.imn.R;
import altf4.imn.MainActivity;
import altf4.imn.customeviews.StandardPostNotify;
import mmls.MMLSpost;

public class NotificationMaster {

    private static final String DEF_NOTIF_CHANNEL_ID = "Default_Channel";
    private static final int UPDATE_NOTIF_ID = 0;

    private static NotificationManager notificationManager;

    public NotificationMaster(Context ctxt){
        NotificationChannel default_notif_channel= new NotificationChannel(
                DEF_NOTIF_CHANNEL_ID,"Default Channel",NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager = (NotificationManager) ctxt.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(default_notif_channel);
    }

    public void showHelloNotification(Context ctxt) {
        //this is for testing only
        Notification notification =  Makebuilder(ctxt).setContentText("Hello!").build();
        notificationManager.notify(UPDATE_NOTIF_ID, notification);
    }

    public void showStringNotification(Context ctxt,String msg){
        //shows a string for notification.
        Notification notification =  Makebuilder(ctxt).setContentText(msg).build();
        notificationManager.notify(UPDATE_NOTIF_ID, notification);
    }

    public void showListNotification(Context ctxt, List<MMLSpost> target){
        //shows a list of unnotified post
        NotificationCompat.Builder nbuild = Makebuilder(ctxt).setContentText("Updates from MMLS");
        int nmsg = 0;
        int notifIDcount = 1;
        for(MMLSpost post : target){
            //for each post in the target
            nbuild.setContentTitle(post.getTitle());
            nbuild.setContentText(post.getContent()); //.setNumber ?
            notificationManager.notify(notifIDcount++,nbuild.build());
        }

    }

    public void showIntentNotification(Context ctxt,String msg){
        PendingIntent pi = PendingIntent.getActivity(ctxt, 0, new Intent(ctxt, MainActivity.class), 0);
        Notification notification =  Makebuilder(ctxt).setContentText(msg).setContentIntent(pi).build();
        notificationManager.notify(UPDATE_NOTIF_ID, notification);
    }

    private NotificationCompat.Builder Makebuilder(Context ctxt){
        Intent showIntent = new Intent(ctxt, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(ctxt, 0, showIntent, 0);
        NotificationCompat.Builder out = new NotificationCompat.Builder(ctxt,DEF_NOTIF_CHANNEL_ID);
        out.setSmallIcon(R.drawable.cal_notify);
        out.setOngoing(false);
        out.setAutoCancel(true);
        out.setContentIntent(contentIntent);
        out.setDefaults(Notification.DEFAULT_ALL);
        return out;
    }


}
