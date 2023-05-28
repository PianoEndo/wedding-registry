package com.pianoendo.weddingregistry.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pianoendo.weddingregistry.R;
import com.pianoendo.weddingregistry.model.ItemModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private static final String TAG = "CustomAdapter";
//    private String[] mDataSet;
    private ArrayList<String> mDataSet = new ArrayList<String>();

    FirebaseAuth auth;
    FirebaseUser user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference ref;
    Object[] keyList;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentText = textView.getText().toString();
                    String newText;
                    if(currentText.contains("purchased")) {
                        newText = currentText.replace(" (purchased)", "");
                    }
                    else {
                        newText = currentText.concat(" (purchased)");
                    }
                    int position = getAbsoluteAdapterPosition();

                    // update dataset with newText
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            System.out.println(snapshot.getValue());
                            System.out.println(snapshot.getClass());
                            HashMap data = (HashMap) snapshot.getValue();
                            Object[] keyList = data.keySet().toArray();
                            System.out.println(position);
                            String key = keyList[position].toString();
                            HashMap item = (HashMap) data.get(key);
                            Log.d("hash", String.valueOf(item));
                            Map<String, Object> update = new HashMap<>();
                            update.put(key + "/itemName", newText);
                            ref.updateChildren(update);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("data-failed", String.valueOf(error.getCode()));
                        }
                    });
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * datSet String[] containing the data to populate views to be used by RecyclerView.
     */

    public CustomAdapter(ArrayList<String> dataSet) {
        mDataSet = dataSet;
    }

    // specify the view that the app uses for each item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = database.getReference().child(user.getUid());
        ref = ref.child("item");

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDataSet != null) {
            return mDataSet.size();
        }
        else {
            return 0;
        }
    }
}
