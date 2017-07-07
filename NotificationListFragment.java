package com.hpe.rumarco.enotepager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;

import java.io.File;
import java.util.List;

import static android.content.Context.AUDIO_SERVICE;

public class NotificationListFragment extends Fragment
{
    private RecyclerView m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​;
    private NotificationAdapter mAdapter;
    private ShowDialogs mShowDialogs = new ShowDialogs();
    private static final String DIALOG_DATE = "DialogDate";
    private static final String LOG_CLASS = "NotificationListFragmen";
    private WriteToLog mWriteToLog;
    private ContentValues args = new ContentValues();
    private SQLiteDatabase mDatabase;
    private static SoundNotification mMusicPlayer;
    private MediaPlayer mp;
    private AudioManager am;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mWriteToLog = new WriteToLog("sdcard/debug_log_enotepager.txt");
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        EnoteNotificationSetupQueryToObject enoteNotificationSetupQueryToObject = EnoteNotificationSetupQueryToObject.get(getContext());
        am = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_enote_list, container, false);
        m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​ = (RecyclerView)view.findViewById(R.id.notification_recycler_view);
        m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​.setLayoutManager(new LinearLayoutManager(getActivity()));
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider);
        m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        updateUI();
        //getEnoteNotificationArray();
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String tolog = "OnResume\n";
        Log.d(LOG_CLASS,"_onResume:"+tolog);
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        EnoteNotificationEvent mEnoteNotificationEvent = new EnoteNotificationEvent(getContext());
        updateUI();
        //getEnoteNotificationArray();
    }

    public void o​n​C​r​e​a​t​e​O​p​t​i​o​n​s​M​e​n​u​(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_notification_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_refresh_notif_list_single:
                updateUI();
                return true;
            case R.id.menu_about:
                FragmentManager manager = getFragmentManager();
                mShowDialogs.show(manager, DIALOG_DATE);
                return true;
            /*case R.id.menu_make_default:
                final String myPackageName = getContext().getPackageName();
                    Intent intent_d = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                    intent_d.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName);
                    startActivity(intent_d);
                return true;*/
            case R.id.menu_all_notifications:
                Intent launchAllNotifications = new Intent(getContext(), MainAllNotificationActivity.class);
                launchAllNotifications.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(launchAllNotifications);
                return true;
            case R.id.menu_configuration:
                Intent launchConfigurationGeneral = new Intent(getContext(), ConfigurationMainActivity.class);
                launchConfigurationGeneral.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(launchConfigurationGeneral);
                return true;
            case R.id.menu_send_debug:
                Intent intent = new Intent(Intent.ACTION_SEND);
                String strTo[] = {"marco.ruiz.mora@gmail.com"};
                String strSubject = "eNotePager Debug File(debug_log_enotepager.txt)";
                String strMailBody = "File Name: debug_log_enotepager.txt";
                intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                intent.putExtra(Intent.EXTRA_SUBJECT, strSubject);
                intent.putExtra(Intent.EXTRA_TEXT, strMailBody);
                //Uri attachments = Uri.parse("file://sdcard/debug_log_enotepager.txt");
                intent.setType("plain/text");
                Uri uri = Uri.fromFile(new File("sdcard/debug_log_enotepager.txt"));
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                //intent.putExtra(Intent.EXTRA_STREAM, attachments);
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
                //mWriteToLog.sendEmailWithLog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        mMusicPlayer = SoundNotification.getInstance(getContext());
        mMusicPlayer.stop();
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = 0.3f;
        int seventyVolume = (int) (maxVolume*percent);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, seventyVolume, 0);
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        EnoteNotificationEntryQueryToObject enoteNotification = EnoteNotificationEntryQueryToObject.get(getActivity());
        List<EnoteNotificationObject> enotenotification = enoteNotification.getEnoteNotificationEntry();

        if (mAdapter == null)
        {
            mAdapter = new NotificationAdapter(enotenotification);
            m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setEnoteNotificationObject(enotenotification);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mNotificationIncPri;
        private TextView mNotificationSloType;
        private TextView mNotificationIncId;
        private TextView mNotificationSloPercent;
        private TextView mNotificationIncLastUpdate;
        private TextView mNotificationIncCustomer;
        private TextView mNotificationIncWorkgroup;

        private EnoteNotificationObject mEnoteNotificationObject;

        public NotificationHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            mNotificationIncPri = (TextView)itemView.findViewById(R.id.list_item_notification_inc_pri_text_view);
            mNotificationSloType = (TextView)itemView.findViewById(R.id.list_item_notification_slo_type_text_view);
            mNotificationIncId = (TextView)itemView.findViewById(R.id.list_item_notification_inc_id_text_view);
            mNotificationSloPercent = (TextView)itemView.findViewById(R.id.list_item_notification_slo_percent_text_view);
            mNotificationIncLastUpdate = (TextView)itemView.findViewById(R.id.list_item_notification_title_last_update_text_view);
            mNotificationIncCustomer = (TextView)itemView.findViewById(R.id.list_item_notification_customer_text_view);
            mNotificationIncWorkgroup = (TextView)itemView.findViewById(R.id.list_item_notification_workgroup_text_view);
        }

        public void bindNotification(EnoteNotificationObject enotepagernotification)
        {
            mEnoteNotificationObject = enotepagernotification;
            mNotificationIncPri.setText(mEnoteNotificationObject.getIncidentPriority() + ":");
            mNotificationSloType.setText(mEnoteNotificationObject.getIncidentSloType() + ":");
            mNotificationIncId.setText(mEnoteNotificationObject.getIncidentId());
            mNotificationSloPercent.setText(mEnoteNotificationObject.getIncidentSloPercent() + "");
            mNotificationIncCustomer.setText(mEnoteNotificationObject.getIncidentCustomer() + ":");
            mNotificationIncWorkgroup.setText(mEnoteNotificationObject.getIncidentWorkGroup());
            mNotificationIncLastUpdate.setText(mEnoteNotificationObject.getIncidentReceivedTimeStamp().toString());
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(getActivity(), mEnoteNotificationObject.getIncidentId() + " was acknowledged!", Toast.LENGTH_SHORT).show();
            args.clear();
            args.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK, "ACK");
            mDatabase.update(NotificationsDbScheme.NotificationsTable.NAME, args, NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK + " = 'NO_ACK'" + " AND " + NotificationsDbScheme.NotificationsTable.Cols.INCIDENTID + " = " +"'"+ mEnoteNotificationObject.getIncidentId()+"' AND "+ NotificationsDbScheme.NotificationsTable.Cols.INCIDENTSLOPERCENT + " = " +"'"+ mEnoteNotificationObject.getIncidentSloPercent()+"'"  , null);
            mDatabase.close();
            updateUI();
        }
    }
    private class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder>
    {
        private List<EnoteNotificationObject> mEnoteNotificationObject;

        public NotificationAdapter(List<EnoteNotificationObject> enoteNotification)
        {
            mEnoteNotificationObject = enoteNotification;
        }

        @Override
        public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_notification, parent, false);
            return new NotificationHolder(view);
        }

        @Override
        public void onBindViewHolder(NotificationHolder holder, int position){
            EnoteNotificationObject enotenotification = mEnoteNotificationObject.get(position);
            holder.bindNotification(enotenotification);
        }

        @Override
        public int getItemCount()
        {
            String tolog = "Log:" + mEnoteNotificationObject.size() + "\n";
            Log.d("getItemCount",tolog);
            return mEnoteNotificationObject.size();
        }

        public void setEnoteNotificationObject(List<EnoteNotificationObject> notification)
        {
            String tolog = "setEnoteNotificationObject\n";
            Log.d("updateUI",tolog);
            mEnoteNotificationObject = notification;
        }
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration
    {
        private Drawable mDivider;

        public DividerItemDecoration (Drawable divider)
        {
            mDivider = divider;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);

            if (parent.getChildAdapterPosition(view) == 0)
            {
                return;
            }
            outRect.top = mDivider.getIntrinsicHeight();
        }
        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state)
        {
            int dividerLeft = parent.getPaddingLeft();
            int dividerRight = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int dividerTop = child.getBottom() + params.bottomMargin;
                int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();

                mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
                mDivider.draw(canvas);
            }
        }
    }

}


