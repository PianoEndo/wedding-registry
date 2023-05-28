package com.pianoendo.weddingregistry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pianoendo.weddingregistry.databinding.ActivityHostBinding;
import com.pianoendo.weddingregistry.databinding.ActivityLaunchBinding;

public class HostActivity extends AppCompatActivity {

    private ActivityHostBinding binding;

    private View.OnClickListener button_signup_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent theIntent = new Intent(HostActivity.this, RegistrationActivity.class);
            startActivity(theIntent);
        }
    };

    private View.OnClickListener button_login_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(HostActivity.this, LoginActivity.class);
            startActivity(theIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSignup.setOnClickListener(button_signup_clickListener);
        binding.buttonLogin.setOnClickListener(button_login_clickListener);
    }
}