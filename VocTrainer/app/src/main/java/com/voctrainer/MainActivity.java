package com.voctrainer;

/*
    VocTrainer (Alpha-Version)
    12.01.2022
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.logging.Level;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btn_start;
    public Button btn_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button) findViewById(R.id.button_start);
        btn_start.setText("Deine Schritte zählen");
        btn_start.setOnClickListener(this);

        btn_help = (Button) findViewById(R.id.button_help);
        btn_help.setText("?");
        btn_help.setOnClickListener(this);
        Toast.makeText(getApplicationContext(),"MID Project: Voctrainer\n(Pre-Alpha-Version)", Toast.LENGTH_LONG).show();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button_start) {
            Intent intent = new Intent(MainActivity.this, MovingCounter.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button_help) {
            Intent intent = new Intent(MainActivity.this, Help.class);
            intent.putExtra("activity_id", 0); //MainActivity is ID = 0
            startActivity(intent);
        }
    }
}