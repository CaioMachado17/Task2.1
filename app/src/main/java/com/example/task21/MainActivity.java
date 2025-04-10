package com.example.task21;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//package com.example.unitconverterapp;

import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCategory, spinnerFrom, spinnerTo;
    EditText editTextValue;
    Button buttonConvert;
    TextView textViewResult;

    String[] categories = {"Length", "Weight", "Temperature"};
    String[] lengthUnits = {"Inch", "Foot", "Yard", "Mile", "Cm", "Km"};
    String[] weightUnits = {"Pound", "Ounce", "Ton", "Kg", "Gram"};
    String[] tempUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    ArrayAdapter<String> unitAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextValue = findViewById(R.id.editTextValue);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        updateUnitSpinners(lengthUnits);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (categories[position]) {
                    case "Length":
                        updateUnitSpinners(lengthUnits);
                        break;
                    case "Weight":
                        updateUnitSpinners(weightUnits);
                        break;
                    case "Temperature":
                        updateUnitSpinners(tempUnits);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();
                String inputValue = editTextValue.getText().toString();

                if (inputValue.isEmpty()) {
                    textViewResult.setText("Please enter a value.");
                    return;
                }

                double value = Double.parseDouble(inputValue);
                double result = convert(value, fromUnit, toUnit);
                textViewResult.setText(String.format("%.2f %s", result, toUnit));
            }
        });
    }

    private void updateUnitSpinners(String[] units) {
        unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(unitAdapter);
        spinnerTo.setAdapter(unitAdapter);
    }

    private double convert(double value, String from, String to) {
        double base = 0.0;

        switch (from) {
            case "Inch": base = value * 2.54; break;
            case "Foot": base = value * 30.48; break;
            case "Yard": base = value * 91.44; break;
            case "Mile": base = value * 160934; break;
            case "Cm": base = value; break;
            case "Km": base = value * 100000; break;

            case "Pound": base = value * 453.592; break;
            case "Ounce": base = value * 28.3495; break;
            case "Ton": base = value * 907185; break;
            case "Kg": base = value * 1000; break;
            case "Gram": base = value; break;

            case "Celsius": base = value; break;
            case "Fahrenheit": base = (value - 32) / 1.8; break;
            case "Kelvin": base = value - 273.15; break;
        }

        switch (to) {
            case "Inch": return base / 2.54;
            case "Foot": return base / 30.48;
            case "Yard": return base / 91.44;
            case "Mile": return base / 160934;
            case "Cm": return base;
            case "Km": return base / 100000;

            case "Pound": return base / 453.592;
            case "Ounce": return base / 28.3495;
            case "Ton": return base / 907185;
            case "Kg": return base / 1000;
            case "Gram": return base;

            case "Celsius": return base;
            case "Fahrenheit": return (base * 1.8) + 32;
            case "Kelvin": return base + 273.15;

            default: return value;
        }
    }
}
