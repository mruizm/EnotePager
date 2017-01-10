package com.hpe.rumarco.enotepager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rumarco on 26/11/2016.
 */
//public abstract class SingleFragmentActivity extends FragmentActivity{
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unack_notifications_main);

        //FragmentManager used to add a fragment to activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment_notification = fm.findFragmentById(R.id.fragment_container);

        if (fragment_notification == null)
        {
            fragment_notification = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment_notification).commit();
        }
    }
}
