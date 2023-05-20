package com.dsmini.skillup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.dsmini.skillup.Lectures.LecturesLoginActivity;
import com.dsmini.skillup.Students.StudentLoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView students;
    LottieAnimationView  lec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        students = findViewById(R.id.btn_std);
        lec = findViewById(R.id.btn_tutor);

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });

        lec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LecturesLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}