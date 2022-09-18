package com.example.ussdcodeapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityManagerCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call permission launcher
        ActivityResultLauncher<String> callPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {});

        //request call permission if is not granted
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            //request the call permission
            callPermissionLauncher.launch(Manifest.permission.CALL_PHONE);
        }

        //check airtime balance btn
        TextView airtimeBalanceBtn = findViewById(R.id.airtimeBalance);
        airtimeBalanceBtn.setOnClickListener(view -> runSimpleUSSDCode("*102#"));

        //check bundle balance btn
        TextView bundleBalanceBtn = findViewById(R.id.bundleBalance);
        bundleBalanceBtn.setOnClickListener(view -> runSimpleUSSDCode("*102*01#"));

        //mobile money btn
        TextView mobileMoneyBtn = findViewById(R.id.mobileMoney);
        mobileMoneyBtn.setOnClickListener(view -> runMulOpUSSDCode("*150*01#"));

        //SIM Card Registration status btn
        TextView registrationStatusBtn = findViewById(R.id.registrationStatus);
        registrationStatusBtn.setOnClickListener(view -> runSimpleUSSDCode("*106#"));

        //more services btn
        TextView availableServicesBtn = findViewById(R.id.availableServices);
        availableServicesBtn.setOnClickListener(view -> runMulOpUSSDCode("*147*00#"));

        //user custom input for USSD Code
        TextView openCustomCodeDialogBtn = findViewById(R.id.customDialogBtn);
        openCustomCodeDialogBtn.setOnClickListener(view -> openCustomUSSDCodeDialog());
    }

    private void openCustomUSSDCodeDialog() {
        //show custom input dialog
        View view = LayoutInflater.from(this).inflate(R.layout.custom_code, null);
        EditText codeBtn = view.findViewById(R.id.codeBtn);
        TextView runBtn = view.findViewById(R.id.runBtn);
        TextView cancelBtn = view.findViewById(R.id.cancelBtn);

        //build the dialog
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(view);
        final AlertDialog dialog = alertBuilder.show();

        //dismiss dialog on cancel btn
        cancelBtn.setOnClickListener(view1 -> dialog.dismiss());

        //run USSD code when runBtn is click
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String USSDCode = codeBtn.getText().toString();

                //USSD code starts * and ends with #
                if(!USSDCode.startsWith("*") && !USSDCode.endsWith("#")){
                    Toast.makeText(getApplicationContext(), "Enter a valid USSD code", Toast.LENGTH_SHORT).show();
                    return;
                }

                //run the USSD Code
                runMulOpUSSDCode(USSDCode);
                //dismiss the dialog
                dialog.dismiss();
            }
        });
    }

    //operations which expect more than one user inputs
    private void runMulOpUSSDCode(String USSDCode) {
        //encode the USSD String input to a valid USSD code
        //remove the last Character ie. # so that it can be encode, so *102# will be *102
        USSDCode = USSDCode.substring(0, USSDCode.length()-1);

        //encoding
        USSDCode = USSDCode + Uri.encode("#");

        //run the code
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+USSDCode));
        startActivity(intent);
    }

    private void runSimpleUSSDCode(String USSDCode){
    //
    }
}