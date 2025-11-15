package com.example.fittracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySignup extends AppCompatActivity {

    private EditText etPrenom, etNom, etDateNaiss, etMail, etMdp, etMdpConfirm, etTaille, etPoids;
    private Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etPrenom = findViewById(R.id.prenom);
        etNom = findViewById(R.id.nom);
        etDateNaiss = findViewById(R.id.datenaiss);
        etMail = findViewById(R.id.mail);
        etMdp = findViewById(R.id.mdpsignup);
        etMdpConfirm = findViewById(R.id.mdpsignupconfirme);
        etTaille = findViewById(R.id.taille);
        etPoids = findViewById(R.id.poids);
        btnSign = findViewById(R.id.sign);

        btnSign.setOnClickListener(v -> {
            String prenom = etPrenom.getText().toString().trim();
            String nom = etNom.getText().toString().trim();
            String dateNaiss = etDateNaiss.getText().toString().trim();
            String email = etMail.getText().toString().trim();
            String mdp = etMdp.getText().toString().trim();
            String mdpConfirm = etMdpConfirm.getText().toString().trim();
            String taille = etTaille.getText().toString().trim();
            String poids = etPoids.getText().toString().trim();

            if(TextUtils.isEmpty(prenom) || TextUtils.isEmpty(nom) || TextUtils.isEmpty(dateNaiss) ||
                    TextUtils.isEmpty(email) || TextUtils.isEmpty(mdp) || TextUtils.isEmpty(mdpConfirm) ||
                    TextUtils.isEmpty(taille) || TextUtils.isEmpty(poids)) {
                Toast.makeText(ActivitySignup.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!mdp.equals(mdpConfirm)) {
                Toast.makeText(ActivitySignup.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(ActivitySignup.this, "Inscription réussie!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
