package com.nikhil.vjitadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    LinearLayout dr,feed,syllabi,circular,timetable,examdates,attendance,examhallallotment,broadcastmessage,contactus,aboutus,iflip;
    TextView quot;
    String txt;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        dr=findViewById(R.id.dr);
        feed=findViewById(R.id.feed);
        syllabi=findViewById(R.id.syllabi);
        circular=findViewById(R.id.circular);
        timetable=findViewById(R.id.timetable);
        examdates=findViewById(R.id.examdates);
        attendance=findViewById(R.id.attendance);
        examhallallotment=findViewById(R.id.examhallallotment);
        broadcastmessage=findViewById(R.id.msg);
        contactus=findViewById(R.id.contactus);
        iflip=findViewById(R.id.imageflipper);
        aboutus=findViewById(R.id.aboutus);
        quot=findViewById(R.id.quot);




        dr.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, DriverRoute.class);

            startActivity(intent);
        });
        feed.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, Feedup.class);

            startActivity(intent);
        });
        syllabi.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,UploadPDF.class);
            intent.putExtra("page","syllabi");
            intent.putExtra("name","Syllabi");

            startActivity(intent);
        });
        circular.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,UploadPDF.class);
            intent.putExtra("page","circular");
            intent.putExtra("name","Circular");
            startActivity(intent);
        });
        timetable.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,UploadPDF.class);
            intent.putExtra("page","timetable");
            intent.putExtra("name","Time Table");

            startActivity(intent);
        });
        examdates.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,UploadPDF.class);
            intent.putExtra("page","examdates");
            intent.putExtra("name","Exam Dates");

            startActivity(intent);
        });
        attendance.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,UploadPDF.class);
            intent.putExtra("page","attendance");
            intent.putExtra("name","Attendance");

            startActivity(intent);
        });
        examhallallotment.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,UploadPDF.class);
            intent.putExtra("page","examhallallotment");
            intent.putExtra("name","Exam Hall Allotment");

            startActivity(intent);
        });
        broadcastmessage.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,MessagesActivity.class);
            startActivity(intent);
        });
        contactus.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,ChatsFragment.class);
            startActivity(intent);
        });
        iflip.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,imageFlipper.class);

            startActivity(intent);
        });
        aboutus.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,UploadPDF.class);
            intent.putExtra("page","aboutus");
            intent.putExtra("name","About Us");

            startActivity(intent);
        });
        reference = FirebaseDatabase.getInstance().getReference("quot");
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String q = dataSnapshot.child("quot").getValue(String.class);
                    quot.setText(q);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        quot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Quotation");
                alertDialog.setMessage("Enter quot");

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_baseline_cloud_upload_24);

                alertDialog.setPositiveButton("Upload",
                        (dialog, which) -> {
                            txt = input.getText().toString();
                            if (!txt.equals("")) {

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("quot");

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("quot",txt);
                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                            }
                        });

                alertDialog.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());

                alertDialog.show();
            }

        });

    }
}