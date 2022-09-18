package com.example.mtn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_MESSAGE = null;
    EditText eNumber,ePin;
    Button SignIn;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eNumber = (EditText) findViewById(R.id.eNumber);
        ePin =(EditText) findViewById(R.id.ePin);
        SignIn = (Button) findViewById(R.id.SignIn);

        DB = new DBHelper(this);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = eNumber.getText().toString();
                String pass = ePin.getText().toString();

                Cursor signIn = DB.SignIn();
                if (user.equals("") || pass.equals(""))
                    Toast.makeText(MainActivity.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(pass)){
                        Boolean checkuser = DB.checkusername(user);
                        if (checkuser==true){
                            Boolean checkusernamepassword = DB.checkusernamepassword(user,pass);
                            if (checkusernamepassword==true){
                                Intent intent = new Intent(getApplicationContext(),success.class);
                                intent.putExtra(EXTRA_MESSAGE,user);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this,"Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this,"No such credentials! please sign up ",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"No DB", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}