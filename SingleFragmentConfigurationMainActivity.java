package com.hpe.rumarco.enotepager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rumarco on 26/11/2016.
 */
public abstract class SingleFragmentConfigurationMainActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_main);
        //initToolBar();

        //FragmentManager used to add a fragment to activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment_notification = fm.findFragmentById(R.id.fragment_container_general_configuration);

        if (fragment_notification == null)
        {
            fragment_notification = createFragment();
            fm.beginTransaction().add(R.id.fragment_container_general_configuration, fragment_notification).commit();
        }
    }
}

