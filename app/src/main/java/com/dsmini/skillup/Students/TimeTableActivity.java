package com.dsmini.skillup.Students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dsmini.skillup.R;
import com.dsmini.skillup.Students.adapters.noteAdapter;
import com.dsmini.skillup.Students.adapters.timetableAdapter;
import com.dsmini.skillup.Students.models.notemodel;
import com.dsmini.skillup.Students.models.timetablemodule;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class TimeTableActivity extends AppCompatActivity {

    private RecyclerView recview;

     timetableAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        recview=(RecyclerView)findViewById(R.id.note_view);
        recview.setLayoutManager(new LinearLayoutManager(TimeTableActivity.this));

        FirebaseRecyclerOptions<timetablemodule> options =
                new FirebaseRecyclerOptions.Builder<timetablemodule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TimeTable").orderByChild("Category").equalTo("Lecture"), timetablemodule.class)
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