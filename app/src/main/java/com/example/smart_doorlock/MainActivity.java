package com.example.smart_doorlock;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //도어락 제어
        Button doorlockBtn = findViewById(R.id.doorlock_btn);
        doorlockBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, DoorlockActivity.class));
            }
        });

        //방문자 목록
        Button imageListBtn = findViewById(R.id.image_list_btn);
        imageListBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ImageActivity.class));
            }
        });

        Button setBtn = findViewById(R.id.setting);
        setBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });
    }
}