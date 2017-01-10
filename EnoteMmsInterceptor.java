package com.hpe.rumarco.enotepager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by rumarco on 03/01/2017.
 */
public class EnoteMmsInterceptor extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        if(intent.getAction().equals("android.provider.Telephony.WAP_PUSH_DELIVER"))
        {

        }
    }
}
