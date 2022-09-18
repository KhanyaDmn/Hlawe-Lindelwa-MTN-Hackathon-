package com.example.mtn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class success extends AppCompatActivity {
    private static final String EXTRA_MESSAGE = null;
    Button transfer;
     Button activit;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        transfer = (Button) findViewById(R.id.transfer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String user = intent.getStringExtra(EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView4);
        textView.setText(user);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView vdate = findViewById(R.id.textView9);
        vdate.setText(currentDate);

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                transfer_funds();
            }
        });
        activit = (Button) findViewById(R.id.activity);
        DB = new DBHelper(this);
        activit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(success.this,"No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                res.moveToNext();
                    buffer.append("Number:" + res.getString(0) + "\n");
                    buffer.append("Money: E" + res.getString(1) + "\n");
                    AlertDialog.Builder builder = new AlertDialog.Builder(success.this);
                    builder.setCancelable(true);
                    builder.setTitle("Transaction Details");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }

        });


    }
    public void transfer_funds(){
        Intent intent  = new Intent(this, transfer_funds.class);
        startActivity(intent);
    }




}