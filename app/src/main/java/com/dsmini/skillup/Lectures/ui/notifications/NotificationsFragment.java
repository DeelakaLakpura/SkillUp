package com.dsmini.skillup.Lectures.ui.notifications;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dsmini.skillup.R;
import com.dsmini.skillup.databinding.FragmentNotificationsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private String Date;
    private TimePicker tm1, tm2;
    private TextView tx;
    private  EditText title,desc,lectime;

    private     ProgressDialog progressDialog;

    private  String Module,Category;

    private CardView btn;

  private  Spinner mySpinner,Module_Spinner;

    FirebaseDatabase database = FirebaseDatabase.getInstance();




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        progressDialog=new ProgressDialog(getContext());

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        title = view.findViewById(R.id.titleEt);
        desc = view.findViewById(R.id.txthourse);
        lectime = view.findViewById(R.id.time);

        btn = view.findViewById(R.id.uploadBtn);

         mySpinner = view.findViewById(R.id.my_spinner);
         Module_Spinner = view.findViewById(R.id.my_spinner_module);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });







        String[] items = {"Lecture", "Examination"};
        String[] modules = {"PUSL2023", "PUSL2022","PUSL2021","PUSL2020"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_items, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        ArrayAdapter<String> module_adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_items, modules);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Module_Spinner.setAdapter(module_adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category = parent.getItemAtPosition(position).toString();
                // Do something with the selected text
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Module_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Module = parent.getItemAtPosition(position).toString();
                // Do something with the selected text
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        TextView editText = view.findViewById(R.id.datePicker);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the calendar view here
                // Get the current date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a new DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Update the EditText view with the selected date
                                Date = (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });








        return view;

    }

    private void insertData() {



        String lecTitle = title.getText().toString();
        String lecDesc = desc.getText().toString();
        String category = Category;
        String module = Module;
        String time = lectime.getText().toString();
        String date = Date;


        Random rand = new Random();
        int max = 1000000;
        int randomNum = rand.nextInt(max);

        if (lecTitle.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Lecture Title", Toast.LENGTH_SHORT).show();
        } else if (lecDesc.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Note ", Toast.LENGTH_SHORT).show();

        }
        else if (category.isEmpty()) {
            Toast.makeText(getContext(), "Please Select Category ", Toast.LENGTH_SHORT).show();

        }
        else if (module.isEmpty()) {
            Toast.makeText(getContext(), "Please Select Module ", Toast.LENGTH_SHORT).show();

        }
        else if (time.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Lecture Start Time ", Toast.LENGTH_SHORT).show();

        }
        else {

            progressDialog.setTitle("Uploading");
            progressDialog.setMessage("please wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> data = new HashMap<>();
            data.put("Title", lecTitle);
            data.put("Description", lecDesc);
            data.put("Category", category);
            data.put("Module", module);
            data.put("Time", time);
            data.put("Date", date);
            databaseRef.child("TimeTable").child(String.valueOf(randomNum)).setValue(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                            FirebaseMessaging.getInstance().send(new RemoteMessage.Builder("sender_id@appspot.gcm.googleapis.com")
                                    .setMessageId(Integer.toString(new Random().nextInt(9999)))
                                    .addData("message", "A new timetable has been added!")
                                    .build());

                            // Data successfully uploaded
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "TimeTable Upload Success!!", Toast.LENGTH_SHORT).show();

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