package com.hpe.rumarco.enotepager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme.NotificationsTable;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme.NotificationsSloTTOArray;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme.NotificationsSloTTRArray;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme.NotificationsEonArray;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme.NotificationsSoundActive;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme.NotificationsCustomTable;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme.HpSmWorkgroupList;

//Class that creates and updates sqlite db and tables when application is installed or updated
public class NotificationsBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 5;
    private static final String DATABASE_NAME = "notificationsBase.db";

    public NotificationsBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d("onCreateDB", String.valueOf(VERSION));
        //Create the sqlite database for enote notifications
        db.execSQL("create table " + NotificationsTable.NAME + "("  + " _id integer primary key autoincrement, " +
        NotificationsTable.Cols.INCIDENTID + ", " +
        NotificationsTable.Cols.INCIDENTPRIORITY + ", " +
        NotificationsTable.Cols.INCIDENTCUSTOMER + ", " +
        NotificationsTable.Cols.INCIDENTWORKGROUP + ", " +
        NotificationsTable.Cols.INCIDENTSLOTYPE + ", " +
        NotificationsTable.Cols.INCIDENTSLOPERCENT + ", " +
        NotificationsTable.Cols.INCIDENTRECEIVEDDATE + ", " +
        NotificationsTable.Cols.INCIDENTACK + ")");

        //Create the sqlite database for TTO notifications management
        db.execSQL("create table " + NotificationsSloTTOArray.NAME + "("  + " _id integer primary key autoincrement, " +
            NotificationsSloTTOArray.Cols.TTO_0_PERCENT + ", " +
            NotificationsSloTTOArray.Cols.TTO_50_PERCENT + ", " +
            NotificationsSloTTOArray.Cols.TTO_75_PERCENT + ", " +
            NotificationsSloTTOArray.Cols.TTO_100_PERCENT + ", " +
            NotificationsSloTTOArray.Cols.TTO_LAST_UPDATE + ")");
            db.execSQL("insert into " + NotificationsSloTTOArray.NAME + "(" + NotificationsSloTTOArray.Cols.TTO_0_PERCENT + ","
                + NotificationsSloTTOArray.Cols.TTO_50_PERCENT + "," + NotificationsSloTTOArray.Cols.TTO_75_PERCENT + ","
                    + NotificationsSloTTOArray.Cols.TTO_100_PERCENT + ") values('P1_0_0','P1_0_50', 'P1_0_75', 'P1_0_100')");
            db.execSQL("insert into " + NotificationsSloTTOArray.NAME + "(" + NotificationsSloTTOArray.Cols.TTO_0_PERCENT + ","
                + NotificationsSloTTOArray.Cols.TTO_50_PERCENT + "," + NotificationsSloTTOArray.Cols.TTO_75_PERCENT + ","
                + NotificationsSloTTOArray.Cols.TTO_100_PERCENT + ") values('P2_0_0','P2_0_50', 'P2_0_75', 'P2_0_100')");
            db.execSQL("insert into " + NotificationsSloTTOArray.NAME + "(" + NotificationsSloTTOArray.Cols.TTO_0_PERCENT + ","
                + NotificationsSloTTOArray.Cols.TTO_50_PERCENT + "," + NotificationsSloTTOArray.Cols.TTO_75_PERCENT + ","
                + NotificationsSloTTOArray.Cols.TTO_100_PERCENT + ") values('P3_0_0','P3_0_50', 'P3_0_75', 'P3_0_100')");
            db.execSQL("insert into " + NotificationsSloTTOArray.NAME + "(" + NotificationsSloTTOArray.Cols.TTO_0_PERCENT + ","
                + NotificationsSloTTOArray.Cols.TTO_50_PERCENT + "," + NotificationsSloTTOArray.Cols.TTO_75_PERCENT + ","
                + NotificationsSloTTOArray.Cols.TTO_100_PERCENT + ") values('P4_0_0','P4_0_50', 'P4_0_75', 'P4_0_100')");

        //Create the sqlite database for TTR notifications management
        db.execSQL("create table " + NotificationsSloTTRArray.NAME + "("  + " _id integer primary key autoincrement, " +
                NotificationsSloTTRArray.Cols.TTR_0_PERCENT + ", " +
                NotificationsSloTTRArray.Cols.TTR_50_PERCENT + ", " +
                NotificationsSloTTRArray.Cols.TTR_75_PERCENT + ", " +
                NotificationsSloTTRArray.Cols.TTR_100_PERCENT + ", " +
                NotificationsSloTTRArray.Cols.TTR_LAST_UPDATE + ")");

        //Create the sqlite database for EON notifications management
        db.execSQL("create table " + NotificationsEonArray.NAME + "("  + " _id integer primary key autoincrement, " +
                NotificationsEonArray.Cols.FW_NOTIF_GATE + ", " +
                NotificationsEonArray.Cols.IS_ACTIVE + ", " +
                NotificationsEonArray.Cols.LAST_UPDATE + ")");
        db.execSQL("insert into " + NotificationsEonArray.NAME + "(" + NotificationsEonArray.Cols.FW_NOTIF_GATE + ","
                + NotificationsEonArray.Cols.IS_ACTIVE + ","
                + NotificationsEonArray.Cols.LAST_UPDATE + ") values('EON_GATEWAY','0', '0')");
        db.execSQL("insert into " + NotificationsEonArray.NAME + "(" + NotificationsEonArray.Cols.FW_NOTIF_GATE + ", "
                + NotificationsEonArray.Cols.IS_ACTIVE + ", "
                + NotificationsEonArray.Cols.LAST_UPDATE + ") values('SM_GATEWAY','0', '0')");

        //Create the sqlite database for Sound Notification management
        db.execSQL("create table " + NotificationsSoundActive.NAME + "("  + " _id integer primary key autoincrement, " +
                NotificationsSoundActive.Cols.IS_SOUND_ACTIVE +  ")");
        db.execSQL("insert into " + NotificationsSoundActive.NAME + "(" + NotificationsSoundActive.Cols.IS_SOUND_ACTIVE + ") values('0')");

        //Create the sqlite database for HPSM active WG management
        db.execSQL("create table " + HpSmWorkgroupList.NAME + "("  + " _id integer primary key autoincrement, " +
                HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME +  ", " +
                HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME_DATE_ADDED +")");

        //Create the sqlite database for custom notifications management
        db.execSQL("create table " + NotificationsCustomTable.NAME + "("  + " _id integer primary key autoincrement, " +
                NotificationsCustomTable.Cols.CUSTOM_RULE_NAME + ", " +
                NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE + ")");

        db.execSQL("insert into " + NotificationsCustomTable.NAME + "(" + NotificationsCustomTable.Cols.CUSTOM_RULE_NAME + ","
                + NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE + ") values('REMEDY_RULE','0')");
        db.execSQL("insert into " + NotificationsCustomTable.NAME + "(" + NotificationsCustomTable.Cols.CUSTOM_RULE_NAME + ","
                + NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE + ") values('CHANGE_RULE','0')");
        db.execSQL("insert into " + NotificationsCustomTable.NAME + "(" + NotificationsCustomTable.Cols.CUSTOM_RULE_NAME + ","
                + NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE + ") values('UNASSIGNED_RULE','0')");
        db.execSQL("insert into " + NotificationsCustomTable.NAME + "(" + NotificationsCustomTable.Cols.CUSTOM_RULE_NAME + ","
                + NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE + ") values('ESCALATION_RULE','0')");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        int moveToNextVersion = oldVersion + 1;
        switch(moveToNextVersion) {
            case 4:
                upgradeDB(db);
                break;
        }
    }
    private void upgradeDB(SQLiteDatabase db)
    {
        Log.d("OnUpgradeDB", "OnUpgradeDB");
        //Create the sqlite database for HPSM active WG management
        db.execSQL("create table " + HpSmWorkgroupList.NAME + "("  + " _id integer primary key autoincrement, " +
                HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME +  ", " +
                HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME_DATE_ADDED +")");
        //Create the sqlite database for custom notifications management
        /*db.execSQL("create table " + NotificationsCustomTable.NAME + "("  + " _id integer primary key autoincrement, " +
                NotificationsCustomTable.Cols.CUSTOM_RULE_NAME + ", " +
                NotificationsCustomTable.Cols.IS_CUSTOM_RULE_ACTIVE + ")");*/
        //db.execSQL("insert into " + HpSmWorkgroupList.NAME + "(" + HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME + ") values('0')");
    }
}
