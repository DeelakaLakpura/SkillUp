package com.dsmini.skillup.Students.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsmini.skillup.R;
import com.dsmini.skillup.Students.NotesActivity;
import com.dsmini.skillup.Students.ViewNotesActivity;
import com.dsmini.skillup.Students.models.notemodel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class noteAdapter extends FirebaseRecyclerAdapter<notemodel,noteAdapter.myviewholder>
{
    private Context mContext;

    public noteAdapter(@NonNull FirebaseRecyclerOptions<notemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull notemodel model)
    {
        holder.notename.setText(model.getTitle());
        holder.module.setText(model.getModel());
        holder.note.setText(model.getNote());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView notename,module,note;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            notename = (TextView)itemView.findViewById(R.id.notename);
            module = (TextView)itemView.findViewById(R.id.module_name);
            note = (TextView)itemView.findViewById(R.id.note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, ViewNotesActivity.class);
                    intent.putExtra("name", notename.getText().toString());
                    intent.putExtra("notemodule", module.getText().toString());
                    intent.putExtra("fullnote", note.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
