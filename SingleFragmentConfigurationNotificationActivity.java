package com.hpe.rumarco.enotepager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rumarco on 17/12/2016.
 * Class that defines hosting activity layout and fragment container id
 */
public abstract class SingleFragmentConfigurationNotificationActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_notification_setup_main);
        //initToolBar();

        //FragmentManager used to add a fragment to activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment_notification = fm.findFragmentById(R.id.fragment_container_configuration_notification_setup);

        if (fragment_notification == null)
        {
            fragment_notification = createFragment();
            fm.beginTransaction().add(R.id.fragment_container_configuration_notification_setup, fragment_notification).commit();
        }
    }
}
