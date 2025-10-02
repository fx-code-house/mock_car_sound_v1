package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class MyCarDialog extends Dialog {

    private TextView brandText;
    private TextView modelText;
    private TextView generationText;
    private TextView seriesText;
    private TextView statusText;
    private Button changeCarButton;
    private ImageView closeButton;

    private OnChangeCarListener listener;

    public interface OnChangeCarListener {
        void onChangeCarClicked();
    }

    public MyCarDialog(@NonNull Context context, OnChangeCarListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_my_car, null);
        setContentView(view);

        initViews(view);
        loadCarInfo();
        setupClickListeners();

        // 设置窗口属性
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void initViews(View view) {
        brandText = view.findViewById(R.id.brand_text);
        modelText = view.findViewById(R.id.model_text);
        generationText = view.findViewById(R.id.generation_text);
        seriesText = view.findViewById(R.id.series_text);
        statusText = view.findViewById(R.id.status_text);
        changeCarButton = view.findViewById(R.id.change_car_button);
        closeButton = view.findViewById(R.id.close_button);
    }

    private void loadCarInfo() {
        SharedPreferences prefs = getContext().getSharedPreferences("car_selection", Context.MODE_PRIVATE);
        String carInfo = prefs.getString("car_info", "");

        if (!carInfo.isEmpty()) {
            // 解析车辆信息
            String[] parts = carInfo.split(" ");
            if (parts.length >= 4) {
                brandText.setText(parts[0]);
                modelText.setText(parts[1]);
                generationText.setText(parts[2]);
                seriesText.setText(parts[3]);
            }
            statusText.setText("已连接");
        } else {
            // 显示默认信息
            brandText.setText("Toyota");
            modelText.setText("Camry");
            generationText.setText("XV50");
            seriesText.setText("Sedan 4-doors");
            statusText.setText("未连接");
        }
    }

    private void setupClickListeners() {
        closeButton.setOnClickListener(v -> dismiss());

        changeCarButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChangeCarClicked();
            }
            dismiss();
        });
    }

    public void updateCarInfo(String brandName, String modelName, String generationName, String seriesName) {
        brandText.setText(brandName);
        modelText.setText(modelName);
        generationText.setText(generationName);
        seriesText.setText(seriesName);
        statusText.setText("已连接");
    }
}