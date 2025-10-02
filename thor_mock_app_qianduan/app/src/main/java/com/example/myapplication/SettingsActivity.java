package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private ImageView backButton;
    private ImageView bellIcon;
    private EditText newDeviceIdEdit;
    private LinearLayout myCarItem;
    private LinearLayout sirItem;
    private Switch driveSelectSwitch;
    private Switch natireCentralSwitch;
    private LinearLayout bluetoothSignalItem;
    private LinearLayout formatItem;
    private TextView selectedCarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        backButton = findViewById(R.id.back_button);
        bellIcon = findViewById(R.id.bell_icon);
        newDeviceIdEdit = findViewById(R.id.new_device_id_edit);
        myCarItem = findViewById(R.id.my_car_item);
        sirItem = findViewById(R.id.sir_item);
        driveSelectSwitch = findViewById(R.id.drive_select_switch);
        natireCentralSwitch = findViewById(R.id.natire_central_switch);
        bluetoothSignalItem = findViewById(R.id.bluetooth_signal_item);
        formatItem = findViewById(R.id.format_item);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> finish());

        bellIcon.setOnClickListener(v -> {
            // 点击通知铃铛
        });

        myCarItem.setOnClickListener(v -> {
            // 显示我的车Dialog
            MyCarDialog myCarDialog = new MyCarDialog(this, () -> {
                // 换车按钮点击 - 显示车辆选择Dialog
                showCarSelectionDialog();
            });
            myCarDialog.show();
        });

        // 输入新设备点击事件 - 弹出四层级联选择
        newDeviceIdEdit.setOnClickListener(v -> {
            showCarSelectionDialog();
        });

        newDeviceIdEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showCarSelectionDialog();
                newDeviceIdEdit.clearFocus();
            }
        });

        sirItem.setOnClickListener(v -> {
            // 处理sir点击
        });

        bluetoothSignalItem.setOnClickListener(v -> {
            // 处理蓝牙信号点击
        });

        formatItem.setOnClickListener(v -> {
            // 处理格式化点击
        });

        // Switch监听器
        driveSelectSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 处理Drive Select开关
        });

        natireCentralSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 处理Natire Central开关
        });
    }

    private void showCarSelectionDialog() {
        CarSelectionDialog carSelectionDialog = new CarSelectionDialog(this,
            (brandId, brandName, modelId, modelName, generationId, generationName, seriesId, seriesName) -> {
                // 将选择结果设置到输入框中
                String carInfo = String.format("%s %s %s %s", brandName, modelName, generationName, seriesName);
                newDeviceIdEdit.setText(carInfo);
                Toast.makeText(this, "已选择车辆: " + carInfo, Toast.LENGTH_SHORT).show();

                // 保存车辆信息到SharedPreferences
                saveCarSelection(brandId, modelId, generationId, seriesId, carInfo);
            });
        carSelectionDialog.show();
    }

    private void saveCarSelection(int brandId, int modelId, int generationId, int seriesId, String carInfo) {
        // 保存车辆选择信息到SharedPreferences
        getSharedPreferences("car_selection", MODE_PRIVATE)
                .edit()
                .putInt("brand_id", brandId)
                .putInt("model_id", modelId)
                .putInt("generation_id", generationId)
                .putInt("series_id", seriesId)
                .putString("car_info", carInfo)
                .apply();
    }
}