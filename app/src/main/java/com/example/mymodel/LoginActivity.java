package com.example.mymodel;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymodel.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin,btn_Click_reg;
    private UserDAO userDAO;
    LottieAnimationView lotty_reclama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);
        //binding.lottyReclama.setAnimation(R.raw.hello);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        btn_Click_reg= findViewById(R.id.registerNow);


        userDAO = new UserDAO(this);
        userDAO.open();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (userDAO.checkUser(email, password)) {
                    // Переход на MainActivity только после успешного входа
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Закрытие LoginActivity
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Click_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), RegistrActivity.class);
                startActivity(intent);finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}