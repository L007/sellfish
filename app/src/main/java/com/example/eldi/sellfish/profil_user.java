package com.example.eldi.sellfish;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class profil_user extends AppCompatActivity {
    EditText etNama,etUsername,etPassword,etEmail,etNoKtp;
    String nama,username,password,email,no_ktp,user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        etNama=(EditText)findViewById(R.id.etNama);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etNoKtp=(EditText)findViewById(R.id.etNoKtp);

        nama=getIntent().getStringExtra("nama");
        username=getIntent().getStringExtra("username");
        password=getIntent().getStringExtra("password");
        email=getIntent().getStringExtra("email");
        no_ktp=getIntent().getStringExtra("no_ktp");




        etNama.setText(nama);
        etUsername.setText(username);
        etPassword.setText(password);
        etEmail.setText(email);
        etNoKtp.setText(no_ktp);

    }
}
