package com.dsmini.skillup.Students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dsmini.skillup.R;
import com.dsmini.skillup.Students.adapters.timetableAdapter;
import com.dsmini.skillup.Students.models.timetablemodule;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExamActivity extends AppCompatActivity {

    private RecyclerView recview;

    private    String url;



    timetableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);


        Button openLinkButton = findViewById(R.id.openLinkButton);
        openLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent intent = new Intent(ExamActivity.this,WebViewActivity.class);
             startActivity(intent);
            }
        });

        recview=(RecyclerView)findViewById(R.id.note_view);
        recview.setLayoutManager(new LinearLayoutManager(ExamActivity.this));

        FirebaseRecyclerOptions<timetablemodule> options =
                new FirebaseRecyclerOptions.Builder<timetablemodule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TimeTable").orderByChild("Category").equalTo("Examination"), timetablemodule.class)
                        .build();

        adapter=new timetableAdapter(options);
        recview.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}