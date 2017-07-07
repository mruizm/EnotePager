package com.hpe.rumarco.enotepager.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.hpe.rumarco.enotepager.EnoteNotificationArrayObject;
import com.hpe.rumarco.enotepager.EnoteNotificationObject;
import com.hpe.rumarco.enotepager.EnoteWorkGroupObject;
import com.hpe.rumarco.enotepager.EnoteNotificationCustomObject;

/**
 * Created by rumarco on 09/12/2016.
 */
public class NotificationCursorWrapper extends CursorWrapper{
    public NotificationCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public EnoteNotificationObject getEnoteNotification()
    {
        String notifTicketPriority = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTPRIORITY));
        String notifTicketId = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTID));
        String notifTicketSloType = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTSLOTYPE));
        String notifTicketSloPercent = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTSLOPERCENT));
        String notifTicketCustomer = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTCUSTOMER));
        String notifTicketWorkgroup = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTWORKGROUP));
        String notifTicketAck = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK));
        String notifTicketTimeStamp = getString(getColumnIndex(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTRECEIVEDDATE));

        EnoteNotificationObject notification_entry = new EnoteNotificationObject();
        notification_entry.setIncidentPriority(notifTicketPriority);
        notification_entry.setIncidentId(notifTicketId);
        notification_entry.setIncidentSloType(notifTicketSloType);
        notification_entry.setIncidentSloPercent(notifTicketSloPercent);
        notification_entry.setIncidentCustomer(notifTicketCustomer);
        notification_entry.setIncidentWorkGroup(notifTicketWorkgroup);
        notification_entry.setIncidentReceivedTimeStamp(notifTicketTimeStamp);

        return notification_entry;
    }

    public EnoteNotificationArrayObject getEnoteNotificationArray()
    {
        String tto_0 = getString(getColumnIndex(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_0_PERCENT));
        String tto_50 = getString(getColumnIndex(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_50_PERCENT));
        String tto_75 = getString(getColumnIndex(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_75_PERCENT));
        String tto_100 = getString(getColumnIndex(NotificationsDbScheme.NotificationsSloTTOArray.Cols.TTO_100_PERCENT));

        EnoteNotificationArrayObject notification_array = new EnoteNotificationArrayObject();
        notification_array.setAlertTTO_0(tto_0);
        notification_array.setAlertTTO_50(tto_50);
        notification_array.setAlertTTO_75(tto_75);
        notification_array.setAlertTTO_100(tto_100);

        return notification_array;
    }

    public EnoteNotificationArrayObject getEnoteNotificationEonSmActive()
    {
        String fw_name = getString(getColumnIndex(NotificationsDbScheme.NotificationsEonArray.Cols.FW_NOTIF_GATE));
        String fw_is_active = getString(getColumnIndex(NotificationsDbScheme.NotificationsEonArray.Cols.IS_ACTIVE));
        //String fw_last_update = getString(getColumnIndex(NotificationsDbScheme.NotificationsEonArray.Cols.LAST_UPDATE));

        EnoteNotificationArrayObject notification_array = new EnoteNotificationArrayObject();
        notification_array.setAlertEON_SM(fw_name);
        notification_array.setAlert_EON_SM_active(fw_is_active);

        return notification_array;
    }

    public EnoteNotificationArrayObject getEnoteSoundIsActive()
    {
        String is_sound_active = getString(getColumnIndex(NotificationsDbScheme.NotificationsSoundActive.Cols.IS_SOUND_ACTIVE));

        EnoteNotificationArrayObject notification_array = new EnoteNotificationArrayObject();
        notification_array.setAlertSoundIsActive(is_sound_active);

        return notification_array;
    }

    public EnoteWorkGroupObject getWorkGroupName()
    {
        String hpsm_wg_name = getString(getColumnIndex(NotificationsDbScheme.HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME));

        EnoteWorkGroupObject workgroup_name = new EnoteWorkGroupObject();
        workgroup_name.setHpSmWgName(hpsm_wg_name);

        return workgroup_name;
    }

    public EnoteNotificationCustomObject getCustomNotificationsIsActive()
    {
        String custome_rule_name = getString(getColumnIndex(NotificationsDbScheme.NotificationsCustomTable.Cols.CUSTOM_RULE_NAME));
        String is_custome_rule_active = getString(getColumnIndex(NotificationsDbScheme.NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE));

        EnoteNotificationCustomObject custom_notification_entry = new EnoteNotificationCustomObject();
        custom_notification_entry.setCustomRuleName(custome_rule_name);
        custom_notification_entry.setIsCustomRuleActive(is_custome_rule_active);
        return custom_notification_entry;
    }
}
