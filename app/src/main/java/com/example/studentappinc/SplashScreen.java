package com.example.studentappinc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        result = getSharedPreferences("SaveData", MODE_PRIVATE);
        final int x = result.getInt("check",0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();/*
                if(x==0){
                Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                }
                else {
                    Intent intent= new Intent(getApplicationContext(),JudgeActivity.class);
                    startActivity(intent);
                    finish();


                }*/
            }
        },1000);

    }
}
