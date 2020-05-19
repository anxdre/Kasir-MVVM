package com.kasir.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kasir.R;
import com.kasir.ui.dashboard.DashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

//                if (email.equals("admin@gmail.com") && pass.equals("admin123")){
//                    etEmail.setText("");
//                    etPass.setText("");
//                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
//                } else {
//                    Toast.makeText(LoginActivity.this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
