package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MovingCounter extends AppCompatActivity implements View.OnClickListener{

    public Button btn_help;
    public Button btn_DEBUG_Skip_to_100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui2_moving_counter);
        this.setTitle("Schritte zählen");

        btn_help = (Button) findViewById(R.id.button_help);
        btn_help.setText("?");
        btn_help.setOnClickListener(this);

        btn_DEBUG_Skip_to_100 = (Button) findViewById(R.id.button_Debug_100Steps);
        btn_DEBUG_Skip_to_100.setText("Start");
        btn_DEBUG_Skip_to_100.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_help) {
        Intent intent = new Intent(MovingCounter.this, Help.class);
        intent.putExtra("activity_id", 1); //MainActivity is ID = 0
        startActivity(intent);
        }
        else if(v.getId() == R.id.button_Debug_100Steps) {
            Intent intent = new Intent(MovingCounter.this, ViewSteps.class);
            startActivity(intent);
            this.finish();
        }
    }
}