package com.dsmini.skillup.Lectures;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dsmini.skillup.R;
import com.dsmini.skillup.databinding.ActivityLecturesDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class LecturesDashboardActivity extends AppCompatActivity {

    private ActivityLecturesDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLecturesDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_home)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_lectures_dashboard);

        NavigationUI.setupWithNavController(binding.navView, navController);
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