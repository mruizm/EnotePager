package com.hpe.rumarco.enotepager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by rumarco on 17/12/2016.
 */
public class ConfigurationNotificationActivity extends SingleFragmentConfigurationNotificationActivity
{
    public static Intent newIntent(Context context)
    {
        return new Intent(context, ConfigurationNotificationActivity.class);
    }

    @Override
    protected Fragment createFragment()
    {
        return new ConfigurationNotificationFragment();
    }
}


