package com.hpe.rumarco.enotepager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.List;

/**
 * Created by rumarco on 27/11/2016.
 */
public class NotificationAllListActivity extends SingleFragmentAllNotificationActivity
{

    public static Intent newIntent(Context context)
    {
        return new Intent(context, NotificationAllListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected Fragment createFragment()
    {
        //mEnoteNotificationEvent.showEnoteNotificationActive("eNotePager Active", "EON Notifications Enabled", "");
        //Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        //startActivity(intent);
        return new NotificationAllListFragment();
    }
}
