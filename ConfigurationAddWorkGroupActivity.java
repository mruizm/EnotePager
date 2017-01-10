package com.hpe.rumarco.enotepager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;

/**
 * Created by rumarco on 16/12/2016.
 */

public class ConfigurationAddWorkGroupActivity extends SingleFragmentAddWorkGroupActivity
{
    private SQLiteDatabase mDatabase;
    private ContentValues args = new ContentValues();
    private ConfigurationAddWorkGroupFragment mConfigurationAddWorkGroupFragment;


    public static Intent newIntent(Context context)
    {
        return new Intent(context, ConfigurationAddWorkGroupActivity.class);
    }

    @Override
    protected Fragment createFragment()
    {
        /*mDatabase = new NotificationsBaseHelper(this).getWritableDatabase();
        View inflatedView = getLayoutInflater().inflate(R.layout.activity_add_wg_main, null);*/
        /*final EditText mEditTextInsertWGText = (EditText) findViewById(R.id.edit_text_add_wg);
        ImageButton mImageButtonAddWG = (ImageButton) findViewById(R.id.add_wg_to_list);
        mImageButtonAddWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                CharSequence edit_text_value = mEditTextInsertWGText.getText();
                String mWgName = edit_text_value.toString();
                Toast.makeText(getApplicationContext(), "HPSM WorkGroup " + mWgName + " added to database!", Toast.LENGTH_SHORT).show();
                args.put(NotificationsDbScheme.HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME, mWgName);
                mDatabase.insert(NotificationsDbScheme.HpSmWorkgroupList.NAME, null, args);
                mEditTextInsertWGText.setText("");
                mConfigurationAddWorkGroupFragment = new ConfigurationAddWorkGroupFragment();
            }
        });*/
        return new ConfigurationAddWorkGroupFragment();
    }
}

