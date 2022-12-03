package com.example.smart_doorlock;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private ArrayList<Model> list = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Noti");
    private DatabaseReference root1 = FirebaseDatabase.getInstance().getReference("Image");
    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    String noti = null;
    String time;
    String imgUrl;

    NotificationManager manager;
    NotificationCompat.Builder builder;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button btn = findViewById(R.id.create);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                root.child("noti").setValue("1");
            }
        });

        Query query = root.orderByChild("noti");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Noti value = snapshot.getValue(Noti.class);

                noti = value.getNoti();

                Log.v("gggu", "Value is " + noti);

                if (noti.equals("1")) {
                    Log.v("gggu", "Valueee is " + noti);
                    Notify();
                    root.child("noti").setValue("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void Notify() {

        Query query1 = root1.orderByChild("time").limitToLast(1);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }

                for(Model item:list) {
                    imgUrl = item.getImageUrl();
                    time = item.getTime();
                }
                Log.v("Time", "Value is " + time);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Bitmap mLargeNoti = BitmapFactory.decodeResource(getResources(), R.drawable.brand_icon);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
            builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.noti_icon)
                    .setLargeIcon(mLargeNoti)
                    .setContentTitle("출입이 감지되었습니다")
                    .setContentText(time)
                    .setDefaults(Notification.DEFAULT_SOUND);
        }

        Intent intent = new Intent(NotificationActivity.this, VisitorActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);

        Notification notification = builder.build();

        //알림창 실행
        manager.notify(1,notification);
    }
}