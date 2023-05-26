package com.dsmini.skillup.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsmini.skillup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class StudentDashboardActivity extends AppCompatActivity {

    private FirebaseUser users;
    private DatabaseReference reference;
    private String UserID;
    private TextView count1,count2;
    private ImageView img;
    private  String greeting ;
    private CardView card1,card2,card3,card4;

    String Fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        count1 = findViewById(R.id.std_count);
        count2 = findViewById(R.id.leccount);
        img = findViewById(R.id.imguser);
        card1 = findViewById(R.id.Cat_notes);
        card2 = findViewById(R.id.timetable);
        card3 = findViewById(R.id.exam_time);
        card4 = findViewById(R.id.chat);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDashboardActivity.this,NotesActivity.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDashboardActivity.this,TimeTableActivity.class);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDashboardActivity.this,ExamActivity.class);
                startActivity(intent);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDashboardActivity.this,AIBotActivity.class);
                startActivity(intent);
            }
        });


        users = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Students");
        UserID = users.getUid();



        final TextView fullnameText = (TextView) findViewById(R.id.displayuser);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                students userProfile = snapshot.getValue(students.class);
                if(userProfile != null)
                {
                    Fullname = userProfile.fullname;

                    Calendar c = Calendar.getInstance();
                    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


                    if(timeOfDay >= 0 && timeOfDay < 12){

                        greeting = "~ᵍᵒᵒᵈ ᴹᵒʳⁿⁱⁿᵍ~";

                    }else if(timeOfDay >= 12 && timeOfDay < 16){

                        greeting = "~ᴳᵒᵒᵈ ᴬᶠᵗᵉʳⁿᵒᵒⁿ~";
                    }else if(timeOfDay >= 16 && timeOfDay < 21){

                        greeting = "~ᴳᵒᵒᵈ ᴱᵛᵉⁿⁱⁿᵍ~";
                    }else if(timeOfDay >= 21 && timeOfDay < 24){

                        greeting = "~ᴳᵒᵒᵈ ᴺⁱᵍʰᵗ~";
                    }
                    fullnameText.setText("Hi! " + Fullname + "\n" + greeting);

                }
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StudentDashboardActivity.this);
                        String message =  "Hey! " +  Fullname+". Are you sure you want to logout?";
                        builder.setMessage(message);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(StudentDashboardActivity.this, StudentLoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count= dataSnapshot.getChildrenCount();

                count1.setText(dataSnapshot.getChildrenCount()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseRef.child("Lectures").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count= dataSnapshot.getChildrenCount();
                count2.setText(dataSnapshot.getChildrenCount()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    public void onBackPressed() {
 Dialog customDialog = new Dialog(this);
        customDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.getWindow().getAttributes().windowAnimations
                = android.R.style.Animation_Dialog;
        customDialog.setContentView(R.layout.exit_dialog);
        TextView yesbtn = customDialog.findViewById(R.id.btn_yes);
        TextView nobtn = customDialog.findViewById(R.id.btn_no);

        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });
        customDialog.show();


    }
}