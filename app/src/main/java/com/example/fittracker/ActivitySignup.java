package com.example.fittracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySignup extends AppCompatActivity {

    private EditText etPrenom, etNom, etDateNaiss, etMail, etMdp, etMdpConfirm, etTaille, etPoids;
    private Button btnSign;

    private UserDataBase db;  // <<=== DATABASE INSTANCE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new UserDataBase(this);  // <<=== INIT DB

        etPrenom = findViewById(R.id.prenom);
        etNom = findViewById(R.id.nom);
        etDateNaiss = findViewById(R.id.datenaiss);
        etMail = findViewById(R.id.mail);
        etMdp = findViewById(R.id.mdpsignup);
        etMdpConfirm = findViewById(R.id.mdpsignupconfirme);
        etTaille = findViewById(R.id.taille);
        etPoids = findViewById(R.id.poids);
        btnSign = findViewById(R.id.sign);

        btnSign.setOnClickListener(v -> handleSignup());
    }

    private void handleSignup() {
        String prenom = etPrenom.getText().toString().trim();
        String nom = etNom.getText().toString().trim();
        String dateNaiss = etDateNaiss.getText().toString().trim();
        String email = etMail.getText().toString().trim();
        String mdp = etMdp.getText().toString().trim();
        String mdpConfirm = etMdpConfirm.getText().toString().trim();
        String taille = etTaille.getText().toString().trim();
        String poids = etPoids.getText().toString().trim();

        // --- VALIDATION ---
        if (prenom.isEmpty() || nom.isEmpty() || dateNaiss.isEmpty() || email.isEmpty() ||
                mdp.isEmpty() || mdpConfirm.isEmpty() || taille.isEmpty() || poids.isEmpty()) {

            showMessage("Veuillez remplir tous les champs");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showMessage("Format de l'email invalide");
            return;
        }

        if (!dateNaiss.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showMessage("Date de naissance invalide (format JJ/MM/AAAA)");
            return;
        }

        if (!mdp.equals(mdpConfirm)) {
            showMessage("Les mots de passe ne correspondent pas");
            return;
        }

        // --- INSERT INTO DATABASE ---
        boolean inserted = db.addUser(
                nom,
                prenom,
                email,
                mdp,
                dateNaiss,
                taille,
                poids
        );

        if (!inserted) {
            showMessage("Erreur: cet email existe déjà");
            return;
        }

        showMessage("Inscription réussie !");
        finish(); // Return to login
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
