package com.nikhil.vjitadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.pdf.PdfDocument;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class UploadPDF extends AppCompatActivity {

    LinearLayout lyear,lsection;
    Spinner fyear,fsection;
    Button upload;
    Uri imageuri;
    String pg;
    TextView textView;
    String name;
    ProgressDialog dialog;


    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        lyear=findViewById(R.id.lyear);
        lsection=findViewById(R.id.lsection);

        textView=findViewById(R.id.textview);
        fyear = (Spinner) findViewById(R.id.year);
        fsection = (Spinner) findViewById(R.id.section);
        upload=findViewById(R.id.upload);
        String[] year = new String[] {
                "1","2","3","4"

        };

        ArrayAdapter<String> yea = new ArrayAdapter<String>(UploadPDF.this,
                android.R.layout.simple_spinner_item, year);
        yea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fyear.setAdapter(yea);

        String[] section = new String[] {
                "A","B","C","D"

        };

        ArrayAdapter<String> sec = new ArrayAdapter<String>(UploadPDF.this,
                android.R.layout.simple_spinner_item, section);
        sec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fsection.setAdapter(sec);

        upload.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        pg= intent.getStringExtra("page");
        textView.setText(name);


        if (pg.equals("syllabi")||pg.equals("examdates")){

            lsection.setVisibility(View.INVISIBLE);

        }

        if(pg.equals("aboutus")||pg.equals("circular")) {
            lyear.setVisibility(View.INVISIBLE);
            lsection.setVisibility(View.INVISIBLE);
        }

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
            Toast.makeText(UploadPDF.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            final StorageReference filepath = storageReference.child("uploads").child(timestamp + "." + "pdf");
            Toast.makeText(UploadPDF.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask((Continuation) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    String year= fyear.getSelectedItem().toString().toLowerCase();
                    String section= fsection.getSelectedItem().toString().toLowerCase();
                    Uri downloadUri = task.getResult();
                    String mUri = Objects.requireNonNull(downloadUri).toString();


                    if (pg.equals("syllabi")||pg.equals("examdates")){
                        reference = FirebaseDatabase.getInstance().getReference(pg);
                        HashMap<String, Object> map = new HashMap<>();
                        lsection.setVisibility(View.INVISIBLE);
                        map.put(year, ""+mUri);
                        reference.updateChildren(map);
                    }
                    if(pg.equals("timetable")||pg.equals("attendance")||pg.equals("examhallallotment")) {

                        reference = FirebaseDatabase.getInstance().getReference(pg);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(year+"/"+section, ""+mUri);
                        reference.updateChildren(map);
                    }
                    if(pg.equals("aboutus")||pg.equals("circular")) {
                        lyear.setVisibility(View.INVISIBLE);
                        lsection.setVisibility(View.INVISIBLE);

                        reference = FirebaseDatabase.getInstance().getReference(pg);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put( pg,""+mUri);
                        reference.updateChildren(map);
                    }
                    dialog.dismiss();
                    Toast.makeText(UploadPDF.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(UploadPDF.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private static String getMonth(String date) throws ParseException, java.text.ParseException {
        Date d = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("MMMM").format(cal.getTime());
        return monthName;
    }
}