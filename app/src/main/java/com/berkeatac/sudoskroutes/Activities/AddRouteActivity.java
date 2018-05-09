package com.berkeatac.sudoskroutes.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berkeatac.sudoskroutes.Model.Grade;
import com.berkeatac.sudoskroutes.Model.RouteObject;
import com.berkeatac.sudoskroutes.MyApplication;
import com.berkeatac.sudoskroutes.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddRouteActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;

    private TextView dateTextView;
    private TextView creatorTextView;

    private EditText titleEditText;
    private EditText descEditText;
    private EditText creatorEditText;

    private Spinner gradeSpinner;
    private Button doneButton;

    private ImageView routeImageView;
    private Uri selectedImage = null;

    private boolean isImageSelected = false;
    private DatabaseReference database;
    MyApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        app = (MyApplication) getApplicationContext();

        RelativeLayout progressLayout = findViewById(R.id.progressMenu);
        ConstraintLayout addLayout = findViewById(R.id.rootMenu);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Route");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        database = FirebaseDatabase.getInstance().getReference();

        dateTextView = findViewById(R.id.creationDate);
        creatorTextView = findViewById(R.id.creatorName);
        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descEditText);
        doneButton = findViewById(R.id.doneButton);
        routeImageView = findViewById(R.id.imageView);
        creatorEditText = findViewById(R.id.creatorEditText);

        gradeSpinner = (Spinner) findViewById(R.id.spinner);
        gradeSpinner.setAdapter(new ArrayAdapter<Grade>(this,
                android.R.layout.simple_spinner_dropdown_item, Grade.values()));

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", new Locale("tr", "TR"));

        dateTextView.setText(format.format(date));
        creatorEditText.setText(app.getUserName());

        doneButton.setOnClickListener(view -> {

            progressLayout.setVisibility(View.VISIBLE);
            addLayout.setVisibility(View.GONE);

            // send the entered data to firebase
            if (!titleEditText.getText().toString().equals("") && isImageSelected && !creatorEditText.getText().equals("")) {
                RouteObject obj = new RouteObject(app.getCurrentUser().getUid(), titleEditText.getText().toString(),
                        creatorEditText.getText().toString(), dateTextView.getText().toString(),
                        gradeSpinner.getSelectedItemPosition(), descEditText.getText().toString(),
                        null);

                //upload photo to storage
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference photoRef = storageRef.child("images/"+selectedImage.getLastPathSegment());
                UploadTask uploadTask = photoRef.putFile(selectedImage);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(exception -> {
                    progressLayout.setVisibility(View.GONE);
                    addLayout.setVisibility(View.VISIBLE);
                    // Handle unsuccessful uploads
                }).addOnSuccessListener(taskSnapshot -> {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    obj.setImageUrl(downloadUrl.toString());
                    String key = database.child("routes").push().getKey();
                    database.child("routes").child(key).setValue(obj);

                    progressLayout.setVisibility(View.GONE);
                    addLayout.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                });

            } else {
                progressLayout.setVisibility(View.GONE);
                addLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Title & Image should not be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        routeImageView.setOnClickListener(view -> {
            // image picker here
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && null != data) {

            selectedImage = data.getData();
            Picasso.get().load(selectedImage).into(routeImageView);
            isImageSelected = true;

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
