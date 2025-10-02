package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SoundControlActivity extends AppCompatActivity {

    private ImageView backButton;
    private ImageView bellIcon;
    private ImageView closeButton;
    private Switch enableSwitch;
    private TextView dynamicModeText;
    private TextView idleModeText;
    private SeekBar volumeSeekBar;
    private SeekBar idleSeekBar;
    private SeekBar frequencySeekBar;
    private LinearLayout sound1Button;
    private LinearLayout sound2Button;
    private LinearLayout sound3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_control);

        initViews();
        setupClickListeners();
        setupSeekBars();
    }

    private void initViews() {
        backButton = findViewById(R.id.back_button);
        bellIcon = findViewById(R.id.bell_icon);
        closeButton = findViewById(R.id.close_button);
        enableSwitch = findViewById(R.id.enable_switch);
        dynamicModeText = findViewById(R.id.dynamic_mode_text);
        idleModeText = findViewById(R.id.idle_mode_text);
        volumeSeekBar = findViewById(R.id.volume_seekbar);
        idleSeekBar = findViewById(R.id.idle_seekbar);
        frequencySeekBar = findViewById(R.id.frequency_seekbar);
        sound1Button = findViewById(R.id.sound_1_button);
        sound2Button = findViewById(R.id.sound_2_button);
        sound3Button = findViewById(R.id.sound_3_button);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> finish());

        bellIcon.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });

        closeButton.setOnClickListener(v -> finish());

        // 动态启动/启动响应 切换
        dynamicModeText.setOnClickListener(v -> {
            dynamicModeText.setTextColor(getColor(R.color.blue_selected));
            idleModeText.setTextColor(getColor(R.color.gray_unselected));
        });

        idleModeText.setOnClickListener(v -> {
            idleModeText.setTextColor(getColor(R.color.blue_selected));
            dynamicModeText.setTextColor(getColor(R.color.gray_unselected));
        });

        // 声音选择按钮
        sound1Button.setOnClickListener(v -> selectSound(1));
        sound2Button.setOnClickListener(v -> selectSound(2));
        sound3Button.setOnClickListener(v -> selectSound(3));

        // 开启/关闭声音
        enableSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 控制音效的开启/关闭
        });
    }

    private void setupSeekBars() {
        // 设置进度条的初始值和监听器
        volumeSeekBar.setProgress(75); // 默认75%音量
        idleSeekBar.setProgress(50);   // 默认50%怠速音
        frequencySeekBar.setProgress(60); // 默认60%音效频率

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 处理音量变化
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        idleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 处理怠速音变化
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        frequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 处理音效频率变化
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void selectSound(int soundNumber) {
        // 重置所有按钮状态
        resetSoundButtons();

        // 选中当前按钮
        switch (soundNumber) {
            case 1:
                sound1Button.setBackgroundResource(R.color.blue_selected);
                break;
            case 2:
                sound2Button.setBackgroundResource(R.color.blue_selected);
                break;
            case 3:
                sound3Button.setBackgroundResource(R.color.blue_selected);
                break;
        }
    }

    private void resetSoundButtons() {
        sound1Button.setBackgroundResource(R.color.gray_unselected);
        sound2Button.setBackgroundResource(R.color.gray_unselected);
        sound3Button.setBackgroundResource(R.color.gray_unselected);
    }
}