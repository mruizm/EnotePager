package com.hpe.rumarco.enotepager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;

import java.util.List;


/**
 * Created by rumarco on 27/11/2016.
 * CrimeListFragment
 */
public class NotificationAllListFragment extends Fragment
{
    private RecyclerView m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​;
    private NotificationAdapter mAdapter;
    private static final String DIALOG_DATE = "DialogDate";
    private static final String LOG_CLASS = "NotificationListFragmen";
    private WriteToLog mWriteToLog;
    private ContentValues args = new ContentValues();
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mWriteToLog = new WriteToLog("sdcard/debug_log_enotepager.txt");
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        //setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_enote_all_list, container, false);
        m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​ = (RecyclerView)view.findViewById(R.id.notification_recycler_view_all);
        m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​.setLayoutManager(new LinearLayoutManager(getActivity()));
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider);
        m​NotificationR​e​c​y​c​l​e​r​V​i​e​w​.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        updateUI();
        getEnoteNotificationArray();
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String tolog = "OnResume\n";
        Log.d(LOG_CLASS,"_onResume:"+tolog);
        EnoteNotificationEvent mEnoteNotificationEvent = new EnoteNotificationEvent(getContext());
        updateUI();
        getEnoteNotificationArray();
    }

    public void o​n​C​r​e​a​t​e​O​p​t​i​o​n​s​M​e​n​u​(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_all_notification_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_refresh_notif_list_single_all:
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        EnoteNotificationEntryQueryToObject enoteNotification = EnoteNotificationEntryQueryToObject.get(getActivity());
        List<EnoteNotificationObject> enotenotification = enoteNotification.getEnoteNotificationEntryAll();

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
        //String tolog = "Log:" +mEnoteNotificationObject.size() + "\n";
        //Log.d("getItemCount",tolog);

    }
    public void getEnoteNotificationArray()
    {
        EnoteNotificationSetupQueryToObject enoteNotificationSetupQueryToObject = EnoteNotificationSetupQueryToObject.get(getActivity());
        List<EnoteNotificationArrayObject> enotenotification_array = enoteNotificationSetupQueryToObject.getEnoteNotificationArray();
        String tolog = "";
        for (EnoteNotificationArrayObject n_array: enotenotification_array)
        {
            tolog = n_array.getAlertTTO_0() + ";" + n_array.getAlertTTO_50() + ";" +n_array.getAlertTTO_75() + ";" + n_array.getAlertTTO_100() + "\n";
            Log.d("NotificationMatrix",tolog);
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
            Toast.makeText(getActivity(), mEnoteNotificationObject.getIncidentId() + " already acknowledged!", Toast.LENGTH_SHORT).show();
            /*mNotificationIncPri.setTextColor(getResources().getColor(R.color.colorAkcAlert));
            mNotificationSloType.setTextColor(getResources().getColor(R.color.colorAkcAlert));
            mNotificationIncId.setTextColor(getResources().getColor(R.color.colorAkcAlert));
            mNotificationSloPercent.setTextColor(getResources().getColor(R.color.colorAkcAlert));
            mNotificationIncCustomer.setTextColor(getResources().getColor(R.color.colorAkcAlert));
            mNotificationIncWorkgroup.setTextColor(getResources().getColor(R.color.colorAkcAlert));
            mNotificationIncLastUpdate.setTextColor(getResources().getColor(R.color.colorAkcAlert));
            args.clear();
            args.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK, "ACK");
            mDatabase.update(NotificationsDbScheme.NotificationsTable.NAME, args, NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK + " = 'NO_ACK'" + " AND " + NotificationsDbScheme.NotificationsTable.Cols.INCIDENTID + " = " +"'"+mEnoteNotificationObject.getIncidentId()+"' AND "+ NotificationsDbScheme.NotificationsTable.Cols.INCIDENTSLOPERCENT + " = " +"'"+mEnoteNotificationObject.getIncidentSloPercent()+"'"  , null);
            updateUI();*/
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


