package com.example.fittracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etMail, etMdp;
    private Button btnSignIn;
    private TextView tvInscrire;
    private UserDataBase db;  // <-- database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMail = findViewById(R.id.mail);
        etMdp = findViewById(R.id.mdp);
        btnSignIn = findViewById(R.id.add);
        tvInscrire = findViewById(R.id.tv_inscrire);
        db = new UserDataBase(this);

        btnSignIn.setOnClickListener(v -> handleLogin());
        makeInscrireClickable();
    }

    private void handleLogin() {
        String email = etMail.getText().toString().trim();
        String password = etMdp.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Veuillez remplir tous les champs");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showMessage("Format de l'email invalide");
            return;
        }

        // 🔍 Check user in database
        if (!db.checkUser(email, password)) {
            showMessage("Email ou mot de passe incorrect");
            return;
        }

        showMessage("Connexion réussie!");

        // Redirect to Dashboard
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void makeInscrireClickable() {
        String fullText = tvInscrire.getText().toString();
        String keyword = "inscrivez-vous";

        int start = fullText.toLowerCase().indexOf(keyword.toLowerCase());
        if (start < 0) return;

        int end = start + keyword.length();
        SpannableString spannable = new SpannableString(fullText);

        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(MainActivity.this, ActivitySignup.class));
            }
        };

        spannable.setSpan(span, start, end, 0);

        tvInscrire.setMovementMethod(LinkMovementMethod.getInstance());
        tvInscrire.setText(spannable);
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
