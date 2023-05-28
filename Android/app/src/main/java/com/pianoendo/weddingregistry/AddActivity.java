package com.pianoendo.weddingregistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pianoendo.weddingregistry.databinding.ActivityAddBinding;
import com.pianoendo.weddingregistry.model.ItemModel;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.add.setOnClickListener(button_adder_clickListener);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = database.getReference().child(user.getUid());
        ref = ref.child("item");
    }

    private View.OnClickListener button_adder_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String itemName = binding.editTextName.getText().toString();
            String memo = binding.editTextMemo.getText().toString();
            String price = binding.editTextPrice.getText().toString();

            ItemModel newItem = new ItemModel(itemName, memo, price);
            ref.push().setValue(newItem);

//            String key = ref.push().getKey();

//            Map<String, Object> item = newItem.toMap();
//            Map<String, Object> childUpdates = new HashMap<>();
//            childUpdates.put(newItem);
//            ref.updateChildren(childUpdates);
            Toast.makeText(AddActivity.this, "data added", Toast.LENGTH_SHORT).show();
            returnToMain();

//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    Toast.makeText(AddActivity.this, "data added", Toast.LENGTH_SHORT).show();
//                    returnToMain();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(AddActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    };

    private void returnToMain() {
        Intent theIntent = new Intent (AddActivity.this, MainActivity.class);
        startActivity(theIntent);
    }
}