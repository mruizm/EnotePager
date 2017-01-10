package com.hpe.rumarco.enotepager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.hpe.rumarco.enotepager.database.NotificationsBaseHelper;
import com.hpe.rumarco.enotepager.database.NotificationsDbScheme;

import java.util.List;

public class ConfigurationAddWorkGroupFragment extends Fragment
{
    private RecyclerView m​ListWgR​e​c​y​c​l​e​r​V​i​e​w​;
    private NotificationAdapter mAdapter;
    private EditText mEditTextInsertWGText;
    private ImageButton mImageButtonAddWG;
    private SQLiteDatabase mDatabase;
    private ContentValues args = new ContentValues();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        //setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_workgroup, container, false);
        m​ListWgR​e​c​y​c​l​e​r​V​i​e​w​ = (RecyclerView)view.findViewById(R.id.notification_recycler_view_add_wg);
        m​ListWgR​e​c​y​c​l​e​r​V​i​e​w​.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEditTextInsertWGText = (EditText)view.findViewById(R.id.edit_text_add_wg);
        mImageButtonAddWG = (ImageButton)view.findViewById(R.id.add_wg_to_list);
        mImageButtonAddWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                CharSequence edit_text_value = mEditTextInsertWGText.getText();
                String mWgName = edit_text_value.toString();
                if(mWgName.isEmpty())
                {
                    Toast.makeText(getContext(), "Empty HPSM WorkGroup! Entry not added into database!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "HPSM WorkGroup " + mWgName + " added into database!", Toast.LENGTH_SHORT).show();
                    args.put(NotificationsDbScheme.HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME, mWgName);
                    mDatabase.insert(NotificationsDbScheme.HpSmWorkgroupList.NAME, null, args);
                    mEditTextInsertWGText.setText("");
                    mDatabase.close();
                    updateUI();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
        mDatabase = new NotificationsBaseHelper(getContext()).getWritableDatabase();
        EnoteAddWorkGroupQueryToObject enoteAddWorkGroupQueryToObject = EnoteAddWorkGroupQueryToObject.get(getActivity());
        List<EnoteWorkGroupObject> enotewg_active = enoteAddWorkGroupQueryToObject.getEnoteNotificationActiveWorkGroups();

        if (mAdapter == null)
        {
            mAdapter = new NotificationAdapter(enotewg_active);
            m​ListWgR​e​c​y​c​l​e​r​V​i​e​w​.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setEnoteWorkGroupObject(enotewg_active);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mHpSmWorkGroupNametoRem;
        private ImageButton mImageButtonWorkGroupNameRem;
        private ImageButton mImageButtonAddWorkGroup;

        private EnoteWorkGroupObject mEnoteWorkGroupObject;

        public NotificationHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            mHpSmWorkGroupNametoRem = (TextView) itemView.findViewById(R.id.list_item_remove_wg);
            mImageButtonWorkGroupNameRem = (ImageButton) itemView.findViewById(R.id.rem_wg_from_list);
        }

        public void bindNotification(EnoteWorkGroupObject enotepager_active_wg)
        {
            mEnoteWorkGroupObject = enotepager_active_wg;
            mHpSmWorkGroupNametoRem.setText(mEnoteWorkGroupObject.getHpSmWgName());
            mImageButtonWorkGroupNameRem.findViewById(R.id.rem_wg_from_list);
            mImageButtonWorkGroupNameRem.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View arg0) {
                    args.clear();
                    Toast.makeText(getActivity(), "HPSM Workgroup " +mEnoteWorkGroupObject.getHpSmWgName() + " was removed from list!", Toast.LENGTH_SHORT).show();
                    /*
                    args.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK, "ACK");*/
                    mDatabase.delete(NotificationsDbScheme.HpSmWorkgroupList.NAME, NotificationsDbScheme.HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME + " = " + "'" + mEnoteWorkGroupObject.getHpSmWgName() + "'", null);
                    mDatabase.close();
                updateUI();
                   }
                });
        }

        @Override
        public void onClick(View v)
        {
            //mImageButtonWorkGroupNameRem.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View arg0) {
                /*    args.clear();
                    Toast.makeText(getActivity(), "HPSM Workgroup " +mEnoteWorkGroupObject.getHpSmWgName() + " was removed from list!", Toast.LENGTH_SHORT).show();
                    /*
                    args.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK, "ACK");*/
                    //mDatabase.delete(NotificationsDbScheme.HpSmWorkgroupList.NAME, NotificationsDbScheme.HpSmWorkgroupList.Cols.HPSM_WORKGROUP_NAME + " = " + "'" + mEnoteWorkGroupObject.getHpSmWgName() + "'", null);
                    //mDatabase.close();
                    //updateUI();
             //   }
            //});

            /*Toast.makeText(getActivity(), mEnoteNotificationObject.getIncidentId() + " was acknowledged!", Toast.LENGTH_SHORT).show();
            args.clear();
            args.put(NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK, "ACK");
            mDatabase.update(NotificationsDbScheme.NotificationsTable.NAME, args, NotificationsDbScheme.NotificationsTable.Cols.INCIDENTACK + " = 'NO_ACK'" + " AND " + NotificationsDbScheme.NotificationsTable.Cols.INCIDENTID + " = " +"'"+ mEnoteNotificationObject.getIncidentId()+"' AND "+ NotificationsDbScheme.NotificationsTable.Cols.INCIDENTSLOPERCENT + " = " +"'"+ mEnoteNotificationObject.getIncidentSloPercent()+"'"  , null);
            mDatabase.close();
            updateUI();*/
            //updateUI();
        }
    }
    private class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder>
    {
        private List<EnoteWorkGroupObject> mEnoteWorkGroupObject;

        public NotificationAdapter(List<EnoteWorkGroupObject> enoteWorkGroup)
        {
            mEnoteWorkGroupObject = enoteWorkGroup;
        }

        @Override
        public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_remove_wg, parent, false);
            return new NotificationHolder(view);
        }

        @Override
        public void onBindViewHolder(NotificationHolder holder, int position){
            EnoteWorkGroupObject enote_active_wg = mEnoteWorkGroupObject.get(position);
            holder.bindNotification(enote_active_wg);
        }

        @Override
        public int getItemCount()
        {
            String tolog = "Log:" + mEnoteWorkGroupObject.size() + "\n";
            Log.d("WGgetItemCount",tolog);
            return mEnoteWorkGroupObject.size();
        }

        public void setEnoteWorkGroupObject(List<EnoteWorkGroupObject> active_wg_object)
        {
             mEnoteWorkGroupObject = active_wg_object;
        }
    }
}


