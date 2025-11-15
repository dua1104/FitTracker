package com.example.fittracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMail = findViewById(R.id.mail);
        etMdp = findViewById(R.id.mdp);
        btnSignIn = findViewById(R.id.add);
        tvInscrire = findViewById(R.id.tv_inscrire);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etMail.getText().toString();
                String password = etMdp.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Add real login logic here
                    Toast.makeText(MainActivity.this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String text = tvInscrire.getText().toString();
        SpannableString spannable = new SpannableString(text);

        int start = text.indexOf("inscrivez-vous");
        int end = start + "inscrivez-vous".length();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(MainActivity.this, ActivitySignup.class);
                startActivity(intent);
            }
        };

        spannable.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvInscrire.setText(spannable);
        tvInscrire.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
