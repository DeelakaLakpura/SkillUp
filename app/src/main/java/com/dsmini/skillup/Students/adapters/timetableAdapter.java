package com.dsmini.skillup.Students.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsmini.skillup.R;
import com.dsmini.skillup.Students.NotesActivity;
import com.dsmini.skillup.Students.ViewNotesActivity;
import com.dsmini.skillup.Students.models.notemodel;
import com.dsmini.skillup.Students.models.timetablemodule;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class timetableAdapter extends FirebaseRecyclerAdapter<timetablemodule,timetableAdapter.myviewholder>
{
    private Context mContext;

    public timetableAdapter(@NonNull FirebaseRecyclerOptions<timetablemodule> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull timetablemodule model)
    {
        holder.time.setText(model.getTime());
        holder.title.setText(model.getTitle());
        holder.date.setText(model.getDate());
        holder.category.setText(model.getCategory());
        holder.module.setText(model.getModule());



    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_card,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView title,date,time,category,module;
        Switch Reminder;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.timetable_name);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            category = (TextView)itemView.findViewById(R.id.category_name);
            module = (TextView)itemView.findViewById(R.id.module);



        }
    }
}
