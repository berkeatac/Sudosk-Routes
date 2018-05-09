package com.berkeatac.sudoskroutes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berkeatac.sudoskroutes.Model.Grade;
import com.berkeatac.sudoskroutes.Model.RouteObject;
import com.berkeatac.sudoskroutes.MyApplication;
import com.berkeatac.sudoskroutes.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class MenuActivity extends AppCompatActivity {

    private static String TAG = "BAKBAK";
    private TextView grade0;
    private TextView grade1;
    private TextView grade2;
    private TextView grade3;
    private TextView grade4;
    private TextView grade5;
    private TextView grade6;
    private DatabaseReference database;
    private List<RouteObject> routeList = new ArrayList<>();
    private MyApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        app = (MyApplication) getApplicationContext();

        getSupportActionBar().setTitle("Choose Grade");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout progressLayout = findViewById(R.id.progressMenu);
        LinearLayout menuLayout = findViewById(R.id.rootMenu);

        FirebaseApp.initializeApp(this);

        MyApplication app = (MyApplication) getApplicationContext();

        showCaseDisplay();

        grade0 = findViewById(R.id.grade0);
        grade1 = findViewById(R.id.grade1);
        grade2 = findViewById(R.id.grade2);
        grade3 = findViewById(R.id.grade3);
        grade4 = findViewById(R.id.grade4);
        grade5 = findViewById(R.id.grade5);
        grade6 = findViewById(R.id.grade6);

        database = FirebaseDatabase.getInstance().getReference();

        progressLayout.setVisibility(View.VISIBLE);  //To show ProgressBar
        menuLayout.setVisibility(View.GONE);

        database.child("routes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                routeList.clear();
                if (dataSnapshot.getChildrenCount() > 1) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        RouteObject route = new RouteObject(
                                ds.child("id").getValue(String.class),
                                ds.child("name").getValue(String.class),
                                ds.child("creator").getValue(String.class),
                                ds.child("date").getValue(String.class),
                                ds.child("grade").getValue(int.class),
                                ds.child("description").getValue(String.class),
                                ds.child("imageUrl").getValue(String.class)
                        );
                        routeList.add(route);
                    }
                }
                app.setRouteList(routeList);
                setListeners();
                progressLayout.setVisibility(View.GONE);  //To show ProgressBar
                menuLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.plusButton) {
            if (app.getUserName().equals("")){
                Toast.makeText(getApplicationContext(), "Log in to add routes", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, AddRouteActivity.class);
                startActivity(intent);
            }
        }

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    void setListeners() {
        Intent intent = new Intent(this, RouteListActivity.class);

        grade0.setOnClickListener(view -> {
            intent.putExtra("grade", Grade.GRADE03C.getIntValue());
            startActivity(intent);
        });

        grade1.setOnClickListener(view -> {
            intent.putExtra("grade", Grade.GRADE4A4C.getIntValue());
            startActivity(intent);
        });

        grade2.setOnClickListener(view -> {
            intent.putExtra("grade", Grade.GRADE5A5B.getIntValue());
            startActivity(intent);
        });

        grade3.setOnClickListener(view -> {
            intent.putExtra("grade", Grade.GRADE5C6A.getIntValue());
            startActivity(intent);
        });

        grade4.setOnClickListener(view -> {
            intent.putExtra("grade", Grade.GRADE6B6C.getIntValue());
            startActivity(intent);
        });

        grade5.setOnClickListener(view -> {
            intent.putExtra("grade", Grade.GRADE7A7B.getIntValue());
            startActivity(intent);
        });

        grade6.setOnClickListener(view -> {
            intent.putExtra("grade", Grade.GRADE7B.getIntValue());
            startActivity(intent);
        });
    }

    private void showCaseDisplay() {
        if (app.isFirstTime()) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            new FancyShowCaseView.Builder(this)
                    .title("Click on + to add a route!")
                    .focusRectAtPosition(width-100, 100,  160, 160)
                    .roundRectRadius(60)
                    .build()
                    .show();
        }
        app.setFirstTime(false);
    }
}
