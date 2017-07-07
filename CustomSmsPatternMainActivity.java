package com.hpe.rumarco.enotepager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
/**
 * Created by rumarco on 14 jun 2017.
 */

public class CustomSmsPatternMainActivity extends SingleFragmentConfigurationCustomSmsMainActivity {
    public static Intent newIntent(Context context)
    {
        return new Intent(context, CustomSmsPatternMainActivity.class);
    }

    @Override
    protected Fragment createFragment()
    {
        return new CustomSmsPatternMainFragment();
    }
}
