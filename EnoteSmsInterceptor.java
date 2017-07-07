/*eNote SMS Alerts
'HP : N-IM17207085 (Priority 1) - Dispatched' : 'N-INCSSP-GDO-GPS'
'OS DO TOOLS : N-IM17207085 (Priority 2) - TTO 50 Percent' : 'N-INCSSP-GDO-GPS'
'CVTC : N-IM17207085 (Priority 3) - TTO 75 Percent' : 'L-INCSSP-GDO-GPS'
'KPMI : N-IM17207085 (Priority 3) - TTIR 75 Percent' : 'N-INCSSP-GDO-GPS'
'CVTC : N-IM17207085 (Priority 3) - TTO deadline' : 'L-INCSSP-GDO-GPS'
'KPMI : N-IM17207085 (Priority 4) - TTO deadline' : 'N-INCSSP-GDO-GPS'
'BAKM : N-IM17207085 (Priority 4) - TTIR deadline' : 'N-INCSSP-GDO-GPS'
eNote PROD-AMS Action Required:
Alfonso Garbanzo requirements:
Remedy Incident Assignment Ticket # IM13061954 has been assigned toais security - mainframe and Severity 3
Remedy Incident Assignment Ticket # IM13061954 has been assigned toais security - mainframe and Severity 3 RTOP
EMERGENCY CHANGE REQUEST CM1044042 HAS BEEN OPENED TO ais security - mainframe
** ESCALATION ** Remedy Incident Ticket # IM12345678 needs immediateattention!
Alert - There Are Unassigned Tickets - Automation - Alert - There Are Unassigned Tickets Tickets: 4 - P1/P2 Included
*/
//v1.0.1    Added feature to not display SMS notification if eNote pattern found but not within matrix
//          Show notification if SMS pattern different than eNote's pattern
package com.hpe.rumarco.enotepager;

import android.content.*;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.AUDIO_SERVICE;

public class EnoteSmsInterceptor extends BroadcastReceiver {

    //private Context mContext;
    private SQLiteDatabase mDatabase;
    private static SoundNotification mMusicPlayer;
    private MediaPlayer mp;
    private AudioManager am;
    Context context1;
    private final static String TAG = "AudioFocus";
    String priority_entry = "";
    String eon_sm_active = "";
    String enotepager_sound_active = "";
    String enotepager_wg_hpsm = "";
    String whereClause = "";
    String customer_rules_active;

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this.abortBroadcast();
        context1 = context.getApplicationContext();
        am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        //int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = 0.7f;
        int seventyVolume = (int) (maxVolume*percent);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, seventyVolume, 0);
        EnoteNotificationSetupQueryToObject enoteNotificationSetupQueryToObject = EnoteNotificationSetupQueryToObject.get(context1);
        EnoteAddWorkGroupQueryToObject enoteAddWorkGroupQueryToObject = EnoteAddWorkGroupQueryToObject.get(context1);
        EnoteNotificationCustomQueryToObject enoteNotificationCustomQueryToObject = EnoteNotificationCustomQueryToObject.get(context1);

        //EnoteNotificationSetupQueryToObject enoteNotificationSetupEonSm = EnoteNotificationSetupQueryToObject.get(context1);
        List<EnoteNotificationArrayObject> enotenotification_array;
        List<EnoteNotificationArrayObject> enotenotification_array_eon_sm;
        List<EnoteNotificationArrayObject> enotenotification_sound_active;
        List<EnoteWorkGroupObject> enotenotification_hpsm_acive_wg_array;

        List<EnoteNotificationCustomObject> enotenotification_custom_notif;

        //Boolean variables used to determine funcionality of eNotePager
        boolean sound_alert_sm = false;
        boolean sound_alert_eon = false;
        boolean sound_alert_remedy = false;
        boolean sound_alert_escalation = false;
        boolean sound_alert_change = false;
        boolean sound_alert_unassigned = false;

        boolean eon_boolean_receive_notification = false;
        boolean sm_boolean_receive_notification = false;
        boolean enotepager_sound_active_bool = false;
        boolean sm_boolean_wg_is_acive = false;
        boolean sm_pattern_at_least_matched = false;

        boolean remedy_boolean_received_notif = false;
        boolean change_boolean_received_notif = false;
        boolean unassiged_boolean_received_notif = false;
        boolean escalation_boolean_received_notif = false;


        //StringTokenizer used to decompose configuration values
        StringTokenizer st;         //To decompose slo_type obtained from SMS
        StringTokenizer st2;        //To decompose notification thresholds user settings
        StringTokenizer st3;        //To deoompose if EON and SM if active from user settings

        final EnoteNotificationObject mReceivedNotification = new EnoteNotificationObject();
        //Pattern notification_pattern = Pattern.compile("\'([\\w\\s]+)\\s+:\\s+([\\w|\\d|-]+)\\s+\\(([\\w|\\s\\d]+)\\)\\s+-\\s+([\\w|\\d|\\s]+)\'\\s+:\\s+\'([\\w|\\d|-]+)\'");
        Pattern notification_pattern = Pattern.compile("\\\\?\'([\\w\\s]+)\\s+:\\s+([\\w|\\d|-]+[\\d]+?)\\s+\\(([\\w|\\s\\d]+)\\)\\s+-\\s+([\\w|\\d|\\s]+)\\\\?'\\s+:\\s+\\\\?'([\\w|\\d|-]+)\\\\?\'");
        //Pattern eon_notification_patter = Pattern.compile("eNote PROD-AMS Action Required.*\\s+?;\\s+?VRU Event Number:\\s+?(\\d+)\\s+?.*CI:\\s+([\\s\\d]+).*");
        Pattern eon_notification_patter = Pattern.compile("eNote PROD-AMS Action Required:.*;.*VRU Event Number:\\s+(\\d+)\\s?;.*CI:\\s?([\\d\\w]+)");

        Pattern remedy_pattern = Pattern.compile("Remedy Incident Assignment Ticket #\\s+(IM\\d+) has been assigned to\\s?(ais security - mainframe) and (.*)");
        Pattern escalation_pattern = Pattern.compile("\\*\\*\\s?(ESCALATION)\\s?\\*\\*\\s?(Remedy)\\s?Incident\\s?Ticket\\s?#\\s?(IM\\d+)\\s?needs\\s?immediate\\s?attention!");
        Pattern change_pattern = Pattern.compile("EMERGENCY\\s?CHANGE\\s?REQUEST\\s?(.*)\\s?HAS\\s?BEEN\\s?OPENED\\s?TO\\s?(ais\\s?security\\s?-\\s?mainframe)");
        Pattern unassigned_patter = Pattern.compile("Alert\\s?-\\s?There\\s[a|A]re\\s?[u|U]nassigned\\s?[t|T]ickets\\s?Tickets:\\s?(\\d+)\\s?-\\s?(.*)\\s[i|I]ncluded");

        //Init sqlite database from query operations (in/out data)
        mDatabase = new NotificationsBaseHelper(context1).getWritableDatabase();

        //Get the list of HPSM workgroups from user settings
        enotenotification_hpsm_acive_wg_array = enoteAddWorkGroupQueryToObject.getEnoteNotificationActiveWorkGroups();

        //Checks whether SM and EON checkbox flags are active
        enotenotification_array_eon_sm = enoteNotificationSetupQueryToObject.getEnoteNotificationSmEonActive(null);
        for (EnoteNotificationArrayObject n_array_sm_eon: enotenotification_array_eon_sm)
        {
            eon_sm_active = n_array_sm_eon.getAlertEON_SM() + ";" + n_array_sm_eon.getAlert_EON_SM_active();
            if(eon_sm_active.contains("EON_GATEWAY;1"))
            {
                eon_boolean_receive_notification = true;
            }
            if(eon_sm_active.contains(("SM_GATEWAY;1")))
            {
                sm_boolean_receive_notification = true;
            }
            Log.d("Query",eon_sm_active);
        }

        //Checks whether eNotePager sound checkbox flag is active
        enotenotification_sound_active = enoteNotificationSetupQueryToObject.getEnoteNotificationSoundActive(null);
        for (EnoteNotificationArrayObject n_array_sound_active: enotenotification_sound_active)
        {
            enotepager_sound_active = n_array_sound_active.getAlertSoundIsActive();
            Log.d("APP_SOUND_ACTIVE", enotepager_sound_active);
            if(enotepager_sound_active.equals("1"))
            {
                enotepager_sound_active_bool = true;
            }
        }

        enotenotification_custom_notif = enoteNotificationCustomQueryToObject.getEnoteNotificationCustomActive(null);
        for (EnoteNotificationCustomObject n_custom: enotenotification_custom_notif)
        {
            customer_rules_active = n_custom.getCustomRuleName() + ";" + n_custom.getIsCustomRuleActive();
            Log.d("REMEDY_RULE", customer_rules_active);
            if(customer_rules_active.equals("REMEDY_RULE;1"))
            {
                remedy_boolean_received_notif = true;
            }
            if(customer_rules_active.equals("CHANGE_RULE;1"))
            {
                change_boolean_received_notif = true;
            }
            if(customer_rules_active.equals("UNASSIGNED_RULE;1"))
            {
                unassiged_boolean_received_notif = true;
            }
            if(customer_rules_active.equals("ESCALATION_RULE;1"))
            {
                escalation_boolean_received_notif = true;
            }
        }

        //if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            final EnoteNotificationEvent mEnoteNotificationEvent = new EnoteNotificationEvent(context1);
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_body;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_body = msgs[i].getMessageBody();
                        Matcher pattern_matcher = notification_pattern.matcher(msg_body);
                        Matcher pattern_matcher_eon = eon_notification_patter.matcher(msg_body);
                        Matcher pattern_matcher_remedy = remedy_pattern.matcher(msg_body);
                        Matcher pattern_matcher_escalation = escalation_pattern.matcher(msg_body);
                        Matcher pattern_matcher_change = change_pattern.matcher(msg_body);
                        Matcher pattern_matcher_unassigned = unassigned_patter.matcher(msg_body);

                        //Process SMS if match eNote SMS notification
                        if (pattern_matcher.find()){
                            //this.abortBroadcast();
                            //Valid message, now send it to DB maybe? or just log it for now
                            String tolog = "Log: ["+getreadabledate()+"] New Message: "+msg_body+"\n";
                            Log.d("GotSm",tolog);

                            //Separate SMS fields for database processing
                            String inc_customer = pattern_matcher.group(1);
                            String inc_id = pattern_matcher.group(2);
                            String inc_priority = pattern_matcher.group(3);
                            String inc_slo_type_percent = pattern_matcher.group(4);
                            String inc_workgroup = pattern_matcher.group(5);

                            //Validate if the HPSM workgroup obtained from the SMS is contained within user's workgroup settings
                            for (EnoteWorkGroupObject n_array_active_wg: enotenotification_hpsm_acive_wg_array)
                            {
                                enotepager_wg_hpsm = n_array_active_wg.getHpSmWgName();
                                Log.d("APP_SOUND_ACTIVE", enotepager_sound_active);
                                if(enotepager_wg_hpsm.equals(inc_workgroup))
                                {
                                    sm_boolean_wg_is_acive = true;
                                }
                            }

                            //Adds separated SMS fields into EnoteNotificationObject object
                            mReceivedNotification.setIncidentCustomer(inc_customer);
                            mReceivedNotification.setIncidentId(inc_id);
                            if (inc_priority.equalsIgnoreCase("Priority 1"))
                            {
                                mReceivedNotification.setIncidentPriority("P1");
                            }
                            if (inc_priority.equalsIgnoreCase("Priority 2"))
                            {
                                mReceivedNotification.setIncidentPriority("P2");
                            }
                            if (inc_priority.equalsIgnoreCase("Priority 3"))
                            {
                                mReceivedNotification.setIncidentPriority("P3");
                            }
                            if (inc_priority.equalsIgnoreCase("Priority 4"))
                            {
                                mReceivedNotification.setIncidentPriority("P4");
                            }

                            st = new StringTokenizer(inc_slo_type_percent, " ");

                            if (inc_slo_type_percent.equalsIgnoreCase("Dispatched"))
                            {
                                mReceivedNotification.setIncidentSloType("TTO");
                                mReceivedNotification.setIncidentSloPercent("0%");
                            }
                            else{
                                while(st.hasMoreTokens()){
                                    String notif_token = st.nextToken();
                                    //Log.d("NotificationAdded",notif_token);
                                    if(notif_token.equalsIgnoreCase("TTO") || notif_token.equalsIgnoreCase("TTIR") || notif_token.equalsIgnoreCase("SPTTC")){
                                        mReceivedNotification.setIncidentSloType("TTO");
                                    }
                                    if(notif_token.equalsIgnoreCase("50") || notif_token.equalsIgnoreCase("75") || notif_token.equalsIgnoreCase("deadline")){
                                        if (notif_token.equalsIgnoreCase("deadline")){
                                            mReceivedNotification.setIncidentSloPercent("100%");
                                        }
                                        else{
                                            mReceivedNotification.setIncidentSloPercent(notif_token+"%");
                                        }
                                    }
                                }
                            }
                            mReceivedNotification.setIncidentWorkGroup(inc_workgroup);
                            mReceivedNotification.setIncidentAck("NO_ACK");
                            Log.d("SM_NotificationAdded",tolog);
                            //Insert EnoteNotificationObject values into sqlite database
                            ContentValues values = getContentValues(mReceivedNotification);
                            //mDatabase.insert(NotificationsDbScheme.NotificationsTable.NAME, null, values);
                            String percent_inc_slo = mReceivedNotification.getIncidentSloPercent();
                            String regex_percent = "%";
                            Pattern p = Pattern.compile(regex_percent);
                            Matcher m = p.matcher(percent_inc_slo);
                            percent_inc_slo = m.replaceAll("");


                            whereClause = " tto_" + percent_inc_slo + "_percent like '" + mReceivedNotification.getIncidentPriority() + "_%'";
                            Log.d("Query",whereClause);
                            enotenotification_array = enoteNotificationSetupQueryToObject.getEnoteNotificationArrayPrio(whereClause);
                            for (EnoteNotificationArrayObject n_array: enotenotification_array)
                            {
                                priority_entry = n_array.getAlertTTO_0() + ";" + n_array.getAlertTTO_50() + ";" + n_array.getAlertTTO_75() + ";" + n_array.getAlertTTO_100();
                                Log.d("Query",priority_entry);
                            }
                            st2 = new StringTokenizer(priority_entry, ";");
                            String notif_token_2;
                            if(sm_boolean_receive_notification && sm_boolean_wg_is_acive)
                            {
                                Log.d("NotificationArray", "SM_ALERT_ACTIVE");
                                while(st2.hasMoreTokens()) {
                                    notif_token_2 = st2.nextToken()+"%";
                                    Log.d("NotificationArray", notif_token_2);
                                    {
                                        String alert_prio_composed = mReceivedNotification.getIncidentPriority() + "_1_" + mReceivedNotification.getIncidentSloPercent();
                                        Log.d("NotificationArray", alert_prio_composed);
                                        if (notif_token_2.contains(mReceivedNotification.getIncidentPriority() + "_1_" + mReceivedNotification.getIncidentSloPercent()))
                                        {
                                            sound_alert_sm = true;
                                        }
                                    }
                                }
                            }

                            //Displays TOAST
                            /*Toast.makeText(context, inc_customer + " " + " " + mReceivedNotification.getIncidentId() + " " + mReceivedNotification.getIncidentPriority()
                                    + " " + mReceivedNotification.getIncidentSloType() + " " + mReceivedNotification.getIncidentSloPercent() + " " + mReceivedNotification.getIncidentWorkGroup() + " " + mReceivedNotification.getIncidentReceivedTimeStamp(), Toast.LENGTH_SHORT).show();*/

                            //Timer to avoid overlap of sounds between default SMS app and eNotePager
                            if (sound_alert_sm)
                            {
                                if (enotepager_sound_active_bool)
                                {
                                    if (mMusicPlayer == null) {
                                        mMusicPlayer = SoundNotification.getInstance(context1);
                                    }
                                    mMusicPlayer.play();
                                }
                                mDatabase.insert(NotificationsDbScheme.NotificationsTable.NAME, null, values);
                                Log.d("NotificationArray", "TRUE");
                                mEnoteNotificationEvent.showEnoteNotification("New HPSM Ticket In Queue!", mReceivedNotification.getIncidentSloType()+":", mReceivedNotification.getIncidentSloPercent(), mReceivedNotification.getIncidentPriority()+":", mReceivedNotification.getIncidentId());
                                return;
                            }
                            //If priority is not defined in matrix
                            else
                            {
                                Log.d("NotificationArray", "Priority not within matrix");
                                return;
                            }
                        }
                        if (pattern_matcher_eon.find())
                        {
                            //this.abortBroadcast();
                            String tolog = "Log: ["+getreadabledate()+"] New Message: "+msg_body+"\n";
                            Log.d("GotEON",tolog);
                            String eon_vru_number = pattern_matcher_eon.group(1);
                            String eon_ci_name = pattern_matcher_eon.group(2);
                            Log.d("GotEON",eon_vru_number);
                            Log.d("GotEON",eon_ci_name);
                            mReceivedNotification.setIncidentPriority("Escalation");
                            mReceivedNotification.setIncidentId("EON");
                            mReceivedNotification.setIncidentSloType("VRU Event Number");
                            mReceivedNotification.setIncidentSloPercent(eon_vru_number);
                            mReceivedNotification.setIncidentCustomer("CI");
                            mReceivedNotification.setIncidentWorkGroup(eon_ci_name);
                            mReceivedNotification.setIncidentAck("NO_ACK");

                            Log.d("EON_NotificationAdded",tolog);
                            //Insert EnoteNotificationObject values into sqlite database
                            ContentValues values = getContentValues(mReceivedNotification);


                            if(eon_boolean_receive_notification)
                            {
                                mDatabase.insert(NotificationsDbScheme.NotificationsTable.NAME, null, values);
                                sound_alert_eon = true;
                            }
                            if(sound_alert_eon)
                            {
                                mEnoteNotificationEvent.showEnoteNotification("New EON Escalation!", mReceivedNotification.getIncidentSloType()+":", mReceivedNotification.getIncidentSloPercent(), mReceivedNotification.getIncidentPriority()+":", mReceivedNotification.getIncidentId());
                                if (enotepager_sound_active_bool)
                                {
                                    if (mMusicPlayer == null) {
                                        mMusicPlayer = SoundNotification.getInstance(context1);
                                    }
                                    mMusicPlayer.play();
                                }
                            }
                        }

                        if (pattern_matcher_remedy.find())
                        {
                            String tolog = "Log: ["+getreadabledate()+"] New Message: "+msg_body+"\n";
                            Log.d("GotRemedy",tolog);
                            String remedy_ticket_id = pattern_matcher_remedy.group(1);
                            String remedy_team = pattern_matcher_remedy.group(2);
                            String remedy_sev = pattern_matcher_remedy.group(3);
                            Log.d("GotRemedy",remedy_ticket_id);
                            Log.d("GotRemedy",remedy_team);
                            Log.d("GotRemedy",remedy_sev);
                            mReceivedNotification.setIncidentPriority(remedy_sev);
                            mReceivedNotification.setIncidentId(remedy_ticket_id);
                            mReceivedNotification.setIncidentSloType("New");
                            mReceivedNotification.setIncidentSloPercent("Remedy");
                            mReceivedNotification.setIncidentCustomer("Team");
                            mReceivedNotification.setIncidentWorkGroup(remedy_team);
                            mReceivedNotification.setIncidentAck("NO_ACK");

                            Log.d("EON_NotificationAdded",tolog);
                            //Insert EnoteNotificationObject values into sqlite database
                            ContentValues values = getContentValues(mReceivedNotification);


                            if(remedy_boolean_received_notif)
                            {
                                mDatabase.insert(NotificationsDbScheme.NotificationsTable.NAME, null, values);
                                sound_alert_remedy = true;
                            }
                            if(sound_alert_remedy)
                            {
                                mEnoteNotificationEvent.showEnoteNotification("New Remedy Ticket!", "", "", "", "");
                                if (enotepager_sound_active_bool)
                                {
                                    if (mMusicPlayer == null) {
                                        mMusicPlayer = SoundNotification.getInstance(context1);
                                    }
                                    mMusicPlayer.play();
                                }
                            }
                        }

                        if (pattern_matcher_escalation.find())
                        {
                            String tolog = "Log: ["+getreadabledate()+"] New Message: "+msg_body+"\n";
                            Log.d("GotEscalation",tolog);
                            String escalation_title = pattern_matcher_escalation.group(1);
                            String escalation_tid_id = pattern_matcher_escalation.group(3);
                            Log.d("GotEscalation",escalation_title);
                            mReceivedNotification.setIncidentPriority("INC");
                            mReceivedNotification.setIncidentId(escalation_tid_id);
                            mReceivedNotification.setIncidentSloType("New");
                            mReceivedNotification.setIncidentSloPercent(escalation_title);
                            mReceivedNotification.setIncidentCustomer("WorkFlow");
                            mReceivedNotification.setIncidentWorkGroup("Remedy");
                            mReceivedNotification.setIncidentAck("NO_ACK");

                            Log.d("Esc_NotificationAdded",tolog);
                            //Insert EnoteNotificationObject values into sqlite database
                            ContentValues values = getContentValues(mReceivedNotification);


                            if(escalation_boolean_received_notif)
                            {
                                mDatabase.insert(NotificationsDbScheme.NotificationsTable.NAME, null, values);
                                sound_alert_escalation = true;
                            }
                            if(sound_alert_escalation)
                            {
                                mEnoteNotificationEvent.showEnoteNotification("New Remedy Escalation!", "", "", "", "");
                                if (enotepager_sound_active_bool)
                                {
                                    if (mMusicPlayer == null) {
                                        mMusicPlayer = SoundNotification.getInstance(context1);
                                    }
                                    mMusicPlayer.play();
                                }
                            }
                        }

                        if (pattern_matcher_change.find())
                        {
                            String tolog = "Log: ["+getreadabledate()+"] New Message: "+msg_body+"\n";
                            Log.d("GotChange",tolog);
                            String chg_id = pattern_matcher_change.group(1);
                            String chg_team = pattern_matcher_change.group(2);
                            Log.d("GotChange",chg_id);
                            mReceivedNotification.setIncidentPriority("CHG");
                            mReceivedNotification.setIncidentId(chg_id);
                            mReceivedNotification.setIncidentSloType("New");
                            mReceivedNotification.setIncidentSloPercent("Emergency Change");
                            mReceivedNotification.setIncidentCustomer("Team");
                            mReceivedNotification.setIncidentWorkGroup(chg_team);
                            mReceivedNotification.setIncidentAck("NO_ACK");

                            Log.d("GotChange",tolog);
                            //Insert EnoteNotificationObject values into sqlite database
                            ContentValues values = getContentValues(mReceivedNotification);


                            if(change_boolean_received_notif)
                            {
                                mDatabase.insert(NotificationsDbScheme.NotificationsTable.NAME, null, values);
                                sound_alert_change = true;
                            }
                            if(sound_alert_change)
                            {
                                mEnoteNotificationEvent.showEnoteNotification("New Emergency Change!", "", "", "", "");
                                if (enotepager_sound_active_bool)
                                {
                                    if (mMusicPlayer == null) {
                                        mMusicPlayer = SoundNotification.getInstance(context1);
                                    }
                                    mMusicPlayer.play();
                                }
                            }
                        }

                        if (pattern_matcher_unassigned.find())
                        {
                            String tolog = "Log: ["+getreadabledate()+"] New Message: "+msg_body+"\n";
                            Log.d("GotUn",tolog);
                            String unassign_num = pattern_matcher_unassigned.group(1);
                            String unassign_prio = pattern_matcher_unassigned.group(2);
                            Log.d("GotUn",unassign_num);
                            Log.d("GotUn",unassign_prio);
                            mReceivedNotification.setIncidentPriority("INC");
                            mReceivedNotification.setIncidentId(unassign_prio);
                            mReceivedNotification.setIncidentSloType("New");
                            mReceivedNotification.setIncidentSloPercent("Unassigned");
                            mReceivedNotification.setIncidentCustomer("Ticket Count");
                            mReceivedNotification.setIncidentWorkGroup(unassign_num);
                            mReceivedNotification.setIncidentAck("NO_ACK");

                            Log.d("GotUn",tolog);
                            //Insert EnoteNotificationObject values into sqlite database
                            ContentValues values = getContentValues(mReceivedNotification);


                            if(unassiged_boolean_received_notif)
                            {
                                mDatabase.insert(NotificationsDbScheme.NotificationsTable.NAME, null, values);
                                sound_alert_unassigned = true;
                            }
                            if(sound_alert_unassigned)
                            {
                                mEnoteNotificationEvent.showEnoteNotification("New Unassigned Tickets!", "", "", "", "");
                                if (enotepager_sound_active_bool)
                                {
                                    if (mMusicPlayer == null) {
                                        mMusicPlayer = SoundNotification.getInstance(context1);
                                    }
                                    mMusicPlayer.play();
                                }
                            }
                        }
                        else
                        {
                            mEnoteNotificationEvent.showEnoteNotificationActive(msg_body);
                        }
                    }
                }
                catch(Exception e)
                {
                    Log.d("Exception caught",e.getMessage());
                }
            }
        //}
    }
    //Method to convert current long type timestamp to human readable format - Easter egg ;)
    public String getreadabledate()
    {
        long milles = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.getDefault());
        Date resultdate = new Date(milles);
        return df.format(resultdate);
    }

    //Method to add entry into sqlite based on a EnoteNotificationObject class
    private static ContentValues getContentValues(EnoteNotificationObject notification)
    {
        ContentValues values = new ContentValues();
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTID, notification.getIncidentId());
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTPRIORITY, notification.getIncidentPriority());
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTCUSTOMER, notification.getIncidentCustomer());
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTWORKGROUP, notification.getIncidentWorkGroup());
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTSLOTYPE, notification.getIncidentSloType());
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTSLOPERCENT, notification.getIncidentSloPercent());
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTRECEIVEDDATE, notification.getIncidentReceivedTimeStamp());
        values.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK, notification.getIncidentAck());
        return values;
    }
}

