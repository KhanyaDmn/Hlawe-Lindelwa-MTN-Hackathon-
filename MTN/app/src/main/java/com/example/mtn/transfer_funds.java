package com.example.mtn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class transfer_funds extends AppCompatActivity {
     Button sendMoney;
     EditText tNumber,tMessage;
     CheckBox gen;
     TextView txtGenerator;
     DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_funds);
        sendMoney = (Button) findViewById(R.id.sendMoney);
        tNumber = (EditText) findViewById(R.id.txtNumber);
        tMessage = (EditText) findViewById(R.id.txtMessage);
        DB = new DBHelper(this);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView vvdate = findViewById(R.id.textView12);
        vvdate.setText(currentDate);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Random myRandom = new Random();
        gen = (CheckBox) findViewById(R.id.bntGen);
        final TextView txtGenerator = findViewById(R.id.txtGenerator);

        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(transfer_funds.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    ;
                    sendMessage();

                } else {
                    ActivityCompat.requestPermissions(transfer_funds.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtGenerator.setText(String.valueOf(myRandom.nextInt(100000)));

            }
        });

    }

    private void sendMessage() {
        String ePhone = tNumber.getText().toString().trim();
        String eMessage = tMessage.getText().toString().trim();
        if (!ePhone.equals("") && !eMessage.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(ePhone, null, eMessage,null,null);
            tMessage.setText("");
            tNumber.setText("");
            Toast.makeText(getApplicationContext(),"You have successfully transferred E" + eMessage.toString() + " to " + ePhone.toString() + ". Thank you for using MoMo Wallet", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"Enter all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void transfer_complete(){
        Intent intent  = new Intent(this, transfer_complete.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length >0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED ){
            sendMessage();
        }else{
            Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

}