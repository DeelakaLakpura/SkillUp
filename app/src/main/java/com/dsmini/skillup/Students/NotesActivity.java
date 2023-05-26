package com.dsmini.skillup.Students;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsmini.skillup.R;
import com.dsmini.skillup.Students.adapters.noteAdapter;
import com.dsmini.skillup.Students.models.notemodel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView recview;
    private noteAdapter adapter;

    private DatabaseReference Userref;


    private AutoCompleteTextView autoCompleteTextView;

    private ImageView search,refresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        autoCompleteTextView = findViewById(R.id.txt_search);
        search = findViewById(R.id.search_button);
        refresh = findViewById(R.id.refresh_btn);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recview=(RecyclerView)findViewById(R.id.note_view);
                recview.setLayoutManager(new LinearLayoutManager(NotesActivity.this));

                FirebaseRecyclerOptions<notemodel> options =
                        new FirebaseRecyclerOptions.Builder<notemodel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Notes"), notemodel.class)
                                .build();

                adapter=new noteAdapter(options);
                recview.setAdapter(adapter);
                adapter.startListening();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String search = autoCompleteTextView.getText().toString();
           if(search.isEmpty())
           {
               Toast.makeText(NotesActivity.this, "Please Enter Module Number", Toast.LENGTH_SHORT).show();
           }else {
               Userref = FirebaseDatabase.getInstance().getReference().child("Notes");
               recview = (RecyclerView) findViewById(R.id.note_view);
               recview.setLayoutManager(new LinearLayoutManager(NotesActivity.this));

               FirebaseRecyclerOptions<notemodel> options =
                       new FirebaseRecyclerOptions.Builder<notemodel>()
                               .setQuery(Userref.orderByChild("Model").equalTo(search.toString()), notemodel.class)
                               .build();

               adapter = new noteAdapter(options);
               recview.setAdapter(adapter);
               adapter.startListening();
           }
            }
        });

        String[] cityArray = getResources().getStringArray(R.array.module_names);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NotesActivity.this, android.R.layout.simple_list_item_1, cityArray);
        autoCompleteTextView.setAdapter(arrayAdapter);


        recview=(RecyclerView)findViewById(R.id.note_view);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<notemodel> options =
                new FirebaseRecyclerOptions.Builder<notemodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Notes"), notemodel.class)
                        .build();


        adapter = new noteAdapter(options) {
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if (getItemCount() == 0) {
                    Toast.makeText(NotesActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
                else {
                    recview.setAdapter(adapter);
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NotesActivity.this,StudentDashboardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }




}