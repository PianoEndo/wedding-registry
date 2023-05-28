package com.pianoendo.weddingregistry.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pianoendo.weddingregistry.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuestViewRecyclerViewFragment extends Fragment {
    private static final int DATASET_COUNT = 60;
    private static final String TAG = "RecyclerViewFragment";
    protected RecyclerView.LayoutManager mLayoutManager;
    FirebaseAuth auth;
    FirebaseUser user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    Object[] keyList;

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected ArrayList<String> mDataset;
    ArrayList<String> dataList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int scrollPosition = 0;
//        if (mRecyclerView.getLayoutManager() != null) {
//            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
//                    .findFirstCompletelyVisibleItemPosition();
//        }
//        initDataset();

//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        System.out.println(user);
//        ref = database.getReference().child(user.getUid());
//        ref = ref.child("item");

        System.out.println("dataset initialized");

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.exists()) {
//                    System.out.println("no data");
//                }
//                HashMap data = (HashMap) dataSnapshot.getValue();
//                keyList = data.keySet().toArray();
//                for (int i=0; i<keyList.length; i++) {
//                    HashMap item = (HashMap) data.get(keyList[i]);
//                    Log.d("data_addValueEvent", String.valueOf(item)); // 1
//                    dataList.add(String.valueOf(item.get("itemName")));
//                }
//                Log.d("data_addValueEvent", String.valueOf(dataList)); // 2
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("data-failed", String.valueOf(databaseError.getCode()));
//            }
//        });
    }

    //    public GuestViewRecyclerViewFragment(ArrayList<String> dataList) {
//        this.mDataset = mDataset;
//    }

//    public GuestViewRecyclerViewFragment() {
//
//    }


    public GuestViewRecyclerViewFragment(ArrayList<String> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycler_view_guest_frag, container, false);
        rootView.setTag(TAG);

//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGuest);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        setRecyclerViewLayoutManager();
//        System.out.println("LayoutManager set");
//        mAdapter = new CustomAdapter(mDataset);
//        mRecyclerView.setAdapter(mAdapter);
//        System.out.println("Adapter set");
//        return rootView;

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGuest);
        mLayoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();
        System.out.println("LayoutManager set");
        mAdapter = new CustomAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
//        System.out.println("Adapter set");
        return rootView;
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

//    private void initDataset() {
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        System.out.println(user);
//        ref = database.getReference().child(user.getUid());
//        ref = ref.child("item");
//
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                HashMap data = (HashMap) snapshot.getValue();
//                keyList = data.keySet().toArray();
//                for (int i=0; i<keyList.length; i++) {
//                    HashMap item = (HashMap) data.get(keyList[i]);
//                    Log.d("data", String.valueOf(item)); // 1
//                    dataList.add(String.valueOf(item.get("itemName")));
//                }
//                Log.d("data", String.valueOf(dataList)); // 2
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("data-failed", String.valueOf(error.getCode()));
//            }
//        });
//
//
////        ref.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                if (!dataSnapshot.exists()) {
////                    System.out.println("no data");
////                }
////                HashMap data = (HashMap) dataSnapshot.getValue();
////                keyList = data.keySet().toArray();
////                for (int i=0; i<keyList.length; i++) {
////                    HashMap item = (HashMap) data.get(keyList[i]);
////                    Log.d("data", String.valueOf(item)); // 1
////                    dataList.add(String.valueOf(item.get("itemName")));
////                }
////                Log.d("data", String.valueOf(dataList)); // 2
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                Log.d("data-failed", String.valueOf(databaseError.getCode()));
////            }
////        });
//
//        System.out.println(dataList);
//    }
}
