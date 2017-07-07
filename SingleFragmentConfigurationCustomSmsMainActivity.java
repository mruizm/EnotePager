package com.hpe.rumarco.enotepager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rumarco on 14 jun 2017.
 */
public abstract class SingleFragmentConfigurationCustomSmsMainActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_patterns);
        //initToolBar();

        //FragmentManager used to add a fragment to activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment_notification = fm.findFragmentById(R.id.fragment_container_sms_patterns);

        if (fragment_notification == null)
        {
            fragment_notification = createFragment();
            fm.beginTransaction().add(R.id.fragment_container_sms_patterns, fragment_notification).commit();
        }
    }
}


