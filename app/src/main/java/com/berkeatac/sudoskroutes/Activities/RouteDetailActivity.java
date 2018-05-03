package com.berkeatac.sudoskroutes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.berkeatac.sudoskroutes.Model.RouteObject;
import com.berkeatac.sudoskroutes.R;
import com.squareup.picasso.Picasso;

public class RouteDetailActivity extends AppCompatActivity {
    private ImageView routeImageView;
    private TextView routeNameTextView;
    private TextView routeCreatorTextView;
    private TextView routeDateTextView;
    private TextView routeDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        getSupportActionBar().setTitle("Route Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RouteObject routeObject = getIntent().getParcelableExtra("routeObject");

        routeImageView = findViewById(R.id.routeImageView);
        routeNameTextView = findViewById(R.id.routeNameTextView);
        routeCreatorTextView = findViewById(R.id.routeCreatorTextView);
        routeDateTextView = findViewById(R.id.routeCreationDateTextView);
        routeDescriptionTextView = findViewById(R.id.routeDescriptionTextView);

        Picasso.get().load(routeObject.getImageUrl()).into(routeImageView);
        routeNameTextView.setText(routeObject.getName());
        routeCreatorTextView.setText(routeObject.getCreator());
        routeDateTextView.setText(routeObject.getDate());
        routeDescriptionTextView.setText(routeObject.getDescription());

        routeImageView.setOnClickListener(view -> {
            Intent intent = new Intent(this, SinglePhotoActivity.class);
            intent.putExtra("photoUrl", routeObject.getImageUrl());
            startActivity(intent);
        });
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
