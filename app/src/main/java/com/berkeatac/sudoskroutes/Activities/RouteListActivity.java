package com.berkeatac.sudoskroutes.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.berkeatac.sudoskroutes.Adapters.RouteListAdapter;
import com.berkeatac.sudoskroutes.Model.Grade;
import com.berkeatac.sudoskroutes.Model.RouteObject;
import com.berkeatac.sudoskroutes.MyApplication;
import com.berkeatac.sudoskroutes.R;
import com.berkeatac.sudoskroutes.RouteListClickListener;

import java.util.ArrayList;
import java.util.List;

public class RouteListActivity extends AppCompatActivity implements RouteListClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<RouteObject> gradedRouteList = new ArrayList<>();
    private Integer grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);


        MyApplication app = (MyApplication) getApplication();

        grade = getIntent().getIntExtra("grade", -1);
        mRecyclerView = findViewById(R.id.routeList);

        getSupportActionBar().setTitle(Grade.values()[grade].toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(Grade.values()[grade].getColorId())));


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (app.getRouteList() != null) {
            for(RouteObject ro : app.getRouteList()) {
                if( ro.getGrade().equals(grade)) {
                    gradedRouteList.add(ro);
                }
            }
        }
        // specify an adapter (see also next example)
        mAdapter = new RouteListAdapter(gradedRouteList, this);
        mRecyclerView.setAdapter(mAdapter);

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

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Intent intent = new Intent(this, RouteDetailActivity.class);
        intent.putExtra("routeObject", gradedRouteList.get(position));
        startActivity(intent);
    }
}
