package com.dsmini.skillup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsmini.skillup.Students.StudentLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {


    private Button resetPasswordButton;
    private EditText txtEmail;
    private FirebaseAuth firebaseAuth;

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetPasswordButton = findViewById(R.id.btn_rest);
        txtEmail = findViewById(R.id.rest_email);
        pb = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                sendPasswordResetEmail(email);
            }
        });


    }

    private void sendPasswordResetEmail(String email) {
        pb.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Please Check Your Emails", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(ForgotPasswordActivity.this, StudentLoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.INVISIBLE);

                    }
                });
    }
}