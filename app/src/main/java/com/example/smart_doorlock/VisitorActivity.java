package com.example.smart_doorlock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VisitorActivity extends AppCompatActivity {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");

    String imageUrl = null;
    String time = null;
    String name = null;
    ImageView vImg;
    TextView vName;
    TextView vTime;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        vImg = findViewById(R.id.vImg);
        vName = findViewById(R.id.vName);
        vTime = findViewById(R.id.vTime);

        Query query = root.limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);

                    imageUrl = model.getImageUrl();
                    name = model.getName();
                    time = model.getTime();
                }

                Log.v("model", "imageurl is " + imageUrl);
                Log.v("model", "name is " + name);
                Log.v("model", "time is " + time);

                Glide.with(getApplicationContext()).load(imageUrl).into(vImg);
                vName.setText(name);
                vTime.setText(time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button homebtn = findViewById(R.id.home_btn);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisitorActivity.this, MainActivity.class));
            }
        });

        Button controlbtn = findViewById(R.id.control_btn);
        controlbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisitorActivity.this, DoorlockActivity.class));
            }
        });

        Button listbtn = findViewById(R.id.list_btn);
        listbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisitorActivity.this, ImageActivity.class));
            }
        });
    }
}
