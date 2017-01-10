package com.hpe.rumarco.enotepager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;


import java.util.List;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class NotificationService extends NotificationListenerService {

    private Context context;
    private MediaPlayer mp;
    private AudioManager am;
    private String LOG = "NotificationService";
    private static SoundNotification mMusicPlayer;
    private WriteToLog mWriteToLog;
    private String NotificationConcatText;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mWriteToLog = new WriteToLog("sdcard/debug_log_enotepager.txt");
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(LOG+"_onNotificationPosted","Notification detected!");
        mWriteToLog.addLineToLog(LOG+"_onNotificationPosted:Notification detected!");
        mp = MediaPlayer.create(context, R.raw.pager_sound);
        mp.setLooping(true);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        //String pack = sbn.getPackageName();
        String text = "";
        String title = "";
        int incId = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Bundle extras = extras = sbn.getNotification().extras;
            text = extras.getCharSequence("android.text").toString();
            title = extras.getString("android.title");
            incId = sbn.getId();
        }
        Log.d(LOG+"_onNotificationPosted",title);
        mWriteToLog.addLineToLog(LOG+"_onNotificationPosted:"+title);
        Log.d(LOG+"_onNotificationPosted",text);
        mWriteToLog.addLineToLog(LOG+"_onNotificationPosted:"+text);
        Log.d(LOG+"_onNotificationPosted",String.valueOf(incId));
        mWriteToLog.addLineToLog(LOG+"_onNotificationPosted:"+String.valueOf(incId));

        //if(String.valueOf(incId).equals("100"))
        if(incId == 777)
        {
            if (mMusicPlayer == null) {
                mMusicPlayer = SoundNotification.getInstance(this);
            }
            Log.d(LOG+"_onNotificationPosted","Playing Notification Sound");
            mWriteToLog.addLineToLog(LOG+"_onNotificationPosted:Playing Notification Sound");
            mMusicPlayer.play();
        }
    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(LOG+"_onNotificationRemoved","Notification removed");
        mWriteToLog.addLineToLog(LOG+"_onNotificationRemoved:Notification removed");
        String text = "";
        String title = "";
        int incId = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Bundle extras = extras = sbn.getNotification().extras;
            text = extras.getCharSequence("android.text").toString();
            title = extras.getString("android.title");
            incId = sbn.getId();
        }
        Log.d(LOG+"_onNotificationRemoved",title);
        mWriteToLog.addLineToLog(LOG+"_onNotificationRemoved:"+title);
        Log.d(LOG+"_onNotificationRemoved",text);
        mWriteToLog.addLineToLog(LOG+"_onNotificationRemoved:"+title);
        Log.d(LOG+"_onNotificationRemoved",String.valueOf(incId));
        mWriteToLog.addLineToLog(LOG+"_onNotificationRemoved:"+title);
        incId = sbn.getId();
        //if(String.valueOf(incId).equals("100"))
        if(incId == 777)
        {
            mMusicPlayer.stop();
        }
        Log.d(LOG+"_onNotificationRemoved","Stopping Notification Sound");
        mWriteToLog.addLineToLog(LOG+"_onNotificationRemoved:Stopping Notification Sound");
    }
}