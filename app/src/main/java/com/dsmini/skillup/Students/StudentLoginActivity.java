package com.dsmini.skillup.Students;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsmini.skillup.ForgotPasswordActivity;
import com.dsmini.skillup.Lectures.LecturesDashboardActivity;
import com.dsmini.skillup.Lectures.LecturesLoginActivity;
import com.dsmini.skillup.Lectures.LecturesRegisterActivity;
import com.dsmini.skillup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLoginActivity extends AppCompatActivity {

    private TextView register;

    private EditText txtemail,txtpass;
    private TextView reg,log,forogt,gus,reset;
    private FirebaseAuth mAuth;
    private CheckBox rem;
    private ProgressBar pb;

    private  ImageView showpass;

    boolean isPasswordVisible = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        register = findViewById(R.id.txtreg);

        reg = findViewById(R.id.txtreg);
        txtemail = findViewById(R.id.logphone);
        txtpass = findViewById(R.id.logpass);
        log = findViewById(R.id.btnlog);
        reset = findViewById(R.id.forg);
        mAuth = FirebaseAuth.getInstance();


        pb = findViewById(R.id.progressBar);
        showpass = findViewById(R.id.show_pass_img);




        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // If the password is visible, hide it and update the toggle image
                    txtpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showpass.setImageResource(R.drawable.baseline_visibility_off_24);
                    isPasswordVisible = false;
                } else {
                    // If the password is hidden, show it and update the toggle image
                    txtpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showpass.setImageResource(R.drawable.baseline_visibility_24);
                    isPasswordVisible = true;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intetnt = new Intent(StudentLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intetnt);
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Check if user's email is verified
            if (user.isEmailVerified()) {
                // User is signed in and email is verified
                Intent i = new Intent(StudentLoginActivity.this, StudentDashboardActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                // User's email is not verified, show a message or take appropriate action
                Toast.makeText(this, "Please verify your email to continue", Toast.LENGTH_LONG).show();
            }
        } else {
            // User is not signed in, handle appropriately
        }





        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });








        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentLoginActivity.this,StudentRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userlogin() {
        String logclinetemail = txtemail.getText().toString().trim();
        String clinetlogpass = txtpass.getText().toString().trim();

        if(logclinetemail.isEmpty())
        {
            txtemail.setError("Please Enter Your Email");
            txtemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(logclinetemail).matches())
        {
            txtemail.setError("Please Enter Valid Email Address");
            txtemail.requestFocus();
            return;
        }
        if(clinetlogpass.isEmpty())
        {
            txtpass.setError("Please Enter Your Password");
            txtpass.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(logclinetemail,clinetlogpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(mAuth.getCurrentUser().isEmailVerified()){

                        Intent intent = new Intent(StudentLoginActivity.this,StudentDashboardActivity.class);
                        startActivity(intent);
                        pb.setVisibility(View.INVISIBLE);


                    }else {
                        Toast.makeText(StudentLoginActivity.this, "Please Verify Your Email", Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.INVISIBLE);


                    }


                }else
                {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(StudentLoginActivity.this, "Sorry! Login failed.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}