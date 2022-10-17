package com.nikhil.vjitadmin;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DriverRoute extends AppCompatActivity {

    ListView myListView;
    ProgressBar progressBar;
    LinearLayout ladd;
    DatabaseReference reference,referenc;
    EditText ri,rn;
    Button up;
    Menu menu;

    Query query= FirebaseDatabase.getInstance().getReference("DriverRoute").limitToLast(300);
    ArrayList<String> myArrayList=new ArrayList<>();
    TextView textView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        ladd=findViewById(R.id.add);
        ri=findViewById(R.id.ri);
        rn=findViewById(R.id.rn);
        up=findViewById(R.id.up);

        up.setOnClickListener(view -> {
           String a= ri.getText().toString();
           String b=rn.getText().toString();
           if (!a.isEmpty()&&!b.isEmpty()){
               referenc= FirebaseDatabase.getInstance().getReference("DriverRoute");


               referenc.child(a).setValue(b).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           finish();
                           startActivity(getIntent());
                           Toast.makeText(DriverRoute.this, "Uploaded", Toast.LENGTH_SHORT).show();

                       }
                   }
               });
           }
        });

        myListView=(ListView)findViewById(R.id.listView);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(DriverRoute.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView.setAdapter(myArrayAdapter);



        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;



        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getKey();
                String Value=dataSnapshot.getValue(String.class) ;
                myArrayList.add("\n"+key+"    :    "+Value+"\n");
                myArrayAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                myListView.setOnItemLongClickListener((arg0, arg1, pos, id) -> {
                    Log.v("long clicked","pos: " + pos);
                    final int a=pos+1;
                    String b=String.valueOf(a);
                    if (a>0){


                        reference= FirebaseDatabase.getInstance().getReference("DriverRoute").child(b);
                        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                   System.out.println("success");
                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(getApplicationContext(),"Removed Successfully",Toast.LENGTH_SHORT).show();
                                    myArrayAdapter.notifyDataSetChanged();

                                }
                            }
                        });



                        }
                    return true;
                });

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myArrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menuadd,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){


            case R.id.nav_reload:
                ladd.setVisibility(View.VISIBLE);
            break;



        }

        return super.onOptionsItemSelected(item);
    }

}


