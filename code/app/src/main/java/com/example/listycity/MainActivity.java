package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

// Note: some of this file was modified with the assistance of OpenAI's ChatGPT
// for the sake of assistance with Java syntax (ideas/logic created by me, Cassie Burke,
// but written in Java by ChatGPT because I am unfamiliar with the syntax specifics
// still) and with how to make the text input field and confirm button invisible when
// adding a city has not been selected (again, with Java syntax help). The assistance of
// ChatGPT was just for the sake of figuring out what functions exist and what the syntax
// of those functions are. (January 15th, 2026)

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    Button addButton;
    Button deleteButton;
    Button confirmButton;
    EditText cityInput;

    ArrayAdapter<String> cityAdapter;
    ArrayList<String> datalist;

    int selectedIndex = -1;

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
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        confirmButton = findViewById(R.id.confirm_button);
        cityInput = findViewById(R.id.city_input);

        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        datalist = new ArrayList<>();
        datalist.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, datalist);

        cityList.setAdapter(cityAdapter);

        //city clicked, remember which and highlight it so deletes can happen
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position; //saving which button has been clicked so we know which to delete
            cityList.setItemChecked(position, true); //highlights the selected city
        });

        //hiding text input/confirm button when not needed
        cityInput.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        // --- Button Logic --- //
        // Add button
        addButton.setOnClickListener(v -> {
            //show stuff
            cityInput.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);

            //clear the previous stuff and focus on the input
            cityInput.setText("");
            cityInput.requestFocus();
        });

        //confirm button behaviour
        confirmButton.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();

            //add if the user added something
            if (!newCity.isEmpty()) {
                datalist.add(newCity);
                cityAdapter.notifyDataSetChanged(); //refreshes the list
            }

            //hide the confirm button/text input again
            cityInput.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        });

        //delete button behaviour
        deleteButton.setOnClickListener((v -> {
            if (selectedIndex != -1) { //check that a city is selected
                datalist.remove(selectedIndex);
                cityAdapter.notifyDataSetChanged();

                cityList.clearChoices();
                selectedIndex = -1;
            }
        }));
    }
}