package com.hpe.rumarco.enotepager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by rumarco on 24/12/2016.
 */
public class WriteToLog extends Activity{
    private String mLogfilePath;
    private Context mContext;

    public WriteToLog(String logilepath)
    {
        mLogfilePath = logilepath;
        //mContext = context;
    }
    public void addLineToLog(String text_to_log)
    {
        //File logFile = new File("sdcard/log.file");
        File logFile = new File(mLogfilePath);
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text_to_log);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void sendEmailWithLog()
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        //Intent intent = new Intent(mContext);
        String strTo[] = {"marco.ruiz.mora@gmail.com"};
        String strSubject = "eNotePager Debug File(debug_log_enotepager.txt)";
        String strMailBody = "File Name: debug_log_enotepager.txt";
        intent.putExtra(Intent.EXTRA_EMAIL, strTo);
        intent.putExtra(Intent.EXTRA_SUBJECT, strSubject);
        intent.putExtra(Intent.EXTRA_TEXT, strMailBody);
        //Uri attachments = Uri.parse("file://sdcard/debug_log_enotepager.txt");
        intent.setType("plain/text");
        Uri uri = Uri.fromFile(new File(mLogfilePath));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        //intent.putExtra(Intent.EXTRA_STREAM, attachments);
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        startActivity(intent);
    }

}
