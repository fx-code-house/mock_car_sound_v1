package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TechSupportActivity extends AppCompatActivity {

    private ImageView backButton;
    private ImageView bellIcon;
    private LinearLayout updateSoftwareItem;
    private LinearLayout demonstrationModeItem;
    private LinearLayout supportItem;
    private LinearLayout settingsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_support);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        backButton = findViewById(R.id.back_button);
        bellIcon = findViewById(R.id.bell_icon);
        updateSoftwareItem = findViewById(R.id.update_software_item);
        demonstrationModeItem = findViewById(R.id.demonstration_mode_item);
        supportItem = findViewById(R.id.support_item);
        settingsItem = findViewById(R.id.settings_item);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> finish());

        bellIcon.setOnClickListener(v -> {
            // 通知功能
        });

        updateSoftwareItem.setOnClickListener(v -> {
            // 处理更新软件点击
        });

        demonstrationModeItem.setOnClickListener(v -> {
            // 处理演示模式点击
        });

        supportItem.setOnClickListener(v -> {
            // 处理支持点击
        });

        settingsItem.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
    }
}