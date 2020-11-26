package com.teamteam.sirendog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    Button btn_video_1, btn_video_2, btn_video_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        btn_video_1 = (Button)findViewById(R.id.btn_video_1);
        btn_video_2 = (Button)findViewById(R.id.btn_video_2);
        btn_video_3 = (Button)findViewById(R.id.btn_video_3);

        btn_video_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=nVGpbz1LPWI"));
                startActivity(it);
            }
        });

        btn_video_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/0pLFwMC6nBw"));
                startActivity(it);
            }
        });

        btn_video_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/0DS_jJNALAw"));
                startActivity(it);
            }
        });
    }
}
