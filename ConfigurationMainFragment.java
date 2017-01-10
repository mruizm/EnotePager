package com.hpe.rumarco.enotepager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;

import java.util.List;

/**
 * Created by rumarco on 16/12/2016.
 */

/**
 * Created by rumarco on 27/11/2016.
 * CrimeListFragment
 */
public class ConfigurationMainFragment extends Fragment
{
    private CheckBox mCheckBoxEnotePagerActive;
    private CheckBox mCheckBoxEonActive;
    private CheckBox mCheckBoxSoundActive;
    //private Button mButtonNotificationSetup;
    //private Button mButtonWorkgroupSetup;
    private SQLiteDatabase mDatabase;
    private ContentValues args = new ContentValues();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        EnoteNotificationSetupQueryToObject enoteNotificationSetupQueryToObject = EnoteNotificationSetupQueryToObject.get(getActivity());
        List<EnoteNotificationArrayObject> enotenotification_eon_sm_notif = enoteNotificationSetupQueryToObject.getEnoteNotificationSmEonActive(null);
        List<EnoteNotificationArrayObject> enotenotification_sound_active = enoteNotificationSetupQueryToObject.getEnoteNotificationSoundActive(null);

        String eon_sm_active = "";
        String app_sound_active = "";
        final EnoteNotificationObject mReceivedNotification = new EnoteNotificationObject();
        View view = inflater.inflate(R.layout.fragment_configuration_main, container, false);
        mCheckBoxEnotePagerActive = (CheckBox) view.findViewById(R.id.checkbox_enable_enote_pager);
        mCheckBoxEonActive = (CheckBox) view.findViewById(R.id.checkbox_enable_eon_escalations);
        mCheckBoxSoundActive = (CheckBox) view.findViewById(R.id.checkbox_no_sound);

        //Retirves values of eon and sm activation and set checkboxes
        for (EnoteNotificationArrayObject n_array_sm_eon: enotenotification_eon_sm_notif)
        {
            eon_sm_active = n_array_sm_eon.getAlertEON_SM() + ";" + n_array_sm_eon.getAlert_EON_SM_active();
            Log.d("EON_SM", eon_sm_active);
            if(eon_sm_active.equals("EON_GATEWAY;1"))
            {
                mCheckBoxEonActive.setChecked(true);
            }
            if(eon_sm_active.equals("SM_GATEWAY;1"))
            {
                mCheckBoxEnotePagerActive.setChecked(true);
            }
        }
        //Retrieves value of if sound is enabled
        for (EnoteNotificationArrayObject n_array_sound_active: enotenotification_sound_active)
        {
            app_sound_active = n_array_sound_active.getAlertSoundIsActive();
            Log.d("APP_SOUND_ACTIVE", app_sound_active);
            if(app_sound_active.equals("1"))
            {
                mCheckBoxSoundActive.setChecked(true);
            }
        }

        mCheckBoxEnotePagerActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsEonArray.Cols.IS_ACTIVE, "1");
                    mDatabase.update(NotificationsDbScheme.NotificationsEonArray.NAME, args, NotificationsDbScheme.NotificationsEonArray.Cols.FW_NOTIF_GATE+ " = 'SM_GATEWAY'", null);
                    Toast.makeText(getContext(), "Enabled SM Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsEonArray.Cols.IS_ACTIVE, "0");
                    mDatabase.update(NotificationsDbScheme.NotificationsEonArray.NAME, args, NotificationsDbScheme.NotificationsEonArray.Cols.FW_NOTIF_GATE+ " = 'SM_GATEWAY'", null);
                    Toast.makeText(getContext(), "Disabled SM Notifications", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxEonActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsEonArray.Cols.IS_ACTIVE, "1");
                    mDatabase.update(NotificationsDbScheme.NotificationsEonArray.NAME, args, NotificationsDbScheme.NotificationsEonArray.Cols.FW_NOTIF_GATE+ " = 'EON_GATEWAY'", null);
                    Toast.makeText(getContext(), "Enabled EON Escalations", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsEonArray.Cols.IS_ACTIVE, "0");
                    mDatabase.update(NotificationsDbScheme.NotificationsEonArray.NAME, args, NotificationsDbScheme.NotificationsEonArray.Cols.FW_NOTIF_GATE+ " = 'EON_GATEWAY'", null);
                    Toast.makeText(getContext(), "Disabled EON Escalations", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxSoundActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSoundActive.Cols.IS_SOUND_ACTIVE, "1");
                    mDatabase.update(NotificationsDbScheme.NotificationsSoundActive.NAME, args, NotificationsDbScheme.NotificationsSoundActive.Cols.IS_SOUND_ACTIVE+ " = '0'", null);
                    Toast.makeText(getContext(), "Sound Enabled!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSoundActive.Cols.IS_SOUND_ACTIVE, "0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSoundActive.NAME, args, NotificationsDbScheme.NotificationsSoundActive.Cols.IS_SOUND_ACTIVE+ " = '1'", null);
                    Toast.makeText(getContext(), "Sound Disabled! (Log only).", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        EnoteNotificationSetupQueryToObject enoteNotificationSetupQueryToObject = EnoteNotificationSetupQueryToObject.get(getActivity());
        List<EnoteNotificationArrayObject> enotenotification_eon_sm_notif = enoteNotificationSetupQueryToObject.getEnoteNotificationSmEonActive(null);
        List<EnoteNotificationArrayObject> enotenotification_sound_active = enoteNotificationSetupQueryToObject.getEnoteNotificationSoundActive(null);
        String eon_sm_active = "";
        String app_sound_active = "";
        for (EnoteNotificationArrayObject n_array_sm_eon: enotenotification_eon_sm_notif)
        {
            eon_sm_active = n_array_sm_eon.getAlertEON_SM() + ";" + n_array_sm_eon.getAlert_EON_SM_active();
            Log.d("EON_SM", eon_sm_active);
            if(eon_sm_active.equals("EON_GATEWAY;1"))
            {
                mCheckBoxEonActive.setChecked(true);
            }
            if(eon_sm_active.equals("SM_GATEWAY;1"))
            {
                mCheckBoxEnotePagerActive.setChecked(true);
            }
        }

        //Retrieves value of if sound is enabled
        for (EnoteNotificationArrayObject n_array_sound_active: enotenotification_sound_active)
        {
            app_sound_active = n_array_sound_active.getAlertSoundIsActive();
            Log.d("APP_SOUND_ACTIVE", app_sound_active);
            if(app_sound_active.equals("1"))
            {
                mCheckBoxSoundActive.setChecked(true);
            }
        }
    }

    public void o​n​C​r​e​a​t​e​O​p​t​i​o​n​s​M​e​n​u​(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main_configuration, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_configuration_notif_array:
                Intent launchNotificationMatrix = new Intent(getContext(), ConfigurationNotificationActivity.class);
                launchNotificationMatrix.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(launchNotificationMatrix);
                return true;
            case R.id.menu_configuration_wg_array:
                Intent launchNotificationWorkgroup = new Intent(getContext(), ConfigurationAddWorkGroupActivity.class);
                launchNotificationWorkgroup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(launchNotificationWorkgroup);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public void o​n​C​r​e​a​t​e​O​p​t​i​o​n​s​M​e​n​u​(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_notification_list, menu);
    }*/

    private class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        /*private TextView mNotificationIncPri;
        private TextView mNotificationSloType;
        private TextView mNotificationIncId;
        private TextView mNotificationSloPercent;
        private TextView mNotificationIncLastUpdate;
        private TextView mNotificationIncCustomer;
        private TextView mNotificationIncWorkgroup;

        private EnoteNotificationObject mEnotePagerNotification;*/

        public NotificationHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            /*
            mNotificationIncPri = (TextView)itemView.findViewById(R.id.list_item_notification_inc_pri_text_view);
            mNotificationSloType = (TextView)itemView.findViewById(R.id.list_item_notification_slo_type_text_view);
            mNotificationIncId = (TextView)itemView.findViewById(R.id.list_item_notification_inc_id_text_view);
            mNotificationSloPercent = (TextView)itemView.findViewById(R.id.list_item_notification_slo_percent_text_view);
            mNotificationIncLastUpdate = (TextView)itemView.findViewById(R.id.list_item_notification_title_last_update_text_view);
            mNotificationIncCustomer = (TextView)itemView.findViewById(R.id.list_item_notification_customer_text_view);
            mNotificationIncWorkgroup = (TextView)itemView.findViewById(R.id.list_item_notification_workgroup_text_view);*/
        }

        public void bindNotification(EnoteNotificationObject enotepagernotification)
        {
            /*mEnotePagerNotification = enotepagernotification;
            mNotificationIncPri.setText(mEnotePagerNotification.getIncidentPriority() + ":");
            mNotificationSloType.setText(mEnotePagerNotification.getIncidentSloType() + ":");
            mNotificationIncId.setText(mEnotePagerNotification.getIncidentId());
            mNotificationSloPercent.setText(mEnotePagerNotification.getIncidentSloPercent() + "%");
            mNotificationIncCustomer.setText(mEnotePagerNotification.getIncidentCustomer() + ":");
            mNotificationIncWorkgroup.setText(mEnotePagerNotification.getIncidentWorkGroup());
            mNotificationIncLastUpdate.setText(mEnotePagerNotification.getIncidentReceivedTimeStamp().toString());*/
        }

        @Override
        public void onClick(View v)
        {
            //Toast.makeText(getActivity(), mEnotePagerNotification.getIncidentId() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}


