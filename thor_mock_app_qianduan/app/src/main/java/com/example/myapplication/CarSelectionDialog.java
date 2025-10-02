package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class CarSelectionDialog extends Dialog {
    private static final String TAG = "CarSelectionDialog";

    private CarDataManager carDataManager;
    private TextView selectedBrandText;
    private TextView selectedModelText;
    private TextView selectedGenerationText;
    private TextView selectedSeriesText;

    // 当前选中的项
    private CarDataManager.CarBrand selectedBrand;
    private CarDataManager.CarModel selectedModel;
    private CarDataManager.CarGeneration selectedGeneration;
    private CarDataManager.CarSeries selectedSeries;

    private OnCarSelectedListener listener;

    public interface OnCarSelectedListener {
        void onCarSelected(int brandId, String brandName, int modelId, String modelName,
                          int generationId, String generationName, int seriesId, String seriesName);
    }

    public CarSelectionDialog(@NonNull Context context, OnCarSelectedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_car_selection, null);
        setContentView(view);

        initViews(view);
        setupCarDataManager();

        // 设置窗口属性
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // 设置Dialog显示在下半屏幕
            android.view.WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
            params.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = android.view.Gravity.BOTTOM;
            getWindow().setAttributes(params);

            // 设置进入和退出动画
            getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        }
    }

    private void initViews(View view) {
        selectedBrandText = view.findViewById(R.id.selected_brand_text);
        selectedModelText = view.findViewById(R.id.selected_model_text);
        selectedGenerationText = view.findViewById(R.id.selected_generation_text);
        selectedSeriesText = view.findViewById(R.id.selected_series_text);

        // 设置点击事件
        view.findViewById(R.id.brand_selection_layout).setOnClickListener(v -> showBrandSelectionDialog());
        view.findViewById(R.id.model_selection_layout).setOnClickListener(v -> showModelSelectionDialog());
        view.findViewById(R.id.generation_selection_layout).setOnClickListener(v -> showGenerationSelectionDialog());
        view.findViewById(R.id.series_selection_layout).setOnClickListener(v -> showSeriesSelectionDialog());

        view.findViewById(R.id.confirm_button).setOnClickListener(v -> confirmSelection());
        view.findViewById(R.id.close_button).setOnClickListener(v -> dismiss());
    }

    private void setupCarDataManager() {
        carDataManager = CarDataManager.getInstance();

        // 显示加载提示
        Toast.makeText(getContext(), "加载车辆数据中...", Toast.LENGTH_SHORT).show();

        carDataManager.loadData(getContext(), success -> {
            if (getContext() instanceof android.app.Activity) {
                ((android.app.Activity) getContext()).runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(getContext(), "车辆数据加载完成", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Car data loaded, brands count: " + carDataManager.getBrands().size());
                    } else {
                        Toast.makeText(getContext(), "车辆数据加载失败", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to load car data");
                    }
                });
            }
        });
    }

    private void showBrandSelectionDialog() {
        List<CarDataManager.CarBrand> brands = carDataManager.getBrands();
        if (brands.isEmpty()) {
            Toast.makeText(getContext(), "车辆数据尚未加载完成，请稍候", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> brandNames = new ArrayList<>();
        for (CarDataManager.CarBrand brand : brands) {
            brandNames.add(brand.name);
        }

        showSelectionDialog("选择品牌", brandNames, (position) -> {
            selectedBrand = brands.get(position);
            selectedBrandText.setText(selectedBrand.name);

            // 清除后续选择
            clearModelSelection();
            clearGenerationSelection();
            clearSeriesSelection();

            Log.d(TAG, "Selected brand: " + selectedBrand.name);
        });
    }

    private void showModelSelectionDialog() {
        if (selectedBrand == null) {
            Toast.makeText(getContext(), "请先选择品牌", Toast.LENGTH_SHORT).show();
            return;
        }

        List<CarDataManager.CarModel> models = carDataManager.getModelsForBrand(selectedBrand.id);
        if (models.isEmpty()) {
            Toast.makeText(getContext(), "该品牌下没有可用车型", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> modelNames = new ArrayList<>();
        for (CarDataManager.CarModel model : models) {
            modelNames.add(model.name);
        }

        showSelectionDialog("选择车型", modelNames, (position) -> {
            selectedModel = models.get(position);
            selectedModelText.setText(selectedModel.name);

            // 清除后续选择
            clearGenerationSelection();
            clearSeriesSelection();

            Log.d(TAG, "Selected model: " + selectedModel.name);
        });
    }

    private void showGenerationSelectionDialog() {
        if (selectedModel == null) {
            Toast.makeText(getContext(), "请先选择车型", Toast.LENGTH_SHORT).show();
            return;
        }

        List<CarDataManager.CarGeneration> generations = carDataManager.getGenerationsForModel(selectedModel.id);
        if (generations.isEmpty()) {
            Toast.makeText(getContext(), "该车型下没有可用世代", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> generationNames = new ArrayList<>();
        for (CarDataManager.CarGeneration generation : generations) {
            String displayName = generation.name;
            if (!generation.yearBegin.isEmpty()) {
                displayName += " (" + generation.yearBegin;
                if (!generation.yearEnd.isEmpty()) {
                    displayName += "-" + generation.yearEnd;
                }
                displayName += ")";
            }
            generationNames.add(displayName);
        }

        showSelectionDialog("选择世代", generationNames, (position) -> {
            selectedGeneration = generations.get(position);
            selectedGenerationText.setText(generationNames.get(position));

            // 清除后续选择
            clearSeriesSelection();

            Log.d(TAG, "Selected generation: " + selectedGeneration.name);
        });
    }

    private void showSeriesSelectionDialog() {
        if (selectedGeneration == null) {
            Toast.makeText(getContext(), "请先选择世代", Toast.LENGTH_SHORT).show();
            return;
        }

        List<CarDataManager.CarSeries> series = carDataManager.getSeriesForGeneration(selectedGeneration.id);
        if (series.isEmpty()) {
            Toast.makeText(getContext(), "该世代下没有可用系列", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> seriesNames = new ArrayList<>();
        for (CarDataManager.CarSeries s : series) {
            seriesNames.add(s.name);
        }

        showSelectionDialog("选择系列", seriesNames, (position) -> {
            selectedSeries = series.get(position);
            selectedSeriesText.setText(selectedSeries.name);

            Log.d(TAG, "Selected series: " + selectedSeries.name);
        });
    }

    private void showSelectionDialog(String title, List<String> items, OnItemSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        ListView listView = new ListView(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            listener.onItemSelected(position);
            ((AlertDialog) parent.getTag()).dismiss();
        });

        AlertDialog dialog = builder.setView(listView)
                .setNegativeButton("取消", null)
                .create();

        listView.setTag(dialog);
        dialog.show();
    }

    private void clearModelSelection() {
        selectedModel = null;
        selectedModelText.setText("请选择车型");
    }

    private void clearGenerationSelection() {
        selectedGeneration = null;
        selectedGenerationText.setText("请选择世代");
    }

    private void clearSeriesSelection() {
        selectedSeries = null;
        selectedSeriesText.setText("请选择系列");
    }

    private void confirmSelection() {
        if (selectedBrand == null || selectedModel == null || selectedGeneration == null || selectedSeries == null) {
            Toast.makeText(getContext(), "请完成所有选择", Toast.LENGTH_SHORT).show();
            return;
        }

        // 返回选择结果
        if (listener != null) {
            listener.onCarSelected(
                    selectedBrand.id, selectedBrand.name,
                    selectedModel.id, selectedModel.name,
                    selectedGeneration.id, selectedGeneration.name,
                    selectedSeries.id, selectedSeries.name
            );
        }
        dismiss();
    }

    private interface OnItemSelectedListener {
        void onItemSelected(int position);
    }
}