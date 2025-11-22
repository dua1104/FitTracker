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

public class ActivitySport extends AppCompatActivity{
    private  EditText etduration;
    private Button btcancelButton,btaddSportButton;
    private Spinner spinner;
    private ArrayList<String> sports;
    private ArrayAdapter<String> adapter;
    private SportDataBase db;
    private TextView caloriesBurnedText = findViewById(R.id.caloriesBurned);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addsport);
        etduration = findViewById(R.id.duration);
        btcancelButton = findViewById(R.id.cancelButton);
        btaddSportButton = findViewById(R.id.addSportButton);
        spinner = findViewById(R.id.sportTypeSpinner);
        db = new SportDataBase(this);

        //recuper les noms de la base
        sports = db.getAllNames();

        // mettre dans le spinner
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                sports);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btaddSportButton.setOnClickListener(view -> addsport());

    }
    private void addsport(){
        String selectedSport = spinner.getSelectedItem().toString();
        double caloriesParHeure = db.getCaloriesByName(selectedSport);
        String duration=etduration.getText().toString().trim();
        double duree = Double.parseDouble(duration);
        if(duree<=0){
            showMessage("Durée invalide");
        }
        double burnedCalories= (duree*caloriesParHeure)/60;
        caloriesBurnedText.setText(String.valueOf(burnedCalories));
        finish();

    }
    //mzel button cancel et le retour au dashbord !!

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
