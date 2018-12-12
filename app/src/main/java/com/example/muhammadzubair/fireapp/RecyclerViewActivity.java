package com.example.muhammadzubair.fireapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {
    private ArrayList<Users> user_info;
    private RecyclerView recyclerview;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);


        //Bundle bundle = getIntent().getExtras();
        /*data_info1 = (ArrayList<Users>) getIntent().getSerializableExtra("data");
        Log.d("Size1",String.valueOf(data_info1.size()));

        data_info = new ArrayList<>();
        Users user = new Users("23","bscs","Ali");
        Log.d("Size",user.getAge());
        data_info.add(user);*/
        //
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_id);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        user_info = new ArrayList<>();
        //RecyclerView my_rcview = (RecyclerView) findViewById(R.id.recyclerview_id);
        //my_rcview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    Users user = userSnapshot.getValue(Users.class);
                    user_info.add(user);
                }
                if(user_info.size()== 0){
                    Toast.makeText(RecyclerViewActivity.this,"There is no data", Toast.LENGTH_SHORT).show();
                }
                else {
                    adapter = new MyAdapter(RecyclerViewActivity.this, user_info);
                    recyclerview.setAdapter(adapter);
                }
                //MyAdapter madapter = new MyAdapter(this, user_info);
                //my_rcview.setAdapter(madapter);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });

    }
}
