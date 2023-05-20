package com.dsmini.skillup.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsmini.skillup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegisterActivity extends AppCompatActivity {

    private EditText name,id,email,password;
    private TextView regbtn,log;
    private CheckBox chk;
    private FirebaseAuth mAuth;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        name = (EditText) findViewById(R.id.username);
        id =(EditText)findViewById(R.id.userID);
        email = (EditText)findViewById(R.id.useremail);
        password = (EditText)findViewById(R.id.pass);
        regbtn = (TextView) findViewById(R.id.btnSend);
        chk = findViewById(R.id.privacy);
        mAuth = FirebaseAuth.getInstance();
        log = findViewById(R.id.txt_new);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });
        pb = findViewById(R.id.progressBar);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentRegisterActivity.this,StudentLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registeruser()
    {
        String stdname = name.getText().toString().trim();
        String uid = id.getText().toString().trim();
        String clientemail = email.getText().toString().trim();
        String clientpass = password.getText().toString().trim();


        if(stdname.isEmpty())
        {
            Toast.makeText(this, "Please Enter Your Name.", Toast.LENGTH_SHORT).show();

            return;
        }
        if(uid.isEmpty())
        {
            Toast.makeText(this, "Please Enter Your ID.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(clientemail.isEmpty())
        {
            Toast.makeText(this, "Please Enter Your Email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(clientemail).matches())
        {
            Toast.makeText(this, "Please Enter Your Valid Email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(clientpass.isEmpty())
        {
            Toast.makeText(this, "Please Enter New Password.", Toast.LENGTH_SHORT).show();
            return;
        }


        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(clientemail,clientpass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            students std = new students(stdname,uid,clientemail);
                            FirebaseDatabase.getInstance().getReference("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(std).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {

                                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            Dialog customDialog = new Dialog(StudentRegisterActivity.this);
                                                            customDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                                                    WindowManager.LayoutParams.WRAP_CONTENT);
                                                            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                            customDialog.getWindow().getAttributes().windowAnimations
                                                                    = android.R.style.Animation_Dialog;
                                                            customDialog.setContentView(R.layout.success_dialog);
                                                            customDialog.setCanceledOnTouchOutside(true);
                                                            TextView yesbtn = customDialog.findViewById(R.id.btn_yes);


                                                            yesbtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    customDialog.cancel();
                                                                    startActivity(new Intent(StudentRegisterActivity.this, StudentLoginActivity.class));
                                                                }
                                                            });

                                                            customDialog.show();

                                                            pb.setVisibility(View.GONE);
                                                        }
                                                    }
                                                });



                                            }else
                                            {
                                                Toast.makeText(StudentRegisterActivity.this, "Sorry! Your account has not been created, please try again.", Toast.LENGTH_SHORT).show();
                                                pb.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }else
                        {
                            Toast.makeText(StudentRegisterActivity.this, "Sorry! Your account has not been created, please try again..", Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);

                        }
                    }
                });


    }


}
