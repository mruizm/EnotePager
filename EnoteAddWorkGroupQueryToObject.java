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
 * Created by rumarco on 28/12/2016.
 */
public class EnoteAddWorkGroupQueryToObject {
    private static EnoteAddWorkGroupQueryToObject sEnoteAddWorkGroupQueryToObjects;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //Creates a singleton
    public static EnoteAddWorkGroupQueryToObject get(Context context)
    {
        if (sEnoteAddWorkGroupQueryToObjects == null)
        {
            sEnoteAddWorkGroupQueryToObjects = new EnoteAddWorkGroupQueryToObject(context);
        }
        return sEnoteAddWorkGroupQueryToObjects;
    }
    private EnoteAddWorkGroupQueryToObject(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new NotificationsBaseHelper(mContext).getWritableDatabase();
    }

    public List<EnoteWorkGroupObject> getEnoteNotificationActiveWorkGroups()
    {
        List<EnoteWorkGroupObject> enote_notif_wg_active = new ArrayList<>();
        NotificationCursorWrapper cursor_notif_wg_active = queryActiveWorkGroups(null, null);

        try{
            cursor_notif_wg_active.moveToFirst();
            while(!cursor_notif_wg_active.isAfterLast()){
                //cursor_notif_wg_active.getWorkGroupName obtained from NotificationCursorWrapper.java
                enote_notif_wg_active.add(cursor_notif_wg_active.getWorkGroupName());
                cursor_notif_wg_active.moveToNext();
            }
        } finally {
            cursor_notif_wg_active.close();
        }
        return enote_notif_wg_active;
    }

    private NotificationCursorWrapper queryActiveWorkGroups(
            String whereClause,
            String[] whereArgs)
    {
        //String orderBy = NotificationsDbScheme.NotificationsTable.Cols.INCIDENTRECEIVEDDATE + " DESC";
        Cursor cursor = mDatabase.query(NotificationsDbScheme.HpSmWorkgroupList.NAME, null, whereClause, whereArgs, null, null, null);
        return new NotificationCursorWrapper(cursor);
    }
}
