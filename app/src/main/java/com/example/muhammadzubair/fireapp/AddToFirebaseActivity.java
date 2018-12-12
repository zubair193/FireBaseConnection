package com.example.muhammadzubair.fireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddToFirebaseActivity extends AppCompatActivity {

    private EditText name, age, dept;
    private Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_firebase);

        btnsubmit = (Button) findViewById(R.id.btnsubmit);

        name = (EditText) findViewById(R.id.inputname);
        age = (EditText) findViewById(R.id.inputage);
        dept = (EditText) findViewById(R.id.inputdept);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(AddToFirebaseActivity.this,name.getText(), Toast.LENGTH_SHORT).show();
                if(name.getText().equals(null)&& age.getText().equals(null)&& dept.getText().equals(null)){
                    Toast.makeText(AddToFirebaseActivity.this,"Your Must Enter All Fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String Name = String.valueOf(name.getText());
                    String Age = String.valueOf(age.getText());
                    String Dept = String.valueOf(dept.getText());

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users");


                    String id = myRef.push().getKey();
                    Users user = new Users(Age, Dept, Name, id);
                    myRef.child(id).setValue(user);

                    Toast.makeText(AddToFirebaseActivity.this,"Your Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    name.setText(null);
                    age.setText(null);
                    dept.setText(null);

                    //Intent intent = new Intent(AddToFirebaseActivity.this, MainActivity.class);
                    //startActivity(intent);
                }
            }
        });
    }
}
