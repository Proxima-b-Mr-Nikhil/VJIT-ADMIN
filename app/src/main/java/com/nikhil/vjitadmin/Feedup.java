package com.nikhil.vjitadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Feedup extends AppCompatActivity {
    DatabaseReference reference;

    Button button;
    EditText textmsg;
    String msg,time;
    Uri imageuri;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        button=findViewById(R.id.btn);
        textmsg=findViewById(R.id.text);
        button.setOnClickListener(view -> {
            msg=textmsg.getText().toString();
            Date date = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat( "d MMM yyyy");
            time = formatter.format(date);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            dialog.show();
            assert data != null;
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            Toast.makeText(Feedup.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            final StorageReference filepath = storageReference.child("feed").child(timestamp + "."+getFileExtension(imageuri));
            Toast.makeText(Feedup.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask((Continuation) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {

                    Uri downloadUri = task.getResult();
                    String mUri = Objects.requireNonNull(downloadUri).toString();
                        reference = FirebaseDatabase.getInstance().getReference("feed").child(timestamp);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put( "imageUrl",""+mUri);
                        map.put( "name",""+msg);
                        map.put( "time",""+time);
                        map.put( "id",""+timestamp);
                        reference.updateChildren(map);
                    dialog.dismiss();
                    Toast.makeText(Feedup.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(Feedup.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}