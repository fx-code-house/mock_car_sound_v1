package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    // private CardView carCard1;
    // private CardView carCard2;
    private LinearLayout navHome;
    private LinearLayout navSound;
    private LinearLayout navCenter;
    private LinearLayout navTech;
    private LinearLayout navProfile;
    private ImageView bellIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        // carCard1 = findViewById(R.id.car_card_1);
        // carCard2 = findViewById(R.id.car_card_2);
        navHome = findViewById(R.id.nav_home);
        navSound = findViewById(R.id.nav_sound);
        navCenter = findViewById(R.id.nav_center);
        navTech = findViewById(R.id.nav_tech);
        navProfile = findViewById(R.id.nav_profile);
        bellIcon = findViewById(R.id.bell_icon);
    }

    private void setupClickListeners() {
        // Car card click listeners - commented out since cards are removed
        // carCard1.setOnClickListener(v -> onCarCardClicked(1));
        // carCard2.setOnClickListener(v -> onCarCardClicked(2));

        // Bottom navigation
        navHome.setOnClickListener(v -> onNavigationClicked("home"));
        navSound.setOnClickListener(v -> onNavigationClicked("sound"));
        navCenter.setOnClickListener(v -> onNavigationClicked("center"));
        navTech.setOnClickListener(v -> onNavigationClicked("tech"));
        navProfile.setOnClickListener(v -> onNavigationClicked("profile"));

        // Bell icon
        bellIcon.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
    }

    private void onCarCardClicked(int cardNumber) {
        // 点击车辆卡片，打开声音调整页面
        Intent intent = new Intent(this, SoundControlActivity.class);
        intent.putExtra("CAR_NUMBER", cardNumber);
        startActivity(intent);
    }

    private void onNavigationClicked(String section) {
        switch (section) {
            case "home":
                // 当前页面，不需要跳转
                break;
            case "sound":
                startActivity(new Intent(this, SoundControlActivity.class));
                break;
            case "center":
                // ALPHAX中心，暂时不跳转
                break;
            case "tech":
                startActivity(new Intent(this, TechSupportActivity.class));
                break;
            case "profile":
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }
}