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
import com.google.firebase.database.DatabaseReference;

import com.pianoendo.weddingregistry.databinding.ActivityLoginBinding;

import java.util.List;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    private View.OnClickListener button_login_login_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String emailAddress = binding.editTextTextEmailAddress2.getText().toString();
            String password = binding.editTextTextPassword2.getText().toString();
            Log.d("debug", "address and password acquired");
            signIn(emailAddress, password);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        Log.d("debug", String.valueOf(mAuth));
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonLoginLogin.setOnClickListener(button_login_login_clickListener);
    }

    // login using email and password
    private void signIn(String email, String password)
    {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    public void reload() {}

    // if logged in successfully, take the user to MainActivity
    public void updateUI() {
        Intent theIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(theIntent);
    }
}