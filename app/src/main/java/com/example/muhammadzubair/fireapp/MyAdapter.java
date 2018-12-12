package com.example.muhammadzubair.fireapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    ArrayList<Users> data ;
    private Context mContext;
    AlertDialog alertDialog;
    String u_id;

    public MyAdapter(Context mContext, ArrayList<Users> data){
        this.mContext = mContext;
        this.data = data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_view_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myvieholder, final int i) {
        myvieholder.Name.setText(data.get(i).getName());
        myvieholder.Age.setText(data.get(i).getAge());
        myvieholder.Dept.setText(data.get(i).getDept());
        //String title = data[i];
        //myvieholder.userId.setText(title);
        myvieholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Users user = data.get(i);
                u_id = user.getU_id();
                showUpdateDialog(user.getName(), user.getAge(), user.getDept());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Age;
        TextView Dept;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.inputname);
            Age = (TextView) itemView.findViewById(R.id.inputage);
            Dept = (TextView) itemView.findViewById(R.id.inputdept);
        }
    }

    private void showUpdateDialog( String u_name, String u_age, String u_dept){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextname = (EditText) dialogView.findViewById(R.id.inputname1);
        final EditText editTextage = (EditText) dialogView.findViewById(R.id.inputage1);
        final EditText editTextdept = (EditText) dialogView.findViewById(R.id.inputdept1);

        editTextname.setText(u_name);
        editTextname.setEnabled(false);

        editTextage.setText(u_age);
        editTextdept.setText(u_dept);

        final Button btnupdate = (Button) dialogView.findViewById(R.id.btnupdate);
        final Button btndel = (Button) dialogView.findViewById(R.id.btndelete);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextname.getText().toString().trim();
                String age = editTextage.getText().toString().trim();
                String dept = editTextdept.getText().toString().trim();

                if(TextUtils.isEmpty(name) && TextUtils.isEmpty(age) && TextUtils.isEmpty(dept)){
                    editTextname.setError("Name Recquired");
                }
                else {
                    updateUser(name, age, dept);
                    alertDialog.dismiss();
                    //Intent intent = new Intent(mContext, RecyclerViewActivity.class);
                    //mContext.startActivity(intent);
                }
            }
        });

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
        dialogBuilder.setTitle("Updating User");

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void updateUser(String name, String age, String dept){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(u_id);
        Users userss = new Users(age, dept, name, u_id);
        databaseReference.setValue(userss);

        //Toast.makeText(.this,"User updated Sucessfully", Toast.LENGTH_LONG).show();
    }
    private void deleteUser() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(u_id);

        databaseReference.removeValue();
        alertDialog.dismiss();
    }
}

