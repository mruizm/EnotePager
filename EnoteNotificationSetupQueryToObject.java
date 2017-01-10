package com.hpe.rumarco.enotepager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hpe.rumarco.enotepager.database.NotificationCursorWrapper;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rumarco on 19/12/2016.
 */
public class EnoteNotificationSetupQueryToObject
{
    private static EnoteNotificationSetupQueryToObject sEnoteNotificationSetupQueryToObject;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //Creates a singleton
    public static EnoteNotificationSetupQueryToObject get(Context context)
    {
        if (sEnoteNotificationSetupQueryToObject == null)
        {
            sEnoteNotificationSetupQueryToObject = new EnoteNotificationSetupQueryToObject(context);
        }
        return sEnoteNotificationSetupQueryToObject;
    }

    private EnoteNotificationSetupQueryToObject(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new NotificationsBaseHelper(mContext).getWritableDatabase();
    }

    //Returns the List of EnoteNotificationObject objects
    public List<EnoteNotificationArrayObject> getEnoteNotificationArray()
    {
        List<EnoteNotificationArrayObject> enote_notif_array = new ArrayList<>();
        NotificationCursorWrapper cursor_notif_array = queryNotificationsArray(null, null);

        try{
            cursor_notif_array.moveToFirst();
            while(!cursor_notif_array.isAfterLast()){
                enote_notif_array.add(cursor_notif_array.getEnoteNotificationArray());
                cursor_notif_array.moveToNext();
            }
        } finally {
            cursor_notif_array.close();
        }
        return enote_notif_array;
    }

    public List<EnoteNotificationArrayObject> getEnoteNotificationArrayPrio(String whereClause)
    {
        List<EnoteNotificationArrayObject> enote_notif_array_prio = new ArrayList<>();
        NotificationCursorWrapper cursor_notif_array_prio = queryNotificationsArray(whereClause, null);

        try{
            cursor_notif_array_prio.moveToFirst();
            while(!cursor_notif_array_prio.isAfterLast()){
                enote_notif_array_prio.add(cursor_notif_array_prio.getEnoteNotificationArray());
                cursor_notif_array_prio.moveToNext();
            }
        } finally {
            cursor_notif_array_prio.close();
        }
        return enote_notif_array_prio;
    }

    public List<EnoteNotificationArrayObject> getEnoteNotificationSmEonActive(String whereClause)
    {
        List<EnoteNotificationArrayObject> enote_notif_eon_sm_active = new ArrayList<>();
        NotificationCursorWrapper cursor_notif_eon_sm_active = queryNotificationsEonSmActive(whereClause, null);

        try{
            cursor_notif_eon_sm_active.moveToFirst();
            while(!cursor_notif_eon_sm_active.isAfterLast()){
                enote_notif_eon_sm_active.add(cursor_notif_eon_sm_active.getEnoteNotificationEonSmActive());
                cursor_notif_eon_sm_active.moveToNext();
            }
        } finally {
            cursor_notif_eon_sm_active.close();
        }
        return enote_notif_eon_sm_active;
    }

    public List<EnoteNotificationArrayObject> getEnoteNotificationSoundActive(String whereClause)
    {
        List<EnoteNotificationArrayObject> enote_notif_sound_active = new ArrayList<>();
        NotificationCursorWrapper cursor_notif_sound_active = queryNotificationSoundActive(whereClause, null);

        try{
            cursor_notif_sound_active.moveToFirst();
            while(!cursor_notif_sound_active.isAfterLast()){
                enote_notif_sound_active.add(cursor_notif_sound_active.getEnoteSoundIsActive());
                cursor_notif_sound_active.moveToNext();
            }
        } finally {
            cursor_notif_sound_active.close();
        }
        return enote_notif_sound_active;
    }

    //Query to get the events within SQLite
    //private Cursor queryEnoteEvents(
    private NotificationCursorWrapper queryNotificationsArray(
            String whereClause,
            String[] whereArgs)
    {
        //String orderBy = NotificationsDbScheme.NotificationsTable.Cols.INCIDENTRECEIVEDDATE + " DESC";
        Cursor cursor = mDatabase.query(NotificationsDbScheme.NotificationsSloTTOArray.NAME, null, whereClause, whereArgs, null, null, null);
        return new NotificationCursorWrapper(cursor);
    }

    //Query to get the if Enote and EON are active
    //private Cursor queryEnoteEvents(
    private NotificationCursorWrapper queryNotificationsEonSmActive(
            String whereClause,
            String[] whereArgs)
    {
        //String orderBy = NotificationsDbScheme.NotificationsTable.Cols.INCIDENTRECEIVEDDATE + " DESC";
        Cursor cursor = mDatabase.query(NotificationsDbScheme.NotificationsEonArray.NAME, null, whereClause, whereArgs, null, null, null);
        return new NotificationCursorWrapper(cursor);
    }

    //Query to get the if Enote and EON are active
    //private Cursor queryEnoteEvents(
    private NotificationCursorWrapper queryNotificationSoundActive(
            String whereClause,
            String[] whereArgs)
    {
        //String orderBy = NotificationsDbScheme.NotificationsTable.Cols.INCIDENTRECEIVEDDATE + " DESC";
        Cursor cursor = mDatabase.query(NotificationsDbScheme.NotificationsSoundActive.NAME, null, whereClause, whereArgs, null, null, null);
        return new NotificationCursorWrapper(cursor);
    }
}






//<------------------------------------------------------------------------>
//Old methods
/*
private EnoteNotificationEntryQueryToObject(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new NotificationsBaseHelper(mContext).getWritableDatabase();
        /*mEnotePagerNotification = new ArrayList<>();
        for (int i = 1; i <= 4; i++){
            intToStringLoop = String.valueOf(i);
            EnoteNotificationObject notifentry = new EnoteNotificationObject();
            notifentry.setIncidentCustomer("OS DO TOOLS");
            notifentry.setIncidentId("N-IM00000001");
            notifentry.setIncidentPriority("P1");
            notifentry.setIncidentWorkGroup("N-INCSSP-GDO-GPS");
            notifentry.setIncidentSloType("TTO");
            if(i == 1){
                notifentry.setIncidentSloPercent("0");
            }
            if(i == 2){
                notifentry.setIncidentSloPercent("50");
            }
            if(i == 3){
                notifentry.setIncidentSloPercent("75");
            }
            if(i == 4)
            {
                notifentry.setIncidentSloPercent("100");
            }
            notifDate = notifentry.getIncidentReceivedTimeStamp();
            mEnotePagerNotification.add(notifentry);
            //mEnoteSmsInterceptor.addNotification(notifentry);*/
        /*enote_notif = new ArrayList<>();
        NotificationCursorWrapper cursor = queryNotifications(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                enote_notif.add(cursor.getEnoteNotification());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        //}
    }
 */

//Returns the enote notification entry with the given incident Id
    /*public EnoteNotificationObject getNotificationEntry(String incId)
    {
        /*for (EnoteNotificationObject notifentry: mEnotePagerNotification)
        {
            if(notifentry.getIncidentId().equals(incId)){
                return notifentry;
            }
        }
        return null;
        NotificationCursorWrapper cursor = queryNotifications(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTID + " = ?", new String[]{ incId.toString()});
        try {
            if (cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEnoteNotification();
        }
        finally {
            cursor.close();
        }
    }*/

//Query to get the events within SQLite
    /*private Cursor queryEnoteEvents(
            String table,
            String[] columns,
            String where,
            String[] whereArgs,
            String groupBy,
            String having,
            String orderBy,
            String limit)
    {

    }*/
//<------------------------------------------------------------------------>
