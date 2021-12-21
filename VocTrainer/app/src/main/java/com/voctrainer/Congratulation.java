package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Congratulation extends AppCompatActivity implements View.OnClickListener{

    public Button btn_continue;
    public Button btn_correction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui14_congratulation);
        this.setTitle("Quiz beenden");

        btn_continue = (Button) findViewById(R.id.button_exit);
        btn_continue.setText("weiter");
        btn_continue.setOnClickListener(this);

        btn_correction = (Button) findViewById(R.id.button_correction);
        btn_correction.setText("Korrektur");
        btn_correction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_exit) {
            Intent intent = new Intent(Congratulation.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else if (v.getId() == R.id.button_correction) {
            Intent intent = new Intent(Congratulation.this, Correction.class);
            startActivity(intent);
            this.finish();
        }
    }
}