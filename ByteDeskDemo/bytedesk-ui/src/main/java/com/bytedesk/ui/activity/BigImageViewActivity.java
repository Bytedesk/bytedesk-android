package com.bytedesk.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bytedesk.ui.R;

public class BigImageViewActivity extends AppCompatActivity {

    private ImageView mImageView;
    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bytedesk_activity_bigimageview);

        mImageUrl = getIntent().getStringExtra("image_url");

        mImageView = findViewById(R.id.kfds_bigimageview);
        Glide.with(this).load(mImageUrl).into(mImageView);


    }
}
