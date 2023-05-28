package com.pianoendo.weddingregistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.pianoendo.weddingregistry.databinding.ActivityGuestViewRegistryBinding;
import com.pianoendo.weddingregistry.databinding.ActivityMainBinding;
import com.pianoendo.weddingregistry.recyclerview.CustomAdapter;
import com.pianoendo.weddingregistry.recyclerview.GuestViewRecyclerViewFragment;
import com.pianoendo.weddingregistry.recyclerview.RecyclerViewFragment;
import com.pianoendo.weddingregistry.recyclerview.SampleActivityBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuestViewRegistryActivity extends SampleActivityBase {

//    private ActivityGuestViewRegistryBinding binding;
//
    FirebaseAuth auth;
    FirebaseUser user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    Object[] keyList;
    ArrayList<String> dataList = new ArrayList<String>();
//    GuestViewRecyclerViewFragment recycler = new GuestViewRecyclerViewFragment();

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_view_registry);
        System.out.println("contentView set");
        System.out.println(savedInstanceState); // returns null
//        readData();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        System.out.println(user);
        ref = database.getReference().child(user.getUid());
        ref = ref.child("item");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    System.out.println("no data");
                }
                System.out.println(dataSnapshot.getValue().getClass());
                System.out.println(dataSnapshot.getValue());
                HashMap data = (HashMap) dataSnapshot.getValue();
                keyList = data.keySet().toArray();
                for (int i=0; i<keyList.length; i++) {
                    HashMap item = (HashMap) data.get(keyList[i]);
                    dataList.add(String.valueOf(item.get("itemName")));
                    Log.d("data_addValueEvent", String.valueOf(item)); // 1
                }
                Log.d("data_addValueEvent", String.valueOf(dataList)); // 2

                // transaction process
                if (savedInstanceState == null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    GuestViewRecyclerViewFragment fragment = new GuestViewRecyclerViewFragment(dataList);
                    Log.d("Recycle","MainActivity created");
                    transaction.replace(R.id.fragment_container_guest, fragment);
                    System.out.println("fragment replaced");
                    transaction.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("data-failed", String.valueOf(databaseError.getCode()));
            }
        });
    }

//    public void readData() {
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        ref = database.getReference().child(user.getUid());
//        ref = ref.child("item");
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                HashMap data = (HashMap) dataSnapshot.getValue();
//                keyList = data.keySet().toArray();
//                for (int i=0; i<keyList.length; i++) {
//                    HashMap item = (HashMap) data.get(keyList[i]);
//                    Log.d("data", String.valueOf(item)); // 1
//                    dataList.add(String.valueOf(item.get("itemName")));
//                }
//                Log.d("data", String.valueOf(dataList)); // 2
//                createRecyclerObject(); // 7
//                otherFunctions();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("data-failed", String.valueOf(databaseError.getCode()));
//            }
//        });
//    }
// import RecyclerView and see if it works
//    public void createRecyclerObject() {
//        recycler = new GuestViewRecyclerViewFragment(dataList); // 7
//    }

    public void transaction() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        GuestViewRecyclerViewFragment fragment = new GuestViewRecyclerViewFragment(dataList);
        Log.d("Recycle","MainActivity created");
        transaction.replace(R.id.fragment_container_guest, fragment);
        System.out.println("fragment replaced");
//            readData();
        transaction.commit();
    }

    public void binding() {
        Log.d("Binding", "start binding");
//        binding = ActivityGuestViewRegistryBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_guest_view_registry);
        Log.d("Binding","binding defined");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}