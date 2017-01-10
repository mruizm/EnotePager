package com.hpe.rumarco.enotepager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by rumarco on 17/12/2016.
 * Class of Activity that contains GUI fragment elements for Configuration Notification Setup
 */
public class ConfigurationNotificationFragment extends Fragment
{
    private CheckBox mCheckBoxTTO_P1_0;
    private CheckBox mCheckBoxTTO_P1_50;
    private CheckBox mCheckBoxTTO_P1_75;
    private CheckBox mCheckBoxTTO_P1_100;

    private CheckBox mCheckBoxTTO_P2_0;
    private CheckBox mCheckBoxTTO_P2_50;
    private CheckBox mCheckBoxTTO_P2_75;
    private CheckBox mCheckBoxTTO_P2_100;

    private CheckBox mCheckBoxTTO_P3_0;
    private CheckBox mCheckBoxTTO_P3_50;
    private CheckBox mCheckBoxTTO_P3_75;
    private CheckBox mCheckBoxTTO_P3_100;

    private CheckBox mCheckBoxTTO_P4_0;
    private CheckBox mCheckBoxTTO_P4_50;
    private CheckBox mCheckBoxTTO_P4_75;
    private CheckBox mCheckBoxTTO_P4_100;

    private CheckBox mCheckBoxTTR_P1_0;
    private CheckBox mCheckBoxTTR_P1_50;
    private CheckBox mCheckBoxTTR_P1_75;
    private CheckBox mCheckBoxTTR_P1_100;

    private CheckBox mCheckBoxTTR_P2_0;
    private CheckBox mCheckBoxTTR_P2_50;
    private CheckBox mCheckBoxTTR_P2_75;
    private CheckBox mCheckBoxTTR_P2_100;

    private CheckBox mCheckBoxTTR_P3_0;
    private CheckBox mCheckBoxTTR_P3_50;
    private CheckBox mCheckBoxTTR_P3_75;
    private CheckBox mCheckBoxTTR_P3_100;

    private CheckBox mCheckBoxTTR_P4_0;
    private CheckBox mCheckBoxTTR_P4_50;
    private CheckBox mCheckBoxTTR_P4_75;
    private CheckBox mCheckBoxTTR_P4_100;

    private StringTokenizer st;
    private SQLiteDatabase mDatabase;
    private ContentValues args = new ContentValues();

    //private NotificationListFragment mNotificationListFragment = new NotificationListFragment();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        //setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //mNotificationListFragment.getEnoteNotificationArray();
        View view = inflater.inflate(R.layout.fragment_configuration_notifications_setup, container, false);
        //Actions for TTO Notification Matrix
        //Actions for TTO CheckBox P1
        mCheckBoxTTO_P1_0 = (CheckBox) view.findViewById(R.id.checkbox_tto_0_p1);
        mCheckBoxTTO_P1_50 = (CheckBox) view.findViewById(R.id.checkbox_tto_50_p1);
        mCheckBoxTTO_P1_75 = (CheckBox) view.findViewById(R.id.checkbox_tto_75_p1);
        mCheckBoxTTO_P1_100 = (CheckBox) view.findViewById(R.id.checkbox_tto_100_p1);

        mCheckBoxTTO_P2_0 = (CheckBox) view.findViewById(R.id.checkbox_tto_0_p2);
        mCheckBoxTTO_P2_50 = (CheckBox) view.findViewById(R.id.checkbox_tto_50_p2);
        mCheckBoxTTO_P2_75 = (CheckBox) view.findViewById(R.id.checkbox_tto_75_p2);
        mCheckBoxTTO_P2_100 = (CheckBox) view.findViewById(R.id.checkbox_tto_100_p2);

        mCheckBoxTTO_P3_0 = (CheckBox) view.findViewById(R.id.checkbox_tto_0_p3);
        mCheckBoxTTO_P3_50 = (CheckBox) view.findViewById(R.id.checkbox_tto_50_p3);
        mCheckBoxTTO_P3_75 = (CheckBox) view.findViewById(R.id.checkbox_tto_75_p3);
        mCheckBoxTTO_P3_100 = (CheckBox) view.findViewById(R.id.checkbox_tto_100_p3);

        mCheckBoxTTO_P4_0 = (CheckBox) view.findViewById(R.id.checkbox_tto_0_p4);
        mCheckBoxTTO_P4_50 = (CheckBox) view.findViewById(R.id.checkbox_tto_50_p4);
        mCheckBoxTTO_P4_75 = (CheckBox) view.findViewById(R.id.checkbox_tto_75_p4);
        mCheckBoxTTO_P4_100 = (CheckBox) view.findViewById(R.id.checkbox_tto_100_p4);

        EnoteNotificationSetupQueryToObject enoteNotificationSetupQueryToObject = EnoteNotificationSetupQueryToObject.get(getActivity());
        List<EnoteNotificationArrayObject> enotenotification_array = enoteNotificationSetupQueryToObject.getEnoteNotificationArray();
        String priority_entry = "";
        String notif_token  = "";

        for (EnoteNotificationArrayObject n_array: enotenotification_array)
        {
            priority_entry = n_array.getAlertTTO_0() + ";" + n_array.getAlertTTO_50() + ";" +n_array.getAlertTTO_75() + ";" + n_array.getAlertTTO_100();
            st = new StringTokenizer(priority_entry, ";");
            while(st.hasMoreTokens()) {
                notif_token = st.nextToken();
                Log.d("NotificationArray",notif_token);
                //P1 Notifications
                if(notif_token.equals("P1_1_0"))
                {
                    mCheckBoxTTO_P1_0.setChecked(true);
                }
                if(notif_token.equals("P1_1_50"))
                {
                    mCheckBoxTTO_P1_50.setChecked(true);
                }
                if(notif_token.equals("P1_1_75"))
                {
                    mCheckBoxTTO_P1_75.setChecked(true);
                }
                if(notif_token.equals("P1_1_100"))
                {
                    mCheckBoxTTO_P1_100.setChecked(true);
                }
                //P2 Notifications
                if(notif_token.equalsIgnoreCase("P2_1_0"))
                {
                    mCheckBoxTTO_P2_0.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P2_1_50"))
                {
                    mCheckBoxTTO_P2_50.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P2_1_75"))
                {
                    mCheckBoxTTO_P2_75.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P2_1_100"))
                {
                    mCheckBoxTTO_P2_100.setChecked(true);
                }

                //P3 Notifications
                if(notif_token.equalsIgnoreCase("P3_1_0"))
                {
                    mCheckBoxTTO_P3_0.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_0",notif_token);
                }
                if(notif_token.equalsIgnoreCase("P3_1_50"))
                {
                    mCheckBoxTTO_P3_50.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_50",notif_token);
                }
                if(notif_token.equalsIgnoreCase("P3_1_75"))
                {
                    mCheckBoxTTO_P3_75.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_75",notif_token);
                }
                if(notif_token.equalsIgnoreCase("P3_1_100"))
                {
                    mCheckBoxTTO_P3_100.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_100",notif_token);
                }

                //P4 Notifications
                if(notif_token.equalsIgnoreCase("P4_1_0"))
                {
                    mCheckBoxTTO_P4_0.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P4_1_50"))
                {
                    mCheckBoxTTO_P4_50.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P4_1_75"))
                {
                    mCheckBoxTTO_P4_75.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P4_1_100"))
                {
                    mCheckBoxTTO_P4_100.setChecked(true);
                }

            }
            //Log.d("NotificationMatrix",tolog);
        }


        mCheckBoxTTO_P1_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P1_1_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P1_0_0'", null);
                    Toast.makeText(getContext(), "Enabled TTO P1 0% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P1_0_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P1_1_0'", null);
                    Toast.makeText(getContext(), "Disabled TTO P1 0% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P1_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P1_1_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P1_0_50'", null);
                    Toast.makeText(getContext(), "Enabled TTO P1 50% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P1_0_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P1_1_50'", null);
                    Toast.makeText(getContext(), "Disabled TTO P1 50% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P1_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P1_1_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P1_0_75'", null);
                    Toast.makeText(getContext(), "Enabled TTO P1 75% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P1_0_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P1_1_75'", null);
                    Toast.makeText(getContext(), "Disabled TTO P1 75% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P1_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P1_1_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P1_0_100'", null);
                    Toast.makeText(getContext(), "Enabled TTO P1 100% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P1_0_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P1_1_100'", null);
                    Toast.makeText(getContext(), "Disabled TTO P1 100% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Actions for TTO CheckBox P2
        mCheckBoxTTO_P2_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P2_1_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P2_0_0'", null);
                    Toast.makeText(getContext(), "Enabled TTO P2 0% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P2_0_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P2_1_0'", null);
                    Toast.makeText(getContext(), "Disabled TTO P2 0% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P2_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P2_1_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P2_0_50'", null);
                    Toast.makeText(getContext(), "Enabled TTO P2 50% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P2_0_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P2_1_50'", null);
                    Toast.makeText(getContext(), "Disabled TTO P2 50% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P2_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P2_1_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P2_0_75'", null);
                    Toast.makeText(getContext(), "Enabled TTO P2 75% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P2_0_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P2_1_75'", null);
                    Toast.makeText(getContext(), "Disabled TTO P2 75% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P2_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P2_1_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P2_0_100'", null);
                    Toast.makeText(getContext(), "Enabled TTO P2 100% Notification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P2_0_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P2_1_100'", null);
                    Toast.makeText(getContext(), "Disabled TTO P2 100% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Actions for TTO CheckBox P3
        mCheckBoxTTO_P3_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P3_1_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P3_0_0'", null);
                    Toast.makeText(getContext(), "Enabled TTO P3 0% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P3_0_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P3_1_0'", null);
                    Toast.makeText(getContext(), "Disabled TTO P3 0% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P3_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P3_1_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P3_0_50'", null);
                    Toast.makeText(getContext(), "Enabled TTO P3 50% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P3_0_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P3_1_50'", null);
                    Toast.makeText(getContext(), "Disabled TTO P3 50% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P3_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P3_1_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P3_0_75'", null);
                    Toast.makeText(getContext(), "Enabled TTO P3 75% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P3_0_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P3_1_75'", null);
                    Toast.makeText(getContext(), "Disabled TTO P3 75% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P3_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P3_1_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P3_0_100'", null);
                    Toast.makeText(getContext(), "Enabled TTO P3 100% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P3_0_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P3_1_100'", null);
                    Toast.makeText(getContext(), "Disabled TTO P3 100% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Actions for TTO CheckBox P4
        mCheckBoxTTO_P4_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P4_1_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P4_0_0'", null);
                    Toast.makeText(getContext(), "Enabled TTO P4 0% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT, "P4_0_0");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT + " = 'P4_1_0'", null);
                    Toast.makeText(getContext(), "Disabled TTO P4 0% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P4_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P4_1_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P4_0_50'", null);
                    Toast.makeText(getContext(), "Enabled TTO P4 50% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT, "P4_0_50");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT + " = 'P4_1_50'", null);
                    Toast.makeText(getContext(), "Disabled TTO P4 50% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P4_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P4_1_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P4_0_75'", null);
                    Toast.makeText(getContext(), "Enabled TTO P4 75% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT, "P4_0_75");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT + " = 'P4_1_75'", null);
                    Toast.makeText(getContext(), "Disabled TTO P4 75% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBoxTTO_P4_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P4_1_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P4_0_100'", null);
                    Toast.makeText(getContext(), "Enabled TTO P4 100% Notifications", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT, "P4_0_100");
                    mDatabase.update(NotificationsDbScheme.NotificationsSloTTOArray.NAME, args, NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT + " = 'P4_1_100'", null);
                    Toast.makeText(getContext(), "Disabled TTO P4 100% Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Actions for TTO Notification Matrix
        //Actions for TTR CheckBox P1
        mCheckBoxTTR_P1_0 = (CheckBox) view.findViewById(R.id.checkbox_ttr_0_p1);
        mCheckBoxTTR_P1_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P1 0%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P1 0%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P1_50 = (CheckBox) view.findViewById(R.id.checkbox_ttr_50_p1);
        mCheckBoxTTR_P1_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P1 50%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P1 50%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P1_75 = (CheckBox) view.findViewById(R.id.checkbox_ttr_75_p1);
        mCheckBoxTTR_P1_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P1 75%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P1 75%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P1_100 = (CheckBox) view.findViewById(R.id.checkbox_ttr_100_p1);
        mCheckBoxTTR_P1_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P1 100%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P1 100%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Actions for TTR CheckBox P2
        mCheckBoxTTR_P2_0 = (CheckBox) view.findViewById(R.id.checkbox_ttr_0_p2);
        mCheckBoxTTR_P2_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P2 0%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P2 0%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P2_50 = (CheckBox) view.findViewById(R.id.checkbox_ttr_50_p2);
        mCheckBoxTTR_P2_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P2 50%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P2 50%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P2_75 = (CheckBox) view.findViewById(R.id.checkbox_ttr_75_p2);
        mCheckBoxTTR_P2_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P2 75%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P2 75%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P2_100 = (CheckBox) view.findViewById(R.id.checkbox_ttr_100_p2);
        mCheckBoxTTR_P2_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P2 100%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P2 100%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Actions for TTR CheckBox P3
        mCheckBoxTTR_P3_0 = (CheckBox) view.findViewById(R.id.checkbox_ttr_0_p3);
        mCheckBoxTTR_P3_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P3 0%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P3 0%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P3_50 = (CheckBox) view.findViewById(R.id.checkbox_ttr_50_p3);
        mCheckBoxTTR_P3_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P3 50%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR 3 50%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P3_75 = (CheckBox) view.findViewById(R.id.checkbox_ttr_75_p3);
        mCheckBoxTTR_P3_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P3 75%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P3 75%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P3_100 = (CheckBox) view.findViewById(R.id.checkbox_ttr_100_p3);
        mCheckBoxTTR_P3_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P3 100%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P3 100%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Actions for TTR CheckBox P4
        mCheckBoxTTR_P4_0 = (CheckBox) view.findViewById(R.id.checkbox_ttr_0_p4);
        mCheckBoxTTR_P4_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P4 0%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P4 0%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P4_50 = (CheckBox) view.findViewById(R.id.checkbox_ttr_50_p4);
        mCheckBoxTTR_P4_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P4 50%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P4 50%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P4_75 = (CheckBox) view.findViewById(R.id.checkbox_ttr_75_p4);
        mCheckBoxTTR_P4_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P4 75%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P4 75%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxTTR_P4_100 = (CheckBox) view.findViewById(R.id.checkbox_ttr_100_p4);
        mCheckBoxTTR_P4_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(getContext(), "Checked TTR P4 100%", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Un-Checked TTR P4 100%", Toast.LENGTH_SHORT).show();
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
        List<EnoteNotificationArrayObject> enotenotification_array = enoteNotificationSetupQueryToObject.getEnoteNotificationArray();
        String priority_entry = "";
        String notif_token  = "";

        for (EnoteNotificationArrayObject n_array: enotenotification_array)
        {
            priority_entry = n_array.getAlertTTO_0() + ";" + n_array.getAlertTTO_50() + ";" +n_array.getAlertTTO_75() + ";" + n_array.getAlertTTO_100();
            st = new StringTokenizer(priority_entry, ";");
            while(st.hasMoreTokens()) {
                notif_token = st.nextToken();
                Log.d("NotificationArray",notif_token);
                //P1 Notifications
                if(notif_token.equals("P1_1_0"))
                {
                    mCheckBoxTTO_P1_0.setChecked(true);
                }
                if(notif_token.equals("P1_1_50"))
                {
                    mCheckBoxTTO_P1_50.setChecked(true);
                }
                if(notif_token.equals("P1_1_75"))
                {
                    mCheckBoxTTO_P1_75.setChecked(true);
                }
                if(notif_token.equals("P1_1_100"))
                {
                    mCheckBoxTTO_P1_100.setChecked(true);
                }
                //P2 Notifications
                if(notif_token.equalsIgnoreCase("P2_1_0"))
                {
                    mCheckBoxTTO_P2_0.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P2_1_50"))
                {
                    mCheckBoxTTO_P2_50.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P2_1_75"))
                {
                    mCheckBoxTTO_P2_75.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P2_1_100"))
                {
                    mCheckBoxTTO_P2_100.setChecked(true);
                }

                //P3 Notifications
                if(notif_token.equalsIgnoreCase("P3_1_0"))
                {
                    mCheckBoxTTO_P3_0.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_0",notif_token);
                }
                if(notif_token.equalsIgnoreCase("P3_1_50"))
                {
                    mCheckBoxTTO_P3_50.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_50",notif_token);
                }
                if(notif_token.equalsIgnoreCase("P3_1_75"))
                {
                    mCheckBoxTTO_P3_75.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_75",notif_token);
                }
                if(notif_token.equalsIgnoreCase("P3_1_100"))
                {
                    mCheckBoxTTO_P3_100.setChecked(true);
                    Log.d("mCheckBoxTTO_P3_100",notif_token);
                }

                //P4 Notifications
                if(notif_token.equalsIgnoreCase("P4_1_0"))
                {
                    mCheckBoxTTO_P4_0.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P4_1_50"))
                {
                    mCheckBoxTTO_P4_50.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P4_1_75"))
                {
                    mCheckBoxTTO_P4_75.setChecked(true);
                }
                if(notif_token.equalsIgnoreCase("P4_1_100"))
                {
                    mCheckBoxTTO_P4_100.setChecked(true);
                }

            }
            //Log.d("NotificationMatrix",tolog);
        }
        /*
        String tolog = "OnResume\n";
        Log.d("onResume",tolog);
        EnoteNotificationEvent mEnoteNotificationEvent = new EnoteNotificationEvent(getContext());
        updateUI();*/
        //mNotificationListFragment.getEnoteNotificationArray();
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
            /*
            itemView.setOnClickListener(this);
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
