package com.hpe.rumarco.enotepager.database;

//Class that defines tables used by the eNotePager Application
public class NotificationsDbScheme
{
    //eNotePager table that holds processed hpsm sms and/or eon sms
    public static final class NotificationsTable
    {
        public static final String NAME = "enote_notifications";

        public static final class Cols
        {
            public static final String INCIDENTID = "incident_id";
            public static final String INCIDENTPRIORITY = "incident_priority";
            public static final String INCIDENTCUSTOMER = "incident_customer";
            public static final String INCIDENTWORKGROUP = "incident_workgroup";
            public static final String INCIDENTSLOTYPE = "incident_slo_type";
            public static final String INCIDENTSLOPERCENT = "incident_slo_percent";
            public static final String INCIDENTACK = "incident_ack";
            public static final String INCIDENTRECEIVEDDATE = "incident_received_date";
        }
    }

    //eNotePager table that holds TTO active notification matrix
    public static final class NotificationsSloTTOArray
    {
        public static final String NAME = "enote_slo_tto_array";

        public static final class Cols
        {
            public static final String TTO_0_PERCENT = "tto_0_percent";
            public static final String TTO_50_PERCENT = "tto_50_percent";
            public static final String TTO_75_PERCENT = "tto_75_percent";
            public static final String TTO_100_PERCENT = "tto_100_percent";
            public static final String TTO_LAST_UPDATE = "tto_last_update";
        }
    }

    //eNotePager table that holds TTR active notification matrix
    public static final class NotificationsSloTTRArray
    {
        public static final String NAME = "enote_slo_ttr_array";

        public static final class Cols
        {
            public static final String TTR_0_PERCENT  = "ttr_0_percent";
            public static final String TTR_50_PERCENT = "ttr_50_percent";
            public static final String TTR_75_PERCENT = "ttr_75_percent";
            public static final String TTR_100_PERCENT = "ttr_100_percent";
            public static final String TTR_LAST_UPDATE = "ttr_last_update";
        }
    }

    //eNotePager table that holds whether the app will process hpsm sms and/or eon sms
    public static final class NotificationsEonArray
    {
        public static final String NAME = "enote_sm_eon_array";

        public static final class Cols
        {
            public static final String FW_NOTIF_GATE = "fw_gate_name";
            public static final String IS_ACTIVE = "fw_gate_is_active";
            public static final String LAST_UPDATE = "sm_eon_last_update";
        }
    }

    //eNotePager table that holds whether the app will sound or log only
    public static final class NotificationsSoundActive
    {
        public static final String NAME = "enote_sm_eon_sound_active";

        public static final class Cols
        {
            public static final String IS_SOUND_ACTIVE = "enote_app_is_sound_active";
        }
    }

    //eNotePager table that holds list of active HPSM workgroups to get notifications for
    public static final class HpSmWorkgroupList
    {
        public static final String NAME = "enote_app_hpsm_workgroup";

        public static final class Cols
        {
            public static final String HPSM_WORKGROUP_NAME = "enote_app_hpsm_wg_name";
            public static final String HPSM_WORKGROUP_NAME_DATE_ADDED = "enote_app_hpsm_wg_added";
        }
    }

    //eNotePager table that holds list of active custom notifications
    public static final class NotificationsCustomTable
    {
        public static final String NAME = "enote_custom_notifications";

        public static final class Cols
        {
            public static final String CUSTOM_RULE_NAME = "custom_rule_name";
            public static final String IS_CUSTOM_RULE_ACTIVE = "custom_rule_is_active";
        }
    }
}
