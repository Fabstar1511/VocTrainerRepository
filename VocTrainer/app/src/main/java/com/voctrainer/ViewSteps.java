package com.voctrainer;
/*
    Mobile Interaction Design - Group 5
    VocTrainer 0.1.1
    BETA vom 15.01.2022
*/

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewSteps extends AppCompatActivity {

    //Pattern is 2400sec long
    private final long[] vibrationPattern = {0, 250, 100, 250, 100, 250, 100, 250, 100, 1000}; //sleep, vibrate, sleep, vibrate;...

    private ImageView ivCheckin;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui3_view_steps);
        this.setTitle("Schritte zählen"); //button_findArea

        ivCheckin = (ImageView) findViewById(R.id.checkin);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notice100Steps();
            }
        }, 500);
    }

    private void notice100Steps(){
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(vibrationPattern, -1);
        final Handler handler = new Handler();
        ivCheckin.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToGeoMap();
            }
        }, 3500);
    }

    private void jumpToGeoMap(){
        vibrator.cancel();
        Intent intent = new Intent(ViewSteps.this, GeoMap.class);
        startActivity(intent);
        this.finish();
    }
    public void onBackPressed(){
    }
}