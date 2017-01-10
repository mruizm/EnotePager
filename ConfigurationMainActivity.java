package com.hpe.rumarco.enotepager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by rumarco on 16/12/2016.
 */

public class ConfigurationMainActivity extends SingleFragmentConfigurationMainActivity
{
    public static Intent newIntent(Context context)
    {
        return new Intent(context, ConfigurationMainActivity.class);
    }

    @Override
    protected Fragment createFragment()
    {
        return new ConfigurationMainFragment();
    }
}

