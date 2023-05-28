package com.pianoendo.weddingregistry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pianoendo.weddingregistry.databinding.ActivityLaunchBinding;

public class LaunchActivity extends AppCompatActivity{

    private ActivityLaunchBinding binding;

    private View.OnClickListener button_host_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent theIntent = new Intent(LaunchActivity.this, HostActivity.class);
            startActivity(theIntent);
        }
    };

    private View.OnClickListener button_guest_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(LaunchActivity.this, GuestActivity.class);
            startActivity(theIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonHost.setOnClickListener(button_host_clickListener);
        binding.buttonGuest.setOnClickListener(button_guest_clickListener);
    }
}