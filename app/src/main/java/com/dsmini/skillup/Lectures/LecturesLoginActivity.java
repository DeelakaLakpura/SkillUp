package com.dsmini.skillup.Lectures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dsmini.skillup.R;
import com.dsmini.skillup.Students.StudentDashboardActivity;
import com.dsmini.skillup.Students.StudentLoginActivity;
import com.dsmini.skillup.Students.StudentRegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LecturesLoginActivity extends AppCompatActivity {

    TextView register ;
    EditText txtemail, txtpass;

    private ProgressDialog progressDialog;

    FirebaseAuth mAuth;

    Button log;
    CheckBox rem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures_login);
        register = findViewById(R.id.signupText);

        progressDialog=new ProgressDialog(this);

        txtemail = findViewById(R.id.username);
        txtpass = findViewById(R.id.logpass);
        log = findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LecturesLoginActivity.this, LecturesRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userlogin() {
        String logclinetemail = txtemail.getText().toString().trim();
        String clinetlogpass = txtpass.getText().toString().trim();

        if (logclinetemail.isEmpty()) {
            txtemail.setError("Please Enter Your Email");
            txtemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(logclinetemail).matches()) {
            txtemail.setError("Please Enter Valid Email Address");
            txtemail.requestFocus();
            return;
        }
        if (clinetlogpass.isEmpty()) {
            txtpass.setError("Please Enter Your Password");
            txtpass.requestFocus();
            return;
        }
        progressDialog.setTitle("Log In");
        progressDialog.setMessage("please wait...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(logclinetemail, clinetlogpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Intent intent = new Intent(LecturesLoginActivity.this, LecturesDashboardActivity.class);
                    startActivity(intent);



                } else {

                    Toast.makeText(LecturesLoginActivity.this, "Sorry! Login failed.", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });


    }
}
