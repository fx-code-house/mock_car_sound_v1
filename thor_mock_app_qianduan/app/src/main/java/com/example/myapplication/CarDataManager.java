package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CarDataManager {
    private static final String TAG = "CarDataManager";
    private static CarDataManager instance;
    private List<CarBrand> brands;
    private boolean isLoaded = false;

    private CarDataManager() {
        brands = new ArrayList<>();
    }

    public static synchronized CarDataManager getInstance() {
        if (instance == null) {
            instance = new CarDataManager();
        }
        return instance;
    }

    // 异步加载数据
    public void loadData(Context context, LoadDataCallback callback) {
        if (isLoaded) {
            callback.onDataLoaded(true);
            return;
        }

        new Thread(() -> {
            try {
                loadDataFromAssets(context);
                isLoaded = true;
                if (callback != null) {
                    callback.onDataLoaded(true);
                }
                Log.d(TAG, "Car data loaded successfully. Total brands: " + brands.size());
            } catch (Exception e) {
                Log.e(TAG, "Failed to load car data", e);
                if (callback != null) {
                    callback.onDataLoaded(false);
                }
            }
        }).start();
    }

    private void loadDataFromAssets(Context context) throws IOException, JSONException {
        InputStream inputStream = context.getAssets().open("canbin.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        JSONArray jsonArray = new JSONArray(builder.toString());
        parseCarData(jsonArray);
    }

    private void parseCarData(JSONArray jsonArray) throws JSONException {
        brands.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject brandObj = jsonArray.getJSONObject(i);
            CarBrand brand = new CarBrand();
            brand.id = brandObj.getInt("id_car_mark");
            brand.name = brandObj.getString("name");
            brand.models = new ArrayList<>();

            JSONArray modelsArray = brandObj.getJSONArray("models");
            for (int j = 0; j < modelsArray.length(); j++) {
                JSONObject modelObj = modelsArray.getJSONObject(j);
                CarModel model = new CarModel();
                model.id = modelObj.getInt("id_car_model");
                model.name = modelObj.getString("name");
                model.brandId = modelObj.getInt("id_car_mark");
                model.generations = new ArrayList<>();

                JSONArray generationsArray = modelObj.getJSONArray("generations");
                for (int k = 0; k < generationsArray.length(); k++) {
                    JSONObject genObj = generationsArray.getJSONObject(k);
                    CarGeneration generation = new CarGeneration();
                    generation.id = genObj.getInt("id_car_generation");
                    generation.name = genObj.getString("name");
                    generation.modelId = genObj.getInt("id_car_model");
                    generation.yearBegin = genObj.optString("year_begin", "");
                    generation.yearEnd = genObj.optString("year_end", "");
                    generation.series = new ArrayList<>();

                    JSONArray seriesArray = genObj.getJSONArray("series");
                    for (int l = 0; l < seriesArray.length(); l++) {
                        JSONObject serieObj = seriesArray.getJSONObject(l);
                        CarSeries series = new CarSeries();
                        series.id = serieObj.getInt("id_car_serie");
                        series.name = serieObj.getString("name");
                        series.modelId = serieObj.getInt("id_car_model");
                        series.generationId = serieObj.getInt("id_car_generation");

                        generation.series.add(series);
                    }
                    model.generations.add(generation);
                }
                brand.models.add(model);
            }
            brands.add(brand);
        }
    }

    public List<CarBrand> getBrands() {
        return brands;
    }

    public List<CarModel> getModelsForBrand(int brandId) {
        for (CarBrand brand : brands) {
            if (brand.id == brandId) {
                return brand.models;
            }
        }
        return new ArrayList<>();
    }

    public List<CarGeneration> getGenerationsForModel(int modelId) {
        for (CarBrand brand : brands) {
            for (CarModel model : brand.models) {
                if (model.id == modelId) {
                    return model.generations;
                }
            }
        }
        return new ArrayList<>();
    }

    public List<CarSeries> getSeriesForGeneration(int generationId) {
        for (CarBrand brand : brands) {
            for (CarModel model : brand.models) {
                for (CarGeneration generation : model.generations) {
                    if (generation.id == generationId) {
                        return generation.series;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    // 数据类
    public static class CarBrand {
        public int id;
        public String name;
        public List<CarModel> models;
    }

    public static class CarModel {
        public int id;
        public String name;
        public int brandId;
        public List<CarGeneration> generations;
    }

    public static class CarGeneration {
        public int id;
        public String name;
        public int modelId;
        public String yearBegin;
        public String yearEnd;
        public List<CarSeries> series;
    }

    public static class CarSeries {
        public int id;
        public String name;
        public int modelId;
        public int generationId;
    }

    public interface LoadDataCallback {
        void onDataLoaded(boolean success);
    }
}