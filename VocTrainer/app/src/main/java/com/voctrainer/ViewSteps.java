package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ViewSteps extends AppCompatActivity implements View.OnClickListener {

    public Button btn_findArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui3_view_steps);
        this.setTitle("Schritte zaehlen"); //button_findArea

        btn_findArea = (Button) findViewById(R.id.button_findArea);
        btn_findArea.setText("Finde Fachgebiet");
        btn_findArea.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_findArea) {
            Intent intent = new Intent(ViewSteps.this, GeoMap.class);
            startActivity(intent);
            this.finish();
        }
    }
}