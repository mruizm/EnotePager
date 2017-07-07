package com.hpe.rumarco.enotepager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by rumarco on 27/11/2016.
 */
public class NotificationListActivity extends SingleFragmentActivity
{
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    public static final int BIND_NOTIFICATION_LISTENER_SERVICE = 2;
    EnoteSmsInterceptor mEnoteSmsInterceptor = new EnoteSmsInterceptor();

    public static Intent newIntent(Context context)
    {
        return new Intent(context, NotificationListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected Fragment createFragment()
    {
        permissionReadNotifications();
        permissionSmsRead();
        /*final String myPackageName = getPackageName();
        Intent intent =
                new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                myPackageName);
        startActivity(intent);*/
        //mEnoteNotificationEvent.showEnoteNotificationActive("eNotePager Active", "EON Notifications Enabled", "");
        //Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        //startActivity(intent);
        return new NotificationListFragment();
    }
    private void permissionSmsRead()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            }
        }
    }

    private void permissionReadNotifications()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE},
                        BIND_NOTIFICATION_LISTENER_SERVICE);
            }
        }
    }
}
