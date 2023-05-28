package com.pianoendo.weddingregistry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pianoendo.weddingregistry.databinding.ActivityMainBinding;
import com.pianoendo.weddingregistry.recyclerview.RecyclerViewFragment;
import com.pianoendo.weddingregistry.recyclerview.SampleActivityBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends SampleActivityBase {

    private ActivityMainBinding binding;

    FirebaseAuth auth;
    FirebaseUser user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    Object[] keyList;
    ArrayList<String> dataList = new ArrayList<String>();

    Button scan_btn;
    Button generator_btn;

    Button adder_btn;

    Button venmo_btn;
    TextView textView;

    int hasChild = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Log.d("Recycle","MainActivity created");

        // read data
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("reference", String.valueOf(snapshot.hasChild(user.getUid())));
                if (snapshot.hasChild(user.getUid())==false) {
                    setContentView(R.layout.empty_list);
                    adder_btn = findViewById(R.id.button_add3);
                    adder_btn.setOnClickListener(button_adder_clickListner);
                }
                else {
                    setContentView(R.layout.activity_main);
//                    defineViews();
                    generator_btn = findViewById(R.id.generator);
                    adder_btn = findViewById(R.id.adder);
                    venmo_btn = findViewById(R.id.venmo);
//            setListeners();

                    generator_btn.setOnClickListener(button_generator_clickListener);
                    adder_btn.setOnClickListener(button_adder_clickListner);
                    venmo_btn.setOnClickListener(button_venmo_clickListener);
                    ref = database.getReference().child(user.getUid()).child("item");

                    // set addValueEventListener so that the registry reflects the change everytime data is modified
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HashMap data = (HashMap) dataSnapshot.getValue();
                            keyList = data.keySet().toArray();
                            for (int i = 0; i < keyList.length; i++) {
                                System.out.println(data.get(keyList[i]));
                                System.out.println(data.get(keyList[i]).getClass());
                                HashMap item = (HashMap) data.get(keyList[i]);
                                Log.d("data", String.valueOf(item)); // 1
                                dataList.add(String.valueOf(item.get("itemName")));
                            }
                            Log.d("data", String.valueOf(dataList)); // 2

                            // transaction
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            RecyclerViewFragment recycler = new RecyclerViewFragment(dataList);
                            transaction.replace(R.id.sample_content_fragment, recycler);
                            transaction.commit();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("data-failed", String.valueOf(databaseError.getCode()));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        if(intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                textView.setText(contents);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // jump to the QR code generation page
    private View.OnClickListener button_generator_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(MainActivity.this, GeneratorActivity.class);
            startActivity(theIntent);
        }
    };

    // jump to the adding item page
    private View.OnClickListener button_adder_clickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(theIntent);
        }
    };

    // jump to the venmo registration page
    private View.OnClickListener button_venmo_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(MainActivity.this, VenmoActivity.class);
            startActivity(theIntent);
        }
    };
}