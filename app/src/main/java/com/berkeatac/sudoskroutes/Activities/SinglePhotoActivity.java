package com.berkeatac.sudoskroutes.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.berkeatac.sudoskroutes.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SinglePhotoActivity extends AppCompatActivity {

    PhotoView bigImage;
    String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Route Image");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bigImage = findViewById(R.id.bigImage);

        if(getIntent().hasExtra("photoUrl")) {
            photoUrl = getIntent().getStringExtra("photoUrl");
            Picasso.get().load(photoUrl).placeholder(R.drawable.placeholder_mtn).into(bigImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}