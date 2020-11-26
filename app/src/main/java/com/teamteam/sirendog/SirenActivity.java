package com.teamteam.sirendog;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SirenActivity extends AppCompatActivity {
    public static final int SIREN = R.raw.siren;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    private LinearLayout linearLayout;
    private boolean isRed = false;

    private static int streamId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren);

        linearLayout = findViewById(R.id.linear_layout);

        TimerTask timerTask = new TimerTask()
        {
            int count = 60;

            @Override
            public void run()
            {
                isRed = !isRed;
                linearLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout.setBackgroundColor(isRed ? Color.WHITE : Color.RED);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0 ,1000);

        initSounds(this);
        play(SIREN);
    }

    // sound media initialize
    public static void initSounds(Context context) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        soundPoolMap = new HashMap(2);
        soundPoolMap.put(SIREN, soundPool.load(context, SIREN, 1));
    }

    public static void play(int raw_id){
        if( soundPoolMap.containsKey(raw_id) ) {
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    streamId = soundPool.play(soundPoolMap.get(raw_id), 1, 1, 1, 1000, 1f);
                }
            });
        }
    }

    public static void stop(){
        soundPool.stop(streamId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }
}
