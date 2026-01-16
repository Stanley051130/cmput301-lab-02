package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dataList);
        cityList.setAdapter(cityAdapter);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonConfirm = findViewById(R.id.buttonConfirm);
        EditText editCity = findViewById(R.id.editCity);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
        });

        // 5) Add city button (optional: just focus input)
        buttonAdd.setOnClickListener(v -> editCity.requestFocus());

        // 6) Confirm adds typed city
        buttonConfirm.setOnClickListener(v -> {
            String city = editCity.getText().toString().trim();
            if (city.isEmpty()) return;

            dataList.add(city);
            cityAdapter.notifyDataSetChanged();
            editCity.setText("");
        });

        // 7) Delete selected city
        buttonDelete.setOnClickListener(v -> {
            if (selectedIndex == -1) {
                Toast.makeText(this, "Tap a city first to select it.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedIndex);
            selectedIndex = -1;

            cityList.clearChoices();
            cityAdapter.notifyDataSetChanged();
        });
    }
}