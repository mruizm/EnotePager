package com.hpe.rumarco.enotepager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;

import java.util.List;


public class CustomSmsPatternMainFragment extends Fragment
{
    private CheckBox mCheckBoxRemedyActive;
    private CheckBox mCheckBoxChangeActive;
    private CheckBox mCheckBoxUnassigTickets;
    private CheckBox mCheckBoxEscalationTickets;
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
        EnoteNotificationCustomQueryToObject enoteNotificationCustomQueryToObject = EnoteNotificationCustomQueryToObject.get(getActivity());
        List<EnoteNotificationCustomObject> enotenotification_custom_notif = enoteNotificationCustomQueryToObject.getEnoteNotificationCustomActive(null);

        String customer_rules_active = "";
        View view = inflater.inflate(R.layout.fragment_configuration_custom_patterns, container, false);
        mCheckBoxRemedyActive = (CheckBox) view.findViewById(R.id.checkbox_enable_remedy);
        mCheckBoxChangeActive = (CheckBox) view.findViewById(R.id.checkbox_enable_change_request);
        mCheckBoxUnassigTickets = (CheckBox) view.findViewById(R.id.checkbox_enable_unassigned_tickets);
        mCheckBoxEscalationTickets = (CheckBox) view.findViewById(R.id.checkbox_enable_escalations);

        //Retirves values of eon and sm activation and set checkboxes
        for (EnoteNotificationCustomObject n_custom: enotenotification_custom_notif)
        {
            customer_rules_active = n_custom.getCustomRuleName() + ";" + n_custom.getIsCustomRuleActive();
            Log.d("REMEDY_RULE", customer_rules_active);
            if(customer_rules_active.equals("REMEDY_RULE;1"))
            {
                mCheckBoxRemedyActive.setChecked(true);
            }
            if(customer_rules_active.equals("CHANGE_RULE;1"))
            {
                mCheckBoxChangeActive.setChecked(true);
            }
            if(customer_rules_active.equals("UNASSIGNED_RULE;1"))
            {
                mCheckBoxUnassigTickets.setChecked(true);
            }
            if(customer_rules_active.equals("ESCALATION_RULE;1"))
            {
                mCheckBoxEscalationTickets.setChecked(true);
            }
        }

        mCheckBoxRemedyActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE, "1");
                    mDatabase.update(NotificationsDbScheme.NotificationsCustomTable.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'REMEDY_RULE'", null);
                    Toast.makeText(getContext(), "Enabled Remedy Notifications!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE, "0");
                    mDatabase.update(NotificationsDbScheme.NotificationsCustomTable.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'REMEDY_RULE'", null);
                    Toast.makeText(getContext(), "Disabled Remedy Notifications!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxChangeActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE, "1");
                    mDatabase.update(NotificationsDbScheme.NotificationsCustomTable.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'CHANGE_RULE'", null);
                    Toast.makeText(getContext(), "Enabled Change Notifications!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsEonArray.Cols.IS_ACTIVE, "0");
                    mDatabase.update(NotificationsDbScheme.NotificationsEonArray.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'CHANGE_RULE'", null);
                    Toast.makeText(getContext(), "Disabled Change Notifications!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxUnassigTickets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE, "1");
                    mDatabase.update(NotificationsDbScheme.NotificationsCustomTable.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'UNASSIGNED_RULE'", null);
                    Toast.makeText(getContext(), "Enabled Unassigned Notifications!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE, "0");
                    mDatabase.update(NotificationsDbScheme.NotificationsCustomTable.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'UNASSIGNED_RULE'", null);
                    Toast.makeText(getContext(), "Disabled Unassigned Notifications!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCheckBoxEscalationTickets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE, "1");
                    mDatabase.update(NotificationsDbScheme.NotificationsCustomTable.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'ESCALATION_RULE'", null);
                    Toast.makeText(getContext(), "Enabled Escalation Notifications!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    args.clear();
                    args.put(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE, "0");
                    mDatabase.update(NotificationsDbScheme.NotificationsCustomTable.NAME, args, NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME+ " = 'ESCALATION_RULE'", null);
                    Toast.makeText(getContext(), "Disabled Escalation Notifications!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        EnoteNotificationCustomQueryToObject enoteNotificationCustomQueryToObject = EnoteNotificationCustomQueryToObject.get(getActivity());
        List<EnoteNotificationCustomObject> enotenotification_custom_notif = enoteNotificationCustomQueryToObject.getEnoteNotificationCustomActive(null);

        String customer_rules_active = "";
        for (EnoteNotificationCustomObject n_custom: enotenotification_custom_notif)
        {
            customer_rules_active = n_custom.getCustomRuleName() + ";" + n_custom.getIsCustomRuleActive();
            Log.d("REMEDY_RULE", customer_rules_active);
            if(customer_rules_active.equals("REMEDY_RULE;1"))
            {
                mCheckBoxRemedyActive.setChecked(true);
            }
            if(customer_rules_active.equals("CHANGE_RULE;1"))
            {
                mCheckBoxChangeActive.setChecked(true);
            }
            if(customer_rules_active.equals("UNASSIGNED_RULE;1"))
            {
                mCheckBoxUnassigTickets.setChecked(true);
            }
            if(customer_rules_active.equals("ESCALATION_RULE;1"))
            {
                mCheckBoxEscalationTickets.setChecked(true);
            }
        }
    }
}



