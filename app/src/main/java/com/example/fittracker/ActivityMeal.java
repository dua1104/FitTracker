package com.example.fittracker;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityMeal extends AppCompatActivity{
    private  EditText etquantityEditText;
    private Button btcancelButton,btaddMealButton;
    private Spinner spinner;
    private ArrayList<String> meals;
    private ArrayAdapter<String> adapter;
    private FoodDataBase db;
    private TextView caloriesConsumedText = findViewById(R.id.caloriesConsumed);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addmeal);
        etquantityEditText = findViewById(R.id.quantityEditText);
        btcancelButton = findViewById(R.id.cancelButton);
        btaddMealButton = findViewById(R.id.addMealButton);
        spinner = findViewById(R.id.mealTypeSpinner);
        db = new FoodDataBase(this);

        //recuper les noms de la base
        meals = db.getAllNames();

        // mettre dans le spinner
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                meals);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btaddMealButton.setOnClickListener(view -> addMeal());

    }
    private void addMeal(){
        String selectedMeal = spinner.getSelectedItem().toString();
        double caloriesParGram = db.getCaloriesByName(selectedMeal);
        String quantity=etquantityEditText.getText().toString().trim();
        double q = Double.parseDouble(quantity);
        if(q<=0){
            showMessage("Durée invalide");
        }
        double consumedCalories= (q*caloriesParGram)/100;
        caloriesConsumedText.setText(String.valueOf(consumedCalories));
        finish();

    }
    //mzel button cancel et le retour au dashbord !!

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
