package com.dsmini.skillup.Lectures.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dsmini.skillup.R;
import com.dsmini.skillup.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private  String module_name;
    private EditText txt1,txt2;

    private CardView btn;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Spinner Module_Spinner = view.findViewById(R.id.my_spinner_module);
        txt1 = view.findViewById(R.id.titleEt);
        txt2= view.findViewById(R.id.txtnote);
        btn= view.findViewById(R.id.cv_uploadBtn);
        progressDialog=new ProgressDialog(getContext());

        String[] modules = {"PUSL2023", "PUSL2022","PUSL2021","PUSL2020"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_items, modules);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Module_Spinner.setAdapter(adapter);
        module_name = Module_Spinner.getSelectedItem().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });



        return view;
    }
    private void insertData() {

        String noteTitle = txt1.getText().toString();
        String NoteModule = module_name;
        String FullNote = txt2.getText().toString();

        Random rand = new Random();
        int max = 1000000;
        int randomNum = rand.nextInt(max);

        if (noteTitle.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Note Title", Toast.LENGTH_SHORT).show();
        } else if (FullNote.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Note ", Toast.LENGTH_SHORT).show();

        } else {

            progressDialog.setTitle("Uploading");
            progressDialog.setMessage("please wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> data = new HashMap<>();
            data.put("Title", noteTitle);
            data.put("Model", NoteModule);
            data.put("Note", FullNote);
            databaseRef.child("Notes").child(String.valueOf(randomNum)).setValue(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data successfully uploaded
                            progressDialog.dismiss();

                            

                            Toast.makeText(getContext(), "Note Upload Success!!", Toast.LENGTH_SHORT).show();
                            
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error uploading data
                            progressDialog.dismiss();
                        }

                    });


        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}