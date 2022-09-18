package com.example.mtn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class transfer_complete extends AppCompatActivity {
    private Button okay;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_complete);
        okay = (Button) findViewById(R.id.okay);
        DB = new DBHelper(this);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success();
            }
        });

    }
    public void success(){
        Intent intent  = new Intent(this, success.class);
        startActivity(intent);
    }

}