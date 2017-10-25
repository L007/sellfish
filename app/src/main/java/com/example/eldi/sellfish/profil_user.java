package com.example.eldi.sellfish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class profil_user extends AppCompatActivity {
    EditText etNama,etUsername,etPassword,etEmail,etNoKtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        etNama=(EditText)findViewById(R.id.etNama);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etNoKtp=(EditText)findViewById(R.id.etNoKtp);


        etNama.setText("eldi");
        etPassword.setText("halo");
    }
}
