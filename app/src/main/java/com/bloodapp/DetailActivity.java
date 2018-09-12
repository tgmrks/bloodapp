package com.bloodapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ImageView ivContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int id, position, drawId;
        String title;
        
        Intent it = getIntent();
        Bundle param = it.getExtras();

        id = param.getInt("id");
        position = param.getInt("position");
        title = param.getString("title");
        drawId = param.getInt("draw");
        
        Log.i("DetailActivity", String.valueOf(id));
        Log.i("DetailActivity", String.valueOf(position));
        Log.i("DetailActivity", title);
        Log.i("DetailActivity", String.valueOf(drawId));
       // Log.i("DetailActivity", String.valueOf(drawable);

        tvTitle = (TextView) findViewById(R.id.tv_detail_title);
        ivContent = (ImageView) findViewById(R.id.iv_detail_content);

        //tvTitle.setText(title);
        ivContent.setImageResource(drawId);
    }

}
