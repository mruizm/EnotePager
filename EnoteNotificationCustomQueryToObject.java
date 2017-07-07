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
public class EnoteNotificationCustomQueryToObject
{
    private static EnoteNotificationCustomQueryToObject sEnoteNotificationCustomQueryToObject;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //Creates a singleton
    public static EnoteNotificationCustomQueryToObject get(Context context)
    {
        if (sEnoteNotificationCustomQueryToObject == null)
        {
            sEnoteNotificationCustomQueryToObject = new EnoteNotificationCustomQueryToObject(context);
        }
        return sEnoteNotificationCustomQueryToObject;
    }

    private EnoteNotificationCustomQueryToObject(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new NotificationsBaseHelper(mContext).getWritableDatabase();
    }

    public List<EnoteNotificationCustomObject> getEnoteNotificationCustomActive(String whereClause)
    {
        List<EnoteNotificationCustomObject> enote_notif_custom_active = new ArrayList<>();
        NotificationCursorWrapper cursor_custom_notif_active = queryNotificationsCustomActive(whereClause, null);

        try{
            cursor_custom_notif_active.moveToFirst();
            while(!cursor_custom_notif_active.isAfterLast()){
                enote_notif_custom_active.add(cursor_custom_notif_active.getCustomNotificationsIsActive());
                cursor_custom_notif_active.moveToNext();
            }
        } finally {
            cursor_custom_notif_active.close();
        }
        return enote_notif_custom_active;
    }

    //Query to get the if Enote and EON are active
    //private Cursor queryEnoteEvents(
    private NotificationCursorWrapper queryNotificationsCustomActive(
            String whereClause,
            String[] whereArgs)
    {
        //String orderBy = NotificationsDbScheme.NotificationsTable.Cols.INCIDENTRECEIVEDDATE + " DESC";
        Cursor cursor = mDatabase.query(NotificationsDbScheme.NotificationsCustomTable.NAME, null, whereClause, whereArgs, null, null, null);
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
