package com.hpe.rumarco.enotepager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by rumarco on 28/12/2016.
 */
public abstract class SingleFragmentAddWorkGroupActivity extends AppCompatActivity{
    protected abstract Fragment createFragment();
    private ConfigurationAddWorkGroupFragment mConfigurationAddWorkGroupFragment;
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wg_main);

        //FragmentManager used to add a fragment to activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment_notification = fm.findFragmentById(R.id.fragment_container_workgroup_list);

        if (fragment_notification == null)
        {
            fragment_notification = createFragment();
            fm.beginTransaction().add(R.id.fragment_container_workgroup_list, fragment_notification).commit();
        }
    }
}

