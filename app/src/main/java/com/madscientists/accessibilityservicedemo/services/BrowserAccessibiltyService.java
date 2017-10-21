package com.madscientists.accessibilityservicedemo.services;

import android.accessibilityservice.AccessibilityService;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.madscientists.accessibilityservicedemo.utils.Util;

/**
 * Created by madscientist on 21/10/17.
 */

public class BrowserAccessibiltyService extends AccessibilityService {

    private static final int NOTIFICATION_ID = 1234;
    private static final String TAG = BrowserAccessibiltyService.class.getCanonicalName();
    private static final String APP_PACKAGE ="com.ninetaleswebventures.frapp";
    private NotificationManagerCompat notificationManagerCompat;
    private String search_text="";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getText().size()!=0){
            search_text = accessibilityEvent.getText().get(0).toString();
        }
        Log.d(TAG, "onAccessibilityEvent: "+ search_text);
        search_text =search_text.replace("http://","");
        search_text = search_text.replace("www.","");
        String beforeText = accessibilityEvent.getBeforeText().toString();
        beforeText = beforeText.replace("http://","");
        beforeText = beforeText.replace("www.","");

        if (search_text.equalsIgnoreCase("frapp.in")){
            buildNotifications();
            showNotification("");
        }else if (search_text.equalsIgnoreCase("frapp.in/internships")){
            buildNotifications();
            showNotification("com://frapp.in/sendto/popular_internships");
        }else if (search_text.equalsIgnoreCase("frapp.in/resume")){
            buildNotifications();
            showNotification("com://frapp.in/sendto/resume");
        } else if (beforeText.equalsIgnoreCase("frapp.in")
                || beforeText.equalsIgnoreCase("frapp.in/internships")
                || beforeText.equalsIgnoreCase("frapp.in/resume")
                || beforeText.isEmpty()) {
            if (notificationManagerCompat!=null){
                notificationManagerCompat.cancel(NOTIFICATION_ID);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
    private void showNotification(String deeplink){
        Intent intent;
        String actionTitle = "Open App";
        if (Util.isPackageInstalled(APP_PACKAGE,getPackageManager())){
            if (!TextUtils.isEmpty(deeplink)){
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
            }else
                intent = new Intent(getPackageManager().getLaunchIntentForPackage(APP_PACKAGE));
        }else{
            intent = Util.getOpenAppOrOpenPlayStoreIntent(APP_PACKAGE);
            actionTitle = "Download App";
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this,
                NotificationChannel.DEFAULT_CHANNEL_ID)
                .setContentTitle("Open in app?")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())
                .setContentText("An app for this website exits, wish to open this page in app?")
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play,actionTitle, pendingIntent));

        if (Build.VERSION.SDK_INT >= 21) nBuilder.setVibrate(new long[0]);

        notificationManagerCompat.notify(NOTIFICATION_ID,nBuilder.build());
    }
    private void buildNotifications(){
        if (notificationManagerCompat==null){
            notificationManagerCompat = NotificationManagerCompat.from(this);
        }
    }

}
