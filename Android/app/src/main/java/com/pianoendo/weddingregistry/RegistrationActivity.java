package com.pianoendo.weddingregistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pianoendo.weddingregistry.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private View.OnClickListener button_register_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String firstName = binding.editTextFirstName.getText().toString();
            String lastName = binding.editTextLastName.getText().toString();
            String emailAddress = binding.editTextEmailAddress.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            createAccount(emailAddress,password);
        }
    };

    // create a new account with email address and password
    private void createAccount(String emailAddress, String password) {
        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Signup failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonRegister.setOnClickListener(button_register_clickListener);
    }

    // if account created successfully, take the user to LogInActivity
    private void updateUI(FirebaseUser user) {
        if(user != null){
            Toast.makeText(this,"You signed up successfully",Toast.LENGTH_LONG).show();
            Intent theIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(theIntent);
        }
        else {

        }
    }
}