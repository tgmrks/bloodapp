package com.bloodapp.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bloodapp.Model.NotificationModel;
import com.bloodapp.R;

import java.util.ArrayList;

public class NotificationCustomAdapter extends ArrayAdapter {

    private ArrayList contentList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView msgTitle, msgBody, msgDate;
        CheckBox checkBox;
    }

    public NotificationCustomAdapter(ArrayList data, Context context) {
        super(context, R.layout.notification_item_layout, data);
        this.contentList = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public NotificationModel getItem(int position) {
        return (NotificationModel) contentList.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
            viewHolder.msgTitle = (TextView) convertView.findViewById(R.id.tv_notification_title);
            viewHolder.msgBody = (TextView) convertView.findViewById(R.id.tv_notification_message);
            viewHolder.msgDate = (TextView) convertView.findViewById(R.id.tv_notification_date);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_notification_visited);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        NotificationModel item = getItem(position);


        viewHolder.msgTitle.setText(item.getMsgTitle());
        viewHolder.msgBody.setText(item.getMsgBody());
        viewHolder.msgDate.setText(item.getMsgDate());
        viewHolder.checkBox.setChecked(item.isChecked());


        return result;
    }
}