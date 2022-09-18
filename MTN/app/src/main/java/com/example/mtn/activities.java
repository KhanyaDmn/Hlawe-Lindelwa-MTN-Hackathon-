package com.example.mtn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class activities extends AppCompatActivity {
     Button Btn;
    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Btn = (Button) findViewById(R.id.tempBtn);
        DB = new DBHelper(this);


        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(activities.this,"No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Number:" + res.getString(0)+"\n");
                    buffer.append("Pin:" + res.getString(1)+"\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(activities.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });





    }


    public void details(){
        Intent intent  = new Intent(this, details.class);
        startActivity(intent);
    }


}