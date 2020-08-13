package com.example.indee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.indee.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MovieAdapter movieAdapter;
    JSONArray movieListItem;
    JSONObject jsonObject;
    ArrayList<MovieListItem.TestData> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        movieAdapter = new MovieAdapter(this);
        try {
            jsonObject = new JSONObject(getJson());
            movieListItem = jsonObject.getJSONArray("TestData");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < movieListItem.length(); i++) {
            try {
                JSONObject jsonObject = movieListItem.getJSONObject(i);
                MovieListItem.TestData testData = new MovieListItem.TestData();
                testData.posterLink = jsonObject.getString("posterLink");
                testData.name = jsonObject.getString("name");
                testData.type = jsonObject.getString("type");
                testData.release_year = jsonObject.getLong("release_year");
                testData.created_on = jsonObject.getString("created_on");
                testData.updated_on = jsonObject.getString("updated_on");
                testData.shortDescription = jsonObject.getString("shortDescription");
                testData.description = jsonObject.getString("description");
                testData.video_duration = jsonObject.getString("video_duration");
                testData.payment_plan = jsonObject.getString("payment_plan");
                testData.id = jsonObject.getLong("id");
                arrayList.add(testData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            movieAdapter.addListItem(arrayList);
            binding.recyclerView.setAdapter(movieAdapter);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private String getJson() throws IOException, JSONException {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onDestroy() {
        binding.recyclerView.setAdapter(null);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                movieAdapter.addListItem(arrayList);
                binding.recyclerView.setAdapter(movieAdapter);
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
            }
        }

    }
}