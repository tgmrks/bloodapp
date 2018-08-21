package com.bloodapp.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloodapp.R;

public class HomeItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtTitle, txtSubTitle;
    public ImageView imgThumb;
    private RecyclerViewClickListener mListener;

    public HomeItemHolder(View view, RecyclerViewClickListener listener) {
        super(view);

        mListener = listener;

        txtTitle = (TextView) view.findViewById(R.id.tv_home_item_title);
        txtSubTitle = (TextView) view.findViewById(R.id.tv_home_item_subtitle);
        imgThumb = (ImageView) view.findViewById(R.id.iv_home_item_thumbnail);

        imgThumb.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        mListener.onClick(view, getAdapterPosition());
    }

}