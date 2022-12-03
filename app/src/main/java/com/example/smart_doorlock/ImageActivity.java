package com.example.smart_doorlock;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> list = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");

    ImageAdapter adapter;
    String imageUrl = null;
    String time = null;
    String name = null;
    String[] id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ImageAdapter(ImageActivity.this, list);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();

                for(Model item:list) {
                    imageUrl = item.getImageUrl();
                    time = item.getTime();
                    name = item.getName();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        id = getResources().getStringArray(R.array.id_array);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, id);
        //드롭다운 클릭 시 선택 창
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ImageActivity.this, "선택: " + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                list.clear();

                if (position == 0) {
                    root.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            list.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();

                            for(Model item:list) {
                                imageUrl = item.getImageUrl();
                                time = item.getTime();
                                name = item.getName();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (position == 1) {
                    Query myTopPostQuery = root.orderByChild("name").equalTo("김화정");
                    myTopPostQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();

                            for(Model item:list) {
                                imageUrl = item.getImageUrl();
                                time = item.getTime();
                                name = item.getName();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                } else if (position == 2) {
                    Query myTopPostQuery = root.orderByChild("name").equalTo("윤현진");
                    myTopPostQuery.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();

                            for(Model item:list) {
                                imageUrl = item.getImageUrl();
                                time = item.getTime();
                                name = item.getName();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                } else if (position == 3) {
                    Query myTopPostQuery = root.orderByChild("name").equalTo("임다솜");
                    myTopPostQuery.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();

                            for(Model item:list) {
                                imageUrl = item.getImageUrl();
                                time = item.getTime();
                                name = item.getName();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                } else if (position == 4) {
                    Query myTopPostQuery = root.orderByChild("name").equalTo("소희");
                    myTopPostQuery.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();

                            for(Model item:list) {
                                imageUrl = item.getImageUrl();
                                time = item.getTime();
                                name = item.getName();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                } else if (position == 5) {
                    Query myTopPostQuery = root.orderByChild("name").equalTo("Guest");
                    myTopPostQuery.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();

                            for(Model item:list) {
                                imageUrl = item.getImageUrl();
                                time = item.getTime();
                                name = item.getName();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

