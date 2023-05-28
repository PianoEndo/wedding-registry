package com.pianoendo.weddingregistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.OutputStream;
import java.util.Objects;

public class GeneratorActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 1;
    ImageView imageView;
    Button btn_save;
    EditText editText;

    FirebaseAuth auth;
    FirebaseUser user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        editText = findViewById(R.id.esit_text);
        Button button = findViewById(R.id.button);
        btn_save = findViewById(R.id.button_save);
        imageView = findViewById(R.id.qr_code);

        addValueEventListenerToRef();
//        setInfo(readInfo());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE, 300, 300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    imageView.setImageBitmap(bitmap);
                }

                catch (WriterException e){
                    throw new RuntimeException(e);
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(GeneratorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                }
                else {
                    ActivityCompat.requestPermissions(GeneratorActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },REQUEST_CODE);
                }
            }
        });
    }

    // reference: https://www.youtube.com/watch?v=eEm2XzVtAlA, https://www.youtube.com/watch?v=n8HdrLYL9DA&t=18s
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                saveImage();
            }
            else {
                Toast.makeText(GeneratorActivity.this, "Please provide required permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // reference: https://www.youtube.com/watch?v=Ul4hum3y0J8
    private void saveImage() {
        Uri images;
        ContentResolver contentResolver = getContentResolver();
        images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,System.currentTimeMillis()+".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"images/*");
        Uri uri = contentResolver.insert(images,contentValues);

        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);

            Toast.makeText(GeneratorActivity.this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(GeneratorActivity.this, "Image not saved", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void addValueEventListenerToRef() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ref = database.getReference().child(user.getUid());
        ref = ref.child("venmo");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String info = dataSnapshot.getValue(String.class);
                contents = info;
                Log.d("Data", info);
                Log.d("Data", contents);
                setInfo(readInfo());
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                Log.w("Error", "loadPost:onCancelled");
            }
        });
        Log.d("Venmo", ""+contents);
    }

    public String readInfo() {
        Log.d("Venmo", ""+contents);
        return contents;
    }

    public void setInfo(String contents) {
        editText.setText(contents);
    }
}