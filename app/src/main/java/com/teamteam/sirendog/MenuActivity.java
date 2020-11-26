package com.teamteam.sirendog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    Button btnEmergency;
    Button btnVideo;
    Button btnSafetyHouse;
    Button btnAppInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnEmergency = findViewById(R.id.btn_emergency_call);
        btnVideo = findViewById(R.id.btn_video);
        btnSafetyHouse = findViewById(R.id.btn_safety_house);
        btnAppInfo = findViewById(R.id.btn_app_info);

        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, EmergencyCallActivity.class));
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, VideoActivity.class));
            }
        });

        btnSafetyHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, IntroActivity.class));
            }
        });

         btnAppInfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MenuActivity.this, AppInfoActivity.class));
             }
         });

    }
}
