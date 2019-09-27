package com.example.lapitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StarActivity extends AppCompatActivity {


    private Button mRegBtn;
    private Button mLogBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        mRegBtn = (Button) findViewById(R.id.start_reg_btn );
        mLogBtn = (Button)findViewById( R.id.start_login_btn );
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent reg_intent = new Intent(StarActivity.this,RegisterActivity.class);
                startActivity(reg_intent);

            }
        });
        mLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent log_intent = new Intent(StarActivity.this,LoginActivity.class);
                startActivity(log_intent);

            }
        });


        }
    }

