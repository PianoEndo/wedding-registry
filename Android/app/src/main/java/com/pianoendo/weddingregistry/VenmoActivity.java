package com.pianoendo.weddingregistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pianoendo.weddingregistry.databinding.ActivityVenmoBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class VenmoActivity extends AppCompatActivity {

    ActivityVenmoBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    String accountName = "not yet registered";
    private static final String FILE_NAME = "venmo_account";
    static final String STATE_ACCOUNTNAME = "accountName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null) {
//            accountName = savedInstanceState.getString(STATE_ACCOUNTNAME);
//        }
        binding = ActivityVenmoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = database.getReference().child(user.getUid());
        ref = ref.child("venmo");
//        context = getApplicationContext();
//        if (isVenmoAccountRegistered()) {
//            accountName = readVenmoName();
//            binding.editTextAccount.setText(readVenmoName());
//        }
        binding.register.setOnClickListener(button_register_clickListener);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state.
//        savedInstanceState.putString(STATE_ACCOUNTNAME, accountName);

        // Always call the superclass so it can save the view hierarchy state.
        super.onSaveInstanceState(savedInstanceState);
    }

    private View.OnClickListener button_register_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userInput = binding.editTextAccount.getText().toString();
//            Log.d("Venmo",userInput);
//            Log.d("Directory",context.getFilesDir().getAbsolutePath());
//            writeVenmoName(userInput);
            ref.setValue(user.getUid() + ", " + userInput);
        }
    };

//    public void writeVenmoName(String userInput) {
//        try(FileOutputStream fos = context.openFileOutput(FILE_NAME, MODE_PRIVATE)) {
//            fos.write(userInput.getBytes());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public boolean isVenmoAccountRegistered() {
//        for (String fileName : context.fileList()) {
//            if (fileName.equals(FILE_NAME)) {
//                return true;
//            }
//        }
//        return false;
//    }

//    public String readVenmoName() {
//        StringBuilder sb = new StringBuilder();
//        String venmoAccount;
//        try {
//            FileInputStream fis = context.openFileInput(FILE_NAME);
//            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
//            BufferedReader br = new BufferedReader(inputStreamReader);
//            String line = br.readLine();
//            while (line != null) {
//                sb.append(line);
//                line = br.readLine();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            venmoAccount = sb.toString();
//        }
//        return venmoAccount.replace("\n","").replace("\r","");
//    }
}