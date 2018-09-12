package com.bloodapp.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bloodapp.Model.Content;
import com.bloodapp.Model.Profile;
import com.bloodapp.R;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemHolder> {

    private List<Content> contentList;
    private Context context;
    private RecyclerViewClickListener mListener;

    public HomeItemAdapter(List<Content> contentList, RecyclerViewClickListener listener) {
        this.contentList = contentList;
        mListener = listener;
    }

    @Override
    public HomeItemHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item_layout, parent, false);

        this.context = parent.getContext();

        Log.i("ItemHolder", "onCreate");
        return new HomeItemHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(HomeItemHolder holder, int position) {

        holder.txtTitle.setText(contentList.get(position).getTitle().toString());
        holder.imgThumb.setImageResource(contentList.get(position).getDrawId());

        for (Content c: contentList) {
            Log.i("ItemHolder", position + " - " + c.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }
}
