package com.pianoendo.weddingregistry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.pianoendo.weddingregistry.databinding.ActivityGuestBinding;

public class GuestActivity extends AppCompatActivity {

    private ActivityGuestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonJumpToList.setOnClickListener(button_jumpToList_clickListener);
        binding.buttonScanner.setOnClickListener(button_scanner_clickListener);
    }

    public View.OnClickListener button_jumpToList_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(GuestActivity.this, GuestViewRegistryActivity.class);
            startActivity(theIntent);
        }
    };

    // open camera and scan QR code
    // reference: https://www.youtube.com/watch?v=bWEt-_z7BOY&t=110s
    public View.OnClickListener button_scanner_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(GuestActivity.this);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setPrompt("Scan a QR Code");
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.initiateScan();
        }
    };
}